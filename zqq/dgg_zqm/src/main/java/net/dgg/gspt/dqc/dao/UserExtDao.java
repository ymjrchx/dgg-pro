package net.dgg.gspt.dqc.dao;

import net.dgg.gspt.dqc.entity.business.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/10/11 16:04
 */
@Repository
public interface UserExtDao {

    List<User> query(Map map);

    List<Map> queryMap(Map map);


    User findUserById(String userId);

    /*根据userId修改status为启用*/
    void updateStatus1(String userId);

    /*根据userId修改status为禁用*/
    void updateStatus2(String userId);

    Integer count(Map map);

    void updateStatus(@Param("userId") String userId, @Param("status") String status);

    void insertUser(User user);

    void updateUserStatus(@Param("pushStatus") String pushStatus, @Param("userIdList") List<String> userIdList);

}
