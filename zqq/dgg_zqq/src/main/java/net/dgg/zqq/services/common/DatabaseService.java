package net.dgg.zqq.services.common;

import net.dgg.zqq.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/11/13.
 */
@Service
public class DatabaseService {

    @Autowired
    JdbcDao jdbcDao;

    public List<Map<String, Object>> query(String table, Map<String, Object> query) {
        return jdbcDao.query(table, query);
    }

    public List<Map<String, Object>> query(String table, Map<String, Object> query, Map<String, Object> orderBy) {
        return jdbcDao.query(table, query, orderBy);
    }

    public List<Map<String, Object>> query(String table) {
        return jdbcDao.query(table);
    }

    public int delete(String table, Map<String, Object> query) {
        return jdbcDao.delete(table, query);
    }

    public int update(String table, Map<String, Object> selector, Map<String, Object> data) {
        return jdbcDao.update(table, selector, data);
    }

    public int insert(String table, Map<String, Object> data) {
        return jdbcDao.insert(table, data);
    }
}
