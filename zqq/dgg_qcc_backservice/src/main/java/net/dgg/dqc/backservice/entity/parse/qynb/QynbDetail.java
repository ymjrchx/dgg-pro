package net.dgg.dqc.backservice.entity.parse.qynb;

import java.util.List;

/**
 * 企业年报详情
 * Created by jiangsh on 2018/6/4 16:33
 */
public class QynbDetail {
    private String No; //序号
    private String Year; //报送年度
    private String Remarks; //备注
    private Boolean HasDetailInfo; //是否有详细信息
    private String PublishDate; //发布日期

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

    private String CompanyId;

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public Boolean getHasDetailInfo() {
        return HasDetailInfo;
    }

    public void setHasDetailInfo(Boolean hasDetailInfo) {
        HasDetailInfo = hasDetailInfo;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
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

    public static class BasicInfoData{
        private String RegNo; //注册号
        private String CompanyName; //企业名称
        private String OperatorName; //经营者姓名
        private String ContactNo; //企业联系电话
        private String PostCode; //邮政编码
        private String Address; //企业通信地址
        private String EmailAddress; //电子邮箱
        private String IsStockRightTransfer; //有限责任公司本年度是否发生股东股权转让
        private String Status; //企业经营状态
        private String HasWebSite; //是否有网站或网店
        private String HasNewStockOrByStock; //企业是否有投资信息或购买其他公司股权
        private String EmployeeCount; //从业人数
        private String BelongTo; //隶属关系
        private String CapitalAmount; //资金数额
        private String HasProvideAssurance; //是否有对外担保信息
        private String OperationPlaces; //经营场所
        private String MainType; //主体类型
        private String OperationDuration; //经营期限
        private String IfContentSame; //章程信息(是否一致)
        private String DifferentContentv; //章程信息(不一致内容)
        private String GeneralOperationItem; //经营范围(一般经营项目)
        private String ApprovedOperationItem; //经营范围(许可经营项目)

        public String getRegNo() {
            return RegNo;
        }

        public void setRegNo(String regNo) {
            RegNo = regNo;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getOperatorName() {
            return OperatorName;
        }

        public void setOperatorName(String operatorName) {
            OperatorName = operatorName;
        }

        public String getContactNo() {
            return ContactNo;
        }

        public void setContactNo(String contactNo) {
            ContactNo = contactNo;
        }

        public String getPostCode() {
            return PostCode;
        }

        public void setPostCode(String postCode) {
            PostCode = postCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getEmailAddress() {
            return EmailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            EmailAddress = emailAddress;
        }

        public String getIsStockRightTransfer() {
            return IsStockRightTransfer;
        }

        public void setIsStockRightTransfer(String isStockRightTransfer) {
            IsStockRightTransfer = isStockRightTransfer;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getHasWebSite() {
            return HasWebSite;
        }

        public void setHasWebSite(String hasWebSite) {
            HasWebSite = hasWebSite;
        }

        public String getHasNewStockOrByStock() {
            return HasNewStockOrByStock;
        }

        public void setHasNewStockOrByStock(String hasNewStockOrByStock) {
            HasNewStockOrByStock = hasNewStockOrByStock;
        }

        public String getEmployeeCount() {
            return EmployeeCount;
        }

        public void setEmployeeCount(String employeeCount) {
            EmployeeCount = employeeCount;
        }

        public String getBelongTo() {
            return BelongTo;
        }

        public void setBelongTo(String belongTo) {
            BelongTo = belongTo;
        }

        public String getCapitalAmount() {
            return CapitalAmount;
        }

        public void setCapitalAmount(String capitalAmount) {
            CapitalAmount = capitalAmount;
        }

        public String getHasProvideAssurance() {
            return HasProvideAssurance;
        }

        public void setHasProvideAssurance(String hasProvideAssurance) {
            HasProvideAssurance = hasProvideAssurance;
        }

        public String getOperationPlaces() {
            return OperationPlaces;
        }

        public void setOperationPlaces(String operationPlaces) {
            OperationPlaces = operationPlaces;
        }

        public String getMainType() {
            return MainType;
        }

        public void setMainType(String mainType) {
            MainType = mainType;
        }

        public String getOperationDuration() {
            return OperationDuration;
        }

        public void setOperationDuration(String operationDuration) {
            OperationDuration = operationDuration;
        }

        public String getIfContentSame() {
            return IfContentSame;
        }

        public void setIfContentSame(String ifContentSame) {
            IfContentSame = ifContentSame;
        }

        public String getDifferentContentv() {
            return DifferentContentv;
        }

        public void setDifferentContentv(String differentContentv) {
            DifferentContentv = differentContentv;
        }

        public String getGeneralOperationItem() {
            return GeneralOperationItem;
        }

        public void setGeneralOperationItem(String generalOperationItem) {
            GeneralOperationItem = generalOperationItem;
        }

        public String getApprovedOperationItem() {
            return ApprovedOperationItem;
        }

        public void setApprovedOperationItem(String approvedOperationItem) {
            ApprovedOperationItem = approvedOperationItem;
        }
    }
    public static class PartnerList{
        private String No; //序号
        private String Name; //股东/发起人
        private String ShouldCapi; //认缴出资额
        private String ShouldDate; //认缴出资时间
        private String ShouldType; //认缴出资方式
        private String RealCapi; //实缴出资额
        private String RealDate; //实缴出资时间
        private String RealType; //实缴出资方式
        private String Form; //出资类型
        private String InvestmentRatio; //出资比例

        public String getNo() {
            return No;
        }

        public void setNo(String no) {
            No = no;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getShouldCapi() {
            return ShouldCapi;
        }

        public void setShouldCapi(String shouldCapi) {
            ShouldCapi = shouldCapi;
        }

        public String getShouldDate() {
            return ShouldDate;
        }

        public void setShouldDate(String shouldDate) {
            ShouldDate = shouldDate;
        }

        public String getShouldType() {
            return ShouldType;
        }

        public void setShouldType(String shouldType) {
            ShouldType = shouldType;
        }

        public String getRealCapi() {
            return RealCapi;
        }

        public void setRealCapi(String realCapi) {
            RealCapi = realCapi;
        }

        public String getRealDate() {
            return RealDate;
        }

        public void setRealDate(String realDate) {
            RealDate = realDate;
        }

        public String getRealType() {
            return RealType;
        }

        public void setRealType(String realType) {
            RealType = realType;
        }

        public String getForm() {
            return Form;
        }

        public void setForm(String form) {
            Form = form;
        }

        public String getInvestmentRatio() {
            return InvestmentRatio;
        }

        public void setInvestmentRatio(String investmentRatio) {
            InvestmentRatio = investmentRatio;
        }
    }
    public static class AssetsData{
        private String TotalAssets; //资产总额
        private String TotalOwnersEquity; //所有者权益合计
        private String GrossTradingIncome; //营业总收入
        private String TotalProfit; //利润总额
        private String MainBusinessIncome; //营业总收入中主营业务
        private String NetProfit; //净利润
        private String TotalTaxAmount; //纳税总额
        private String TotalLiabilities; //负债总额
        private String BankingCredit; //金融贷款
        private String GovernmentSubsidy; //获得政府扶持资金、补助

        public String getTotalAssets() {
            return TotalAssets;
        }

        public void setTotalAssets(String totalAssets) {
            TotalAssets = totalAssets;
        }

        public String getTotalOwnersEquity() {
            return TotalOwnersEquity;
        }

        public void setTotalOwnersEquity(String totalOwnersEquity) {
            TotalOwnersEquity = totalOwnersEquity;
        }

        public String getGrossTradingIncome() {
            return GrossTradingIncome;
        }

        public void setGrossTradingIncome(String grossTradingIncome) {
            GrossTradingIncome = grossTradingIncome;
        }

        public String getTotalProfit() {
            return TotalProfit;
        }

        public void setTotalProfit(String totalProfit) {
            TotalProfit = totalProfit;
        }

        public String getMainBusinessIncome() {
            return MainBusinessIncome;
        }

        public void setMainBusinessIncome(String mainBusinessIncome) {
            MainBusinessIncome = mainBusinessIncome;
        }

        public String getNetProfit() {
            return NetProfit;
        }

        public void setNetProfit(String netProfit) {
            NetProfit = netProfit;
        }

        public String getTotalTaxAmount() {
            return TotalTaxAmount;
        }

        public void setTotalTaxAmount(String totalTaxAmount) {
            TotalTaxAmount = totalTaxAmount;
        }

        public String getTotalLiabilities() {
            return TotalLiabilities;
        }

        public void setTotalLiabilities(String totalLiabilities) {
            TotalLiabilities = totalLiabilities;
        }

        public String getBankingCredit() {
            return BankingCredit;
        }

        public void setBankingCredit(String bankingCredit) {
            BankingCredit = bankingCredit;
        }

        public String getGovernmentSubsidy() {
            return GovernmentSubsidy;
        }

        public void setGovernmentSubsidy(String governmentSubsidy) {
            GovernmentSubsidy = governmentSubsidy;
        }
    }

    public static class WebSiteList{
        private String No; //序号
        private String Type; //类型
        private String Name; //名称
        private String WebSite; //网址

        public String getNo() {
            return No;
        }

        public void setNo(String no) {
            No = no;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getWebSite() {
            return WebSite;
        }

        public void setWebSite(String webSite) {
            WebSite = webSite;
        }
    }
    public static class ChangeList{
        private String No; //序号
        private String ChangeName; //修改事项
        private String Before; //变更前内容
        private String After; //变更后内容
        private String ChangeDate; //变更日期

        public String getNo() {
            return No;
        }

        public void setNo(String no) {
            No = no;
        }

        public String getChangeName() {
            return ChangeName;
        }

        public void setChangeName(String changeName) {
            ChangeName = changeName;
        }

        public String getBefore() {
            return Before;
        }

        public void setBefore(String before) {
            Before = before;
        }

        public String getAfter() {
            return After;
        }

        public void setAfter(String after) {
            After = after;
        }

        public String getChangeDate() {
            return ChangeDate;
        }

        public void setChangeDate(String changeDate) {
            ChangeDate = changeDate;
        }
    }
}


class InvestInfoList{
    private String No; //序号
    private String Name; //投资设立企业或购买股权企业名称
    private String RegNo; //注册号
    private String ShouldCapi; //认缴出资额（万元）
    private String ShareholdingRatio; //持股比例（%）

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getShouldCapi() {
        return ShouldCapi;
    }

    public void setShouldCapi(String shouldCapi) {
        ShouldCapi = shouldCapi;
    }

    public String getShareholdingRatio() {
        return ShareholdingRatio;
    }

    public void setShareholdingRatio(String shareholdingRatio) {
        ShareholdingRatio = shareholdingRatio;
    }
}

class ProvideAssuranceList{
    private String No; //序号
    private String Creditor; //债权人
    private String Debtor; //债务人
    private String CreditorCategory; //主债权种类
    private String CreditorAmount; //主债权数额
    private String FulfillObligation; //履行债务的期限
    private String AssuranceDurn; //保证的期间
    private String AssuranceType; //保证的方式
    private String AssuranceScope; //保证担保的范围

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getCreditor() {
        return Creditor;
    }

    public void setCreditor(String creditor) {
        Creditor = creditor;
    }

    public String getDebtor() {
        return Debtor;
    }

    public void setDebtor(String debtor) {
        Debtor = debtor;
    }

    public String getCreditorCategory() {
        return CreditorCategory;
    }

    public void setCreditorCategory(String creditorCategory) {
        CreditorCategory = creditorCategory;
    }

    public String getCreditorAmount() {
        return CreditorAmount;
    }

    public void setCreditorAmount(String creditorAmount) {
        CreditorAmount = creditorAmount;
    }

    public String getFulfillObligation() {
        return FulfillObligation;
    }

    public void setFulfillObligation(String fulfillObligation) {
        FulfillObligation = fulfillObligation;
    }

    public String getAssuranceDurn() {
        return AssuranceDurn;
    }

    public void setAssuranceDurn(String assuranceDurn) {
        AssuranceDurn = assuranceDurn;
    }

    public String getAssuranceType() {
        return AssuranceType;
    }

    public void setAssuranceType(String assuranceType) {
        AssuranceType = assuranceType;
    }

    public String getAssuranceScope() {
        return AssuranceScope;
    }

    public void setAssuranceScope(String assuranceScope) {
        AssuranceScope = assuranceScope;
    }
}

class StockChangeList{
    private String No; //序号
    private String Name; //股东
    private String Before; //变更前股权比例
    private String After; //变更后股权比例
    private String ChangeDate; //股权变更日期

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBefore() {
        return Before;
    }

    public void setBefore(String before) {
        Before = before;
    }

    public String getAfter() {
        return After;
    }

    public void setAfter(String after) {
        After = after;
    }

    public String getChangeDate() {
        return ChangeDate;
    }

    public void setChangeDate(String changeDate) {
        ChangeDate = changeDate;
    }
}

class AdministrationLicenseList{
    private String No; //序号
    private String Name; //许可文件名称
    private String EndDate; //有效期至

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}

class BranchList{
    private String Name; //分支机构名称
    private String RegNo; //注册号
    private String Address; //住所
    private String Principal; //负责人

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPrincipal() {
        return Principal;
    }

    public void setPrincipal(String principal) {
        Principal = principal;
    }
}

class EmployeeList{
    private String No; //序号
    private String Name; //名称
    private String Job; //职位
    private String CerNo; //证照/证件号码
    private String ScertName; //证件名称

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getCerNo() {
        return CerNo;
    }

    public void setCerNo(String cerNo) {
        CerNo = cerNo;
    }

    public String getScertName() {
        return ScertName;
    }

    public void setScertName(String scertName) {
        ScertName = scertName;
    }
}
