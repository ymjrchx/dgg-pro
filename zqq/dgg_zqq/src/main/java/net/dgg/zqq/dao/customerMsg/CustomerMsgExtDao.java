package net.dgg.zqq.dao.customerMsg;

import net.dgg.zqq.entity.customerMsg.CustomerMsg;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <cuseDao>
 * @despriction
 * @create 2019/01/06 15:13
 **/
public interface CustomerMsgExtDao {

    List<CustomerMsg> query(Map map);

    List<Map> queryMap(Map map);

    Integer count(Map map);

}
