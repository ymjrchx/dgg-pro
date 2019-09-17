package net.dgg.zqq.dao.serviceAndAttr;

import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @ClassName <ServiceAttrDao>
 * @despriction
 * @create 2018-09-27 13:58:53+
 **/
@Repository
public interface ServiceAttrExtDao {


    List<ServiceAttr> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

}