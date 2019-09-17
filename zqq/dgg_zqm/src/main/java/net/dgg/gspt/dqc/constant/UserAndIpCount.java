package net.dgg.gspt.dqc.constant;

/**
 * @author 刘阳
 * @ClassName <UserAndIpCount>
 * @despriction
 * @create 2018/10/21 13:21
 **/
public interface UserAndIpCount {

    int keyExpire = 65; // 过期时间，秒

    String userCountKeyPrefix = "user_count_"; // 记录用户每分钟访问次数

    String ipCountKeyPrefix = "ip_count_"; // 记录用户每分钟访问次数

    String ipDisableKeyPrefix = "ip_disable_"; // ip 禁用
}
