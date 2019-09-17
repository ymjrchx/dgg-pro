package net.dgg.dqc.backservice.entity.parse;

/**
 * 企业族谱查询-企业对外投资
 * Created by jiangsh on 2018/6/5 11:12
 */
public class Qyzp {
    private String keyNo; //内部KeyNo
    private String name; //公司名称
    private String no; //注册号
    private String creditCode; //全国同一信用代码
    private String econKind; //企业类型
    private String status; //状态
    private double registCapi; //注册资本
    private String operName; //法人
    private String fundedRatio; //出资比例
    private String startDate; //出资日期

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEconKind() {
        return econKind;
    }

    public void setEconKind(String econKind) {
        this.econKind = econKind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRegistCapi() {
        return registCapi;
    }

    public void setRegistCapi(double registCapi) {
        this.registCapi = registCapi;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getFundedRatio() {
        return fundedRatio;
    }

    public void setFundedRatio(String fundedRatio) {
        this.fundedRatio = fundedRatio;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
