package net.dgg.bigdata.manager.permission.entity;

import net.dgg.bigdata.manager.menu.entity.MenuMain;

import java.util.List;

/**
 * 用户Access权限实体类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/6
 * Time:13:40
 */
public class UserAccessPermissionEntity {
    //菜单List
    private List<MenuMain> menuMainList;

    //操作权限List
    private List<String> operatePermissionList;

    public List<MenuMain> getMenuMainList() {
        return menuMainList;
    }

    public void setMenuMainList(List<MenuMain> menuMainList) {
        this.menuMainList = menuMainList;
    }

    public List<String> getOperatePermissionList() {
        return operatePermissionList;
    }

    public void setOperatePermissionList(List<String> operatePermissionList) {
        this.operatePermissionList = operatePermissionList;
    }
}
