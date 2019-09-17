package net.dgg.zqq.utils;/**
 * Created by Administrator on 2018/5/11.
 */

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <GsonUtils>
 * @despriction json 工具
 * @create 2018/05/11 9:58
 **/
public class GsonUtils {
    private static String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 创建 转 json 字符串的Gson 对象 数字类型 全部转换为字符串
     *
     * @return
     */
    public static Gson buildSerGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new JsonSerializer<Integer>() {
                    @Override
                    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
                        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(String.valueOf(src));
                    }
                }).registerTypeAdapter(Long.class, new JsonSerializer<Long>() {
                    @Override
                    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
                        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(String.valueOf(src));
                    }
                }).registerTypeAdapter(BigDecimal.class, new JsonSerializer<BigDecimal>() {
                    @Override
                    public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
                        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(String.valueOf(src.setScale(2)));
                    }
                }).setDateFormat(dateFormatPattern)
                .create();
        return gson;
    }

    /**
     * 转json 字符串
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        return buildSerGson().toJson(o);
    }

    /**
     * @Title:
     * @Description: TODO String转map
     * @param:
     * @return:
     * @throw:
     * @author: 代杨
     * @creat_date: 2018/6/11
     **/
    public static Map<String, Object> json2map(String str_json) {
        Map<String, Object> res = null;
        try {
            Gson gson = new Gson();
            res = gson.fromJson(str_json, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }

}
