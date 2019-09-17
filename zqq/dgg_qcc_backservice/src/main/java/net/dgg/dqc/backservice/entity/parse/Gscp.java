package net.dgg.dqc.backservice.entity.parse;

/**
 * 公司产品查询
 * Created by jiangsh on 2018/6/5 11:17
 */
public class Gscp {
    private String CompanyName; //公司名称
    private String Link; //公司的关联链接
    private String ImageUrl; //产品的图片
    private String Name; //产品名称
    private String Domain; //产品领域
    private String Tags; //产品标签
    private String Description; //产品描述
    private String Category; //产品类型

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
