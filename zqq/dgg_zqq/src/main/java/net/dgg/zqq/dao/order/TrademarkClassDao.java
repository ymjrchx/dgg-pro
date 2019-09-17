package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.TrademarkClass;
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
public interface TrademarkClassDao {
    void save(TrademarkClass trademarkClass);

    void update(TrademarkClass trademarkClass);

    TrademarkClass findById(@Param("id") Long id);

    List<TrademarkClass> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}