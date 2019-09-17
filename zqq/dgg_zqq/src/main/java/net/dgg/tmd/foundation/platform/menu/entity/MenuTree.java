package net.dgg.tmd.foundation.platform.menu.entity;

import java.util.Date;
import java.util.List;

/**
 * 树形菜单结构
 * Created by wu on 2018-02-24.
 */
public class MenuTree {
    //父节点ID
    private long pId;

    //菜单ID
    private long menuId;

    //菜单名称
    private String menuName;

    //菜单URL
    private String menuUrl;

    //菜单编号
    private String menuCode;

    //菜单排序
    private Integer sortNum;

    //备注
    private String remark;

    //图标
    private String icon;

    //创建人ID
    private long creatorId;

    //创建时间
    private Date createtime;

    //更新人ID
    private long updatorId;

    //更新时间
    private Date updatetime;

    //子节点
    private List<MenuTree> children;

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
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

    public List<MenuTree> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }
}
