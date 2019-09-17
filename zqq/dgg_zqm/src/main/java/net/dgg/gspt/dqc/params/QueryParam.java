package net.dgg.gspt.dqc.params;

/**
 * Created by jiangsh on 2018/7/3 19:00
 */
public class QueryParam {

    private String times;

    //条件
    private String econKind; //企业类型
    private String status; //企业状态
    private String registCapi; //注册资金，传code，格式“500-1000”，搜索500-1000万之间
    private String startDate; //成立日期，格式“2015”
    private String province; //省份
    private String industry; //行业
    
    private String keyWord; //关键字

    //必传
    private String type;
    private int pageIndex;
    private int pageSize;

    private String proCode;  //省份code
    private String indCode;  //行业code
    private String isLeafPro;  //省份是否叶子节点， 0 叶子节点
    private String isLeafInd;  //行业是否叶子节点

    private String orgType; //机构类型
    private String senior; //高级
    private String content; //内容

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getIndCode() {
        return indCode;
    }

    public void setIndCode(String indCode) {
        this.indCode = indCode;
    }

    public String getIsLeafPro() {
        if (isLeafPro == null) isLeafPro = "empty";
        return isLeafPro;
    }

    public void setIsLeafPro(String isLeafPro) {
        this.isLeafPro = isLeafPro;
    }

    public String getIsLeafInd() {
        if (isLeafInd == null) isLeafInd = "empty";
        return isLeafInd;
    }

    public void setIsLeafInd(String isLeafInd) {
        this.isLeafInd = isLeafInd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getEconKind() {
        return econKind;
    }

    public void setEconKind(String econKind) {
        this.econKind = econKind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistCapi() {
        return registCapi;
    }

    public void setRegistCapi(String registCapi) {
        this.registCapi = registCapi;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSenior() {
        return senior;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }
}
