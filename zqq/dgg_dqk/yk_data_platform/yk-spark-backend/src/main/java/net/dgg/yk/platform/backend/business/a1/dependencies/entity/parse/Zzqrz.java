package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 著作权软著查询-软件著作权多重查询
 * Created by jiangsh on 2018/6/5 11:04
 */
public class Zzqrz {
    private String category; //分类号
    private String publishDate; //发布日期
    private String versionNo; //版本号
    private String registerNo; //登记号
    private String registerAperDate; //登记批准日期
    private String name; //软件全称
    private String shortName; //软件简称
    private String owner;  //软件著作权人

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getRegisterAperDate() {
        return registerAperDate;
    }

    public void setRegisterAperDate(String registerAperDate) {
        this.registerAperDate = registerAperDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
