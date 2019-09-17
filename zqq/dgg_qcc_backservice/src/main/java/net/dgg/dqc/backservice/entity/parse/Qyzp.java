package net.dgg.dqc.backservice.entity.parse;

/**
 * 企业族谱查询-企业对外投资
 * Created by jiangsh on 2018/6/5 11:12
 */
public class Qyzp {
    private String KeyNo; //内部KeyNo
    private String Name; //公司名称
    private String No; //注册号
    private String CreditCode; //全国同一信用代码
    private String EconKind; //企业类型
    private String Status; //状态
    private double RegistCapi; //注册资本
    private String OperName; //法人
    private String FundedRatio; //出资比例
    private String StartDate; //出资日期

    public double getRegistCapi() {
        return RegistCapi;
    }

    public void setRegistCapi(double registCapi) {
        RegistCapi = registCapi;
    }

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

    public String getEconKind() {
        return EconKind;
    }

    public void setEconKind(String econKind) {
        EconKind = econKind;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getOperName() {
        return OperName;
    }

    public void setOperName(String operName) {
        OperName = operName;
    }

    public String getFundedRatio() {
        return FundedRatio;
    }

    public void setFundedRatio(String fundedRatio) {
        FundedRatio = fundedRatio;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
}
