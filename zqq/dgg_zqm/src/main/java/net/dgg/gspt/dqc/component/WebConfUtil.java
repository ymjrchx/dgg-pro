package net.dgg.gspt.dqc.component;


import net.dgg.gspt.dqc.constant.StatusConstant;
import net.dgg.gspt.dqc.constant.WebConfKeyConstant;
import net.dgg.gspt.dqc.framework.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author 刘阳
 * @ClassName <WebConUtil>
 * @despriction 公共方法
 * @create 2018/08/10 11:29
 **/
interface WebConfUtil {

    /**
     * 根据code 设置自身数值,并设置到redis
     *
     * @param code
     * @param value
     */
    default void setSelfAndRedis(String code, String value) throws NoSuchFieldException, IllegalAccessException {
        Field codeField = this.getClass().getDeclaredField(code);
        codeField.setAccessible(Boolean.TRUE);
        codeField.set(this, value);
        RedisUtils.set(WebConfKeyConstant.WEB_CONF_KEY.concat(code), value);
    }

    /**
     * 获取中文字段解释
     *
     * @param code
     * @return
     */
    default String getFieldExplain(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        Field codeField = null;
        try {
            codeField = this.getClass().getDeclaredField(code);
        } catch (NoSuchFieldException e) {

        }
        if (codeField == null) {
            return null;
        }
        WebConfPamExplain webConPamExplain = codeField.getDeclaredAnnotation(WebConfPamExplain.class);
        return webConPamExplain.value();
    }

    /**
     * 字符串 转型
     *
     * @param val
     * @param type
     * @return
     */
    default Object transToType(String val, String type) {
        if (val == null) {
            return null;
        }
        switch (type) {
            case "Integer":
                return Integer.valueOf(val);
            case "String":
                return val;
            case "Long":
                return Long.valueOf(val);
            case "Boolean":
                switch (val) {
                    case StatusConstant.ENABLE:
                        return Boolean.TRUE;
                    case StatusConstant.DISABLE:
                        return Boolean.FALSE;
                    default:
                        return Boolean.valueOf(val);
                }
            case "Float":
                return Float.valueOf(val);
            case "Double":
                return Double.valueOf(val);
            case "BigDecimal":
                return new BigDecimal(val);
            default:
                return val;
        }
    }

    default Integer parseInteger(String val) {
        return StringUtils.isEmpty(val) ? null : Integer.valueOf(val);
    }

    default Long parseLong(String val) {
        return StringUtils.isEmpty(val) ? null : Long.valueOf(val);
    }

}
