package net.dgg.bigdata.sjjs.web.entity.dto.search.clues;

/**
 * @ClassName: YkCluesRecord
 * @Description: 线索转化记录
 * @Author: jiangsh
 * @Date: 2018/12/18 11:36
 */
public class YkCluesRecord {
    private String num; //工号
    private String userId; //用户ID
    private String comId; //企业ID
    private String comName; //企业名称
    private String comWebSite; //企业官网
    private String status; //跟进状态
    private String toWhere; //转换去处
    private String reason; //原因
    private String chargePerson; //负责人
    private String chargeDept; //负责人部门
    private String statusUpdateTime; //状态更新时间，格式：2017-05-15T00:00:00

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComWebSite() {
        return comWebSite;
    }

    public void setComWebSite(String comWebSite) {
        this.comWebSite = comWebSite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToWhere() {
        return toWhere;
    }

    public void setToWhere(String toWhere) {
        this.toWhere = toWhere;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargeDept() {
        return chargeDept;
    }

    public void setChargeDept(String chargeDept) {
        this.chargeDept = chargeDept;
    }

    public String getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(String statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }
}
