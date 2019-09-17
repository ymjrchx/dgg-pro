package net.dgg.base.baseDao;

import net.dgg.base.baseDao.crudDao.CountUtilDao;
import net.dgg.base.baseDao.crudDao.sqlProvider.SqlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <CountDao>
 * @despriction
 * @create 2018/12/07 18:19
 **/
public class CountDao<T> extends QueryDao<T> {
    @Autowired
    private CountUtilDao countUtilDao;

    /**
     * 统计
     *
     * @param param
     * @return
     */
    public Long count(Map<String, Object> param) {
        Map<String, Object> map = new HashMap(64);
        if (param != null) {
            map.putAll(param);
        }
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.FIELD_MAP_KEY, this.getFieldMap());
        map.put(SqlProviderUtil.FIELD_LIST_KEY, this.getFieldList());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        return this.countUtilDao.count(map);
    }

}
