package net.dgg.dqc.backservice.entity.dealPorvinceName;


import net.dgg.dqc.backservice.entity.MongoBaseEntity;

/**
 * @author 刘阳
 * @ClassName <Address>
 * @despriction
 * @create 2018/07/13 10:29
 **/
public class Address extends MongoBaseEntity {
    String dengji_zhusuo;
    String address;
    String province;

    public String getDengji_zhusuo() {
        return dengji_zhusuo;
    }

    public void setDengji_zhusuo(String dengji_zhusuo) {
        this.dengji_zhusuo = dengji_zhusuo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
