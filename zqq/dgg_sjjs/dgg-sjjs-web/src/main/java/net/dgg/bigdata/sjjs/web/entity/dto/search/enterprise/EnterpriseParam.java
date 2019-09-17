package net.dgg.bigdata.sjjs.web.entity.dto.search.enterprise;

/**
 * @ClassName: EnterpriseParam
 * @Description: 企业搜索请求参数
 * @Author: jiangsh
 * @Date: 2018/12/11 9:51
 */
public class EnterpriseParam {

    private String keyWord; //关键字

    private String queryScope; //查询范围，传值： name,headPerson,major,brand,tel; 多个用“、”分隔；
    private String status; //企业状态
    private String regCapital; //注册资本
    private String regTime; //注册时长，传值： “成立不足1年”-> "<1"， “成立1-3年” -> "1-3"， “成立15年以上” -> ">15"
    private String province; //省份
    private String industry; //行业

    private String phone; //是否有联系电话，  有 1， 没有 0
    private String email; //是否有邮箱，  有 1， 没有 0

    private int pageIndex;
    private int pageSize;

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

    public String getQueryScope() {
        return queryScope;
    }

    public void setQueryScope(String queryScope) {
        this.queryScope = queryScope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
