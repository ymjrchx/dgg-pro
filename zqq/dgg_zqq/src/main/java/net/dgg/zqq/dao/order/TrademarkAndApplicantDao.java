package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <TrademarkAndApplicantDao>
 * @despriction
 * @create 2018-11-20 18:25:06+
 **/ 
@Repository
public interface TrademarkAndApplicantDao {
    void save(TrademarkAndApplicant trademarkAndApplicant);

    void update(TrademarkAndApplicant trademarkAndApplicant);

    TrademarkAndApplicant findById(@Param("id") Long id);

    List<TrademarkAndApplicant> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}