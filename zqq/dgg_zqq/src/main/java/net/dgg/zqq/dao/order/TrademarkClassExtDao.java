package net.dgg.zqq.dao.order;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <TrademarkClassDao>
 * @despriction
 * @create 2018-11-17 18:46:09+
 **/
@Repository
public interface TrademarkClassExtDao {
    /**
     * 查询订单商标的一级分类信息
     *
     * @param orderId 订单ID
     * @return
     */
    List<Map> queryAllClassLevel1(@Param("orderId") Long orderId);


}