package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportEmployee {
    private Integer no;

    private String name;

    private String job;

    private String cerNo;

    private String scertName;

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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getCerNo() {
        return cerNo;
    }

    public void setCerNo(String cerNo) {
        this.cerNo = cerNo == null ? null : cerNo.trim();
    }

    public String getScertName() {
        return scertName;
    }

    public void setScertName(String scertName) {
        this.scertName = scertName == null ? null : scertName.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}