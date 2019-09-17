package dgg.net.entity;

import java.util.Date;

public class PopularizeRecord {
    private Long id;

    private String popularizeName;

    private String code;

    private String popularizeUrl;

    private String busType;

    private String area;

    private String channel;

    private String haveForm;

    private String dept;

    private String userName;

    private Date createTime;

    private Long createrId;

    private Long createrOrgId;

    private String createrName;

    private Date updateTime;

    private Long updaterId;

    private Long updaterOrgId;

    private String updaterName;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPopularizeName() {
        return popularizeName;
    }

    public void setPopularizeName(String popularizeName) {
        this.popularizeName = popularizeName == null ? null : popularizeName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPopularizeUrl() {
        return popularizeUrl;
    }

    public void setPopularizeUrl(String popularizeUrl) {
        this.popularizeUrl = popularizeUrl == null ? null : popularizeUrl.trim();
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getHaveForm() {
        return haveForm;
    }

    public void setHaveForm(String haveForm) {
        this.haveForm = haveForm == null ? null : haveForm.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getCreaterOrgId() {
        return createrOrgId;
    }

    public void setCreaterOrgId(Long createrOrgId) {
        this.createrOrgId = createrOrgId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName == null ? null : createrName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Long getUpdaterOrgId() {
        return updaterOrgId;
    }

    public void setUpdaterOrgId(Long updaterOrgId) {
        this.updaterOrgId = updaterOrgId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName == null ? null : updaterName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}