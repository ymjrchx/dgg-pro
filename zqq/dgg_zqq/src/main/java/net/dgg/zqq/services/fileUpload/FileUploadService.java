package net.dgg.zqq.services.fileUpload;

import net.dgg.zqq.component.SimsunFontBean;
import net.dgg.zqq.dao.fileUpload.FileUploadRecordDao;
import net.dgg.zqq.entity.fileUpload.FileUploadRecord;
import net.dgg.zqq.services.fast_dfs.FastDfsService;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author 刘阳
 * @ClassName <FileUploadService>
 * @despriction 文件上传
 * @create 2018/10/10 11:41
 **/
@Service
public class FileUploadService {
    @Value("${fastdfs.perview_host}")
    private String host;

    @Autowired
    private FastDfsService fastDfsService;
    @Autowired
    private FileUploadRecordDao fileUploadRecordDao;
    @Autowired
    private SimsunFontBean simsunFontBean;

    // 留边
    private Integer padding = 12;

    @Transactional
    public List<Map> upload(String type, String dataId, MultipartFile... files) {
        Assert.hasText(type, "文件用途不能为空！");
        Assert.isTrue(files.length > 0, "文件不能为空！");

        Map<MultipartFile, byte[]> fileByteMap = new HashMap<>(files.length);
        for (MultipartFile file : files) {
            Assert.isTrue(!file.isEmpty(), "不能上传空文件！");
            byte[] bytes = this.multipartFileToByte(file);
            Assert.isTrue(bytes != null && bytes.length > 0, String.format("%s文件获取字节数组失败！", file.getOriginalFilename()));
            fileByteMap.put(file, bytes);
        }
        // 上传
        Map<String, String> pathNameMap = this.bathUpload(fileByteMap);

        // 创建 文件上传记录
        this.createFileUploadRecord(type, pathNameMap, dataId);
        List<Map> reMapList = new ArrayList(files.length);
        for (Map.Entry<String, String> entry : pathNameMap.entrySet()) {
            reMapList.add(this.getResultMap(entry.getValue(), entry.getKey()));
        }
        return reMapList;
    }

    // 批量上传 保证 上传 全部成功或全部失败， 一个失败 全部失败
    private Map<String, String> bathUpload(Map<MultipartFile, byte[]> fileByteMap) {
        Map<String, String> pathNameMap = new HashMap<>();
        try {
            for (Map.Entry<MultipartFile, byte[]> entry : fileByteMap.entrySet()) {
                String uploadPath = fastDfsService.upload(entry.getValue(), entry.getKey().getOriginalFilename(), this.getExtName(entry.getKey().getOriginalFilename()));
                Assert.hasText(uploadPath, String.format("%s文件上传失败", entry.getKey().getOriginalFilename()));
                pathNameMap.put(uploadPath, entry.getKey().getOriginalFilename());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 失败 全部从文件服务器删除
            for (String path : pathNameMap.keySet()) {
                this.fastDfsService.deleteFile(path);
            }
            throw e;
        }
        return pathNameMap;
    }

    private byte[] multipartFileToByte(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map getResultMap(String name, String uploadPath) {
        Map reMap = new HashMap();
        reMap.put("name", name);
        reMap.put("fsPath", uploadPath);
        reMap.put("fsHost", this.host);
        return reMap;
    }

    // 获取文件扩展名
    private String getExtName(String name) {
        String dot = ".";
        if (StringUtils.isEmpty(name) || name.indexOf(dot) < 0) {
            return null;
        }
        return name.substring(name.lastIndexOf(dot) + 1, name.length());
    }

    /**
     * 创建数据记录
     *
     * @param type
     * @param pathNameMap
     */
    private void createFileUploadRecord(String type, Map<String, String> pathNameMap, String dataId) {
        Date date = new Date();
        for (Map.Entry<String, String> entry : pathNameMap.entrySet()) {
            FileUploadRecord fileUploadRecord = new FileUploadRecord();
            fileUploadRecord.setId(KeyWorker.nextId());
            fileUploadRecord.setCreateTime(date);
            fileUploadRecord.setFsHost(this.host);
            fileUploadRecord.setFsPath(entry.getKey());
            fileUploadRecord.setType(type);
            fileUploadRecord.setName(entry.getValue());
            fileUploadRecord.setType(type);
            fileUploadRecord.setDataId(dataId);
            this.fileUploadRecordDao.save(fileUploadRecord);
        }
    }

    /**
     * 创建图标
     *
     * @param userId
     * @param text
     * @param imgWidth
     * @param imgHeight
     * @param type
     */
    @Transactional
    public Map createTrademark(String userId, String text, Integer imgWidth, Integer imgHeight, String type) {
        //Assert.hasText(userId, "用户ID不能为空！");
        Assert.hasText(text, "商标文本不能为空！");
        Assert.notNull(imgWidth, "商标宽度不能为空！");
        Assert.isTrue(imgWidth.intValue() > 0, "商标宽度应大于0");
        Assert.notNull(imgHeight, "商标高度不能为空！");
        Assert.isTrue(imgHeight.intValue() > 0, "商标高度应大于0");
        Assert.isTrue(imgHeight.intValue() <= imgWidth.intValue(), "商标高度应不小于宽度！");
        Assert.hasText(type, "文件用途不能为空！");


        String imgType = "jpg";
        String name = text.concat(".").concat(imgType);

        byte[] bytes = this.createImgByte(text, imgWidth, imgHeight, imgType);
        Assert.notNull(bytes, "图片创建失败!");
        Assert.isTrue(bytes.length > 0, "图片创建失败!");

        String path = this.fastDfsService.upload(bytes, name, imgType);

        // 创建文件记录
        this.createFileUploadRecord(type, new HashMap() {{
            put(path, name);
        }}, null);

        return this.getResultMap(name, path);

    }

    // 字符位置计算 理解  https://www.oschina.net/uploads/doc/javase-6-doc-api-zh_CN/java/awt/FontMetrics.html
    // 创建字节数组
    private byte[] createImgByte(String text, Integer imgWidth, Integer imgHeight, String imgType) {
        BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = buffImg.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.fillRect(0, 0, imgWidth, imgHeight);//填充整个屏幕

        int len = text.length();
        int s = (imgWidth - this.padding * 2) / len;

        Font font = this.simsunFontBean.getFont(Font.BOLD, new Float(s).floatValue());
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);

        int AS = metrics.getAscent(), BE = metrics.getDescent(), w = metrics.stringWidth(text), leading = metrics.getLeading();

        int left = (imgWidth - this.padding * 2 - w) / 2;
        int top = imgHeight / 2 + (AS - ((AS + BE) / 2)) - (leading / 2);

        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(metrics.getFont());
        g2.setColor(Color.BLACK);
        g2.drawString(text, left + this.padding, top);
        g2.dispose();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(buffImg, imgType, os);
            return os.toByteArray();//从流中获取数据数组。

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    /**
     * group 地址
     *
     * @param groupPath
     */
    @Transactional
    public void delete(String groupPath) {
        Assert.hasText(groupPath, "文件group地址不能为空！");
        List<FileUploadRecord> fileUploadRecords = fileUploadRecordDao.query(new HashMap() {{
            put("fsPath", groupPath);
        }});
        for (FileUploadRecord record : fileUploadRecords) {
            this.fileUploadRecordDao.deleteById(record.getId());
        }
        this.fastDfsService.deleteFile(groupPath);
    }
}
