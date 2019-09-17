package net.dgg.dqc.backservice.utils;

import java.util.UUID;

/**
 * @author 刘阳
 * @ClassName <UserKeyUtils>
 * @despriction 用户接口Key的工具
 * @create 2018/07/25 13:54
 **/
public class UserKeyUtils {

    /**
     * 创建用户key
     *
     * @return
     */
    public static String createUserKey() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


}
