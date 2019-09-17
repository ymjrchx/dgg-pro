package net.dgg.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json序列化帮助类
 *
 * @author nature
 * @create 2017-12-19 22:53
 */
public class DggJsonUtils {
    /**
     * 对象到json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * json字符串到对象
     *
     * @param jsonStr
     * @param objClass
     * @return
     */
    public static <T> T json2Obj(String jsonStr, Class<T> objClass) {
        T t = JSON.parseObject(jsonStr, objClass);
        return t;

    }

    /**
     * 反序列化java类型为数组
     *
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> objClass) {
        return JSON.parseArray(jsonStr, objClass);
    }

    /**
     * 反序列化json字符串为目标对象基类数组
     *
     * @param jsonStr          json字符串
     * @param objClass         对象的实际类型
     * @param destinationClass 返回值中的类型，这个类型必须为实际类型的基类，否则会出现异常
     * @param <T>              对象的实际类型
     * @param <TT>             返回值需要的类型
     * @return
     */
    public static <T, TT extends T> List<T> json2List(String jsonStr,
                                                      Class<TT> objClass, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<T>();
        List<TT> parseArray = json2List(jsonStr, objClass);
        for (T t : parseArray) {
            destinationList.add(t);
        }
        return destinationList;
    }
    
    public static <T> T transferTo(Object text, Class<T> clazz) {
        return JSONObject.parseObject(JSONObject.toJSONString(text), clazz);
    }

    /**
     * 设置公共json字符串返回信息
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static String getJsonString(int code,String msg,Object data){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if(data != null)
            json.put("data", data);
        return json.toJSONString();
    }

    /**
     * map方式公共返回方法
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Map<String,Object> getResponseForMap(int code, String msg, Object data){
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        if (data!=null){
            map.put("data",data);
        }
        return map;
    }
}
