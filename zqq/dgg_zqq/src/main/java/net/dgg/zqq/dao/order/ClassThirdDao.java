package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.ClassThird;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ClassThirdDao>
 * @despriction
 * @create 2018-09-27 16:16:45+
 **/
@Repository
public interface ClassThirdDao {
    void save(ClassThird classThird);

    void update(ClassThird classThird);

    ClassThird findById(@Param("id") Long id);

    List<ClassThird> query(Map map);

    List<ClassThird> queryByKey(String key);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}