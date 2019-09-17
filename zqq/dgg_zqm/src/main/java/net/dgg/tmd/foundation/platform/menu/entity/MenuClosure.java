package net.dgg.tmd.foundation.platform.menu.entity;

import java.util.Date;

public class MenuClosure {
    //ID
    private long id;

    //菜单ID
    private long menuId;

    //祖先ID
    private long ancestorId;

    //菜单ID距离祖先ID多少
    private long distance;

    //创建人ID
    private long creatorId;

    //创建时间
    private Date createtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(long ancestorId) {
        this.ancestorId = ancestorId;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
