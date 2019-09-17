package net.dgg.zqq.dao.userTrademark;

import net.dgg.zqq.entity.userTrademark.UserCompany;
import net.dgg.zqq.entity.userTrademark.UserTrademark;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <UserTrademarkDao>
 * @despriction 
 * @create 2018-10-16 09:52:20+
 **/ 
@Repository
public interface UserTrademarkExtDao {

    UserTrademark findByMap (Map map);

    List<UserTrademark> query(Map map);

    Integer count(Map map);

}