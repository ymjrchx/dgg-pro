package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 动产抵押
 * Created by jiangsh on 2018/8/17 11:26
 */
public class Dcdy {
    private String cmRegisterNum; //登记编号
    private String cmRegisterTime; //登记时间
    private String cmPublicTime; //公示时间
    private String cmRegisterOrgan; //登记机关
    private String cmClaimNum; //被担保债权数额
    private String cmState; //状态

    public String getCmRegisterNum() {
        return cmRegisterNum;
    }

    public void setCmRegisterNum(String cmRegisterNum) {
        this.cmRegisterNum = cmRegisterNum;
    }

    public String getCmRegisterTime() {
        return cmRegisterTime;
    }

    public void setCmRegisterTime(String cmRegisterTime) {
        this.cmRegisterTime = cmRegisterTime;
    }

    public String getCmPublicTime() {
        return cmPublicTime;
    }

    public void setCmPublicTime(String cmPublicTime) {
        this.cmPublicTime = cmPublicTime;
    }

    public String getCmRegisterOrgan() {
        return cmRegisterOrgan;
    }

    public void setCmRegisterOrgan(String cmRegisterOrgan) {
        this.cmRegisterOrgan = cmRegisterOrgan;
    }

    public String getCmClaimNum() {
        return cmClaimNum;
    }

    public void setCmClaimNum(String cmClaimNum) {
        this.cmClaimNum = cmClaimNum;
    }

    public String getCmState() {
        return cmState;
    }

    public void setCmState(String cmState) {
        this.cmState = cmState;
    }
}
