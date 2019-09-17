package net.dgg.base.baseDao;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <daoUtil>
 * @despriction
 * @create 2018/12/04 16:58
 **/
public interface DaoUtil {


    /**
     * 递归获取实体字段 非 static transient
     */

    static List<Field> getAllField(Object src, Class<?> entity) {
        List<Field> fieldList = new ArrayList<>();
        if (entity == null || entity.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entity.getDeclaredFields();
        for (Field f : fields) {
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
                fieldList.add(f);
            }
        }
        if (!entity.getSuperclass().equals(Object.class)) {
            fieldList.addAll(getAllField(src, entity.getSuperclass()));
        }
        return fieldList;
    }

    /**
     * 获取字段名
     *
     * @param fieldList
     * @return
     * @throws IllegalAccessException
     */
    static List<String> getAllFieldNameList(List<Field> fieldList) {
        if (fieldList == null) {
            return null;
        }
        List<String> fieldNameList = new ArrayList<>(fieldList.size());
        for (Field field : fieldList) {
            fieldNameList.add(field.getName());
        }
        return fieldNameList;
    }

    /**
     * 获取字段名的数据库字段名
     *
     * @param fieldList
     * @return
     * @throws IllegalAccessException
     */
    static List<String> getAllTableFieldNameList(List<Field> fieldList) {
        if (fieldList == null) {
            return null;
        }
        List<String> fieldNameList = new ArrayList<>(fieldList.size());
        for (Field field : fieldList) {
            fieldNameList.add(transUpperToLineAndLower(field.getName()));
        }
        return fieldNameList;
    }

    /**
     * 获取对应的值
     *
     * @param fieldList
     * @param src
     * @return
     */
    static List<Object> getAllFieldValue(List<Field> fieldList, Object src) throws IllegalAccessException {
        if (fieldList == null) {
            return null;
        }
        List<Object> fieldValueList = new ArrayList<>(fieldList.size());
        for (Field field : fieldList) {
            field.setAccessible(true);
            fieldValueList.add(field.get(src));
        }
        return fieldValueList;
    }

    /**
     * 获取Map
     *
     * @param fieldList
     * @param src
     * @return
     */
    static Map<String, Object> getAllFieldMap(List<Field> fieldList, Object src) throws IllegalAccessException {
        if (fieldList == null || src == null) {
            return null;
        }
        Map<String, Object> fieldValueMap = new HashMap<>(fieldList.size());
        for (Field field : fieldList) {
            field.setAccessible(true);
            fieldValueMap.put(field.getName(), field.get(src));
        }
        return fieldValueMap;
    }

    /**
     * 获取表名
     *
     * @param entity
     * @return
     */
    static String getTableName(Class<?> entity) {
        if (entity == null) {
            return null;
        }
        Table table = entity.getAnnotation(Table.class);
        return table == null ? null : table.name();
    }

    /**
     * 获取主键名
     *
     * @param entity
     * @return
     */
    static String getIdName(Class<?> entity) {
        if (entity == null) {
            return null;
        }
        Table table = entity.getDeclaredAnnotation(Table.class);
        return table == null ? null : table.idName();
    }

    /**
     * 获取实体主键字段
     *
     * @param entity
     * @return
     */
    static Field getIdField(List<Field> fieldList, Class<?> entity) {
        if (entity == null || fieldList == null) {
            return null;
        }
        String idName = getIdName(entity);

        for (Field field : fieldList) {
            if (idName.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取实体主键值
     *
     * @return
     */
    static Object getIdValue(Field idField, Object obj) {
        if (obj == null || idField == null) {
            return null;
        }
        idField.setAccessible(true);
        try {
            return idField.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 大写转下横线加小写
     *
     * @param str
     * @return
     * @Param first
     */
    static String transUpperToLineAndLower(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        char[] charArr = str.toCharArray();
        for (int i = 0, len = charArr.length; i < len; i++) {
            char c = charArr[i];
            if (i == 0) {
                sb.append(c);
                continue;
            }
            if (c >= 'A' && c <= 'Z') {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.substring(0);
    }

    /**
     * 转Map
     *
     * @param fieldList
     * @param obj
     * @return
     */
    static Map objToMap(List<Field> fieldList, Object obj) throws IllegalAccessException {
        Map map = new HashMap(fieldList.size());
        for (Field field : fieldList) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

    /**
     * map 转实体
     *
     * @param fieldList
     * @param map
     * @param tClass
     * @param <T>
     * @return
     */
    static <T> T mapToObj(List<Field> fieldList, Map map, Class<T> tClass) {
        if (map == null) {
            return null;
        }
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Assert.notNull(t, "实体对象创建失败！");

        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                field.set(t, map.get(field.getName()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Assert.isTrue(false, String.format("%s 对象字段 %s 设值失败！", tClass.getName(), field.getName()));
            }
        }
        return t;
    }


}
