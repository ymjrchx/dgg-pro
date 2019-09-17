package net.dgg.tmd.foundation.platform.permission.dao;

import net.dgg.tmd.foundation.platform.permission.entity.OperatePermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

public interface OperatePermissionDAO {
    /**
     * 插入权限信息
     * @return
     */
    int insertPermissionInfo(OperatePermissionEntity operatePermissionEntity);

    /**
     * 通过权限实体更新权限信息
     * @return
     */
    int updatePermissionInfo(OperatePermissionEntity operatePermissionEntity);

    /**
     * 通过权限ID删除权限信息
     * @param permsId
     * @return
     */
    int deletePermissionById(@Param("permsId")long permsId);

    /**
     * 通过权限ID获取权限实体
     * @param permsId
     * @return
     */
    OperatePermissionEntity findPermsEntityByPermsId(@Param("permsId")long permsId);

    /**
     * 通过keyWords获取权限实体
     * @param keyWords
     * @return
     */
    List<OperatePermissionEntity> findPermsEntityBykeyWords(@Param("keyWords")Map keyWords);

    /**
     * 根据菜单查询
     * @param menuId
     * @param operatePermissionName 搜索条件
     * @param menuId 菜单是否绑定权限 如果为0：未绑定的数据  1.绑定的数据
     * @return
     */
    List<OperatePermissionEntity> findOperatePermissionByMenuId(@Param("menuId") Long menuId,
                                                                @Param("operatePermissionName")String operatePermissionName,
                                                                @Param("isMenu")int isMenu);

    /**
     * 获取独立权限
     * @param roleId
     * @return
     */
    List<Map> findOperatePermissionByMenuRole(@Param("roleId") Long roleId, @Param("operatePermissionName")String operatePermissionName);

    /**
     * 获取菜单绑定权限
     * @param roleId
     * @return
     */
    List<Map> findMenuOps(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     *
     * @param searchMap
     * @return
     */
    List<OperatePermissionEntity> queryBySearch(Map searchMap);

    /**
     *
     * @param code
     * @param id
     * @return
     */
    int existCode(@Param("code") String code,@Param("id") Long id);

    /**
     * 根据用户ID查询所具有的所有操作权限CODE
     * @param userId
     * @return
     */
    List<String> findOperationCodeByUserId(@Param("userId") Long userId);
}
