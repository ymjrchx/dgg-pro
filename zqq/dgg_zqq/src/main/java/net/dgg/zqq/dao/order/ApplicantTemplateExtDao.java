package net.dgg.zqq.dao.order;

import net.dgg.zqq.entity.order.ApplicantTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zxc
 * @ClassName <ApplicantTemplateDao>
 * @despriction
 * @create 2018-11-15 11:45:12+
 **/
@Repository
public interface ApplicantTemplateExtDao {

    List<ApplicantTemplate> query(Map map);

    List<Map> queryMap(Map map);

}