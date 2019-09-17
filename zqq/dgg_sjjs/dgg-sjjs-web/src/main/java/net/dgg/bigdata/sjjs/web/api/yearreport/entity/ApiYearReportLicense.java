package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportLicense {
    private Integer no;

    private String name;

    private String endDate;

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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}