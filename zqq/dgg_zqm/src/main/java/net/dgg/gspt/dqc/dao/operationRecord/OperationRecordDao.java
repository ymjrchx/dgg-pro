package net.dgg.gspt.dqc.dao.operationRecord;

import net.dgg.gspt.dqc.entity.operationRecord.OperationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <OperationRecordDao>
 * @despriction
 * @create 2019-01-03 10:07:29+
 **/
@Repository
public interface OperationRecordDao {
    void save(OperationRecord operationRecord);

    void update(OperationRecord operationRecord);

    OperationRecord findById(@Param("id") Long id);

    List<OperationRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}