package net.dgg.dqc.backservice.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author 刘阳
 * @ClassName <ImgDealResult>
 * @despriction 图片处理结果实体
 * @create 2018/08/13 16:18
 **/
@Document(collection = "temp_col")
public class ImgDealResult extends BaseEntity implements Serializable {

    /**
     * jsonId
     */
    String jsonId;
    /**
     * 文件名
     */
    String fileName;

    /**
     * 大文件夹路径
     */
    private String dirPath;

    /**
     * 文件子路径
     */
    private String sonPath;

    /**
     * 完成路径
     */
    private String fullPath;

    /**
     * 处理结果 0 成功  1 失败  2 出现异常
     */
    private Integer result;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 服务器路径
     */
    private String fastDfsPath;

    /**
     * 文件字节
     */
    private byte[] bytes;


    public String getJsonId() {
        return jsonId;
    }

    public void setJsonId(String jsonId) {
        this.jsonId = jsonId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getSonPath() {
        return sonPath;
    }

    public void setSonPath(String sonPath) {
        this.sonPath = sonPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFastDfsPath() {
        return fastDfsPath;
    }

    public void setFastDfsPath(String fastDfsPath) {
        this.fastDfsPath = fastDfsPath;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
