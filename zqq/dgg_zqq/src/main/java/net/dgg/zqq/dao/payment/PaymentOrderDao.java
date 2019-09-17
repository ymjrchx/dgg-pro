package net.dgg.zqq.dao.payment;

import net.dgg.zqq.entity.payment.PaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/9.
 */
@Repository
public interface PaymentOrderDao {

    List<PaymentOrder> findByProps(Map<String, Object> props);

    int create(PaymentOrder paymentOrder);

    int update(PaymentOrder paymentOrder);

}
