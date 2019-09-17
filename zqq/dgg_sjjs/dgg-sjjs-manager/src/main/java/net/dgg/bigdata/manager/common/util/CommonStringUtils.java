package net.dgg.bigdata.manager.common.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Create by chenz on 2018/4/4
 */
public class CommonStringUtils {

    /**
     * 获取带逗号格式Id字符串的set集合
     * @param str 字符串，以逗号分隔用set去重复
     * @return
     */
    public static Set<Long> splitIdToSet(String str) throws RuntimeException {
        Set<Long> set = new HashSet<>();
        try {
            if (!str.isEmpty()) {
                String[] strArry = str.split(",");
                //去重复操作
                for (int i = 0; i < strArry.length; i++) {
                    set.add(Long.valueOf(strArry[i]));
                }
            }
        }catch (RuntimeException ex){
            throw new RuntimeException("格式错误");
        }
        return set;
    }
}
