package net.dgg.tmd.foundation.platform.user.dao;

import net.dgg.tmd.foundation.platform.user.entity.UserDTO;
import net.dgg.tmd.foundation.platform.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */

@Component
public interface UserRecorderDAO {
    /**
     * 向数据库添加用户实体
     * @param userEntity
     */
    int insertUserEntity(UserEntity userEntity);

    /**
     * 向数据库更新用户实体
     * @param userEntity
     */
    int updateUserEntity(UserEntity userEntity);

    /**
     * 通过用户ID从数据库读取用户实体
     * @param userId
     * @return
     */
    UserEntity findUserEntityByUserId(@Param("userId")long userId);

    /**
     * 通过用户LoginName从数据库读取用户实体
     * @param userLoginName
     * @return
     */
    UserEntity findUserEntityByUserLoginName(@Param("userLoginName")String userLoginName);

    /**
     * 通过关键字查找对应用户
     * @param keyWords
     * @return
     */
    List<UserEntity> findUserEntityListByKeyWords(@Param("keyWords")Map keyWords);

    /**
     * 通过员工ID查询用户DTO实体
     * @param keyWords
     * @return
     */
    List<UserDTO> findUserDTOByOrgIdWithPage(Map keyWords);

    /**
     * 统计指定部门下用户的个数
     * @param orgId
     * @return
     */
    int findUserTotalByOrgId(@Param("orgId")long orgId);

    /**
     * 根据模糊关键字查询用户名称及工号并返回DTO
     * @param keyWords
     * @return
     */
    List<UserDTO> findUserDTOByKeyWordsWithPage(Map keyWords);

    /**
     * 根据模糊关键字查询用户名称及工号并返回总数量
     * @param keyWords
     * @return
     */
    int findUserTotalByKeyWords(@Param("keyWords")Map keyWords);
}
