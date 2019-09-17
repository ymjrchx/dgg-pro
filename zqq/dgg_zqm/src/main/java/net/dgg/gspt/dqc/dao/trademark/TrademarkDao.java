package net.dgg.gspt.dqc.dao.trademark;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by 李程 on 2018/10/23.
 */
@Repository
public interface TrademarkDao {

    Map<String, Object> queryById(String id);

}
