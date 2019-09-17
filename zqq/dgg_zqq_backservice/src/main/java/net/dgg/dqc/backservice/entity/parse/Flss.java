package net.dgg.dqc.backservice.entity.parse;

/**
 * f法律诉讼
 * Created by jiangsh on 2018/8/17 09:42
 */
public class Flss {
    private String lDate; //日期
    private String lCaipanwenshu; //裁判文书
    private String lCaseReason; //案由
    private String lCaseIdentity; //案件身份
    private String lCaseNum; //案号
    private String lCaseType; //案件类型

    public String getlDate() {
        return lDate;
    }

    public void setlDate(String lDate) {
        this.lDate = lDate;
    }

    public String getlCaipanwenshu() {
        return lCaipanwenshu;
    }

    public void setlCaipanwenshu(String lCaipanwenshu) {
        this.lCaipanwenshu = lCaipanwenshu;
    }

    public String getlCaseReason() {
        return lCaseReason;
    }

    public void setlCaseReason(String lCaseReason) {
        this.lCaseReason = lCaseReason;
    }

    public String getlCaseIdentity() {
        return lCaseIdentity;
    }

    public void setlCaseIdentity(String lCaseIdentity) {
        this.lCaseIdentity = lCaseIdentity;
    }

    public String getlCaseNum() {
        return lCaseNum;
    }

    public void setlCaseNum(String lCaseNum) {
        this.lCaseNum = lCaseNum;
    }

    public String getlCaseType() {
        return lCaseType;
    }

    public void setlCaseType(String lCaseType) {
        this.lCaseType = lCaseType;
    }
}
