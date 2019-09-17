package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportStock {
    private Integer no;

    private String name;

    private String before;

    private String after;

    private String changeDate;

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

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before == null ? null : before.trim();
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after == null ? null : after.trim();
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate == null ? null : changeDate.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}