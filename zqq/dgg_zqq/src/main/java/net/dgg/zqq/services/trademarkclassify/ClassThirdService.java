package net.dgg.zqq.services.trademarkclassify;

import net.dgg.zqq.entity.order.ClassThird;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:08 2018/9/30
 */
@Service
public interface ClassThirdService
{
    void save(ClassThird classThird);

    void update(ClassThird classThird);

    ClassThird findById(@Param("id") Long id);

    List<ClassThird> query(Map map);

    Map queryByKey(String key);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);
}
