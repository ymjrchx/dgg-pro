package net.dgg.dqc.backservice.entity.parse;

import java.util.List;

/**
 * 查询法院公告-法院公告详细信息
 * Created by jiangsh on 2018/6/5 11:39
 */
public class Fygg {
    private String Court; //公告法院
    private String Content; //内容
    private String SubmitDate; //上传日期
    private String Province; //所在省份代码
    private String Category; //类别
    private String PublishDate; //刊登日期
    private String Party; //当事人
    private List<NameKeyNoCollection> nameKeyNoColl; //当事人信息

    public String getCourt() {
        return Court;
    }

    public void setCourt(String court) {
        Court = court;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String submitDate) {
        SubmitDate = submitDate;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

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

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public List<NameKeyNoCollection> getNameKeyNoColl() {
        return nameKeyNoColl;
    }

    public void setNameKeyNoColl(List<NameKeyNoCollection> nameKeyNoColl) {
        this.nameKeyNoColl = nameKeyNoColl;
    }

    public static class NameKeyNoCollection{
        private String KeyNo; //公司KeyNo
        private String Name; //名称

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
    }
}


