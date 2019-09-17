package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <OrderDao>
 * @despriction
 * @create 2018-11-15 14:03:47+
 **/ 
@Repository
public interface OrderDao {
    void save(Order order);

    void update(Order order);

    Order findById(@Param("id") Long id);

    List<Order> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}