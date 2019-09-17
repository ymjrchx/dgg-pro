package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @ClassName <TrademarkAndApplicantDao>
 * @despriction
 * @create 2018-11-15 17:08:26+
 **/
@Repository
public interface TrademarkAndApplicantExtDao {

    TrademarkAndApplicant findByOrderId(@Param("orderId") Long orderId);


}