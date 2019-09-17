package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 公司产品查询
 * Created by jiangsh on 2018/6/5 11:17
 */
public class Gscp {
    private String companyName; //公司名称
    private String link; //公司的关联链接
    private String imageUrl; //产品的图片
    private String name; //产品名称
    private String domain; //产品领域
    private String tags; //产品标签
    private String description; //产品描述
    private String category; //产品类型

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
