package net.dgg.bigdata.sjjs.web.entity;

import net.dgg.base.baseDao.Table;
import net.dgg.bigdata.common.entity.BaseEntity;

/**
 * @ClassName: Company
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/12/11 15:07
 */

@Table(name = "yk_company", idName = "userId")
public class Company extends BaseEntity {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 企业全称
     */
    private String companyName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人邮件
     */
    private String contactEmail;

    /**
     * 客户经理代码
     */
    private String customerManagerCode;

    /**
     * 是否认证 0未认证  1人认证
     */
    private Integer isAuth;

    /**
     * 状态 status1启用 status2禁用
     */
    private String status;

    /**
     * 是否删除 0删除  1未删除
     */
    private Integer flag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCustomerManagerCode() {
        return customerManagerCode;
    }

    public void setCustomerManagerCode(String customerManagerCode) {
        this.customerManagerCode = customerManagerCode;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}