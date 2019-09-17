package net.dgg.zqq.dao.serviceAndAttr;

import net.dgg.zqq.entity.serviceAndAttr.ServiceAttrRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @ClassName <ServiceAttrRelationDao>
 * @despriction
 * @create 2018-09-30 12:31:39+
 **/ 
@Repository
public interface ServiceAttrRelationDao {
    void save(ServiceAttrRelation serviceAttrRelation);

    void update(ServiceAttrRelation serviceAttrRelation);

    ServiceAttrRelation findById(@Param("id") Long id);

    List<ServiceAttrRelation> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

    void deleteById(@Param("id") Long id);

}