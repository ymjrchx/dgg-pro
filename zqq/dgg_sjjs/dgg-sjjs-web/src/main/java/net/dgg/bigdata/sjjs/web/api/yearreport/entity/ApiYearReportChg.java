package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

import java.util.Date;

public class ApiYearReportChg {
    private Integer no;

    private String changeName;

    private String before;

    private String after;

    private Date changeDate;

    private String yrId;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName == null ? null : changeName.trim();
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

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}