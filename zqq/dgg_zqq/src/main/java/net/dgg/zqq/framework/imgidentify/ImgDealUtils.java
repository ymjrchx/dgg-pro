package net.dgg.zqq.framework.imgidentify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName: ImgDealUtils
 * @Description: 图片处理工具
 * @Author: jiangsh
 * @Date: 2018/11/15 18:33
 */
public class ImgDealUtils {

    public static String getTmpPath(String filePath) throws Exception {
        download(filePath, fileNameDeal(filePath), getSavePath());
        return getSavePath().concat(File.separator).concat(fileNameDeal(filePath));
    }

    public static String getSavePath() {
        return File.separator.concat("ocrimg");
    }


    public static String fileNameDeal(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    /**
     * 下载图片至本地
     *
     * @param urlString 图片url地址
     * @param filename 文件名
     * @param savePath 保存路径
     * @throws Exception
     */
    public static String download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + File.separator + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
        return filename;
    }

    public static void delete(String path) {
        File f = new File(path);
        if (f.exists())
            f.delete();
    }
}
