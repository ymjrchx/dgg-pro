package net.dgg.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.dgg.core.utils.DggStringUtils.isNotEmpty;

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
            String keyJsonPath = StringUtils.isEmpty(jsonPath) ? strKey : jsonPath.concat(".").concat(strKey);
            if (hideList.contains(keyJsonPath) && val instanceof String && !"".equals(val)) {
                map.put(key, encryptionNumber(String.valueOf(val)));
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

    public static String encryptionNumber(String number) {
        if (isNotEmpty(number)) {
            if (number.contains(",")) {
                String[] num = number.split(",");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < num.length; i++) {
                    sb.append(encity(num[i])).append(",");
                }
                return sb.toString().substring(0, sb.toString().length() - 1);
            } else {
                return encity(number);
            }
        } else {
            return "";
        }
    }

    private static String encity(String number) {
        int length = number.length();
        if (length < 1) {
            return number;
        } else if (length == 1) {
            return "*";
        } else if (length > 10) {
            return number.substring(0, 3) + "****" + number.substring(length - 4, length);
        } else {
            return length <= 10 && length > 3 ? number.substring(0, length - 4) + "****" : number.substring(0, 1) + "*";
        }
    }


}
