package net.dgg.tmd.foundation.platform.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户验证类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/2/24
 * Time:11:08
 */
public class DateUtils {

    /**
     * 验证从服务器获取的Json转为map后，属性是否为空，如果不为空则将属性的Object按照固定格式转为Date
     * @param obj
     * @return
     */
    public static Date formatyyyyMMDDHHmmss(Object obj){
        if(null == obj){
            return null;
        }else{
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
