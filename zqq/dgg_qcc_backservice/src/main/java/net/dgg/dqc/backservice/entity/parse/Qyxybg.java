package net.dgg.dqc.backservice.entity.parse;

/**
 *
 * 企业信用报告
 * Created by jiangsh on 2018/6/5 11:10
 */
public class Qyxybg {
    private String OrderNo; //报表订单号
    private String ReportStatus; //报告订单处理状态
    private String ReportUrl; //报告成品地址

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getReportStatus() {
        return ReportStatus;
    }

    public void setReportStatus(String reportStatus) {
        ReportStatus = reportStatus;
    }

    public String getReportUrl() {
        return ReportUrl;
    }

    public void setReportUrl(String reportUrl) {
        ReportUrl = reportUrl;
    }
}
