package net.dgg.gspt.dqc.dao.checkCompanyRecord;

import net.dgg.gspt.dqc.entity.checkCompany.CheckCompanyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <CheckCompanyRecordDao>
 * @despriction 生产主订单Dao
 * @create 2018-08-21 12:02:37+
 **/
@Repository
public interface CheckCompanyRecordDao {
    void save(CheckCompanyRecord checkCompanyRecord);

    void update(CheckCompanyRecord checkCompanyRecord);

    CheckCompanyRecord findById(@Param("id") Long id);

    List<CheckCompanyRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}