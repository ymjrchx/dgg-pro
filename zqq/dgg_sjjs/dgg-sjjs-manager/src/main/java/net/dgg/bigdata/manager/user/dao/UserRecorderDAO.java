package net.dgg.bigdata.manager.user.dao;

import net.dgg.bigdata.common.entity.UserEntity;
import net.dgg.bigdata.manager.user.entity.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Repository
@Mapper
public interface UserRecorderDAO {
    /**
     * 向数据库添加用户实体
     *
     * @param userEntity
     */
    @InsertProvider(type = UserRecorderDAOProvider.class, method = "insertUserEntitySql")
    int insertUserEntity(UserEntity userEntity);

    /**
     * 向数据库更新用户实体
     *
     * @param userEntity
     */
    @UpdateProvider(type = UserRecorderDAOProvider.class, method = "updateUserEntitySql")
    int updateUserEntity(UserEntity userEntity);

    /**
     * 通过用户ID从数据库读取用户实体
     *
     * @param userId
     * @return
     */
    @Select("SELECT user_id as userId, locked as locked, login_name as loginName, login_pwd as loginPwd, real_name as realName," +
            " phone as phone, org_id as orgId, sex as sex, description as description, workarea as workarea, email as email," +
            " dimissiontime as dimissiontime, updatetime as updatetime, entrydate as entrydate FROM user_info WHERE user_id = #{userId}")
    UserEntity findUserEntityByUserId(@Param("userId") long userId);

    /**
     * 通过用户LoginName从数据库读取用户实体
     *
     * @param userLoginName
     * @return
     */
    @Select("SELECT user_id as userId, locked as locked, login_name as loginName, login_pwd as loginPwd, real_name as realName," +
            " phone as phone, org_id as orgId, sex as sex, description as description, workarea as workarea, email as email," +
            " dimissiontime as dimissiontime, updatetime as updatetime, entrydate as entrydate FROM user_info WHERE login_name = #{userLoginName}")
    UserEntity findUserEntityByUserLoginName(@Param("userLoginName") String userLoginName);

    /**
     * 通过关键字查找对应用户
     *
     * @param keyWords
     * @return
     */
    @SelectProvider(type = UserRecorderDAOProvider.class, method = "findUserEntityListByKeyWordsSql")
    List<UserEntity> findUserEntityListByKeyWords(@Param("keyWords") Map keyWords);

    /**
     * 通过员工ID查询用户DTO实体
     *
     * @param keyWords
     * @return
     */
    @SelectProvider(type = UserRecorderDAOProvider.class, method = "findUserDTOByOrgIdWithPageSql")
    List<UserDTO> findUserDTOByOrgIdWithPage(Map keyWords);

    /**
     * 统计指定部门下用户的个数
     *
     * @param orgId
     * @return
     */
    @Select("SELECT count(*) FROM user_info WHERE org_id=#{orgId} AND 1=1")
    int findUserTotalByOrgId(@Param("orgId") long orgId);

    /**
     * 根据模糊关键字查询用户名称及工号并返回DTO
     *
     * @param keyWords
     * @return
     */
    @SelectProvider(type = UserRecorderDAOProvider.class, method = "findUserDTOByKeyWordsWithPageSql")
    List<UserDTO> findUserDTOByKeyWordsWithPage(Map keyWords);

    /**
     * 根据模糊关键字查询用户名称及工号并返回总数量
     *
     * @param keyWords
     * @return
     */
    @SelectProvider(type = UserRecorderDAOProvider.class, method = "findUserTotalByKeyWordsSql")
    int findUserTotalByKeyWords(@Param("keyWords") Map keyWords);
}
