package net.dgg.dqc.backservice.entity.parse;

/**
 *
 * 企业信用报告
 * Created by jiangsh on 2018/6/5 11:10
 */
public class Qyxybg {
    private String orderNo; //报表订单号
    private String reportStatus; //报告订单处理状态
    private String reportUrl; //报告成品地址

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
