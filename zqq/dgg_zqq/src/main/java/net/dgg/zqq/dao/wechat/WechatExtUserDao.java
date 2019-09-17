package net.dgg.zqq.dao.wechat;

import net.dgg.zqq.entity.wechat.WechatExtUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <WechatExtUserDao>
 * @despriction
 * @create 2018-10-20 10:10:42+
 **/ 
@Repository
public interface WechatExtUserDao {
    void save(WechatExtUser wechatExtUser);

    void update(WechatExtUser wechatExtUser);

    WechatExtUser findById(@Param("id") Long id);

    List<WechatExtUser> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}