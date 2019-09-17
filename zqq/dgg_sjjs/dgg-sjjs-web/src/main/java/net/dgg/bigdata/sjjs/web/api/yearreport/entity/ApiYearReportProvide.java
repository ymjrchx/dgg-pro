package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

public class ApiYearReportProvide {
    private Integer no;

    private String creditor;

    private String debtor;

    private String creditorCategory;

    private String creditorAmount;

    private String fulfillObligation;

    private String assuranceDurn;

    private String assuranceType;

    private String assuranceScope;

    private String yrId;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor == null ? null : creditor.trim();
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor == null ? null : debtor.trim();
    }

    public String getCreditorCategory() {
        return creditorCategory;
    }

    public void setCreditorCategory(String creditorCategory) {
        this.creditorCategory = creditorCategory == null ? null : creditorCategory.trim();
    }

    public String getCreditorAmount() {
        return creditorAmount;
    }

    public void setCreditorAmount(String creditorAmount) {
        this.creditorAmount = creditorAmount == null ? null : creditorAmount.trim();
    }

    public String getFulfillObligation() {
        return fulfillObligation;
    }

    public void setFulfillObligation(String fulfillObligation) {
        this.fulfillObligation = fulfillObligation == null ? null : fulfillObligation.trim();
    }

    public String getAssuranceDurn() {
        return assuranceDurn;
    }

    public void setAssuranceDurn(String assuranceDurn) {
        this.assuranceDurn = assuranceDurn == null ? null : assuranceDurn.trim();
    }

    public String getAssuranceType() {
        return assuranceType;
    }

    public void setAssuranceType(String assuranceType) {
        this.assuranceType = assuranceType == null ? null : assuranceType.trim();
    }

    public String getAssuranceScope() {
        return assuranceScope;
    }

    public void setAssuranceScope(String assuranceScope) {
        this.assuranceScope = assuranceScope == null ? null : assuranceScope.trim();
    }

    public String getYrId() {
        return yrId;
    }

    public void setYrId(String yrId) {
        this.yrId = yrId == null ? null : yrId.trim();
    }
}