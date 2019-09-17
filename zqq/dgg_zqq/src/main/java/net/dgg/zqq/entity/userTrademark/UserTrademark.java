package net.dgg.zqq.entity.userTrademark;

import net.dgg.zqq.entity.BaseEntity;

import java.util.Date;

/**
 * @author 刘阳
 * @ClassName <UserTrademark>
 * @despriction 用户导入的商标
 * @create 2018/09/28 17:01
 **/
public class UserTrademark extends BaseEntity {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商标文件地址
     */
    private String trademarkFile;

    /**
     * 商标名称
     */
    private String name;


    /**
     * 注册号
     */
    private String registerNo;

    /**
     * 商标大类编号
     */
    private String classLevel1Code;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 申请人
     */
    private String applicant;


    /**
     * 申请人ID
     */
    private String applicantId;


    /*
     * 0删除  1正常
     */
    private Integer flag;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrademarkFile() {
        return trademarkFile;
    }

    public void setTrademarkFile(String trademarkFile) {
        this.trademarkFile = trademarkFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getClassLevel1Code() {
        return classLevel1Code;
    }

    public void setClassLevel1Code(String classLevel1Code) {
        this.classLevel1Code = classLevel1Code;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
