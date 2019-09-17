package net.dgg.gspt.dqc.dto.imgidentify;

/**
 * @ClassName: BusinessLicense
 * @Description: 营业执照传输对象
 * @Author: jiangsh
 * @Date: 2018/11/14 17:17
 */
public class BusinessLicense {

    private String regCapital; //注册资本
    private String socialCredit; //社会信用代码
    private String org; //单位名称
    private String legalPerson; //法人
    private String no; //证件编号
    private String startDate; //成立日期
    private String address; //地址
    private String endDate; //有效期

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getSocialCredit() {
        return socialCredit;
    }

    public void setSocialCredit(String socialCredit) {
        this.socialCredit = socialCredit;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
