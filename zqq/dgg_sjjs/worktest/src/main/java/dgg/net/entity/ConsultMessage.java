package dgg.net.entity;

import java.util.Date;

public class ConsultMessage extends ConsultMessageKey {
    private String name;

    private String tel;

    private String veri;

    private Integer verinum;

    private String type;

    private String device;

    private String place;

    private String web;

    private String url;

    private Integer status;

    private String ip;

    private String key;

    private String handler;

    private Date handlerat;

    private String crmid;

    private String flag1;

    private String flag2;

    private String flag3;

    private String frontIp;

    private Boolean isImport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getVeri() {
        return veri;
    }

    public void setVeri(String veri) {
        this.veri = veri == null ? null : veri.trim();
    }

    public Integer getVerinum() {
        return verinum;
    }

    public void setVerinum(Integer verinum) {
        this.verinum = verinum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device == null ? null : device.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web == null ? null : web.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    public Date getHandlerat() {
        return handlerat;
    }

    public void setHandlerat(Date handlerat) {
        this.handlerat = handlerat;
    }

    public String getCrmid() {
        return crmid;
    }

    public void setCrmid(String crmid) {
        this.crmid = crmid == null ? null : crmid.trim();
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1 == null ? null : flag1.trim();
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2 == null ? null : flag2.trim();
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3 == null ? null : flag3.trim();
    }

    public String getFrontIp() {
        return frontIp;
    }

    public void setFrontIp(String frontIp) {
        this.frontIp = frontIp == null ? null : frontIp.trim();
    }

    public Boolean getIsImport() {
        return isImport;
    }

    public void setIsImport(Boolean isImport) {
        this.isImport = isImport;
    }
}