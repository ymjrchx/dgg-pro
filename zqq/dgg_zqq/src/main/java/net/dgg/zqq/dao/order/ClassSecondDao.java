package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.ClassSecond;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ClassSecondDao>
 * @despriction
 * @create 2018-09-27 16:16:14+
 **/
@Repository
public interface ClassSecondDao {
    void save(ClassSecond classSecond);

    void update(ClassSecond classSecond);

    ClassSecond findById(@Param("id") Long id);

    List<ClassSecond> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);
    ClassSecond findByNumber(String number);

}