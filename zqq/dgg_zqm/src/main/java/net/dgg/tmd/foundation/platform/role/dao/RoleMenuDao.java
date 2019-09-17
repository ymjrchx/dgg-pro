package net.dgg.tmd.foundation.platform.role.dao;

import net.dgg.tmd.foundation.platform.role.entity.RoleMenuPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuDao {
    /**
     * 报错角色和菜单的关系
     * @param roleMenuPermissions 插入的关系集合
     */
    void save(@Param("roleMenuPermissions") List<RoleMenuPermission> roleMenuPermissions);

    /**
     * 根绝角色id删除菜单关系
     * @param roleId
     */
    void removeByRoleId(@Param("roleId")Long roleId);

    /**
     * 根据菜单id删除角色菜单关系
     * @param menuIds
     */
    void removeByMenuId(@Param("menuIds")List<Long> menuIds);

    /**
     * 根据菜单id和角色id查询关系
     */
    int findByRoleMenu(@Param("roleId") Long roleId,@Param("menuId") Long menuId);
}
