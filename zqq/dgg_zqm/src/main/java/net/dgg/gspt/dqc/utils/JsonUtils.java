package net.dgg.gspt.dqc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * json工具类
 *
 * @author nature
 */
public class JsonUtils {

    /**
     * 对象到json字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.BrowserCompatible,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
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

    public static <T> List<T> json2List(String jsonStr, Class<T> objClass) {
        return JSON.parseArray(jsonStr, objClass);
    }


    /**
     * 将对象转换成json字符串。
     * <p>
     * <pre>
     * 如果传入的为数组、List、Set，并且内容不含复杂数据对象，如String [] arr = {"a","b"};
     * 转后的数据格式为：["a","b"],与javascript的数组格式相同。
     * </pre>
     * <p>
     * <pre>
     * 如果传入的对象的引用属性与其他的实体具有懒加载lazy=true的关系，会报session关闭异常。
     * 如果需要对该对象进行序列化，请将lazy设置为false即可解决。
     * </pre>
     *
     * @param object 传入的对象：集合，map，对象都可以
     * @return 转换成功的JSON字符串,{key:value}
     */
    public static String toJSONString(Object object) {
        String jsonString = null;
        try {
            Gson gson = new Gson();
            jsonString = gson.toJson(object);

        } catch (Exception e) {
        }
        return jsonString;
    }

    /**
     * 将对象转换成json字符串。转换空字段
     * 当name为空时,json串为{"name",""}，或者{"name",null}
     * <pre>
     * 如果传入的为数组、List、Set，并且内容不含复杂数据对象，如String [] arr = {"a","b"};
     * 转后的数据格式为：["a","b"],与javascript的数组格式相同。
     * </pre>
     * <p>
     * <pre>
     * 如果传入的对象的引用属性与其他的实体具有懒加载lazy=true的关系，会报session关闭异常。
     * 如果需要对该对象进行序列化，请将lazy设置为false即可解决。
     * </pre>
     *
     * @param object 传入的对象：集合，map，对象都可以
     * @return 转换成功的JSON字符串,{key:value}
     */
    public static String toJSONStringConvertNull(Object object) {
        String jsonString = null;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            jsonString = gson.toJson(object);
        } catch (Exception e) {
        }
        return jsonString;
    }


    /**
     * <pre>将指定的对象转换成JSON字符串，同时可以设置不需要过滤的属性的全名集合
     * 如果filedFullNames为null，则说明不需要任何过滤，此时建议直接使用toJSONString(Object obj)即可;
     * e.g.
     * String filedFullNames = {"java.util.List<com.abc.User>"};
     * toJSONString(new UserType(),types)
     * </pre>
     *
     * @param object         需要转换的对象
     * @param sikpFieldNames 需要过滤的属性全名 e.g. {"java.util.List<com.abc.User>"}
     * @return 转换成功的JSON字符串,{key:value}
     */
    public static String toJSONString(Object object, String[] sikpFieldNames) {
        final String[] types = sikpFieldNames;
        new GsonBuilder().enableComplexMapKeySerialization();
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }


            public boolean shouldSkipField(FieldAttributes f) {
                if (types != null && Arrays.asList(types).contains(f.getDeclaredType().toString())) {
                    return true;
                }
                return false;
            }
        }).create();
        return gson.toJson(object);
    }

    /**
     * 将JSON字符串转成对应的java对象，若传入的对象不满足转换的条件，则返回null
     * 实现：调用alibba.fastJson的JSON.parseObject，传入jsonString，clazz即可。 对该方法进行try
     * catch,若发生异常，则返回null
     *
     * @param jsonString 传入的JSON字符串
     * @param clazz      需要将JSON字符串已经该对象进行绑定才能转换
     * @return 转换成功的java对象
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
        }
        return t;
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
    public static Map<String,Object> getResponseForMap(int code,String msg,Object data){
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        if (data!=null){
            map.put("data",data);
        }
        return map;
    }

}
