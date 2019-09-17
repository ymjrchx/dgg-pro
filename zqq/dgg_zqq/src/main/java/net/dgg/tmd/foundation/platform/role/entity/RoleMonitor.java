package net.dgg.tmd.foundation.platform.role.entity;

/**
 * 监控角色实体类
 */
public class RoleMonitor {
    //ID
    private Long id;

    //角色ID
    private Long roleId;

    //监控ID
    private Long monitorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }
}
