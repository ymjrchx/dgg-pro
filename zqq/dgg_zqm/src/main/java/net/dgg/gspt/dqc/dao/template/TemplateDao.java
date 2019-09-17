package net.dgg.gspt.dqc.dao.template;

import net.dgg.gspt.dqc.entity.template.Template;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <TemplateDao>
 * @despriction 
 * @create 2018-10-18 15:11:27+
 **/ 
@Repository
public interface TemplateDao {
    void save(Template template);

    void update(Template template);

    Template findById(@Param("id") Long id);

    List<Template> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}