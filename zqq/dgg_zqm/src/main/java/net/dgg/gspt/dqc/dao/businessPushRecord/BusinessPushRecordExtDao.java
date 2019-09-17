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
 * @create 2019-01-07 17:28:33+
 **/ 
@Repository
public interface BusinessPushRecordExtDao {

   void insertBatch(@Param("list") List<BusinessPushRecord> list);


    List<BusinessPushRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);



}