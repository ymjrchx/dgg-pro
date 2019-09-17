package net.dgg.bigdata.sjjs.web.entity.dto.search.senior;

/**
 * @ClassName: TurnClues
 * @Description: 转线索信息
 * @Author: jiangsh
 * @Date: 2018/12/17 11:39
 */
public class TurnClues {

    private String companyName; //企业名称
    private String companyUrl; //企业官网
    private String regMoney; //注册资本
    private String regTime; //成立时间
    private String contactPerson; //联系人
    private String turnCluesTimes; //转线索次数


    @Override
    public String toString() {
        return "{" +
                "companyName='" + companyName + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                ", regMoney='" + regMoney + '\'' +
                ", regTime='" + regTime + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", turnCluesTimes='" + turnCluesTimes + '\'' +
                '}';
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(String regMoney) {
        this.regMoney = regMoney;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getTurnCluesTimes() {
        return turnCluesTimes;
    }

    public void setTurnCluesTimes(String turnCluesTimes) {
        this.turnCluesTimes = turnCluesTimes;
    }
}
