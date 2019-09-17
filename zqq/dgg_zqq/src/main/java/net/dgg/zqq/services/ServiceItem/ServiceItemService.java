package net.dgg.zqq.services.ServiceItem;


import net.dgg.zqq.entity.serviceAndAttr.ServiceItem;

import java.util.List;
import java.util.Map;

/*
 *   服务项
 */
public interface ServiceItemService {

    List<Map<String, Object>> findServiceItemByNumber(String numbers);

    List<Map> pageQuery(Map query);

    void save(ServiceItem serviceItem, String serviceAttrId);

    ServiceItem findById(Long id);

    void update(ServiceItem serviceItem, String serviceAttrId);

    void delete(Long id);

    List<Map> queryByTypeLevel3Code(String code, String serviceItemNum);

    List getServiceItemByCode(String typeLevel3Code);


}
