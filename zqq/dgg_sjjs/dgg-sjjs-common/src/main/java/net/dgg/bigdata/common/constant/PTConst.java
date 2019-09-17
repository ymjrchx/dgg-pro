package net.dgg.bigdata.common.constant;

/**
 * 项目常量设置
 * Created by wu on 2017/7/12.
 */
public class PTConst {

    /**
     * erp用户密匙
     */
    public static final String PWD_KEY="DGGERP962540ADMIN";


    public static String USER_TOKEN="token";


    public static String SYSUSER_TOKEN = "Authorization";

    /**
     * 返回状态码
     */
    public static String RSP_CODE="code";

    /**
     * 返回信息
     */
    public static String RSP_MSG="msg";


    /**
     * 验证码标志
     */
    public static String VERIFY_IMG  = "img";
    public static String VERIFY_SMS = "sms";



    public static String IMG_VERIFY_EXPIRE = "img.verify.expire";

    public static String TRKKKEY_EXPIRE = "baiduKey.expire";

    public static String SMS_VERIFY_EXPIRE = "sms.verify.expire";
    
    /**
     * 分页查询请求条数
     */
    public static int PAGE_SIZE = 100;

    public static final String PROMPT = "未找到相关数据";
    public static final String UPSIZE_PROMPT = "数据量太大，请更换搜索词";
    public static final String INPUT_PARAMS = "请输入查询参数";
    public static final String LOGIN_ONE = "请先登录，才可以查看下一页";
    public static final String LOGIN_TWO = "请先登录，才可以查看详情";
    public static final String LOGIN_THREE = "请先登录，才可以查看列表信息";

}
