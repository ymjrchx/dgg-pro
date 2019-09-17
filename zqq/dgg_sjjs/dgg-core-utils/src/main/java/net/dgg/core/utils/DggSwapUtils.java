package net.dgg.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wu on 2017/8/25.
 */
public class DggSwapUtils {

    /**
     * 请求参数
     */
    public static String REQUEST = "_REQUEST";

    /**
     * 返回参数
     */
    public static String RESPONSE = "_RESPONSE";

    /**
     * 登录用户
     */
    public static String USER_NAME = "USER_NAME";

    static ThreadLocal<Map> threadLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new HashMap();
        }
    };

    public static void put(String key, Object value) {
        Map map = threadLocal.get();
        map.put(key, value);
    }

    public static Object get(String key) {
        return threadLocal.get().get(key);
    }

    public static Object getRequest() {
        return get(REQUEST);
    }

    public static Object getResponse() {
        return get(RESPONSE);
    }
}
