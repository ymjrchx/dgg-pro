package net.dgg.zqq.services.trademarkclassify;

import net.dgg.zqq.entity.order.ClassFirst;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:03 2018/9/30
 */
@Service
public interface ClassFirstService
{
    void save(ClassFirst classFirst);

    void update(ClassFirst classFirst);

    ClassFirst findById(@Param("id") Long id);

    List<ClassFirst> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);
}
