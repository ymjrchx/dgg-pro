package net.dgg.tmd.foundation.platform.user.entity;

import java.util.Date;

/**
 * 用户实体类
 */
public class UserEntity {
    //用户id
    private long userId;

    //帐号是否被锁定:0、正常；1、锁定；2、离职；默认：0、正常
    private int locked;

    //用户登录名，也是用户的工号
    private String loginName;

    //登录密码密文
    private String loginPwd;

    //用户真实姓名
    private String realName;

    //电话
    private String phone;

    //资源组织架构ID
    private long orgId;

    //性别
    private String sex;

    //备注
    private String description;

    //工作区域
    private String workarea;

    //邮箱地址
    private String email;

    //离职时间
    private Date dimissiontime;

    //最后一次更新时间
    private Date updatetime;

    //入职时间
    private Date entrydate;

    //工龄
    private int workage;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkarea() {
        return workarea;
    }

    public void setWorkarea(String workarea) {
        this.workarea = workarea;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDimissiontime() {
        return dimissiontime;
    }

    public void setDimissiontime(Date dimissiontime) {
        this.dimissiontime = dimissiontime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public int getWorkage() {
        return workage;
    }

    public void setWorkage(int workage) {
        this.workage = workage;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", locked=" + locked +
                ", loginName='" + loginName + '\'' +
                ", loginPwd='" + loginPwd + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", orgId=" + orgId +
                ", sex='" + sex + '\'' +
                ", description='" + description + '\'' +
                ", workarea='" + workarea + '\'' +
                ", email='" + email + '\'' +
                ", dimissiontime=" + dimissiontime +
                ", updatetime=" + updatetime +
                ", entrydate=" + entrydate +
                ", workage=" + workage +
                '}';
    }
}
