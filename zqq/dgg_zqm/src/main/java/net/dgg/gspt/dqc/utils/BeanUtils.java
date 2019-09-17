package net.dgg.gspt.dqc.utils;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 刘阳
 * @ClassName <BeanUtils>
 * @despriction
 * @create 2018/08/22 16:41
 **/
public class BeanUtils {

    private static String[] getNullPropertyNames(Object source) {
        Set<String> set = new HashSet<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (null == field.get(source)) {
                    set.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            getParentNullField(set, source, source.getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String[] arr = new String[set.size()];
        set.toArray(arr);
        return arr;
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, (String[]) ArrayUtils.addAll(getNullPropertyNames(src), ignoreProperties));
    }


    // 递归获取父类实体null字段
    private static void getParentNullField(Set<String> fieldSet, Object src, Class<?> entity) throws IllegalAccessException {
        Class<?> superClass = null;
        if (fieldSet == null || entity == null || (superClass = entity.getSuperclass()).equals(Object.class)) {
            return;
        }
        Field[] fields = superClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers()) && f.get(src) == null) {
                fieldSet.add(f.getName());
            }
        }
        getParentNullField(fieldSet, src, superClass);
    }


}
