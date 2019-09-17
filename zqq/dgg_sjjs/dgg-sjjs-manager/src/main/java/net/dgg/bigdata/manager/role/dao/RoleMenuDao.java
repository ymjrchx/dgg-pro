package net.dgg.bigdata.manager.role.dao;

import net.dgg.bigdata.manager.role.entity.RoleMenuPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMenuDao {
    /**
     * 报错角色和菜单的关系
     * @param roleMenuPermissions 插入的关系集合
     */
    @InsertProvider(type = RoleMenuDaoProvider.class,method = "saveSql")
    void save(@Param("roleMenuPermissions") List<RoleMenuPermission> roleMenuPermissions);

    /**
     * 根绝角色id删除菜单关系
     * @param roleId
     */
    @Delete(" DELETE  from role_menu_permission where role_id=#{roleId}")
    void removeByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据菜单id删除角色菜单关系
     * @param menuIds
     */

    @DeleteProvider(type = RoleMenuDaoProvider.class,method = "removeByMenuIdSql")
    void removeByMenuId(@Param("menuIds") List<Long> menuIds);

    /**
     * 根据菜单id和角色id查询关系
     */
    @Select("select count(1) from role_menu_permission where role_id=#{roleId} and menu_id=#{menuId}")
    int findByRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);
}
