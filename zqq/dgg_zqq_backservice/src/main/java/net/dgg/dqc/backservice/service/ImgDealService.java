package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.constant.ImgDealResultConstant;
import net.dgg.dqc.backservice.dao_mongodb.TempEntityMongodbDao;
import net.dgg.dqc.backservice.entity.ImgDealResult;
import net.dgg.dqc.backservice.exception.ImgDealException;
import net.dgg.dqc.backservice.fast_dfs.FastDfsService;
import net.dgg.dqc.backservice.tempEntity.TempEntity;
import net.fblock.core.common.KeyWorker;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

/**
 * @author 刘阳
 * @ClassName <ImgDealService>
 * @despriction 图片处理程序
 * @create 2018/08/13 10:15
 **/
@Service
public class ImgDealService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer threadNum = 5;

    private String dirPath; // 开始路径
    private List<String> pathList = new ArrayList<>(500000); // 文件夹 下 子路径 集合
    private static final String dataSeparator = "@"; // 数据分隔符号
    private static final String jsonPathSeparator = "#"; // json 路径 分隔符号
    private static final String fileExtPathSeparator = ".";
    private static final String urlPathPathSeparatorReplace = "！";
    private static final String arrIndexLabelS = "[";
    private static final String arrIndexLabelE = "]";
    private static final String colonReplace = "~";
    private static final String colon = ":";
    private static final String urlPathPathSeparator = "/";
    private static final String urlStart = "http";
    private static final String questionMark = "?";
    private static final String questionMarkReplace = "；";

    private List<ImgDealResult> failDealResultList = new ArrayList<>();

    private AtomicInteger successCount = new AtomicInteger();
    private AtomicInteger failCount = new AtomicInteger();
    private AtomicInteger failDealCount = new AtomicInteger();


    // 图片字段 map
    private static Map<String, String> urlFieldNameMap = new HashMap<String, String>() {{
        //   put("sciUrl", "sciPath");// 人员图片信息 图片
        //   put("piUrl", "piPath");// 企业业务 图片
        //  put("jpiUrl", "jpiPath");// 竞品信息 图片
        //  put("piUrl", "piPath");// 产品信息 图片
        //  put("wciUrl", "wciPath");// 微信公众号头像 图片
        // put("wcciUrl", "wcciPath");//公众号二维码 图片
        // put("tmiUrl", "tmiPath");// 商标图片
        //put("tmiUrl", "tmiPath");// 商标图片
        put("clUrl", "clPath");// 商标图片
    }};


    @Autowired
    private ImgDealResultService imgDealResultService;
    @Autowired
    private TempEntityMongodbDao tempEntityMongodbDao;
    @Autowired
    private FastDfsService fastDfsService;
    @Autowired
    private ThreadImgDealService threadImgDealService;

    /**
     * 开始处理
     */

    public void startDeal() {
        successCount.set(0);
        failCount.set(0);
        int all = this.pathList.size();
        int threadNum = all <= this.threadNum ? 1 : this.threadNum;
        int partNum = all / threadNum + (all % threadNum == 0 ? 0 : 1);
        int finalThreadNum = all / partNum + (all % partNum == 0 ? 0 : 1);
        for (int i = 0; i < finalThreadNum; i++) {
            int startIndex = i * partNum, endIndex = startIndex + partNum - 1;
            endIndex = Math.min(all - 1, endIndex);
            // System.out.println(String.format("%s: %s-%s", i + 1, startIndex, endIndex));
            threadImgDealService.threadImgDeal(startIndex, endIndex);
        }
    }

    /**
     * 处理失败记录
     */
    public Map startDealFailRecord() {
        successCount.set(0);
        failCount.set(0);
        Integer all = this.imgDealResultService.countFailResult();
        failDealCount.set(all);

        int threadNum = all <= this.threadNum ? 1 : this.threadNum;
        int partNum = all / threadNum + (all % threadNum == 0 ? 0 : 1);
        int finalThreadNum = all / partNum + (all % partNum == 0 ? 0 : 1);
        for (int i = 0; i < finalThreadNum; i++) {
            int startIndex = i * partNum, endIndex = startIndex + partNum - 1;
            endIndex = Math.min(all - 1, endIndex);
            //  System.out.println(String.format("%s： limit %s , %s  ", i + 1, startIndex, partNum));
            threadImgDealService.threadDealFailRecord(startIndex, partNum);
        }
        Map reMap = new HashMap();
        reMap.put("all", all);

        return reMap;
    }

    /**
     * 处理 分段
     *
     * @param startIndex
     * @param endIndex
     */
    public void dealFailRecordIndexStartTo(int startIndex, int endIndex) {
        List<ImgDealResult> imgDealResultList = this.imgDealResultService.getFailResult(startIndex, endIndex);
        for (ImgDealResult imgDealResult : imgDealResultList) {
            this.dealFailRecord(imgDealResult);
            logger.info(Thread.currentThread().getName() + String.format(" :  %s / %s", this.successCount.intValue() + this.failCount.intValue(), this.failDealCount.intValue()));
        }
    }

    /**
     * 处理 单个记录
     *
     * @param imgDealResult
     */
    public void dealFailRecord(ImgDealResult imgDealResult) {
        if (StringUtils.isEmpty(imgDealResult.getDirPath()) || StringUtils.isEmpty(imgDealResult.getSonPath())) {
            return;
        }
        try {
            this.dealPath(imgDealResult.getDirPath(), imgDealResult.getSonPath());

            imgDealResult.setResult(ImgDealResultConstant.SUCCESS);
            imgDealResult.setReason(null);
            imgDealResult.setBytes(null);
            imgDealResult.setUpdateTime(new Date());
            this.successCount.incrementAndGet();
        } catch (Exception e) {
            //e.printStackTrace();
            ImgDealException imgDealException = null;

            // 从文件服务器删除
            if (e instanceof ImgDealException &&
                    StringUtils.isNotEmpty((imgDealException = (ImgDealException) e).getFastDfsPath())) {
                fastDfsService.deleteFile(imgDealException.getFastDfsPath());
            }

            imgDealResult.setResult(ImgDealResultConstant.EXCEPTION);
            imgDealResult.setReason(e.getMessage());
            imgDealResult.setUpdateTime(new Date());
            if (imgDealResult.getBytes() == null && new File(imgDealResult.getFullPath()).exists()) {
                imgDealResult.setBytes(this.fastDfsService.fileToByte(imgDealResult.getFullPath()));
            }
            this.failCount.incrementAndGet();
        }
        this.imgDealResultService.updateResult(imgDealResult);
    }

    /**
     * 处理
     *
     * @param startIndex
     * @param endIndex
     */
    public void dealIndexStartTo(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            this.dealIndex(i);
            logger.info(Thread.currentThread().getName() + String.format(" :  %s / %s", this.successCount.intValue() + this.failCount.intValue(), this.pathList.size()));
        }
    }

    /**
     * 处理 索引位置
     *
     * @param index
     */
    public void dealIndex(int index) {
        String sonPath = this.pathList.get(index);
        try {
            // 处理
            String fastDfsPath = this.dealPath(this.dirPath, sonPath);

            // 创建成功记录
            this.createRecord(dirPath, sonPath, fastDfsPath, ImgDealResultConstant.SUCCESS, null);
            successCount.incrementAndGet();
        } catch (ImgDealException e) {
            e.printStackTrace();
            // 创建记录
            this.createRecord(dirPath, sonPath, e.getFastDfsPath(), ImgDealResultConstant.EXCEPTION, e.getMessage());
            // 从文件服务器删除
            if (StringUtils.isNotEmpty(e.getFastDfsPath())) {
                fastDfsService.deleteFile(e.getFastDfsPath());
            }

            failCount.incrementAndGet();
            // throw e;
        } catch (Exception e) {
            e.printStackTrace();
            // 创建记录
            this.createRecord(dirPath, sonPath, null, ImgDealResultConstant.EXCEPTION, e.getMessage());
            failCount.incrementAndGet();
            // throw e;
        }
    }


    // 处理 单个
    public String dealPath(String dirPath, String sonPath) {
        String fullPath = dirPath.concat(sonPath);
        File file = new File(fullPath);
        Assert.isTrue(file.exists(), "文件不存在！");
        String fileName = this.getFileName(fullPath);
        String fileExtName = this.getFileExtName(fileName);
        String jsonId = this.getJsonId(fileName);
        String[] jsonPath = this.getJsonPath(fullPath);
        Assert.isTrue(jsonPath != null && jsonPath.length > 0, "Json属性路径不能为空！");

        String fastDfsPath = null;
        try {
            // 上传服务器

            fastDfsPath = this.fastDfsService.upload(this.fastDfsService.fileToByte(file.getAbsolutePath()), fileName, fileExtName);
            Assert.hasText(fastDfsPath, "文件上传失败！");

            // 查询 mongo
            TempEntity tempEntity = tempEntityMongodbDao.findOne(jsonId);
            Assert.notNull(tempEntity, "mongo中未查询到此数据");
            // 更新 mongo数据
            this.updateMongo(tempEntity, jsonPath, fileName, fastDfsPath);

            return fastDfsPath;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ImgDealException(e.getMessage()).setFastDfsPath(fastDfsPath);
        }


    }


    // 更新 mongo数据
    private void updateMongo(TempEntity tempEntity, String[] jsonPath, String fileName, String val) {
        this.updateEntityByJsonPath(tempEntity, jsonPath, fileName, val);
        this.tempEntityMongodbDao.save(tempEntity);
    }

    // 遍历实体 更新属性
    private void updateEntityByJsonPath(TempEntity tempEntity, String[] jsonPath, String fileName, String val) {
        Field field = null;
        Object curEntity = tempEntity;
        for (String attr : jsonPath) {
            Assert.hasText(attr, "json属性不能为空！");
            field = this.getField(curEntity, attr);
            Assert.notNull(field, String.format("%s 类没有 %s 属性", curEntity.getClass().getSimpleName(), attr));
            field.setAccessible(true);
            try {
                Object parent = curEntity;
                curEntity = field.get(curEntity);
                Assert.notNull(curEntity, String.format("%s 对象 %s 属性为空！ ", parent.getClass().getSimpleName(), attr));
                Integer index = null;
                // List or Object[]
                if ((curEntity instanceof List || curEntity instanceof Object[]) && attr.contains(arrIndexLabelS) && attr.contains(arrIndexLabelE)) {
                    String indexStr = attr.substring(attr.indexOf(arrIndexLabelS) + 1, attr.indexOf(arrIndexLabelE));
                    Assert.hasText(indexStr, String.format("%s 对象 %s 属性 数组序号提取失败！ ", parent.getClass().getSimpleName(), attr));
                    index = Integer.valueOf(indexStr);
                    Assert.notNull(index, String.format("%s 对象 %s 属性 数组序号转换失败！ ", parent.getClass().getSimpleName(), attr));
                }
                // 提取 list 或 数组中元素
                if (curEntity instanceof List && index != null) {
                    List list = (List) curEntity;
                    Assert.isTrue(list.size() > index, String.format("%s 对象 %s 属性 数组序号越界！ ", parent.getClass().getSimpleName(), attr));
                    curEntity = list.get(index);
                    Assert.notNull(curEntity, String.format("%s 对象 %s 数组元素为空！ ", parent.getClass().getSimpleName(), attr));
                }
                if (curEntity instanceof Object[] && index != null) {
                    Object[] arr = (Object[]) curEntity;
                    Assert.isTrue(arr.length > index, String.format("%s 对象 %s 属性 数组序号越界！ ", parent.getClass().getSimpleName(), attr));
                    curEntity = arr[index];
                    Assert.notNull(curEntity, String.format("%s 对象 %s 数组元素为空！ ", parent.getClass().getSimpleName(), attr));
                }

            } catch (IllegalAccessException e) {
                Assert.isTrue(false, String.format("%s 类的 %s 属性 反射取值失败！", curEntity.getClass().getSimpleName(), attr));
            }
        }
        // 查找图片 所在实体 并更新实体数据
        this.findAndUpdateField(curEntity, fileName, val);

    }


    // 查找图片 所在实体对象 并更新实体数据
    private void findAndUpdateField(Object obj, String fileName, Object val) {
        // 还原图片 url
        String url = fileName.substring(fileName.indexOf(this.urlStart))
                .replaceFirst(this.colonReplace, this.colon)
                .replaceAll(this.urlPathPathSeparatorReplace, this.urlPathPathSeparator)
                .replaceAll(this.questionMarkReplace, this.questionMark);

        // 查找对象
        Object curObj = null;
        try {
            curObj = this.findObj(obj, url);
        } catch (IllegalAccessException e) {
            Assert.isTrue(false, "查找url所在对象时报错！" + e.getMessage());
        }
        Assert.notNull(curObj, String.format(" %s 类下未找到url所在对象！", obj.getClass().getSimpleName()));

        // 查找存储 本地服务器文件地址 的 字段
        Field field = this.findField(curObj);
        Assert.notNull(field, String.format("%s 类没有 存储本地服务器文件地址 的字段", curObj.getClass().getSimpleName()));

        // 更新数据
        field.setAccessible(true);
        try {
            field.set(curObj, val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.isTrue(false, String.format("%s 类的 %s 属性反射更新失败！", curObj.getClass().getSimpleName(), field.getName()));
        }
    }

    // 查找存放 本地服务器文件地址 的 字段
    private Field findField(Object obj) {
        for (String attr : this.urlFieldNameMap.values()) {
            Field field = this.getField(obj, attr);
            if (field != null) {
                return field;
            }
        }
        return null;
    }

    // 查找 url 所在实体对象
    private Object findObj(Object obj, String url) throws IllegalAccessException {
        if (this.stopFind(obj)) {
            return null;
        }
        if (obj instanceof List) {
            for (Object temp : (List) obj) {
                Object tempCur = this.findObj(temp, url);
                if (tempCur != null) {
                    return tempCur;
                }
            }
        }
        if (obj instanceof Object[]) {
            for (Object temp : (Object[]) obj) {
                Object tempCur = this.findObj(temp, url);
                if (tempCur != null) {
                    return tempCur;
                }
            }
        }
        // 当 obj 为 普通对象
        Field[] fieldArr = obj.getClass().getDeclaredFields();
        List<Object> findList = new ArrayList<>();
        for (Field field : fieldArr) {
            field.setAccessible(true);
            Object val = field.get(obj);
            if (val == null) {
                continue;
            }
            // 找到 url 所在的对象
            if (val instanceof String && this.urlFieldNameMap.keySet().contains(field.getName()) && url.equals(val)) {
                return obj;
            }
            // 保存 准备 遍历
            if (!this.stopFind(val)) {
                findList.add(val);
            }
        }
        // 继续向下遍历
        for (Object temp : findList) {
            Object tempCur = this.findObj(temp, url);
            if (tempCur != null) {
                return tempCur;
            }
        }
        return null;
    }

    // 是否停止递归
    private boolean stopFind(Object obj) {
        return obj instanceof String || obj instanceof Integer || obj instanceof Long || obj instanceof Boolean || obj instanceof Date ||
                obj instanceof Double || obj instanceof Float || obj instanceof BigDecimal || obj instanceof BigInteger ||
                obj instanceof Byte || obj instanceof Character || obj instanceof Short;
    }

    // 反射获取属性
    private Field getField(Object obj, String attr) {
        if (obj == null || StringUtils.isEmpty(attr)) {
            return null;
        }
        try {
            return obj.getClass().getDeclaredField(attr.contains(arrIndexLabelS) && attr.contains(arrIndexLabelE)
                    ? attr.substring(0, attr.indexOf(arrIndexLabelS)) : attr);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    // 获取json 路径数组
    private String[] getJsonPath(String fullPath) {
        return StringUtils.isEmpty(fullPath) ? null : fullPath.substring(fullPath.indexOf(this.dataSeparator) + 1, fullPath.indexOf(this.dataSeparator, fullPath.indexOf(this.dataSeparator) + 1)).split(this.jsonPathSeparator);
    }

    // 提取文件名名称中的 mongoId
    private String getJsonId(String fileName) {
        return StringUtils.isEmpty(fileName) ? null : fileName.substring(0, fileName.indexOf(this.dataSeparator));
    }

    // 提取文件扩展名
    private String getFileExtName(String fileName) {
        return StringUtils.isEmpty(fileName) || fileName.lastIndexOf(this.fileExtPathSeparator) < fileName.lastIndexOf(this.urlPathPathSeparatorReplace) ? null : fileName.substring(fileName.lastIndexOf(this.fileExtPathSeparator) + 1);
    }

    // 提取文件名
    private String getFileName(String fullPath) {
        return StringUtils.isEmpty(fullPath) ? null : fullPath.substring(fullPath.lastIndexOf(File.separatorChar) + 1);
    }


    // 递归获取 所有文件
    private void getAllFile(List<String> list, String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            list.add(file.getPath().replace(dirPath, ""));
            return;
        }
        if (file.isDirectory()) {
            String[] sonNameArr = file.list();
            for (String p : sonNameArr) {
                getAllFile(list, path.concat("\\").concat(p));
            }
        }
    }

    // 获取文件夹  路径
    public void setDirPath(String path) {
        path = path.replaceAll(Matcher.quoteReplacement("\\\\"), File.separator.equals("\\") ? "\\" : File.separator);
        path = path.replaceAll(Matcher.quoteReplacement("//"), File.separator.equals("\\") ? "\\" : File.separator);
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        this.dirPath = file.getAbsolutePath();
        if (file.isDirectory()) {
            this.dirPath = this.dirPath.concat(File.separator);
        }
        this.pathList.clear();
        getAllFile(pathList, path);
    }

    public List<String> getPathList() {
        return pathList;
    }

    public String getDirPath() {
        return dirPath;
    }

    // 创建记录
    @Transactional
    public void createRecord(String dirPath, String sonPath, String fastDfsPath, Integer result, String reason) {
        ImgDealResult imgDealResult = new ImgDealResult();
        imgDealResult.setId(KeyWorker.nextId());
        imgDealResult.setDirPath(dirPath);
        imgDealResult.setSonPath(sonPath);
        imgDealResult.setFastDfsPath(fastDfsPath);
        imgDealResult.setFullPath(dirPath.concat(sonPath));
        imgDealResult.setFileName(this.getFileName(imgDealResult.getFullPath()));
        imgDealResult.setJsonId(this.getJsonId(imgDealResult.getFileName()));
        imgDealResult.setReason(reason);
        imgDealResult.setResult(result);
        imgDealResult.setCreateTime(new Date());
        // 失败将文件放入数据库
        if (!ImgDealResultConstant.SUCCESS.equals(result) && new File(imgDealResult.getFullPath()).exists()) {
            imgDealResult.setBytes(this.fastDfsService.fileToByte(imgDealResult.getFullPath()));
        }
        this.imgDealResultService.save(imgDealResult);
    }


    public AtomicInteger getSuccessCount() {
        return successCount;
    }

    public AtomicInteger getFailCount() {
        return failCount;
    }


}
