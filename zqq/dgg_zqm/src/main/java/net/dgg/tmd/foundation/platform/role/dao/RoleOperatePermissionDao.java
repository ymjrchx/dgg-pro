package net.dgg.tmd.foundation.platform.role.dao;

import net.dgg.tmd.foundation.platform.role.entity.RoleOperatePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleOperatePermissionDao {
    /**
     * 角色操作权限关系插入
     * @param roleOperatePermissions
     */
    void save(@Param("roleOperatePermissions") List<RoleOperatePermission> roleOperatePermissions);

    /**
     * 根绝操作权限id删除角色关系
     * @param operatePermissionIds
     */
    void removeByRoleId(@Param("operatePermissionIds")List<Long> operatePermissionIds,@Param("roleId") Long roleId);

    /**
     * 根绝非菜单操作权限id删除角色关系
     * @param operatePermissionIds
     */
    void removeByRoleIdMenuOperms(@Param("operatePermissionIds")List<Long> operatePermissionIds, @Param("roleId") Long roleId);


    /**
     * 根据权限删除关系
     * @param operatePermissionId
     */
    void removeByPermId(@Param("operatePermissionId")Long operatePermissionId);

    int exitsByRoleOperate(@Param("roleId")Long roleId,@Param("operatePermissionId")Long  operatePermissionId);

    int exitsOperate4User(Map condition);

    /**
     * 获取菜单相关的权限
     * @param roleId
     * @return
     */
    List<Long> findRoleMenuOperate(@Param("roleId") Long roleId);
}
