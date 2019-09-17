package net.dgg.zqq.dao.customerMsg;

import net.dgg.zqq.entity.customerMsg.CustomerMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <CustomerMsgDao>
 * @despriction
 * @create 2019-01-06 15:00:29+
 **/
@Repository
public interface CustomerMsgDao {
    void save(CustomerMsg customerMsg);

    void update(CustomerMsg customerMsg);

    CustomerMsg findById(@Param("id") Long id);

    List<CustomerMsg> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}