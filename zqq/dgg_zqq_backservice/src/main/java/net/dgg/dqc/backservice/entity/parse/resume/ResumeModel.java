package net.dgg.dqc.backservice.entity.parse.resume;

/**
 *  简历Model
 * Created by jiangsh on 2018/7/13 11:01
 */
public class ResumeModel {
    private String url; //网址
    private String name; //名称
    private String sex; //性别
    private String age; //年龄
    private String education; //教育
    private String experience; //工作经验
    private String area;  //区域
    private String labels; //标签
    private String certificate_type; //证书类型
    private String certificate_status; //证书状态
    private String salary; //工资
    private String insurance_area; //保险区域
    private String other_note; //备注
    private String image_url; //图片来源
    private String type; //类型

    private String region ; //所属区域
    private String updateTime; //更新时间

    private String hideEndTime; //隐藏时间

    public String getHideEndTime() {
        return hideEndTime;
    }

    public void setHideEndTime(String hideEndTime) {
        this.hideEndTime = hideEndTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_status() {
        return certificate_status;
    }

    public void setCertificate_status(String certificate_status) {
        this.certificate_status = certificate_status;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getInsurance_area() {
        return insurance_area;
    }

    public void setInsurance_area(String insurance_area) {
        this.insurance_area = insurance_area;
    }

    public String getOther_note() {
        return other_note;
    }

    public void setOther_note(String other_note) {
        this.other_note = other_note;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
