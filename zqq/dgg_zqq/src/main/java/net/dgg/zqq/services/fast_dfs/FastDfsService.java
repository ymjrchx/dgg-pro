package net.dgg.zqq.services.fast_dfs;

import org.csource.fastdfs.*;

/**
 * @author 刘阳
 * @ClassName <FastDfsService>
 * @despriction
 * @create 2018/08/14 17:40
 **/
public class FastDfsService extends NFastDFSUtil {
    private String fastDfsSeparator = "/";

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public Boolean deleteFile(String path) {
        try {
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            int i = storageClient.delete_file(path.substring(0, path.indexOf(fastDfsSeparator)), path.substring(path.indexOf(fastDfsSeparator) + 1));
            return i == 0 ? Boolean.TRUE : Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
