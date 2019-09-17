package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.OrderRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <OrderRecordDao>
 * @despriction
 * @create 2018-11-16 08:59:11+
 **/
@Repository
public interface OrderRecordDao {
    void save(OrderRecord orderRecord);

    void update(OrderRecord orderRecord);

    OrderRecord findById(@Param("id") Long id);

    List<OrderRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}