package net.dgg.base.baseDao;

import net.dgg.base.baseDao.crudDao.QueryUtilDao;
import net.dgg.base.baseDao.crudDao.sqlProvider.SqlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <QueryDao>
 * @despriction
 * @create 2018/12/05 10:31
 **/
public abstract class QueryDao<T> extends FindDao<T> {
    @Autowired
    private QueryUtilDao queryUtilDao;

    /**
     * 查询 map 集合
     *
     * @param param
     * @return
     */
    public List<Map> queryMap(Map<String, Object> param) {
        Map<String, Object> map = new HashMap(64);
        if (param != null) {
            map.putAll(param);
        }
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.FIELD_MAP_KEY, this.getFieldMap());
        map.put(SqlProviderUtil.FIELD_LIST_KEY, this.getFieldList());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        return this.queryUtilDao.queryMap(map);
    }

    /**
     * 查询实体
     *
     * @param param
     * @return
     */
    public List<T> query(Map<String, Object> param) {
        List<Map> mapList = this.queryMap(param);
        List<T> list = new ArrayList<>(mapList.size());
        for (Map map : mapList) {
            list.add((T) DaoUtil.mapToObj(this.getFieldList(), map, this.getEntityClass()));
        }
        return list;
    }


}
