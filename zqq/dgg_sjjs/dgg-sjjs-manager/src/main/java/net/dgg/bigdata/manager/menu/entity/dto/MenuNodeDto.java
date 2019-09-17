package net.dgg.bigdata.manager.menu.entity.dto;

import java.util.Date;

public class MenuNodeDto {
    //菜单ID
    private long menuId;

    //菜单名
    private String menuName;

    //菜单URL
    private String menuUrl;

    //菜单编号
    private String menuCode;

    //排序号
    private int sortNum;

    //备注
    private String remark;

    //菜单图标
    private String icon;

    //创建人ID
    private long creatorId;

    //创建时间
    private Date createtime;

    //更新人ID
    private long updatorId;

    //更新时间
    private Date updatetime;

    //祖先ID
    private long ancestorId;

    //角色ID
    private long roleId;

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(long updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public long getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(long ancestorId) {
        this.ancestorId = ancestorId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
