package net.dgg.zqq.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/16.
 */
public class ThreadHelper {

    private static final ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    public static void putThreadContextVar(String name, Object val) {
        if (local.get() == null) {
            local.set(new HashMap<>());
        }
        local.get().put(name, val);
    }

    public static <T> T getThreadContextVar(String name) {
        if (local.get() == null) {
            local.set(new HashMap<>());
        }
        return (T) local.get().get(name);
    }

    public static void removeThreadContextVar(String name) {
        if (local.get() != null) {
            local.get().remove(name);
        }
    }
}
