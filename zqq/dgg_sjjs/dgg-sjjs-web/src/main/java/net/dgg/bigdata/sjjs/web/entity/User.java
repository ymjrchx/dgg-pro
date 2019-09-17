package net.dgg.bigdata.sjjs.web.entity;

import java.math.BigDecimal;
import java.util.Date;

public class User {

    private String userId;
    private String username;
    private Date createtime;
    private String employeeNo;
    private Integer useErpPwd;
    private String loginPwd;
    private String remark;
    private Integer enabled;
    private Date lastUpdate;
    private String nickname;
    private String email;
    private String userCompany;
    private String userPosition;
    private String industry;

    private Integer type;// 用户类型 1 普通用户  2 会员/vip 用户  3 企业用户
    private Integer isMember;
    private Date memberExpirationdate;
    private java.sql.Date birthday;  //生日
    private Integer sex ; // 性别 1为男性  2为女性

    private String key; // 接口key
    private BigDecimal money; // 账户金额
    private Integer isInner; // 是否是内部用户 0 否  1 是
    private Date lastLoginDate; // 最近登录时间

    private Integer regist; // 是否注册 0 否  1 是 ， 快捷登录后的用户未注册的
    private Date vipEndTime; // vip会员有效截止时间
    private String status; //用户启用 禁用 状态
    private Integer flag ; // 已删除 正常

    public String getUserId() {
        return userId;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public Date getMemberExpirationdate() {
        return memberExpirationdate;
    }

    public void setMemberExpirationdate(Date memberExpirationdate) {
        this.memberExpirationdate = memberExpirationdate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getUseErpPwd() {
        return useErpPwd;
    }

    public void setUseErpPwd(Integer useErpPwd) {
        this.useErpPwd = useErpPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getIsInner() {
        return isInner;
    }

    public void setIsInner(Integer isInner) {
        this.isInner = isInner;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getRegist() {
        return regist;
    }

    public void setRegist(Integer regist) {
        this.regist = regist;
    }

    public Date getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(Date vipEndTime) {
        this.vipEndTime = vipEndTime;
    }


    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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