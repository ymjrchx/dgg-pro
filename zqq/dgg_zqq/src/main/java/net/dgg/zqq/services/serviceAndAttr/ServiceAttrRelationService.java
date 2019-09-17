package net.dgg.zqq.services.serviceAndAttr;

import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrRelationDao;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttrRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ServiceAttrRelationService>
 * @despriction
 * @create 2018/10/08 16:29
 **/
@Service
public class ServiceAttrRelationService {
    @Autowired
    private ServiceAttrRelationDao serviceAttrRelationDao;


    public List<ServiceAttrRelation> query(Map map) {
        return this.serviceAttrRelationDao.query(map);
    }

}
