package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <OorderExtDao>
 * @despriction
 * @create 2018/10/11 13:53
 * 用户订单查询
 **/
public interface OrderExtDao {

    List<Order> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);


    //更改订单状态
    void updateStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 查询订单支付状态
     */
    Map queryUserOrderPayStatus(@Param("userId") String userId, @Param("orderNo") String orderNo);

}