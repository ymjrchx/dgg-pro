package net.dgg.base.baseDao;

import net.dgg.base.baseDao.crudDao.DeleteUtilDao;
import net.dgg.base.baseDao.crudDao.SaveUtilDao;
import net.dgg.base.baseDao.crudDao.UpdateUtilDao;
import net.dgg.base.baseDao.crudDao.sqlProvider.SqlProviderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <BaseDao>
 * @despriction
 * @create 2018/12/05 10:36
 **/
public class BaseDao<T> extends CountDao<T> {
    @Autowired
    private SaveUtilDao saveUtilDao;
    @Autowired
    private UpdateUtilDao updateUtilDao;

    @Autowired
    private DeleteUtilDao deleteUtilDao;


    /**
     * 保存
     *
     * @param t
     */

    public void save(T t) {
        Assert.notNull(t, "保存实体不能为空！");
        List<Object> valueList = null;
        try {
            valueList = DaoUtil.getAllFieldValue(this.getFieldList(), t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(valueList, "反射获取实体数据失败！");
        Map map = null;
        try {
            map = DaoUtil.objToMap(this.getFieldList(), t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(map, "实体转Map失败！");
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        this.saveUtilDao.save(map);
    }

    /**
     * 更新
     *
     * @param t
     */
    public void update(T t) {
        Assert.notNull(t, "更新实体不能为空！");
        Object id = DaoUtil.getIdValue(this.getIdField(), t);
        Assert.notNull(id, "更新时主键值不能为空！");
        Map map = null;
        try {
            map = DaoUtil.getAllFieldMap(this.getFieldList(), t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(map, "创建更新数据Map失败");
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.TABLE_FIELD_NAME_LIST_KEY, this.getTableFieldNameList());
        map.put(SqlProviderUtil.FIELD_NAME_LIST_KEY, this.getFieldNameList());
        map.put(SqlProviderUtil.ID_TABLE_KEY, this.getIdTableName());
        map.put(SqlProviderUtil.ID_FIELD_NAME_KEY, this.getIdField().getName());
        this.updateUtilDao.update(map);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Object id) {
        Assert.notNull(id, "删除时Id不能为空");
        Map map = new HashMap();
        map.put(this.getIdFieldName(), id);
        map.put(SqlProviderUtil.TABLE_KEY, this.getTableName());
        map.put(SqlProviderUtil.ID_TABLE_KEY, this.getIdTableName());
        map.put(SqlProviderUtil.ID_FIELD_NAME_KEY, this.getIdField().getName());
        this.deleteUtilDao.delete(map);
    }


}
