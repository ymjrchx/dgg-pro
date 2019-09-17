package net.dgg.zqq.dao.userTrademark;

import net.dgg.zqq.entity.userTrademark.UserTrademark;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <UserTrademarkDao>
 * @despriction 
 * @create 2018-10-16 15:29:14+
 **/ 
@Repository
public interface UserTrademarkDao {
    void save(UserTrademark userTrademark);

    void update(UserTrademark userTrademark);

    UserTrademark findById(@Param("id") Long id);

    List<UserTrademark> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}