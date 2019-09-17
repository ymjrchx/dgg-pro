package net.dgg.bigdata.manager.permission.dao;

import net.dgg.bigdata.manager.permission.entity.OperatePermissionEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
@Mapper
public interface OperatePermissionDAO {
    /**
     * 插入权限信息
     *
     * @return
     */
    @InsertProvider(type = OperatePermissionDAOProvider.class, method = "insertPermissionInfoSql")
    int insertPermissionInfo(OperatePermissionEntity operatePermissionEntity);

    /**
     * 通过权限实体更新权限信息
     *
     * @return
     */
    @UpdateProvider(type = OperatePermissionDAOProvider.class, method = "updatePermissionInfoSql")
    int updatePermissionInfo(OperatePermissionEntity operatePermissionEntity);

    /**
     * 通过权限ID删除权限信息
     *
     * @param permsId
     * @return
     */
    @Delete("DELETE FROM operate_permission WHERE operate_permission_id=#{permsId}")
    int deletePermissionById(@Param("permsId") long permsId);

    /**
     * 通过权限ID获取权限实体
     *
     * @param permsId
     * @return
     */
    @Select("SELECT operate_permission_id AS operatePermissionId, operate_permission_name AS operatePermissionName, code AS code,remark, creator_id AS creatorId, createtime AS createtime FROM operate_permission WHERE operate_permission_id=#{permsId}")
    OperatePermissionEntity findPermsEntityByPermsId(@Param("permsId") long permsId);

    /**
     * 通过keyWords获取权限实体
     *
     * @param keyWords
     * @return
     */

    @SelectProvider(type = OperatePermissionDAOProvider.class, method = "findPermsEntityBykeyWordsSql")
    List<OperatePermissionEntity> findPermsEntityBykeyWords(@Param("keyWords") Map keyWords);

    /**
     * 根据菜单查询
     *
     * @param menuId
     * @param operatePermissionName 搜索条件
     * @param menuId                菜单是否绑定权限 如果为0：未绑定的数据  1.绑定的数据
     * @return
     */
    @SelectProvider(type = OperatePermissionDAOProvider.class, method = "findOperatePermissionByMenuIdSql")
    List<OperatePermissionEntity> findOperatePermissionByMenuId(@Param("menuId") Long menuId,
                                                                @Param("operatePermissionName") String operatePermissionName,
                                                                @Param("isMenu") int isMenu);

    /**
     * 获取独立权限
     *
     * @param roleId
     * @return
     */

    @SelectProvider(type = OperatePermissionDAOProvider.class, method = "findOperatePermissionByMenuRoleSql")
    List<Map> findOperatePermissionByMenuRole(@Param("roleId") Long roleId, @Param("operatePermissionName") String operatePermissionName);

    /**
     * 获取菜单绑定权限
     *
     * @param roleId
     * @return
     */
    @SelectProvider(type = OperatePermissionDAOProvider.class,method = "findMenuOpsSql")
    List<Map> findMenuOps(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * @param searchMap
     * @return
     */

    @SelectProvider(type = OperatePermissionDAOProvider.class,method = "queryBySearchSql")
    List<OperatePermissionEntity> queryBySearch(Map searchMap);

    /**
     * @param code
     * @param id
     * @return
     */

    @SelectProvider(type = OperatePermissionDAOProvider.class,method = "existCodeSql")
    int existCode(@Param("code") String code, @Param("id") Long id);

    /**
     * 根据用户ID查询所具有的所有操作权限CODE
     *
     * @param userId
     * @return
     */
    @Select("SELECT code FROM operate_permission WHERE operate_permission_id " +
            " IN (SELECT DISTINCT(operate_permission_id) FROM role_operate_permission WHERE role_id IN(SELECT role_id FROM user_role WHERE user_id=#{userId}))")
    List<String> findOperationCodeByUserId(@Param("userId") Long userId);
}
