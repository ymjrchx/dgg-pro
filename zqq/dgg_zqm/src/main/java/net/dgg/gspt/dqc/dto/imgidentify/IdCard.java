package net.dgg.gspt.dqc.dto.imgidentify;

/**
 * @ClassName: IdCard
 * @Description: 身份证传输对象
 * @Author: jiangsh
 * @Date: 2018/11/14 15:16
 */
public class IdCard {

    //正面
    private String address; //地址
    private String birthday; //出生日期
    private String name; //姓名
    private String no; //身份证号
    private String gender; //性别
    private String national; //民族

    //反面
    private String startDate; //签发日期
    private String endDate; //失效日期
    private String org; //签发机关

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }
}
