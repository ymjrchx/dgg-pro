package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportInvest {
    private Integer no;

    private String name;

    private String gegNo;

    private String shouldCapi;

    private String shareholdingRatio;

    private String ypReport;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGegNo() {
        return gegNo;
    }

    public void setGegNo(String gegNo) {
        this.gegNo = gegNo == null ? null : gegNo.trim();
    }

    public String getShouldCapi() {
        return shouldCapi;
    }

    public void setShouldCapi(String shouldCapi) {
        this.shouldCapi = shouldCapi == null ? null : shouldCapi.trim();
    }

    public String getShareholdingRatio() {
        return shareholdingRatio;
    }

    public void setShareholdingRatio(String shareholdingRatio) {
        this.shareholdingRatio = shareholdingRatio == null ? null : shareholdingRatio.trim();
    }

    public String getYpReport() {
        return ypReport;
    }

    public void setYpReport(String ypReport) {
        this.ypReport = ypReport == null ? null : ypReport.trim();
    }
}