package net.dgg.dqc.backservice.entity.parse.qygs;

import net.dgg.dqc.backservice.entity.parse.qyfj.ContactInfo;

import java.util.List;

/**
 * 企业工商数据查询
 * Created by jiangsh on 2018/6/4 15:20
 */
public class QygsDetail {
    private String KeyNo; //内部KeyNo
    private String Name; //公司名称
    private String No; //注册号
    private String BelongOrg; //登记机关
    private String OperName; //法人名
    private String StartDate; //成立日期
    private String EndDate; //吊销日期
    private String Status; //企业状态
    private String Province; //省份
    private String UpdatedDate; //更新日期
    private String CreditCode; //社会统一信用代码
    private double RegistCapi; //注册资本
    private double RealCapi; //实缴资本
    private String EconKind; //企业类型
    private String Address; //地址
    private String Scope; //经营范围
    private String TermStart; //营业开始日期
    private String TeamEnd; //营业结束日期
    private String CheckDate; //发照日期
    private String OrgNo; //组织机构代码
    private String IsOnStock; //是否上市(0为未上市，1为上市)
    private String StockNumber; //上市公司代码
    private String StockType; //上市类型
    private String OriginalName; //曾用名

    private String EnglishName; //英文名
    private String personNum; //人员规模
    private String cbPersonNum; //参保人数

    private int CategoryScore; //行业评分
    private int PercentileScore; //公司评分

    private List<Partners> partnersList; //股东信息
    private List<Employees> employeesList; //主要人员
    private List<Branches> branchesList; //分支机构
    private List<ChangeRecords> changeRecordsList; //变更信息
    private List<ContactInfo> contactInfoList; //联系信息
    private String industry; //行业信息


    public String getCbPersonNum() {
        return cbPersonNum;
    }

    public void setCbPersonNum(String cbPersonNum) {
        this.cbPersonNum = cbPersonNum;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public double getRealCapi() {
        return RealCapi;
    }

    public void setRealCapi(double realCapi) {
        RealCapi = realCapi;
    }

    public double getRegistCapi() {
        return RegistCapi;
    }

    public void setRegistCapi(double registCapi) {
        RegistCapi = registCapi;
    }

    public int getPercentileScore() {
        return PercentileScore;
    }

    public void setPercentileScore(int percentileScore) {
        PercentileScore = percentileScore;
    }

    public int getCategoryScore() {
        return CategoryScore;
    }

    public void setCategoryScore(int categoryScore) {
        this.CategoryScore = categoryScore;
    }

    public String getKeyNo() {
        return KeyNo;
    }

    public void setKeyNo(String keyNo) {
        KeyNo = keyNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getBelongOrg() {
        return BelongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        BelongOrg = belongOrg;
    }

    public String getOperName() {
        return OperName;
    }

    public void setOperName(String operName) {
        OperName = operName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String creditCode) {
        CreditCode = creditCode;
    }


    public String getEconKind() {
        return EconKind;
    }

    public void setEconKind(String econKind) {
        EconKind = econKind;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }

    public String getTermStart() {
        return TermStart;
    }

    public void setTermStart(String termStart) {
        TermStart = termStart;
    }

    public String getTeamEnd() {
        return TeamEnd;
    }

    public void setTeamEnd(String teamEnd) {
        TeamEnd = teamEnd;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getOrgNo() {
        return OrgNo;
    }

    public void setOrgNo(String orgNo) {
        OrgNo = orgNo;
    }

    public String getIsOnStock() {
        return IsOnStock;
    }

    public void setIsOnStock(String isOnStock) {
        IsOnStock = isOnStock;
    }

    public String getStockNumber() {
        return StockNumber;
    }

    public void setStockNumber(String stockNumber) {
        StockNumber = stockNumber;
    }

    public String getStockType() {
        return StockType;
    }

    public void setStockType(String stockType) {
        StockType = stockType;
    }

    public String getOriginalName() {
        return OriginalName;
    }

    public void setOriginalName(String originalName) {
        OriginalName = originalName;
    }

    public List<Partners> getPartnersList() {
        return partnersList;
    }

    public void setPartnersList(List<Partners> partnersList) {
        this.partnersList = partnersList;
    }

    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public List<Branches> getBranchesList() {
        return branchesList;
    }

    public void setBranchesList(List<Branches> branchesList) {
        this.branchesList = branchesList;
    }

    public List<ChangeRecords> getChangeRecordsList() {
        return changeRecordsList;
    }

    public void setChangeRecordsList(List<ChangeRecords> changeRecordsList) {
        this.changeRecordsList = changeRecordsList;
    }

    public List<ContactInfo> getContactInfoList() {
        return contactInfoList;
    }

    public void setContactInfoList(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public static class Employees{
        private String Name; //姓名
        private String Job; //职位

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

    }

    public static class Branches{
        private String CompanyId; //公司ID
        private String RegNo; //注册号
        private String Name; //公司名称
        private String BelongOrg; //登记机关
        private String CreditCode; //社会统一信用代码
        private String OperName; //法人姓名或负责人姓名

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String companyId) {
            CompanyId = companyId;
        }

        public String getRegNo() {
            return RegNo;
        }

        public void setRegNo(String regNo) {
            RegNo = regNo;
        }
        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getBelongOrg() {
            return BelongOrg;
        }

        public void setBelongOrg(String belongOrg) {
            BelongOrg = belongOrg;
        }

        public String getCreditCode() {
            return CreditCode;
        }

        public void setCreditCode(String creditCode) {
            CreditCode = creditCode;
        }

        public String getOperName() {
            return OperName;
        }

        public void setOperName(String operName) {
            OperName = operName;
        }
    }

    public static class ChangeRecords{
        private String ProjectName; //变更事项
        private String BeforeContent; //变更前内容
        private String AfterContent; //变更后内容
        private String ChangeDate; //变更日期

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String projectName) {
            ProjectName = projectName;
        }

        public String getBeforeContent() {
            return BeforeContent;
        }

        public void setBeforeContent(String beforeContent) {
            BeforeContent = beforeContent;
        }

        public String getAfterContent() {
            return AfterContent;
        }

        public void setAfterContent(String afterContent) {
            AfterContent = afterContent;
        }

        public String getChangeDate() {
            return ChangeDate;
        }

        public void setChangeDate(String changeDate) {
            ChangeDate = changeDate;
        }
    }

    public static class Industry{
        private String Industry;
        private String SubIndustry;

        public Industry() {
        }

        public Industry(String industry, String subIndustry) {
            Industry = industry;
            SubIndustry = subIndustry;
        }

        public String getIndustry() {
            return Industry;
        }

        public void setIndustry(String industry) {
            Industry = industry;
        }

        public String getSubIndustry() {
            return SubIndustry;
        }

        public void setSubIndustry(String subIndustry) {
            SubIndustry = subIndustry;
        }
    }

    public static class Partners{
        private String StockName; //股东
        private String StockType; //股东类型
        private String StockPercent; //出资比例
        private String ShouldCapi; //认缴出资额
        private String ShoudDate; //认缴出资时间
        private String InvestType; //认缴出资方式
        private String InvestName; //实际出资方式
        private String RealCapi; //实缴出资额
        private String CapiDate; //实缴时间

        public String getShouldCapi() {
            return ShouldCapi;
        }

        public void setShouldCapi(String shouldCapi) {
            ShouldCapi = shouldCapi;
        }

        public String getRealCapi() {
            return RealCapi;
        }

        public void setRealCapi(String realCapi) {
            RealCapi = realCapi;
        }

        public String getStockName() {
            return StockName;
        }

        public void setStockName(String stockName) {
            StockName = stockName;
        }

        public String getStockType() {
            return StockType;
        }

        public void setStockType(String stockType) {
            StockType = stockType;
        }

        public String getStockPercent() {
            return StockPercent;
        }

        public void setStockPercent(String stockPercent) {
            StockPercent = stockPercent;
        }


        public String getShoudDate() {
            return ShoudDate;
        }

        public void setShoudDate(String shoudDate) {
            ShoudDate = shoudDate;
        }

        public String getInvestType() {
            return InvestType;
        }

        public void setInvestType(String investType) {
            InvestType = investType;
        }

        public String getInvestName() {
            return InvestName;
        }

        public void setInvestName(String investName) {
            InvestName = investName;
        }


        public String getCapiDate() {
            return CapiDate;
        }

        public void setCapiDate(String capiDate) {
            CapiDate = capiDate;
        }
    }
}



