/******************************************************************************
 * Copyright (C) 2017 Chengdu Dlhd information industry co. LTD.
 * All Rights Reserved.
 * 本软件为成都顶联互动信息产业有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package net.dgg.bigdata.manager.treebook;


import net.dgg.bigdata.manager.treebook.service.TreeBookService;
import net.dgg.core.utils.DggJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName: TranslateUtil
 * @Description: 翻译数据字典
 * @Author 汤宏
 * @date 2017/11/7 17:24
 */
@Component
public class TranslateUtil {
    @Autowired
    private TreeBookService treeBookService;

    /**
     * 集合数据List<GlcUtils>中字典翻译
     *
     * @param columns    需要翻译的列的key 如：String[] column = {"businessstatus","typecode"};
     * @param columnList 需要翻译的列
     */
    public <T> List<T> translateList(String[] columns, List<T> columnList) {

        //初始化需要返回的集合
        List<T> columnListRe = new ArrayList<T>();
        //遍历入参集合
        if (null != columns && null != columnList) {
            //查询codes集合
            Map<String, String> valMap = this.getListValue(columns, columnList);
            int listSize = columnList.size();
            for (int i = 0; i < listSize; i++) {
                T t = columnList.get(i);
                //对象转Map
                t = translateObjectByValMap(columns, t, valMap);
                columnListRe.add(t);
            }
        }
        return columnListRe;
    }

    /**
     * 针对对象进行属性code翻译
     *
     * @param columns 要翻译的属性数组（String）;如：String[] column = {"businessstatus","typecode"};
     * @param t       要翻译的对象
     * @return
     * @throws IllegalAccessException
     * @Author 周潮
     */
    public <T> T translateObject(String[] columns, T t) {
        T reT = null;
        if (null != columns && null != t) {
            String codes = getObjectCodes(columns, t);
            if (StringUtils.isNotEmpty(codes)) {
                //查询codes集合
                Map<String, String> valMap = treeBookService.getTreeBookNameMulti(codes);
                reT = translateObjectByValMap(columns, t, valMap);
            } else {
                reT = t;
            }
        }
        return reT;
    }

    /**
     * 翻译对象
     *
     * @param column
     * @param t
     * @param valMap code和值
     * @param <T>
     * @return
     */
    public static <T> T translateObjectByValMap(String[] column, T t, Map<String, String> valMap) {
        if (null != column && null != t && null != valMap) {
            //对象转Map
            Map<String, Object> map = object2Map(t);
            //遍历获取需要翻译的code
            for (String key : map.keySet()) {
                //判断需要翻译的code
                if (useLoop(column, key)) {
                    if (null != map.get(key)) {
                        //获取需要翻译的key值
                        String kk = map.get(key).toString();
                        //可能存在一个值有多个key
                        String[] kkStr = kk.split(",");
                        String vals = "";
                        //遍历切割后的集合进行翻译
                        for (int j = 0; j < kkStr.length; j++) {
                            String k = kkStr[j];
                            String val = valMap.get(k);//从返回值中获取值
                            val = (null != val ? val : k);
                            vals += j == 0 ? val : "," + val;
                        }
                        map.put(key, vals);
                    }
                }
            }
            t = (T) map2Object(map, t);
        }
        return t;
    }

    /**
     * 根据单个字典CODE获取数据字典值
     *
     * @param code 字典CODE
     * @return
     */
    public String translateCode(String code) {

        String value = "";
        if (!StringUtils.isEmpty(code)) {
            value = treeBookService.getTreeBookName(code.trim());
        }
        return StringUtils.isNotEmpty(value) ? value : code;
    }

    /**
     * 根据多个字典CODE获取数据字典值，逗号分割：CODE1,CODE2
     *
     * @param codes 字典CODE  逗号分割：CODE1,CODE2
     * @return
     */
    public String translateCodes(String codes) {

        String values = "";
        if (!StringUtils.isEmpty(codes)) {
            codes = repeatCodes(codes);
            Map<String, String> valMap = treeBookService.getTreeBookNameMulti(codes);
            int flot = 0;
            for (String key : valMap.keySet()) {
                String mVal = valMap.get(key);
                values += (flot == 0 ? mVal : "," + mVal);
                flot++;
            }
        }
        return StringUtils.isEmpty(values) ? codes : values;
    }

    /**
     * 获取集合中code值翻译
     *
     * @param columns
     * @param columnList
     * @return
     * @throws IllegalAccessException
     */
    public <T> Map<String, String> getListValue(String[] columns, List<T> columnList) {

        Map<String, String> valMap = new HashMap<String, String>();
        //遍历入参集合
        if (null != columns && null != columnList) {
            //先获取需要翻译的code
            String codes = getListCodes(columns, columnList);
            valMap = treeBookService.getTreeBookNameMulti(codes);
        }
        return valMap;
    }

    /**
     * 获取集合codes值
     *
     * @param columns
     * @param columnList
     * @param <T>
     * @return
     */
    public static <T> String getListCodes(String[] columns, List<T> columnList) {
        String codeStr = "";
        //遍历入参集合
        if (null != columns && null != columnList) {
            //先获取需要翻译的code
            for (int i = 0; i < columnList.size(); i++) {
                T t = columnList.get(i);
                String codss = getObjectCodes(columns, t);
                if (StringUtils.isNotEmpty(codss)) {
                    codeStr += (i == 0 ? codss : "," + codss);
                }

            }
            codeStr = repeatCodes(codeStr);
        }
        return codeStr;
    }

    /**
     * 获取对象中的code值
     *
     * @param columns
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String getObjectCodes(String[] columns, T t) {
        String codes = "";
        //遍历入参集合
        if (null != columns && null != t) {
            //对象转Map
            Map<String, Object> map = object2Map(t);
            //遍历获取需要翻译的code
            int flot = 0;
            for (String key : map.keySet()) {
                //判断需要翻译的code
                if (useLoop(columns, key)) {
                    if (null != map.get(key) && !"".equals(map.get(key))) {
                        codes += (flot == 0 ? map.get(key).toString() : "," + map.get(key).toString());
                        flot++;
                    }
                }
            }
        }
        codes = repeatCodes(codes);
        return codes;
    }


    /**
     * 多个code字符串去重
     *
     * @param codes
     * @return
     */
    public static String repeatCodes(String codes) {
        //剔除重复的code
        Set toCodeSet = new HashSet();
        String[] codeStr = codes.split(",");
        for (int i = 0; i < codeStr.length; i++) {
            toCodeSet.add(codeStr[i]);
        }
        Iterator it = toCodeSet.iterator();
        codes = "";//获取需要的code集合，逗号分割
        int flot = 0;
        while (it.hasNext()) {
            codes += (flot == 0 ? it.next() : "," + it.next());
            flot++;
        }
        return codes;

    }

    /**
     * 查询数组中有无某个值
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }


    /**
     * bean转map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Class<?> clazz = obj.getClass();
        if ("java.util.HashMap".equals(clazz.getName())) {
            return (Map<String, Object>) obj;
        }
        Map map = new HashMap();
        try {
            map = DggJsonUtils.json2Obj(DggJsonUtils.obj2Json(obj), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * map转bean
     *
     * @param map
     * @param obj
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Object obj) {
        Class<?> clazz = obj.getClass();
        if ("java.util.HashMap".equals(clazz.getName())) {
            return map;
        }
        try {
            obj = DggJsonUtils.json2Obj(DggJsonUtils.obj2Json(map), obj.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;

    }

}
