package net.dgg.gspt.dqc.dao.businessPushRecord;

import net.dgg.gspt.dqc.entity.businessPushRecord.BusinessPushRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <BusinessPushRecordDao>
 * @despriction 
 * @create 2019-01-08 09:27:58+
 **/ 
@Repository
public interface BusinessPushRecordDao {
    void save(BusinessPushRecord businessPushRecord);

    void update(BusinessPushRecord businessPushRecord);

    BusinessPushRecord findById(@Param("id") String id);

    List<BusinessPushRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") String id);

}