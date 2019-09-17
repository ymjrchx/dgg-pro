package net.dgg.zqq.dao;

import net.dgg.zqq.entity.business.User;
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

    List<Map> queryMap(Map map);


    User findUserById(String userId);

    /*根据userId修改status为启用*/
    void updateStatus1(String userId);

    /*根据userId修改status为禁用*/
    void updateStatus2(String userId);

    Integer count(Map map);

    void updateStatus(@Param("userId") String userId, @Param("status") String status);

}
