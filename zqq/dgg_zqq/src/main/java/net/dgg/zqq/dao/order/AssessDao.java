package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.Assess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <AssessDao>
 * @despriction
 * @create 2018-11-15 16:30:19+
 **/
@Repository
public interface AssessDao {
    void save(Assess assess);

    void update(Assess assess);

    Assess findById(@Param("id") Long id);

    List<Assess> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}