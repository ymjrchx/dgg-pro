package net.dgg.bigdata.sjjs.web.dao;


import net.dgg.bigdata.sjjs.web.entity.User;
import net.dgg.bigdata.sjjs.web.entity.dto.UserRegisterListDto;
import net.dgg.bigdata.sjjs.web.entity.dto.UserSysListReturnDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserDao {

    int insertUser(User user);

    List<User> findUserByName(User user);

    void updateUser(User user);

    Integer findAllCount(Map condition);

    User selectByPrimaryKey(Map params);

    //查询所有后台用户
    List<UserSysListReturnDto> findAllSysUser(Map condition);

    //查询所有注册用户
    List<UserRegisterListDto> findAllRegisterUsers(Map condition);

    void updateUserByName(User user);

    //查看用户详情（包括用户信息）
    Map<Object, Object> selectUserDetilById(String userId);

    //查看并修改所有到期会员
    void updateAndfindUserMemberExpired();

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    User findUserById(@Param("id") String id);


    /**
     * 通过key查询
     *
     * @param key
     * @return
     */
    User findUserByKey(@Param("key") String key);

    /**
     * 通过key统计
     *
     * @param key
     * @return
     */
    Integer countUserByKey(@Param("key") String key);

    /**
     * 根据userId查出个人信息
     */

    User selectPersonInfoById(@Param("userId") String userId);

    /**
     * 根据username查出个人信息
     */

    List<User> findUserByUserName(@Param("userName") String userName);
}