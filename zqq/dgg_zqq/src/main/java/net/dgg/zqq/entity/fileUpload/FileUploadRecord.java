package net.dgg.zqq.entity.fileUpload;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <fileUploadRecord>
 * @despriction 网站文件上传记录
 * @create 2018/10/10 14:48
 **/
public class FileUploadRecord extends BaseEntity {
    /**
     * 文件名
     */
    private String name;

    /**
     * 服务器地址
     */
    private String fsHost;

    /**
     * group 地址
     */
    private String fsPath;


    /**
     * 上传图片的功能或者用途分类
     */
    private String type;

    /**
     * 图片绑定的业务数据ID
     */
    private String dataId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFsHost() {
        return fsHost;
    }

    public void setFsHost(String fsHost) {
        this.fsHost = fsHost;
    }

    public String getFsPath() {
        return fsPath;
    }

    public void setFsPath(String fsPath) {
        this.fsPath = fsPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
