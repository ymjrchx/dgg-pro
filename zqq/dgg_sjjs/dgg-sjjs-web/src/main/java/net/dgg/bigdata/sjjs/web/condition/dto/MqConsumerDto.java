package net.dgg.bigdata.sjjs.web.condition.dto;

/**
 * @Auther: 陈万国
 * @Date: 2019/1/2 10:33
 * @Description:
 */
public class MqConsumerDto {
    private String companyId;       //公司id
    private String buyUser;         //购买者工号
    private String status;          //状态
    private String companyName;     //公司名称
    private String reason;          //原因
    private String userName;        //购买者名称
    private String userDept;        //购买者部门

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(String buyUser) {
        this.buyUser = buyUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }
}
