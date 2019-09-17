package net.dgg.gspt.dqc.entity.checkCompany;


import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <CheckCompanyName>
 * @despriction 接口核名的企业名字
 * @create 2018/07/20 10:06
 **/
public class CheckCompanyRecord extends BaseEntity {
    private String city; //城市
    private String name; // 搜索的企业名字
    private String industry; // 行业
    private String companyType; // 公司类型
    private String key; // 调用key
    private Integer result; // 0 成功  1 无结果返回

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
