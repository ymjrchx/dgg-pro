package net.dgg.bigdata.manager.menu.dao;

import net.dgg.bigdata.manager.menu.entity.MenuOperatePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuOperatePermissionDao {
    /**
     * 保存菜单-权限关系
     */
    @InsertProvider(type = MenuOperatePermissionDaoProvider.class,method = "saveSql")
    void save(@Param("menuOperatePermissions") List<MenuOperatePermission> menuOperatePermissions);

    /**
     * 删除菜单-权限关系
     * @param operatePermissionIds
     * @param menuId
     */
    @DeleteProvider(type = MenuOperatePermissionDaoProvider.class,method = "removeByMenuIdSql")
    void removeByMenuId(@Param("operatePermissionIds") List<Long> operatePermissionIds, @Param("menuId") Long menuId);

    @Delete("DELETE  from menu_operate_permission where operate_permission_id=#{operatePermissionId}")
    void removeByPermId(@Param("operatePermissionId") Long operatePermissionId);
    /**
     * 根据菜单id查询权限id
     */
    @Select("select operate_permission_id from menu_operate_permission where menu_id = #{menuId}")
    List<Long> queryOpmsIdsByMenuId(@Param("menuId") Long menuId);
}
