package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qynb;

import java.io.Serializable;
import java.util.List;

/**
 * 企业年报详情
 * Created by jiangsh on 2018/6/4 16:33
 */
public class QynbDetail implements Serializable {
    private String no; //序号
    private String year; //报送年度
    private String remarks; //备注
    private Boolean hasDetailInfo; //是否有详细信息
    private String publishDate; //发布日期

    private BasicInfoData basicInfoData; //基本信息
    private AssetsData assetsData; //利润
    private List<ChangeList> changeList; //变更
    private List<InvestInfoList> investInfo; //投资
    private List<PartnerList> partner; //股东
    private List<ProvideAssuranceList> provideAssurance; //债务
    private List<StockChangeList> stockChange; //股东变更
    private List<WebSiteList> webSite; //网站
    private List<AdministrationLicenseList> administrationLicense; //许可文件
    private List<BranchList> branch; //注册
    private List<EmployeeList> employeeList; //员工

    private String companyId;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getHasDetailInfo() {
        return hasDetailInfo;
    }

    public void setHasDetailInfo(Boolean hasDetailInfo) {
        this.hasDetailInfo = hasDetailInfo;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public BasicInfoData getBasicInfoData() {
        return basicInfoData;
    }

    public void setBasicInfoData(BasicInfoData basicInfoData) {
        this.basicInfoData = basicInfoData;
    }

    public AssetsData getAssetsData() {
        return assetsData;
    }

    public void setAssetsData(AssetsData assetsData) {
        this.assetsData = assetsData;
    }

    public List<ChangeList> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<ChangeList> changeList) {
        this.changeList = changeList;
    }

    public List<InvestInfoList> getInvestInfo() {
        return investInfo;
    }

    public void setInvestInfo(List<InvestInfoList> investInfo) {
        this.investInfo = investInfo;
    }

    public List<PartnerList> getPartner() {
        return partner;
    }

    public void setPartner(List<PartnerList> partner) {
        this.partner = partner;
    }

    public List<ProvideAssuranceList> getProvideAssurance() {
        return provideAssurance;
    }

    public void setProvideAssurance(List<ProvideAssuranceList> provideAssurance) {
        this.provideAssurance = provideAssurance;
    }

    public List<StockChangeList> getStockChange() {
        return stockChange;
    }

    public void setStockChange(List<StockChangeList> stockChange) {
        this.stockChange = stockChange;
    }

    public List<WebSiteList> getWebSite() {
        return webSite;
    }

    public void setWebSite(List<WebSiteList> webSite) {
        this.webSite = webSite;
    }

    public List<AdministrationLicenseList> getAdministrationLicense() {
        return administrationLicense;
    }

    public void setAdministrationLicense(List<AdministrationLicenseList> administrationLicense) {
        this.administrationLicense = administrationLicense;
    }

    public List<BranchList> getBranch() {
        return branch;
    }

    public void setBranch(List<BranchList> branch) {
        this.branch = branch;
    }

    public List<EmployeeList> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeList> employeeList) {
        this.employeeList = employeeList;
    }

    public static class BasicInfoData implements Serializable{
        private String regNo; //注册号
        private String companyName; //企业名称
        private String operatorName; //经营者姓名
        private String contactNo; //企业联系电话
        private String postCode; //邮政编码
        private String address; //企业通信地址
        private String emailAddress; //电子邮箱
        private String isStockRightTransfer; //有限责任公司本年度是否发生股东股权转让
        private String status; //企业经营状态
        private String hasWebSite; //是否有网站或网店
        private String hasNewStockOrByStock; //企业是否有投资信息或购买其他公司股权
        private String employeeCount; //从业人数
        private String belongTo; //隶属关系
        private String capitalAmount; //资金数额
        private String hasProvideAssurance; //是否有对外担保信息
        private String operationPlaces; //经营场所
        private String mainType; //主体类型
        private String operationDuration; //经营期限
        private String ifContentSame; //章程信息(是否一致)
        private String differentContentv; //章程信息(不一致内容)
        private String generalOperationItem; //经营范围(一般经营项目)
        private String approvedOperationItem; //经营范围(许可经营项目)

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getIsStockRightTransfer() {
            return isStockRightTransfer;
        }

        public void setIsStockRightTransfer(String isStockRightTransfer) {
            this.isStockRightTransfer = isStockRightTransfer;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHasWebSite() {
            return hasWebSite;
        }

        public void setHasWebSite(String hasWebSite) {
            this.hasWebSite = hasWebSite;
        }

        public String getHasNewStockOrByStock() {
            return hasNewStockOrByStock;
        }

        public void setHasNewStockOrByStock(String hasNewStockOrByStock) {
            this.hasNewStockOrByStock = hasNewStockOrByStock;
        }

        public String getEmployeeCount() {
            return employeeCount;
        }

        public void setEmployeeCount(String employeeCount) {
            this.employeeCount = employeeCount;
        }

        public String getBelongTo() {
            return belongTo;
        }

        public void setBelongTo(String belongTo) {
            this.belongTo = belongTo;
        }

        public String getCapitalAmount() {
            return capitalAmount;
        }

        public void setCapitalAmount(String capitalAmount) {
            this.capitalAmount = capitalAmount;
        }

        public String getHasProvideAssurance() {
            return hasProvideAssurance;
        }

        public void setHasProvideAssurance(String hasProvideAssurance) {
            this.hasProvideAssurance = hasProvideAssurance;
        }

        public String getOperationPlaces() {
            return operationPlaces;
        }

        public void setOperationPlaces(String operationPlaces) {
            this.operationPlaces = operationPlaces;
        }

        public String getMainType() {
            return mainType;
        }

        public void setMainType(String mainType) {
            this.mainType = mainType;
        }

        public String getOperationDuration() {
            return operationDuration;
        }

        public void setOperationDuration(String operationDuration) {
            this.operationDuration = operationDuration;
        }

        public String getIfContentSame() {
            return ifContentSame;
        }

        public void setIfContentSame(String ifContentSame) {
            this.ifContentSame = ifContentSame;
        }

        public String getDifferentContentv() {
            return differentContentv;
        }

        public void setDifferentContentv(String differentContentv) {
            this.differentContentv = differentContentv;
        }

        public String getGeneralOperationItem() {
            return generalOperationItem;
        }

        public void setGeneralOperationItem(String generalOperationItem) {
            this.generalOperationItem = generalOperationItem;
        }

        public String getApprovedOperationItem() {
            return approvedOperationItem;
        }

        public void setApprovedOperationItem(String approvedOperationItem) {
            this.approvedOperationItem = approvedOperationItem;
        }
    }
    public static class PartnerList implements Serializable{

        private String no; //序号
        private String name; //股东/发起人
        private String shouldCapi; //认缴出资额
        private String shouldDate; //认缴出资时间
        private String shouldType; //认缴出资方式
        private String realCapi; //实缴出资额
        private String realDate; //实缴出资时间
        private String realType; //实缴出资方式
        private String form; //出资类型
        private String investmentRatio; //出资比例

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShouldCapi() {
            return shouldCapi;
        }

        public void setShouldCapi(String shouldCapi) {
            this.shouldCapi = shouldCapi;
        }

        public String getShouldDate() {
            return shouldDate;
        }

        public void setShouldDate(String shouldDate) {
            this.shouldDate = shouldDate;
        }

        public String getShouldType() {
            return shouldType;
        }

        public void setShouldType(String shouldType) {
            this.shouldType = shouldType;
        }

        public String getRealCapi() {
            return realCapi;
        }

        public void setRealCapi(String realCapi) {
            this.realCapi = realCapi;
        }

        public String getRealDate() {
            return realDate;
        }

        public void setRealDate(String realDate) {
            this.realDate = realDate;
        }

        public String getRealType() {
            return realType;
        }

        public void setRealType(String realType) {
            this.realType = realType;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public String getInvestmentRatio() {
            return investmentRatio;
        }

        public void setInvestmentRatio(String investmentRatio) {
            this.investmentRatio = investmentRatio;
        }
    }
    public static class AssetsData implements Serializable{
        private String totalAssets; //资产总额
        private String totalOwnersEquity; //所有者权益合计
        private String grossTradingIncome; //营业总收入
        private String totalProfit; //利润总额
        private String mainBusinessIncome; //营业总收入中主营业务
        private String netProfit; //净利润
        private String totalTaxAmount; //纳税总额
        private String totalLiabilities; //负债总额
        private String bankingCredit; //金融贷款
        private String governmentSubsidy; //获得政府扶持资金、补助

        public String getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            this.totalAssets = totalAssets;
        }

        public String getTotalOwnersEquity() {
            return totalOwnersEquity;
        }

        public void setTotalOwnersEquity(String totalOwnersEquity) {
            this.totalOwnersEquity = totalOwnersEquity;
        }

        public String getGrossTradingIncome() {
            return grossTradingIncome;
        }

        public void setGrossTradingIncome(String grossTradingIncome) {
            this.grossTradingIncome = grossTradingIncome;
        }

        public String getTotalProfit() {
            return totalProfit;
        }

        public void setTotalProfit(String totalProfit) {
            this.totalProfit = totalProfit;
        }

        public String getMainBusinessIncome() {
            return mainBusinessIncome;
        }

        public void setMainBusinessIncome(String mainBusinessIncome) {
            this.mainBusinessIncome = mainBusinessIncome;
        }

        public String getNetProfit() {
            return netProfit;
        }

        public void setNetProfit(String netProfit) {
            this.netProfit = netProfit;
        }

        public String getTotalTaxAmount() {
            return totalTaxAmount;
        }

        public void setTotalTaxAmount(String totalTaxAmount) {
            this.totalTaxAmount = totalTaxAmount;
        }

        public String getTotalLiabilities() {
            return totalLiabilities;
        }

        public void setTotalLiabilities(String totalLiabilities) {
            this.totalLiabilities = totalLiabilities;
        }

        public String getBankingCredit() {
            return bankingCredit;
        }

        public void setBankingCredit(String bankingCredit) {
            this.bankingCredit = bankingCredit;
        }

        public String getGovernmentSubsidy() {
            return governmentSubsidy;
        }

        public void setGovernmentSubsidy(String governmentSubsidy) {
            this.governmentSubsidy = governmentSubsidy;
        }
    }

    public static class WebSiteList implements Serializable{
        private String no; //序号
        private String type; //类型
        private String name; //名称
        private String webSite; //网址

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWebSite() {
            return webSite;
        }

        public void setWebSite(String webSite) {
            this.webSite = webSite;
        }
    }
    public static class ChangeList implements Serializable{
        private String no; //序号
        private String changeName; //修改事项
        private String before; //变更前内容
        private String after; //变更后内容
        private String changeDate; //变更日期

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getChangeName() {
            return changeName;
        }

        public void setChangeName(String changeName) {
            this.changeName = changeName;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getChangeDate() {
            return changeDate;
        }

        public void setChangeDate(String changeDate) {
            this.changeDate = changeDate;
        }
    }

    public static class StockChangeList implements Serializable{
        private String no; //序号
        private String name; //股东
        private String before; //变更前股权比例
        private String after; //变更后股权比例
        private String changeDate; //股权变更日期期限围

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getChangeDate() {
            return changeDate;
        }

        public void setChangeDate(String changeDate) {
            this.changeDate = changeDate;
        }
    }

    public static class AdministrationLicenseList implements Serializable{
        private String no; //序号
        private String name; //许可文件名称
        private String endDate; //有效期至

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }

    public static class BranchList implements Serializable{
        private String name; //分支机构名称
        private String regNo; //注册号
        private String address; //住所
        private String principal; //负责人

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }
    }

    public static class EmployeeList implements Serializable{
        private String no; //序号
        private String name; //名称
        private String job; //职位
        private String cerNo; //证照/证件号码
        private String scertName; //证件名称

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getCerNo() {
            return cerNo;
        }

        public void setCerNo(String cerNo) {
            this.cerNo = cerNo;
        }

        public String getScertName() {
            return scertName;
        }

        public void setScertName(String scertName) {
            this.scertName = scertName;
        }
    }

    public static class InvestInfoList implements Serializable{
        private String no; //序号
        private String name; //投资设立企业或购买股权企业名称
        private String regNo; //注册号
        private String shouldCapi; //认缴出资额（万元）
        private String shareholdingRatio; //持股比例（%）

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getShouldCapi() {
            return shouldCapi;
        }

        public void setShouldCapi(String shouldCapi) {
            this.shouldCapi = shouldCapi;
        }

        public String getShareholdingRatio() {
            return shareholdingRatio;
        }

        public void setShareholdingRatio(String shareholdingRatio) {
            this.shareholdingRatio = shareholdingRatio;
        }
    }

    public static class ProvideAssuranceList implements Serializable{
        private String no; //序号
        private String creditor; //债权人
        private String debtor; //债务人
        private String creditorCategory; //主债权种类
        private String creditorAmount; //主债权数额
        private String fulfillObligation; //履行债务的
        private String assuranceDurn; //保证的期间
        private String assuranceType; //保证的方式
        private String assuranceScope; //保证担保的范

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getCreditor() {
            return creditor;
        }

        public void setCreditor(String creditor) {
            this.creditor = creditor;
        }

        public String getDebtor() {
            return debtor;
        }

        public void setDebtor(String debtor) {
            this.debtor = debtor;
        }

        public String getCreditorCategory() {
            return creditorCategory;
        }

        public void setCreditorCategory(String creditorCategory) {
            this.creditorCategory = creditorCategory;
        }

        public String getCreditorAmount() {
            return creditorAmount;
        }

        public void setCreditorAmount(String creditorAmount) {
            this.creditorAmount = creditorAmount;
        }

        public String getFulfillObligation() {
            return fulfillObligation;
        }

        public void setFulfillObligation(String fulfillObligation) {
            this.fulfillObligation = fulfillObligation;
        }

        public String getAssuranceDurn() {
            return assuranceDurn;
        }

        public void setAssuranceDurn(String assuranceDurn) {
            this.assuranceDurn = assuranceDurn;
        }

        public String getAssuranceType() {
            return assuranceType;
        }

        public void setAssuranceType(String assuranceType) {
            this.assuranceType = assuranceType;
        }

        public String getAssuranceScope() {
            return assuranceScope;
        }

        public void setAssuranceScope(String assuranceScope) {
            this.assuranceScope = assuranceScope;
        }
    }
}





