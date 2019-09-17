package net.dgg.tmd.foundation.platform.role.dao;

import net.dgg.tmd.foundation.platform.role.entity.Role;
import net.dgg.tmd.foundation.platform.role.entity.UserRoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface RoleDao {
    /**
     * 校验是否有重复的角色名
     * @param roleNmae 待校验角色名
     * @param roleId 空为插入时校验，不为空时为修改时校验
     * @return
     */
    int existRole(@Param("roleName") String roleNmae,@Param("roleId")Long roleId);
    /**
     * 添加角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 修改角色状态
     * @param enable
     */
    void updateRoleEnable(@Param("enable")Integer enable,@Param("roleId") Long roleId);


    /**
     * 根据搜索条件获取角色
     * @param searchMap
     * @return
     */
    List<Role> queryRoles(Map searchMap);
    /**
     * 根据id获取角色
     * @param roleId:角色id
     * @return
     */
    Role queryRoleById(@Param("roleId") Long roleId);

    /**
     * 根据id查询角色，并用","连接
     * @param userId
     * @return
     */
    String findOwnedRoleNameByUserId(@Param("userId") long userId);

    /**
     * 根据角色名称查询角色ID
     * @param roleName
     * @return
     */
    long findIdByRoleName(@Param("roleName") String roleName);

    /**
     * 查询所有角色名称
     * @return
     */
    List<String> findAllRoleNames(@Param("userId") long userId);

    /**
     * 向数据库插入角色信息
     * @param userRoleDTO
     */
    void insertUserRole(@Param("id")Long id,@Param("userId")Long userId,@Param("roleId")Long roleId,
            @Param("creatorId")Long creatorId,@Param("createtime")Date createtime);

    /**
     * 根据用户ID和角色名称判断用户是否具备相应角色
     * @param map
     * @return
     */
    int findExistRoleByUserIdAndRoleId(@Param("map") Map map);

    /**
     * 根据用户ID和角色名称删除相应角色
     * @param map
     */
    void delRoleWithUserNameAndRoleId(@Param("map") Map map);

    /**
     * 根据用户ID删除所有角色
     * @param userId
     */
    void delAllRoleByUserId(@Param("userId") long userId);

    /**
     * 根据用户ID查询UserRoleDTO实体
     * @param userId
     * @return
     */
    List<UserRoleDTO> findUserRoleDTOByUserId(@Param("userId") long userId);

    /**
     * 根据用户ID查询所有Role实体
     * @param userId
     */
    List<Role> findAllRoleWithoutExist(@Param("userId") long userId);

    /**
     * 根据RoleID查询角色名称
     * @param roleId
     * @return
     */
    String findRoleNameByRoleId(@Param("roleId") long roleId);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAllRoles();

    int existsMenuPermissionUser(Map condition);
}
