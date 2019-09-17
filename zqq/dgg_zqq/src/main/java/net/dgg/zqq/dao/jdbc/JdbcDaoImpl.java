package net.dgg.zqq.dao.jdbc;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/11/13.
 */
@Slf4j
public class JdbcDaoImpl implements JdbcDao {

    private final static Gson gson = new Gson();

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public int insert(String table, Map<String, Object> data) {
        List<String> columns = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into " + table + "(");
        for (String key : data.keySet()) {
            String column = key;
            Object value = data.get(column);
            columns.add(column);
            objects.add(value);
        }
        boolean first = true;
        for (int i = 0; i < columns.size(); i++) {
            if (!first) {
                sql.append(",");
            }
            first = false;
            String column = columns.get(i);
            sql.append(column);
        }
        sql.append(") values (");
        first = true;
        for (int i = 0; i < columns.size(); i++) {
            if (!first) {
                sql.append(",");
            }
            first = false;
            sql.append("?");
        }
        sql.append(")");
        log.info("SQL: {}", sql.toString());
        log.info("Parameter: {}", gson.toJson(objects));
        return jdbcTemplate.update(sql.toString(), objects.toArray());
    }

    public int update(String table, Map<String, Object> selector, Map<String, Object> data) {
        StringBuilder sql = new StringBuilder();
        sql.append("update " + table + " set ");
        List<Object> args = new ArrayList<>();
        boolean first = true;
        for (String key : data.keySet()) {
            if (!first) {
                sql.append(",");
            }
            sql.append("" + key + " = ?");
            args.add(data.get(key));
            first = false;
        }
        sql.append(whereBuild(selector, args));
        log.info("SQL: {}", sql.toString());
        log.info("Parameter: {}", gson.toJson(args));
        return jdbcTemplate.update(sql.toString(), args.toArray());
    }

    @Override
    public List<Map<String, Object>> query(String table, Map<String, Object> queryWhere) {
        return query(table, queryWhere, null);
    }

    @Override
    public List<Map<String, Object>> query(String table, Map<String, Object> queryWhere, Map<String, Object> orderBy) {
        StringBuilder sql = new StringBuilder();
        List<Object> args = new ArrayList<>();
        sql.append(selectSqlBuild(table));
        sql.append(whereBuild(queryWhere, args));
        sql.append(orderByBuild(orderBy));

        log.info("SQL:{}", sql.toString());
        log.info("Sql Parameter {}", gson.toJson(args));

        List<Map<String, Object>> list = jdbcTemplate.query(sql.toString(), args.toArray(), (resultSet, i) -> {
            Map<String, Object> data = new HashMap<>();
            ResultSetMetaData meta = resultSet.getMetaData();
            int c = meta.getColumnCount();
            for (int j = 0; j < c; j++) {
                String column = meta.getColumnName(j + 1);
                data.put(column, resultSet.getObject(column));
            }
            return data;
        });
        return list == null ? new ArrayList<>() : list;

    }

    @Override
    public int delete(String table, Map<String, Object> queryWhere) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ");
        sql.append(table);
        List<Object> args = new ArrayList<>();
        sql.append(whereBuild(queryWhere, args));
        return this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public List<Map<String, Object>> query(String table) {
        return query(table, null);
    }

    public String selectSqlBuild(String table) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        sql.append(table);
        sql.append(" ");
        return sql.toString();
    }

    public String whereBuild(Map<String, Object> whereParameter, List<Object> args) {
        if (whereParameter == null || whereParameter.isEmpty()) {
            return "";
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append(" where ");
            boolean first = true;
            for (String key : whereParameter.keySet()) {
                if (!first) {
                    sql.append(" and ");
                }
                first = false;
                sql.append(key + " ? ");
                args.add(whereParameter.get(key));
            }
            return sql.toString();
        }
    }

    public String orderByBuild(Map<String, Object> orderBy) {
        if (orderBy == null || orderBy.isEmpty()) {
            return "";
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append(" order by ");
            boolean first = true;
            for (String key : orderBy.keySet()) {
                if (!first) {
                    sql.append(",");
                } else {
                    first = false;
                }
                sql.append(" " + key + " " + orderBy.get(key));
            }
            return sql.toString();
        }
    }

}
