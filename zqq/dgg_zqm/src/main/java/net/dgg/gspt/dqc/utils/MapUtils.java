/******************************************************************************
 * Copyright (C) 2017 Chengdu Dlhd information industry co. LTD.
 * All Rights Reserved.
 * 本软件为成都顶联互动信息产业有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package net.dgg.gspt.dqc.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/28.
 * map 工具类
 */
public class MapUtils {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>() {
            {
                // 为list添加数据
                add("1521"); // 纯数字数据
                add("wanghzen123"); // 数字+字符数据
                add("wangzhen"); // 纯字符串 
            }
        };

        System.out.println("初始数据：" + list);

        Predicate<String> filter = s -> !Pattern.compile("wangzhen").matcher(s).find();
        list.removeIf(filter);//移除

        System.out.println(list);
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map<String,Object>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map convertBean(Object bean) {
        if (bean == null) {
            return null;
        }
        Map map = new HashMap();
        for (Field field : getAllField(bean.getClass())) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class p = descriptor.getPropertyType();
            if (p.equals(Integer.class)) {
                //System.out.println("Integer");
            }
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }


    /**
     * 以json为中介转换map为bean
     *
     * @param type
     * @param map
     * @param ignoreAttr 忽略的属性
     * @param <T>
     * @return
     */
    public static <T> T convertJsonMap(Class<T> type, Map map, String... ignoreAttr) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies(new ExclusionStrategy() {
            private List<String> ignoreList = Arrays.asList(ignoreAttr);

            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return ignoreList.contains(f.getName());
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                //过滤掉 类名包含 Bean的类
                return clazz.getName().contains("Bean");
            }
        }).create();
        return gson.fromJson(gson.toJson(map), type);
    }


    /**
     * 根据字段 复制map集合
     *
     * @param mapList   处理的map集合
     * @param filterArr 保留在结果中的属性名数组
     * @return
     */
    public static List<Map> copyMapListByFilter(List<Map> mapList, String[] filterArr) {
        if (mapList == null || mapList.size() == 0 || filterArr == null || filterArr.length == 0) {
            return mapList;
        }
        List<Map> reMapList = new ArrayList<>(mapList.size());
        for (Map map : mapList) {
            Map temp = new HashMap();
            for (String s : filterArr) {
                temp.put(s, map.get(s));
            }
            reMapList.add(temp);
        }
        return reMapList;
    }


    /**
     * 根据key 复制map
     *
     * @param map
     * @param keys
     * @return
     */

    public static Map copyMapByKeys(Map map, String[] keys) {
        if (map == null || keys == null || keys.length == 0) {
            return null;
        }
        Map temp = new HashMap();
        for (String key : keys) {
            temp.put(key, map.get(key));
        }
        return temp;

    }

    private static Set<Field> getAllField(Class<?> beanClass) {
        Set<Field> fieldSet = new HashSet<>();
        for (Field field : beanClass.getDeclaredFields()) {
            fieldSet.add(field);
        }
        getParentField(fieldSet, beanClass);
        return fieldSet;
    }

    // 递归获取父类实体字段
    private static void getParentField(Set<Field> fieldSet, Class<?> entity) {
        Class<?> superClass = null;
        if (fieldSet == null || entity == null || (superClass = entity.getSuperclass()).equals(Object.class)) {
            return;
        }
        Field[] fields = superClass.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers()) && !f.getType().equals(Class.class)) {
                fieldSet.add(f);
            }
        }
        getParentField(fieldSet, superClass);
    }


    public static class A {
        private Integer a = 0;
        private String b = "b";

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }
    }

   /* public static void main(String[] a) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        MapUtils.A a1 = new A();
        a1.a = 1;
        Map map = convertBean(a1);
        System.out.println(new Gson().toJson(map));

        System.out.println(map.get("a") instanceof Integer);

        MapUtils.A b1 = (MapUtils.A) convertMap(MapUtils.A.class, map);

        System.out.println(new Gson().toJson(b1));


    }*/
}
