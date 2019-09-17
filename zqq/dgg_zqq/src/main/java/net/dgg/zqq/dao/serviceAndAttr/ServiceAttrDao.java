package net.dgg.zqq.dao.serviceAndAttr;

import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <ServiceAttrDao>
 * @despriction
 * @create 2018-10-26 11:02:42+
 **/ 
@Repository
public interface ServiceAttrDao {
    void save(ServiceAttr serviceAttr);

    void update(ServiceAttr serviceAttr);

    ServiceAttr findById(@Param("id") Long id);

    List<ServiceAttr> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}