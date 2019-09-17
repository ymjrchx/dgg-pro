package net.dgg.zqq.dao.industry;

import net.dgg.zqq.entity.industry.IndustryCategorysThird;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <IndustryCategorysThirdDao>
 * @despriction
 * @create 2018-10-11 16:44:17+
 **/
@Repository
public interface IndustryCategorysThirdDao {
    void save(IndustryCategorysThird industryCategorysThird);

    void update(IndustryCategorysThird industryCategorysThird);

    IndustryCategorysThird findById(@Param("id") Long id);

    List<IndustryCategorysThird> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}