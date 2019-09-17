package net.dgg.dqc.backservice.entity.parse;

/**
 * 著作权软著查询-软件著作权多重查询
 * Created by jiangsh on 2018/6/5 11:04
 */
public class Zzqrz {
    private String Category; //分类号
    private String PublishDate; //发布日期
    private String VersionNo; //版本号
    private String RegisterNo; //登记号
    private String RegisterAperDate; //登记批准日期
    private String Name; //软件全称
    private String ShortName; //软件简称
    private String Owner;  //软件著作权人

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    public String getVersionNo() {
        return VersionNo;
    }

    public void setVersionNo(String versionNo) {
        VersionNo = versionNo;
    }

    public String getRegisterNo() {
        return RegisterNo;
    }

    public void setRegisterNo(String registerNo) {
        RegisterNo = registerNo;
    }

    public String getRegisterAperDate() {
        return RegisterAperDate;
    }

    public void setRegisterAperDate(String registerAperDate) {
        RegisterAperDate = registerAperDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}
