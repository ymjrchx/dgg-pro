package net.dgg.dqc.backservice.entity.parse.qygs;

import net.dgg.dqc.backservice.entity.parse.qyfj.ContactInfo;

import java.util.List;

/**
 * 企业工商数据查询
 * Created by jiangsh on 2018/6/4 15:20
 */
public class QygsDetail {
    private String keyNo; //内部KeyNo
    private String name; //公司名称
    private String no; //注册号
    private String belongOrg; //登记机关
    private String operName; //法人名
    private String startDate; //成立日期
    private String endDate; //吊销日期
    private String status; //企业状态
    private String province; //省份
    private String updatedDate; //更新日期
    private String creditCode; //社会统一信用代码
    private double registCapi; //注册资本
    private double realCapi; //实缴资本
    private String econKind; //企业类型
    private String address; //地址
    private String scope; //经营范围
    private String termStart; //营业开始日期
    private String teamEnd; //营业结束日期
    private String checkDate; //发照日期
    private String orgNo; //组织机构代码
    private String orgType; //组织机构类型
    private String isOnStock; //是否上市(0为未上市，1为上市)
    private String stockNumber; //上市公司代码
    private String stockType; //上市类型
    private String originalName; //曾用名

    private String englishName; //英文名
    private String personNum; //人员规模
    private String cbPersonNum; //参保人数

    private int categoryScore; //行业评分
    private int percentileScore; //公司评分

    private List<Partners> partnersList; //股东信息
    private List<Employees> employeesList; //主要人员
    private List<Branches> branchesList; //分支机构
    private List<ChangeRecords> changeRecordsList; //变更信息
    private List<ContactInfo> contactInfoList; //联系信息
    private String industry; //行业信息

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

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


    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        this.belongOrg = belongOrg;
    }

    public String getOperName() {
        return operName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public double getRegistCapi() {
        return registCapi;
    }

    public void setRegistCapi(double registCapi) {
        this.registCapi = registCapi;
    }

    public double getRealCapi() {
        return realCapi;
    }

    public void setRealCapi(double realCapi) {
        this.realCapi = realCapi;
    }

    public String getEconKind() {
        return econKind;
    }

    public void setEconKind(String econKind) {
        this.econKind = econKind;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTeamEnd() {
        return teamEnd;
    }

    public void setTeamEnd(String teamEnd) {
        this.teamEnd = teamEnd;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getIsOnStock() {
        return isOnStock;
    }

    public void setIsOnStock(String isOnStock) {
        this.isOnStock = isOnStock;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getCategoryScore() {
        return categoryScore;
    }

    public void setCategoryScore(int categoryScore) {
        this.categoryScore = categoryScore;
    }

    public int getPercentileScore() {
        return percentileScore;
    }

    public void setPercentileScore(int percentileScore) {
        this.percentileScore = percentileScore;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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
        private String name; //姓名
        private String job; //职位

        private String sciUrl; //
        private String sciName; //
        private String sciPath; //

        public String getSciUrl() {
            return sciUrl;
        }

        public void setSciUrl(String sciUrl) {
            this.sciUrl = sciUrl;
        }

        public String getSciName() {
            return sciName;
        }

        public void setSciName(String sciName) {
            this.sciName = sciName;
        }

        public String getSciPath() {
            return sciPath;
        }

        public void setSciPath(String sciPath) {
            this.sciPath = sciPath;
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
    }

    public static class Branches{
        private String companyId; //公司ID
        private String regNo; //注册号
        private String name; //公司名称
        private String belongOrg; //登记机关
        private String creditCode; //社会统一信用代码
        private String operName; //法人姓名或负责人姓名
        private String regTime; //注册时间
        private String regStatus; //状态
        private String license; //营业执照
        private String regMoney; //注册金额

        public String getRegMoney() {
            return regMoney;
        }

        public void setRegMoney(String regMoney) {
            this.regMoney = regMoney;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getRegStatus() {
            return regStatus;
        }

        public void setRegStatus(String regStatus) {
            this.regStatus = regStatus;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBelongOrg() {
            return belongOrg;
        }

        public void setBelongOrg(String belongOrg) {
            this.belongOrg = belongOrg;
        }

        public String getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(String creditCode) {
            this.creditCode = creditCode;
        }

        public String getOperName() {
            return operName;
        }

        public void setOperName(String operName) {
            this.operName = operName;
        }
    }

    public static class ChangeRecords{
        private String projectName; //变更事项
        private String beforeContent; //变更前内容
        private String afterContent; //变更后内容
        private String changeDate; //变更日期

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getBeforeContent() {
            return beforeContent;
        }

        public void setBeforeContent(String beforeContent) {
            this.beforeContent = beforeContent;
        }

        public String getAfterContent() {
            return afterContent;
        }

        public void setAfterContent(String afterContent) {
            this.afterContent = afterContent;
        }

        public String getChangeDate() {
            return changeDate;
        }

        public void setChangeDate(String changeDate) {
            this.changeDate = changeDate;
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
        private String stockName; //股东
        private String stockType; //股东类型
        private String stockPercent; //出资比例
        private String shouldCapi; //认缴出资额
        private String shoudDate; //认缴出资时间
        private String investType; //认缴出资方式
        private String investName; //实际出资方式
        private String realCapi; //实缴出资额
        private String capiDate; //实缴时间

        public String getStockName() {
            return stockName;
        }

        public void setStockName(String stockName) {
            this.stockName = stockName;
        }

        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }

        public String getStockPercent() {
            return stockPercent;
        }

        public void setStockPercent(String stockPercent) {
            this.stockPercent = stockPercent;
        }

        public String getShouldCapi() {
            return shouldCapi;
        }

        public void setShouldCapi(String shouldCapi) {
            this.shouldCapi = shouldCapi;
        }

        public String getShoudDate() {
            return shoudDate;
        }

        public void setShoudDate(String shoudDate) {
            this.shoudDate = shoudDate;
        }

        public String getInvestType() {
            return investType;
        }

        public void setInvestType(String investType) {
            this.investType = investType;
        }

        public String getInvestName() {
            return investName;
        }

        public void setInvestName(String investName) {
            this.investName = investName;
        }

        public String getRealCapi() {
            return realCapi;
        }

        public void setRealCapi(String realCapi) {
            this.realCapi = realCapi;
        }

        public String getCapiDate() {
            return capiDate;
        }

        public void setCapiDate(String capiDate) {
            this.capiDate = capiDate;
        }
    }
}



