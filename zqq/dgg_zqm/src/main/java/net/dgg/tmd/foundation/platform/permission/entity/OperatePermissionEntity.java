package net.dgg.tmd.foundation.platform.permission.entity;

import java.util.Date;

/**
 * 操作权限实体
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
public class OperatePermissionEntity {
    //操作权限ID
    private Long operatePermissionId;

    //操作权限名称
    private String operatePermissionName;

    //操作权限Code
    private String code;

    //备注
    private String remark;

    //创建人ID
    private Long creatorId;

    //创建时间
    private Date createtime;

    public Long getOperatePermissionId() {
        return operatePermissionId;
    }

    public void setOperatePermissionId(Long operatePermissionId) {
        this.operatePermissionId = operatePermissionId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getOperatePermissionName() {
        return operatePermissionName;
    }

    public void setOperatePermissionName(String operatePermissionName) {
        this.operatePermissionName = operatePermissionName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "OperatePermissionEntity{" +
                "operatePermissionId=" + operatePermissionId +
                ", operatePermissionName='" + operatePermissionName + '\'' +
                ", code='" + code + '\'' +
                ", creatorId=" + creatorId +
                ", createtime=" + createtime +
                '}';
    }
}
