package net.dgg.bigdata.manager.role.dao;

import net.dgg.bigdata.manager.role.entity.RoleOperatePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleOperatePermissionDao {
    /**
     * 角色操作权限关系插入
     * @param roleOperatePermissions
     */
    @InsertProvider(type = RoleOperatePermissionDaoProvider.class,method = "saveSql")
    void save(@Param("roleOperatePermissions") List<RoleOperatePermission> roleOperatePermissions);

    /**
     * 根绝操作权限id删除角色关系
     * @param operatePermissionIds
     */

    @DeleteProvider(type = RoleOperatePermissionDaoProvider.class,method = "removeByRoleIdSql")
    void removeByRoleId(@Param("operatePermissionIds") List<Long> operatePermissionIds, @Param("roleId") Long roleId);

    /**
     * 根绝非菜单操作权限id删除角色关系
     * @param operatePermissionIds
     */
    @DeleteProvider(type = RoleOperatePermissionDaoProvider.class,method = "removeByRoleIdMenuOpermsSql")
    void removeByRoleIdMenuOperms(@Param("operatePermissionIds") List<Long> operatePermissionIds, @Param("roleId") Long roleId);


    /**
     * 根据权限删除关系
     * @param operatePermissionId
     */
    @Delete("DELETE  from role_operate_permission where operate_permission_id=#{operatePermissionId}")
    void removeByPermId(@Param("operatePermissionId") Long operatePermissionId);

    @Select("SELECT count(1) from role_operate_permission where role_id =#{roleId} and operate_permission_id=#{operatePermissionId}")
    int exitsByRoleOperate(@Param("roleId") Long roleId, @Param("operatePermissionId") Long operatePermissionId);

    @Select("SELECT count(1) FROM role_operate_permission a JOIN operate_permission b " +
            " JOIN user_role c ON a.operate_permission_id = b.operate_permission_id " +
            " AND a.role_id = c.role_id WHERE c.user_id = #{userId} and b.`code`= #{code}")
    int exitsOperate4User(Map condition);

    /**
     * 获取菜单相关的权限
     * @param roleId
     * @return
     */
    @Select("select operate_permission_id from menu_operate_permission where menu_id in ( " +
            "SELECT menu_id from role_menu_permission where role_id =#{roleId} )")
    List<Long> findRoleMenuOperate(@Param("roleId") Long roleId);
}
