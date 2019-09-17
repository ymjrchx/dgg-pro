package net.dgg.zqq.dao.industry;

import net.dgg.zqq.entity.industry.Industry;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <IndustryDao>
 * @despriction
 * @create 2018-10-11 15:34:34+
 **/
@Repository
public interface IndustryDao {
    void save(Industry industry);

    void update(Industry industry);

    Industry findById(@Param("id") Long id);

    List<Industry> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}