package net.dgg.zqq.services.order;

import net.dgg.zqq.dao.order.PayRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/10/29 9:10
 */

@Service
public class PayRecordService {

    @Autowired
    PayRecordDao payRecordDao;


    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.payRecordDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.payRecordDao.queryMap(query);
        }
        return Collections.emptyList();
    }

}
