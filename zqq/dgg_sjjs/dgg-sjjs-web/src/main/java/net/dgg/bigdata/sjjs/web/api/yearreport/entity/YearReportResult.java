package net.dgg.bigdata.sjjs.web.api.yearreport.entity;

import java.util.List;

/**
 * Created by wu on 2017-11-03.
 */
public class YearReportResult {


    private ApiYearReport basicInfoData;

    private ApiAssetsData assetsData;

    private List<ApiYearReportChg> changeList;

    private List<ApiYearReportInvest> investInfoList;

    private List<ApiYearReportPartner> partnerList;

    private List<ApiYearReportProvide> provideAssuranceList;

    private List<ApiYearReportStock> stockChangeList;

    private List<ApiYearReportWebSite> webSiteList;

    private List<ApiYearReportLicense> administrationLicenseList;

    private List<ApiYearReportBranch> branchList;

    private List<ApiYearReportEmployee> employeeList;

    public ApiYearReport getBasicInfoData() {
        return basicInfoData;
    }

    public void setBasicInfoData(ApiYearReport basicInfoData) {
        this.basicInfoData = basicInfoData;
    }

    public ApiAssetsData getAssetsData() {
        return assetsData;
    }

    public void setAssetsData(ApiAssetsData assetsData) {
        this.assetsData = assetsData;
    }

    public List<ApiYearReportChg> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<ApiYearReportChg> changeList) {
        this.changeList = changeList;
    }

    public List<ApiYearReportInvest> getInvestInfoList() {
        return investInfoList;
    }

    public void setInvestInfoList(List<ApiYearReportInvest> investInfoList) {
        this.investInfoList = investInfoList;
    }

    public List<ApiYearReportPartner> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<ApiYearReportPartner> partnerList) {
        this.partnerList = partnerList;
    }

    public List<ApiYearReportProvide> getProvideAssuranceList() {
        return provideAssuranceList;
    }

    public void setProvideAssuranceList(List<ApiYearReportProvide> provideAssuranceList) {
        this.provideAssuranceList = provideAssuranceList;
    }

    public List<ApiYearReportStock> getStockChangeList() {
        return stockChangeList;
    }

    public void setStockChangeList(List<ApiYearReportStock> stockChangeList) {
        this.stockChangeList = stockChangeList;
    }

    public List<ApiYearReportWebSite> getWebSiteList() {
        return webSiteList;
    }

    public void setWebSiteList(List<ApiYearReportWebSite> webSiteList) {
        this.webSiteList = webSiteList;
    }

    public List<ApiYearReportLicense> getAdministrationLicenseList() {
        return administrationLicenseList;
    }

    public void setAdministrationLicenseList(List<ApiYearReportLicense> administrationLicenseList) {
        this.administrationLicenseList = administrationLicenseList;
    }

    public List<ApiYearReportBranch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<ApiYearReportBranch> branchList) {
        this.branchList = branchList;
    }

    public List<ApiYearReportEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<ApiYearReportEmployee> employeeList) {
        this.employeeList = employeeList;
    }
}
