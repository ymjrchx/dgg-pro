package net.dgg.zqq.framework.sms;


import net.dgg.zqq.utils.JsonUtils;

import java.util.Map;

/**
 * Created by gene on 2017/9/14.
 * desc:分页查询工具
 */
public class PageUtil {

    /**
     * 开始条数，结束条数条件限制统一判断
     * @param startCount
     * @param endCount
     * @return
     */
    public static Map<String,Object> serarchContition(Integer startCount,Integer endCount) {

        //前端非法数据判断
        if (startCount == null || endCount == null) {
            return JsonUtils.getResponseForMap(-2, "开始条数和结束条数不能为空", null);
        }
        if (startCount <= 0) {
            //负数
            return JsonUtils.getResponseForMap(-2, "请求非法，开始条数必须大于0", null);
        } else {
            if (endCount == 0) {
                return JsonUtils.getResponseForMap(-2, "请求非法，结束条数不能小于1", null);
            }
            //开始条数大于结束条数
            if (startCount > endCount) {
                return JsonUtils.getResponseForMap(-2, "请求非法，开始条数不能大于结束条数", null);
            } else {
                //查询数据最多为1000条
                    //开始条数为0
                    if (endCount - startCount+1 > 100) {
                        return JsonUtils.getResponseForMap(-2, "失败，查询条数不能大于100条", null);
                    }
            }
        }
       return null;
    }

}
