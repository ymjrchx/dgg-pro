package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.PayRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <PayRecordDao>
 * @despriction
 * @create 2018-09-27 14:08:21+
 **/
@Repository
public interface PayRecordDao {
    void save(PayRecord payRecord);

    void update(PayRecord payRecord);

    PayRecord findById(@Param("id") Long id);

    List<PayRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}