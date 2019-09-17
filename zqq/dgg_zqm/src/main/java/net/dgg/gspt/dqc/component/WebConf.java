package net.dgg.gspt.dqc.component;


import org.springframework.stereotype.Component;

/**
 * @author 刘阳
 * @ClassName <WebConf>
 * @despriction web配置缓存bean， 属性 首字母 不能大写
 * @create 2018/08/09 10:18
 **/
@Component
public class WebConf implements WebConfUtil {


    @WebConfPamExplain("ip每分钟访问次数上限")
    private String ip_per_minute_limit;

    @WebConfPamExplain("账户每分钟访问次数上限")
    private String user_per_minute_limit;

    @WebConfPamExplain("用户未登录时可查看的数据数量")
    private String login_out_data_num_limit;

    @WebConfPamExplain("普通用户登录后可查看的数据数量")
    private String login_data_num_limit;


    public Integer getLogin_out_data_num_limit() {
        return parseInteger(login_out_data_num_limit);
    }

    public Integer getLogin_data_num_limit() {
        return parseInteger(login_data_num_limit);
    }

    public Integer getIp_per_minnute_limit() {
        return parseInteger(ip_per_minute_limit);
    }

    public Integer getUser_per_minnute_limit() {
        return parseInteger(user_per_minute_limit);
    }
}
