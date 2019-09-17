package net.dgg.bigdata.sjjs.web.entity.dto.search.clues;

/**
 * @ClassName: YkClues
 * @Description: 线索转化
 * @Author: jiangsh
 * @Date: 2018/12/20 14:46
 */
public class YkClues {
    private String num; //工号
    private String comId; //公司id
    private String comName; //公司名
    private String userId; //用户id
    private String status; //线索当前最新状态
    private String legenPerson; //负责人
    private String legenDept; //负责人部门
    private String updateTime; //更新时间，格式：2017-05-15T00:00:00

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLegenPerson() {
        return legenPerson;
    }

    public void setLegenPerson(String legenPerson) {
        this.legenPerson = legenPerson;
    }

    public String getLegenDept() {
        return legenDept;
    }

    public void setLegenDept(String legenDept) {
        this.legenDept = legenDept;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
