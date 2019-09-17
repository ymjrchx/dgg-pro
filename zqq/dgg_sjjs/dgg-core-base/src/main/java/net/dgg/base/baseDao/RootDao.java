package net.dgg.base.baseDao;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <BaseDao>
 * @despriction
 * @create 2018/12/04 16:04
 **/
@Repository
public abstract class RootDao<T extends Object> {

    /**
     * 实体类
     */
    private Class<T> entityClass;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 所有字段
     */
    private List<Field> fieldList;

    /**
     * 所有字段名
     */
    private List<String> fieldNameList;

    /**
     * 所有表字段名
     */
    private List<String> tableFieldNameList;

    /**
     * 表  id  名
     */
    private String idTableName;

    /**
     * id 字段名
     */
    private String idFieldName;

    /**
     * id Field
     */
    private Field idField;

    private Map<String, Field> fieldMap;


    @SuppressWarnings("unchecked")
    protected RootDao() {
        T t = null;
        try {
            t = (T) (this.getGenericType(0).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(t, "实体类获取失败！");
        this.entityClass = (Class<T>) t.getClass();
        this.tableName = DaoUtil.getTableName(this.entityClass);
        Assert.hasText(this.tableName, String.format("%s 表名获取失败！", this.entityClass.getName()));
        this.fieldList = Collections.unmodifiableList(DaoUtil.getAllField(t, this.entityClass));
        this.fieldNameList = Collections.unmodifiableList(DaoUtil.getAllFieldNameList(this.fieldList));
        this.tableFieldNameList = Collections.unmodifiableList(DaoUtil.getAllTableFieldNameList(this.fieldList));
        this.idField = DaoUtil.getIdField(this.fieldList, this.entityClass);
        Assert.notNull(this.idField, "主键字段获取失败！");
        this.idFieldName = this.idField.getName();
        this.idTableName = DaoUtil.transUpperToLineAndLower(this.idFieldName);
        this.fieldMap = new HashMap<>(this.fieldList.size());
        for (Field field : this.fieldList) {
            this.fieldMap.put(field.getName(), field);
        }

    }

    private Class<?> getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected String getTableName() {
        return tableName;
    }

    protected List<Field> getFieldList() {
        return fieldList;
    }

    protected List<String> getFieldNameList() {
        return fieldNameList;
    }

    protected List<String> getTableFieldNameList() {
        return tableFieldNameList;
    }

    protected String getIdTableName() {
        return idTableName;
    }

    protected String getIdFieldName() {
        return idFieldName;
    }

    protected Field getIdField() {
        return idField;
    }

    protected Map<String, Field> getFieldMap() {
        return fieldMap;
    }
}
