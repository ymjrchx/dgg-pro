package net.dgg.zqq.services.trademarkclassify;

import net.dgg.zqq.entity.order.ClassSecond;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huanggai
 * @creatTime 11:06 2018/9/30
 */
@Service
public interface ClassSecondService
{
    void save(ClassSecond classSecond);

    void update(ClassSecond classSecond);

    ClassSecond findById(@Param("id") Long id);

    List<ClassSecond> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);
}
