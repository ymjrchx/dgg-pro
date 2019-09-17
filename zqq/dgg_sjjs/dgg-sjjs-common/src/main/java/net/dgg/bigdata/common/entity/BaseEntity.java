package net.dgg.bigdata.common.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 *
 * @author 徐哲
 * @create 2017-11-01 14:22
 */
public class BaseEntity implements Serializable {
    /**
     * 序列 化号
     */
    private static final long serialVersionUID = -540287793770056213L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人ID
     */
    private Long createrId;
    /**
     * 创建人部门ID
     */
    private Long createrOrgId;
    /**
     * 创建人姓名
     */
    private String createrName;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 修改人ID
     */
    private Long updaterId;
    /**
     * 修改人姓名
     */
    private String updaterName;
    /**
     * 修改人部门ID
     */
    private Long updaterOrgId;

    /**
     * 设置创建人ID,创建人姓名,创建时间(当前)
     *
     * @param user 用户User
     * @author: hanfeng
     */
    public void setCreateUser(UserEntity user) {
        this.createTime = new Date();
        if (null != user) {
//            this.createrId = user.getId();
            this.createrName = user.getRealName() + user.getLoginName();
            this.createrOrgId = user.getOrgId();
        }
    }

    /**
     * 设置修改人ID,修改人姓名,修改时间(当前)
     *
     * @param user
     * @author: hanfeng
     */
    public void setUpdaterUser(UserEntity user) {
        this.updateTime = new Date();
        if (null != user) {
//            this.updaterId = user.getId();
            this.updaterName = user.getRealName() + user.getLoginName();
            this.updaterOrgId = user.getOrgId();
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getCreaterOrgId() {
        return createrOrgId;
    }

    public void setCreaterOrgId(Long createrOrgId) {
        this.createrOrgId = createrOrgId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public Long getUpdaterOrgId() {
        return updaterOrgId;
    }

    public void setUpdaterOrgId(Long updaterOrgId) {
        this.updaterOrgId = updaterOrgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}