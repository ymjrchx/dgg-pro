package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.ApplicantTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <ApplicantTemplateDao>
 * @despriction
 * @create 2018-11-15 11:45:12+
 **/ 
@Repository
public interface ApplicantTemplateDao {
    void save(ApplicantTemplate applicantTemplate);

    void update(ApplicantTemplate applicantTemplate);

    ApplicantTemplate findById(@Param("id") Long id);

    List<ApplicantTemplate> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}