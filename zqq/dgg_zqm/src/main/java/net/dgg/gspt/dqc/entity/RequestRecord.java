package net.dgg.gspt.dqc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;


@Document(collection = "gspt_dqc_request_record")
public class RequestRecord implements Serializable {

    @Id
    @Field("id")
    private String id;
    @Field("token")
    private String token;
    @Field("ip")
    private String ip;
    @Field("url")
    private String url;
    @Field("paras_json")
    private String parasJson;
    @Field("create_time")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParasJson() {
        return parasJson;
    }

    public void setParasJson(String parasJson) {
        this.parasJson = parasJson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
