package net.dgg.bigdata.sjjs.web.dao.forbidIpRecord;

import net.dgg.bigdata.sjjs.web.entity.ForbidIpRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ForbidIpRecordDao>
 * @despriction
 * @create 2018-10-21 15:21:12+
 **/
@Repository
public interface ForbidIpRecordDao {
    void save(ForbidIpRecord forbidIpRecord);

    void update(ForbidIpRecord forbidIpRecord);

    ForbidIpRecord findById(@Param("id") Long id);

    List<ForbidIpRecord> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}