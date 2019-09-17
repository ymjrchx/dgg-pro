package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportPartner {
    private Integer no;

    private String name;

    private String shouldCapi;

    private String shouldDate;

    private String shouldType;

    private String realCapi;

    private String realDate;

    private String realType;

    private String form;

    private String investmentRatio;

    private String yrId;

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

    public String getShouldCapi() {
        return shouldCapi;
    }

    public void setShouldCapi(String shouldCapi) {
        this.shouldCapi = shouldCapi == null ? null : shouldCapi.trim();
    }

    public String getShouldDate() {
        return shouldDate;
    }

    public void setShouldDate(String shouldDate) {
        this.shouldDate = shouldDate == null ? null : shouldDate.trim();
    }

    public String getShouldType() {
        return shouldType;
    }

    public void setShouldType(String shouldType) {
        this.shouldType = shouldType == null ? null : shouldType.trim();
    }

    public String getRealCapi() {
        return realCapi;
    }

    public void setRealCapi(String realCapi) {
        this.realCapi = realCapi == null ? null : realCapi.trim();
    }

    public String getRealDate() {
        return realDate;
    }

    public void setRealDate(String realDate) {
        this.realDate = realDate == null ? null : realDate.trim();
    }

    public String getRealType() {
        return realType;
    }

    public void setRealType(String realType) {
        this.realType = realType == null ? null : realType.trim();
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form == null ? null : form.trim();
    }

    public String getInvestmentRatio() {
        return investmentRatio;
    }

    public void setInvestmentRatio(String investmentRatio) {
        this.investmentRatio = investmentRatio == null ? null : investmentRatio.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}