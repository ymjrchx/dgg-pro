package net.dgg.dqc.backservice.dao_mybatis;

import net.dgg.dqc.backservice.entity.ImportDataRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ImportDataRecordDao>
 * @despriction 生产主订单Dao
 * @create 2018-08-27 17:40:17+
 **/
@Repository
public interface ImportDataRecordDao {
    void save(ImportDataRecord importDataRecord);

    void update(ImportDataRecord importDataRecord);

    ImportDataRecord findById(@Param("id") Long id);

    List<ImportDataRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}