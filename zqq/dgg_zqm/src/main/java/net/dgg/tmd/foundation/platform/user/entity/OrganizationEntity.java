package net.dgg.tmd.foundation.platform.user.entity;

import java.util.Date;

/**
 * 组织机构实体类
 */
public class OrganizationEntity {
    //资源组织ID
    private long orgId;

    //资源组织code
    private String code;

    //资源组织名称
    private String name;

    //描述
    private String description;

    //排序号
    private int sort;

    //管理者用户ID
    private long leaderId;

    //是否启用：1，启用；0，禁用
    private boolean enabled;

    //最后一次更新时间
    private Date updateTime;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(long leaderId) {
        this.leaderId = leaderId;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "orgId=" + orgId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sort=" + sort +
                ", leaderId=" + leaderId +
                ", enabled=" + enabled +
                ", updateTime=" + updateTime +
                '}';
    }
}
