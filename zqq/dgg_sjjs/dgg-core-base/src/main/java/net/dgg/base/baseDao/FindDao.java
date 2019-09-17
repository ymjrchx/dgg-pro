package net.dgg.base.baseDao;

import net.dgg.base.baseDao.crudDao.FindUtilDao;
import net.dgg.base.baseDao.crudDao.sqlProvider.SqlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <FindDao>
 * @despriction
 * @create 2018/12/05 11:44
 **/
public abstract class FindDao<T> extends RootDao<T> {
    @Autowired
    private FindUtilDao findUtilDao;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public Map findMapById(Object id) {
        Assert.notNull(id, "id不能为空！");
        Map map = new HashMap();
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        map.put(SqlProviderUtil.ID_TABLE_KEY, this.getIdTableName());
        map.put(SqlProviderUtil.ID_FIELD_NAME_KEY, this.getIdFieldName());
        map.put(this.getIdFieldName(), id);
        return this.findUtilDao.findById(map);
    }

    /**
     * 根据属性查询
     *
     * @param attr
     * @param value
     * @return
     */
    public Map findMapByAttr(String attr, Object value) {
        Assert.hasText(attr, "属性不能为空！");
        Assert.notNull(value, "属性值不能为空！");
        Assert.isTrue(this.getFieldNameList().contains(attr), String.format("%s 中未定义属性：%s", this.getEntityClass().getName(), attr));
        Map map = new HashMap();
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        map.put(SqlProviderUtil.QUERY_TABLE_KEY, DaoUtil.transUpperToLineAndLower(attr));
        map.put(SqlProviderUtil.QUERY_FIELD_KEY, attr);
        map.put(attr, value);
        return this.findUtilDao.findByAttr(map);
    }

    public T findById(Object id) {
        return (T) DaoUtil.mapToObj(this.getFieldList(), this.findMapById(id), this.getEntityClass());
    }

    public T findByAttr(String attr, Object value) {
        return (T) DaoUtil.mapToObj(this.getFieldList(), this.findMapByAttr(attr, value), this.getEntityClass());
    }

}
