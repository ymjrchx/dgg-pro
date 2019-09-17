package net.dgg.zqq.dao.trademarkConsult;

import net.dgg.zqq.entity.trademarkConsult.TrademarkConsult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <TrademarkConsultDao>
 * @despriction
 * @create 2018-10-22 17:52:14+
 **/
@Repository
public interface TrademarkConsultExtDao {

    List<TrademarkConsult> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

}