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
 * @create 2018-09-28 19:58:27+
 **/
@Repository
public interface ServiceItemExtDao {

    List<ServiceItem> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    ServiceItem findByNumber(@Param("number") String number);


}