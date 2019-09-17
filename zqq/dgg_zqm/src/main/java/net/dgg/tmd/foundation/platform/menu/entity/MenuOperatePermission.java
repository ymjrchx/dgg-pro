package net.dgg.tmd.foundation.platform.menu.entity;

import java.util.Date;

public class MenuOperatePermission {
    //ID
    private Long id;

    //菜单ID
    private Long menuId;

    //操作权限ID
    private Long operatePermissionId;

    //创建人ID
    private Long creatorId;

    //创建时间
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOperatePermissionId() {
        return operatePermissionId;
    }

    public void setOperatePermissionId(Long operatePermissionId) {
        this.operatePermissionId = operatePermissionId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
