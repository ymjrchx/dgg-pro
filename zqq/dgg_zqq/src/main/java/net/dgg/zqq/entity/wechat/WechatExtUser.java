package net.dgg.zqq.entity.wechat;

import com.google.gson.annotations.SerializedName;
import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <WechatUserExt>
 * @despriction 微信用户扩展实体表 , 此表 userId 与 User 实体  userId 一致
 * @create 2018/09/17 16:03
 **/
public class WechatExtUser extends BaseEntity {


    private String userId; // 用户ID

    @SerializedName("openid")
    private String openId; // 普通用户的标识，对当前开发者帐号唯一

    @SerializedName("unionid")
    private String unionId; // 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。

    @SerializedName("nickname")
    private String nickName; // 微信普通用户昵称

    private Integer sex; //  1为男性，2为女性

    private String city; // 普通用户个人资料填写的城市

    private String province; // 普通用户个人资料填写的省份

    private String country;//	国家，如中国为CN

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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
