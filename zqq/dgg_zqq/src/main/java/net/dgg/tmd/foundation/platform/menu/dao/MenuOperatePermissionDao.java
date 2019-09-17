package net.dgg.tmd.foundation.platform.menu.dao;

import net.dgg.tmd.foundation.platform.menu.entity.MenuOperatePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuOperatePermissionDao {
    /**
     * 保存菜单-权限关系
     */
    void save(@Param("menuOperatePermissions") List<MenuOperatePermission> menuOperatePermissions);

    /**
     * 删除菜单-权限关系
     * @param operatePermissionIds
     * @param menuId
     */
    void removeByMenuId(@Param("operatePermissionIds")List<Long> operatePermissionIds, @Param("menuId") Long menuId);

    void removeByPermId(@Param("operatePermissionId") Long operatePermissionId);
    /**
     * 根据菜单id查询权限id
     */
    List<Long> queryOpmsIdsByMenuId(@Param("menuId") Long menuId);
}
