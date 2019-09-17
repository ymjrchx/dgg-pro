package net.dgg.zqq.entity.template;

import net.dgg.zqq.entity.BaseEntity;

public class Template extends BaseEntity {

    /**
     * 用模板类型编码
     */
        private String templateType;
    /**
     * 模板类型名称
     */
    private String templateTypeName;

    /**
     * 模板名称
     */
    private String templateName;

    /*
     * 文件名称
     */
    private String fileName;

    /*
     *  文件路径
     */
    private String fileUrl;

    /*
     * 状态
     */
    private String status;

    /*
     * 0删除  1正常
     */
    private Integer flag;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
