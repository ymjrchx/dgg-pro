package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportBranch {
    private String name;

    private String regNo;

    private String address;

    private String principal;

    private String yrId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo == null ? null : regNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}