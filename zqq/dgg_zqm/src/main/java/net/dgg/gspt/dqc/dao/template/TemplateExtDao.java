package net.dgg.gspt.dqc.dao.template;

import net.dgg.gspt.dqc.entity.template.Template;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <TemplateDao>
 * @despriction 
 * @create 2018-10-18 13:35:54+
 **/ 
@Repository
public interface TemplateExtDao {

    List<Template> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);
}