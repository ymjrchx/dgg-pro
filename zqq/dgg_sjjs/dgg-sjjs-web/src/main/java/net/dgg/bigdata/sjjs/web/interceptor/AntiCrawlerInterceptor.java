package net.dgg.bigdata.sjjs.web.interceptor;

import net.dgg.bigdata.common.constant.PTConst;
import net.dgg.bigdata.sjjs.web.component.WebConf;
import net.dgg.bigdata.sjjs.web.constant.ForbidTypeConstant;
import net.dgg.bigdata.sjjs.web.constant.StatusConstant;
import net.dgg.bigdata.sjjs.web.constant.UserAndIpCount;
import net.dgg.bigdata.sjjs.web.entity.ForbidIpRecord;
import net.dgg.bigdata.sjjs.web.service.ForbidIpRecordService;
import net.dgg.bigdata.sjjs.web.service.UserService;
import net.dgg.bigdata.sjjs.web.service.impl.UserExtService;
import net.dgg.core.redis.DggRedisService;
import net.dgg.core.utils.common.DggKeyWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 反爬虫拦截器，检测ip及账户访问频率
 * Created by zyw on 2018/10/12.
 */
@Component
public class AntiCrawlerInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(AntiCrawlerInterceptor.class);

    private DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");

    @Autowired
    UserService userService;
    @Autowired
    UserExtService userExtService;
    @Autowired
    WebConf webConf;
    @Autowired
    private ForbidIpRecordService forbidIpRecordService;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("进入反爬虫拦截器");
        if (this.checkUserId(request) && this.checkIp(request)) {

            return true;
        }

        return false;


        /*if (StringUtils.isNotEmpty(userid)) {//存在匿名用户
            User user = userDao.findUserById(userid);
            if (httpServletRequest.getParameter("username") == null)
                httpServletRequest.setAttribute("username", user.getUsername());//缓存用户名
            //检查用户状态
            String status = user.getStatus();
            if (status == null || status.equals(StatusConstant.DISABLE)) return false;

            key = String.format("ZQQ_USER_COUNT_%s", userid);
            isNormal = IsFrequencyNormal(key, null);
            if (!isNormal) {
                user.setStatus(StatusConstant.DISABLE);
                userDao.updateUser(user);
                DggRedisService.del(key);
                return false;
            }
        }*/

        //ip检查
        /*String realIp = IpAddressUtils.getIP(httpServletRequest);
        key = String.format("ZQQ_IP_COUNT_%s", realIp);
        Map retMap = new HashMap();
        isNormal = IsFrequencyNormal(key, retMap);
        if (retMap.containsKey("forbidIp")) {
            Map forbidRecord = new HashMap();
            forbidRecord.put("id", KeyWorker.nextId());
            forbidRecord.put("ip", realIp);
            forbidRecord.put("createTime", new Date());
            forbidIpsDao.insert(forbidRecord);
        }*/

    }

    /**
     * 检查访问频率是否正常
     *
     * @param key redis key
     * @return true：正常 false：异常
     */
    private boolean IsFrequencyNormal(String key, Map returnMap) {

        String value = DggRedisService.get(key);
        if (value == null || value.equals("")) {//me没有记录第一次访问直接放行
            if (key.indexOf("USER") > -1) {
                value = String.format("%s %s", new Date().getTime(), 1);//组成：（统计开始的）时间戳,统计次数
            } else {
                value = String.format("%s %s %s", 1, new Date().getTime(), 1);// 字符构成： 状态（1:正常，2:封禁）时间戳 次数
            }
            DggRedisService.set(key, value);
            return true;
        }
        String[] valueArr = value.split(" ");
        int len = valueArr.length;

        //ip额外提前检查封禁状态
        if (key.indexOf("IP") > -1) {
            if (valueArr[0].equals("2")) return false;
        }

        //频率检查
        int timeLimt = 60;
        int countLimit = key.indexOf("IP") > -1 ? webConf.getIp_per_minnute_limit() : webConf.getUser_per_minnute_limit();
        int count = Integer.parseInt(valueArr[len - 1]);
        count++;
        if (count >= countLimit) {
            Long interval = new Date().getTime() - Long.valueOf(valueArr[len - 2]);
            boolean flag = interval <= timeLimt * 1000;
            if (flag && key.indexOf("IP") > -1 && returnMap != null) {//ip添加封禁记录
                DggRedisService.set(key, "2");
                returnMap.put("forbidIp", true);
            }
            if (!flag)//未超频，重置计算次数
            {
                DggRedisService.del(key);
            }
            return !flag;
        } else {
            valueArr[len - 1] = count + "";
            DggRedisService.set(key, StringUtils.join(valueArr, " "));//更新次数记录
        }
        return true;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }


    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 返回redis key
     *
     * @param userId
     * @return
     */
    private String getUserCountKey(String userId) {
        return UserAndIpCount.userCountKeyPrefix.concat(userId).concat("_").concat(format.format(System.currentTimeMillis()));
    }

    /**
     * 验证用户访问次数
     *
     * @param request
     * @return
     */
    private boolean checkUserId(HttpServletRequest request) {
        String userid = request.getHeader(PTConst.USER_TOKEN);
        if (StringUtils.isEmpty(userid)) {
            return true;
        }
        String userCountKey = this.getUserCountKey(userid); // 根据用户ID和 年月日时分 生成key
        Long t = DggRedisService.incr(userCountKey);
        DggRedisService.expire(userCountKey, UserAndIpCount.keyExpire); // 设置过期
        // 超过上限 则禁用用户 并且强制退出
        if (t.intValue() <= this.webConf.getUser_per_minnute_limit()) {
            return true;
        }
        this.userExtService.updateStatus(userid, StatusConstant.DISABLE);
        DggRedisService.del(userid);

        ForbidIpRecord record = new ForbidIpRecord();
        record.setId(DggKeyWorker.nextId());
        record.setIpOrUserId(userid);
        record.setFlag(1);
        record.setTimes(t.intValue());
        record.setType(ForbidTypeConstant.USER);
        record.setCreateTime(new Date());
        this.forbidIpRecordService.save(record);
        logger.info("被反爬虫拦截器拦截:userId:" + userid);
        return false;
    }

    /**
     * 返回redis key
     *
     * @param ip
     * @return
     */
    private String getIpCountKey(String ip) {
        return UserAndIpCount.ipCountKeyPrefix.concat(ip).concat("_").concat(format.format(System.currentTimeMillis()));
    }

    /**
     * 获取IP禁用Key
     *
     * @param ip
     * @return
     */
    private String getIpDisableKey(String ip) {
        return UserAndIpCount.ipDisableKeyPrefix.concat(ip);
    }

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    private boolean checkIp(HttpServletRequest request) {
        String ip = getIP(request);
        String ipDisableKey = this.getIpDisableKey(ip);
        // 检查 ip 是否已经被禁用
        if (DggRedisService.exists(ipDisableKey)) {
            return false;
        }
        // 检查访问次数
        String ipCountKey = this.getIpCountKey(ip);
        Long t = DggRedisService.incr(ipCountKey);
        DggRedisService.expire(ipCountKey, UserAndIpCount.keyExpire);
        if (t.intValue() <= this.webConf.getIp_per_minnute_limit()) {
            return true;
        }
        // 超过 则禁用IP
        DggRedisService.incr(ipDisableKey);

        ForbidIpRecord record = new ForbidIpRecord();
        record.setIpOrUserId(ip);
        record.setId(DggKeyWorker.nextId());
        record.setFlag(1);
        record.setTimes(t.intValue());
        record.setType(ForbidTypeConstant.IP);
        record.setCreateTime(new Date());
        this.forbidIpRecordService.save(record);
        logger.info("被反爬虫拦截器拦截:Ip:" + ip);
        return false;

    }


}
