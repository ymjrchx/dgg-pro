package net.dgg.dqc.backservice.entity.parse.qygs;

/**
 * 企业工商列表（关键字模糊查询）
 * Created by jiangsh on 2018/6/4 16:13
 */
public class Qygs {
    private String KeyNo;
    private String Name;
    private String OperName;
    private String StartDate;
    private String Status;
    private String No;
    private String CreditCode;

    public String getKeyNo() {
        return KeyNo;
    }

    public void setKeyNo(String keyNo) {
        KeyNo = keyNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOperName() {
        return OperName;
    }

    public void setOperName(String operName) {
        OperName = operName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String creditCode) {
        CreditCode = creditCode;
    }
}
