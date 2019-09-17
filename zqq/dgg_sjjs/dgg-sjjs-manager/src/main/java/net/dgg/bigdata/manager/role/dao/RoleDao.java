package net.dgg.bigdata.manager.role.dao;

import net.dgg.bigdata.manager.role.entity.Role;
import net.dgg.bigdata.manager.role.entity.UserRoleDTO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Mapper
public interface RoleDao {
    /**
     * 校验是否有重复的角色名
     * @param roleNmae 待校验角色名
     * @param roleId 空为插入时校验，不为空时为修改时校验
     * @return
     */
    @SelectProvider(type = RoleDaoProvider.class,method = "existRoleSql")
    int existRole(@Param("roleName") String roleNmae, @Param("roleId") Long roleId);
    /**
     * 添加角色
     * @param role
     */
    @Insert("INSERT INTO role(role_id,role_name,creator_id,createtime,updator_id,updatetime,remark,enable) VALUES (#{roleId},#{roleName},#{creatorId},#{createtime},#{updatorId},#{updatetime},#{remark},#{enable})")
    void addRole(Role role);

    /**
     * 修改角色
     * @param role
     */
    @Update("UPDATE role SET role_name=#{roleName},remark=#{remark},updator_id=#{updatorId},updatetime=#{updatetime} where role_id=#{roleId}")
    void updateRole(Role role);

    /**
     * 修改角色状态
     * @param enable
     */
    @Update("UPDATE role SET enable=#{enable} where role_id=#{roleId}")
    void updateRoleEnable(@Param("enable") Integer enable, @Param("roleId") Long roleId);


    /**
     * 根据搜索条件获取角色
     * @param searchMap
     * @return
     */
    @SelectProvider(type = RoleDaoProvider.class,method = "queryRolesSql")
    List<Role> queryRoles(Map searchMap);
    /**
     * 根据id获取角色
     * @param roleId:角色id
     * @return
     */
    @Select("select role_id as roleId ,role_name as roleName,creator_id as creatorId,createtime as createtime," +
            "updator_id as updatorId,updatetime as updatetime,remark ,enable from role where role_id=#{roleId}")
    Role queryRoleById(@Param("roleId") Long roleId);

    /**
     * 根据id查询角色，并用","连接
     * @param userId
     * @return
     */
    @Select("SELECT GROUP_CONCAT(role_name) FROM user_role a LEFT JOIN role b ON a.role_id=b.role_id WHERE 1=1 AND a.user_id=#{userId}")
    String findOwnedRoleNameByUserId(@Param("userId") long userId);

    /**
     * 根据角色名称查询角色ID
     * @param roleName
     * @return
     */

    @Select("SELECT a.role_id FROM role a WHERE 1=1 AND a.role_name=#{roleName}")
    long findIdByRoleName(@Param("roleName") String roleName);

    /**
     * 查询所有角色名称
     * @return
     */
    @Select("SELECT role_name FROM role WHERE 1=1 AND role_name NOT IN(SELECT b.role_name FROM user_role a " +
            "LEFT JOIN role b ON a.role_id=b.role_id WHERE a.user_id=#{userId})")
    List<String> findAllRoleNames(@Param("userId") long userId);

    /**
     * 向数据库插入角色信息
     * @param
     */
    @Insert("INSERT INTO user_role (id,user_id,role_id,creator_id,createtime) values (#{id},#{userId},#{roleId},#{creatorId},#{createtime})")
    void insertUserRole(@Param("id") Long id, @Param("userId") Long userId, @Param("roleId") Long roleId,
                        @Param("creatorId") Long creatorId, @Param("createtime") Date createtime);

    /**
     * 根据用户ID和角色名称判断用户是否具备相应角色
     * @param map
     * @return
     */
    @Select("SELECT COUNT(*) FROM user_role a LEFT JOIN role b ON a.role_id=b.role_id " +
            "WHERE 1=1 AND a.user_id=#{map.userId} AND b.role_id=#{map.roleId}")
    int findExistRoleByUserIdAndRoleId(@Param("map") Map map);

    /**
     * 根据用户ID和角色名称删除相应角色
     * @param map
     */
    @Delete("DELETE FROM user_role WHERE user_id=#{map.userId} AND role_id IN " +
            "(SELECT role_id FROM role WHERE role_id=#{map.roleId})")
    void delRoleWithUserNameAndRoleId(@Param("map") Map map);

    /**
     * 根据用户ID删除所有角色
     * @param userId
     */
    @Delete(" DELETE FROM user_role WHERE user_id=#{userId}")
    void delAllRoleByUserId(@Param("userId") long userId);

    /**
     * 根据用户ID查询UserRoleDTO实体
     * @param userId
     * @return
     */
    @Select("SELECT id AS id, user_id AS userId, role_id AS roleId, creator_id AS creatorId, createtime AS createtime" +
            " FROM user_role WHERE user_id=#{userId}")
    List<UserRoleDTO> findUserRoleDTOByUserId(@Param("userId") long userId);

    /**
     * 根据用户ID查询所有Role实体
     * @param userId
     */
    @Select("SELECT role_id AS roleId, role_name AS roleName, creator_id AS creatorId, createtime AS createtime, " +
            "updator_id AS updatorId, updatetime AS updatetime, remark AS remark, enable AS enable FROM role " +
            "WHERE role_id NOT IN(SELECT role_id FROM user_role WHERE user_id=#{userId})")
    List<Role> findAllRoleWithoutExist(@Param("userId") long userId);

    /**
     * 根据RoleID查询角色名称
     * @param roleId
     * @return
     */
    @Select("SELECT role_name FROM role WHERE role_id=#{roleId}")
    String findRoleNameByRoleId(@Param("roleId") long roleId);

    /**
     * 查询所有角色
     * @return
     */
    @Select(" SELECT role_id AS roleId, role_name AS roleName, creator_id AS creatorId, createtime AS createtime, " +
            "updator_id AS updatorId, updatetime AS updatetime, remark AS remark, enable AS enable FROM role")
    List<Role> findAllRoles();

    @Select("SELECT count(1) FROM user_info a JOIN user_role b JOIN role_menu_permission c JOIN menu_main d ON a.user_id = b.user_id " +
            "AND b.role_id = c.role_id AND c.menu_id = d.menu_id WHERE a.user_id = #{userId} AND d.menu_url = #{url}")
    int existsMenuPermissionUser(Map condition);
}
