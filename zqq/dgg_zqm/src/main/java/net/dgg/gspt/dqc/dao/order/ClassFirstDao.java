package net.dgg.gspt.dqc.dao.order;

import net.dgg.gspt.dqc.entity.order.ClassFirst;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ClassFirstDao>
 * @despriction
 * @create 2018-09-27 16:15:45+
 **/
@Repository
public interface ClassFirstDao {
    void save(ClassFirst classFirst);

    void update(ClassFirst classFirst);

    ClassFirst findById(@Param("id") Long id);

    List<ClassFirst> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

    ClassFirst findByNumber(String number);
}