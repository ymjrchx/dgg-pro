package net.dgg.gspt.dqc.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/11/13.
 */
public interface JdbcDao {

    List<Map<String, Object>> query(String table, Map<String, Object> query);

    List<Map<String, Object>> query(String table);

    List<Map<String, Object>> query(String table, Map<String, Object> queryWhere, Map<String, Object> orderBy);

    int delete(String table, Map<String, Object> query);

    int update(String table, Map<String, Object> selector, Map<String, Object> data);

    int insert(String table, Map<String, Object> data);
}
