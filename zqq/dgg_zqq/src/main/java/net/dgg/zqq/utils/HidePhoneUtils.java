package net.dgg.zqq.utils;

import net.dgg.iboss.base.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <HidePhoneUtils>
 * @despriction 电话隐藏 工具
 * @create 2018/08/13 20:19
 **/

public class HidePhoneUtils {

    public static void hidePhone(String[] colums, List<Map> list) {
        List<String> hideList = Arrays.asList(colums);
        for (Map map : list) {
            hideMap(hideList, map, null);
        }
    }

    /**
     * 递归 隐藏
     *
     * @param hideList
     * @param map
     * @param jsonPath
     */
    private static void hideMap(List<String> hideList, Map map, String jsonPath) {
        if (hideList == null || hideList.size() == 0 || map == null || map.size() == 0) {
            return;
        }
        for (Object key : map.keySet()) {
            String strKey = String.valueOf(key);
            Object val = map.get(strKey);
            if (val == null) {
                continue;
            }
            String keyJsonPath = org.apache.commons.lang.StringUtils.isEmpty(jsonPath) ? strKey : jsonPath.concat(".").concat(strKey);
            if (hideList.contains(keyJsonPath) && val instanceof String && !"".equals(val)) {
                map.put(key, StringUtils.encryptionNumber(String.valueOf(val)));
                continue;
            }
            if (val instanceof Map) {
                hideMap(hideList, (Map) val, keyJsonPath);
                continue;
            }
            if (val instanceof List) {
                for (Map sonMap : (List<Map>) val) {
                    hideMap(hideList, sonMap, keyJsonPath);
                }
                continue;
            }
            if (val instanceof Map[]) {
                for (Map sonMap : (Map[]) val) {
                    hideMap(hideList, sonMap, keyJsonPath);
                }
                continue;
            }

        }
    }


}
