package net.dgg.zqq.entity.qq;

import com.google.gson.annotations.SerializedName;
import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <QQExtUser>
 * @despriction QQ 扩展用户
 * @create 2018/10/18 19:14
 **/
public class QQExtUser extends BaseEntity {

    /**
     * 主表用户ID
     */
    private String userId;
    /**
     * openId
     */
    private String openId;

    /**
     * ret : 0
     * msg :
     * is_lost : 0
     * gender : 男
     * is_yellow_vip : 0
     * city :
     * year : 0
     * level : 0
     * figureurl_2 : http://qzapp.qlogo.cn/qzapp/101508189/3CE3BD7D55094534F71DDABE9BBAC367/100
     * figureurl_1 : http://qzapp.qlogo.cn/qzapp/101508189/3CE3BD7D55094534F71DDABE9BBAC367/50
     * is_yellow_year_vip : 0
     * province :
     * constellation :
     * figureurl : http://qzapp.qlogo.cn/qzapp/101508189/3CE3BD7D55094534F71DDABE9BBAC367/30
     * nickname : 知千秋
     * yellow_vip_level : 0
     * figureurl_qq_1 : http://thirdqq.qlogo.cn/qqapp/101508189/3CE3BD7D55094534F71DDABE9BBAC367/40
     * vip : 0
     * figureurl_qq_2 : http://thirdqq.qlogo.cn/qqapp/101508189/3CE3BD7D55094534F71DDABE9BBAC367/100
     */
    private Integer ret;

    @SerializedName("is_lost")
    private Integer isLost;

    private String gender;

    @SerializedName("is_yellow_vip")
    private String isYellowVip;
    private String city;
    private String year;
    private String level;

    @SerializedName("figureurl_2")
    private String figureurl2;
    @SerializedName("figureurl_1")
    private String figureurl1;

    @SerializedName("is_yellow_year_vip")
    private String isYellowYearVip;
    private String province;
    private String constellation;
    private String figureurl;
    private String nickname;
    @SerializedName("yellow_vip_level")
    private String yellowVipLevel;

    @SerializedName("figureurl_qq_1")
    private String figureurlQq1;
    private String vip;
    @SerializedName("figureurl_qq_2")
    private String figureurlQq2;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public Integer getIsLost() {
        return isLost;
    }

    public void setIsLost(Integer isLost) {
        this.isLost = isLost;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

}
