package net.dgg.zqq.services.order;

import net.dgg.zqq.dao.order.ApplicantTemplateDao;
import net.dgg.zqq.dao.order.TrademarkAndApplicantDao;
import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class TrademarkAndApplicantService {

    @Autowired
    private TrademarkAndApplicantDao trademarkAndApplicantDao;
    @Autowired
    private ApplicantTemplateDao applicantTemplateDao;

    public TrademarkAndApplicant findByOrderId(String orderId) {
        List<TrademarkAndApplicant> list = this.trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }


}
