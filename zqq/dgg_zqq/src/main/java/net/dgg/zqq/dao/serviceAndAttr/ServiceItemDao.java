package net.dgg.zqq.dao.serviceAndAttr;

import net.dgg.zqq.entity.serviceAndAttr.ServiceItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <ServiceItemDao>
 * @despriction
 * @create 2018-11-27 15:01:25+
 **/ 
@Repository
public interface ServiceItemDao {
    void save(ServiceItem serviceItem);

    void update(ServiceItem serviceItem);

    ServiceItem findById(@Param("id") Long id);

    List<ServiceItem> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}