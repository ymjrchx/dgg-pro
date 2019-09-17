package net.dgg.zqq.framework.interceptor;

import net.dgg.zqq.framework.PTConst;
import net.dgg.zqq.framework.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jiangsh on 2018/8/16 19:05
 */
public interface ConstMethod {

    /**
     * 简单分页封装
     *
     * @param pageIndex 第几页
     * @param pageSize  查多少条
     * @return
     */
    default Map<String, Object> pages(int pageIndex, int pageSize) {
        Map<String, Object> dataMap = new HashMap<>();
        int startCount = 1;
        int endCount = 10;
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 10;
        else endCount = pageSize;
        if (pageIndex > 1) {
            startCount = (pageIndex - 1) * pageSize + 1;
            endCount = pageIndex * pageSize;
        }
        dataMap.put("pageIndex", pageIndex);
        dataMap.put("pageSize", pageSize);
        dataMap.put("startCount", startCount);
        dataMap.put("endCount", endCount);
        return dataMap;
    }

    /**
     * 服务 简单分页封装
     *
     * @param pageIndex 第几页
     * @param pageSize  查多少条
     * @return
     */
    default Map<String, Object> pageService(int pageIndex, int pageSize) {
        //判断是否vip之类的，默认不超过50条
        if (pageSize > 50) pageSize = 50;

        Map<String, Object> dataMap = new HashMap<>();
        int startCount = 1;
        int endCount = 10;
        if (pageIndex <= 0) pageIndex = 1;
        if (pageSize <= 0) pageSize = 10;
        else endCount = pageSize;
        if (pageIndex > 1) {
            startCount = (pageIndex - 1) * pageSize + 1;
            endCount = pageIndex * pageSize;
        }
        dataMap.put("pageIndex", pageIndex);
        dataMap.put("pageSize", pageSize);
        dataMap.put("startCount", startCount);
        dataMap.put("endCount", endCount);
        return dataMap;
    }

    /**
     * 判断是否登录
     *
     * @param request
     * @return
     */
    default boolean justLogin(HttpServletRequest request) {
        String token = request.getHeader(PTConst.USER_TOKEN); //userId
        String username = RedisUtils.get(token);
        if (StringUtils.isNotEmpty(username)) return true; //已登录
        else return false;
    }

}
