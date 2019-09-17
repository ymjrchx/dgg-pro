package net.dgg.zqq.framework;

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


    /**
     * redis 数据超时变量设置
     */
    public static String TOKEN_EXPIRE = "token.expire";

    public static String TOKEN_EXPIRE_DAY = "token.expire.day";

    public static String IMG_VERIFY_EXPIRE = "img.verify.expire";

    public static String TRKKKEY_EXPIRE = "baiduKey.expire";

    public static String SMS_VERIFY_EXPIRE = "sms.verify.expire";
    
    /**
     * 分页查询请求条数
     */
    public static int PAGE_SIZE = 100;

    public static final String PROMPT = "未找到相关数据";
    public static final String INPUT_PARAMS = "请输入查询参数";
    public static final String LOGIN_ONE = "请先登录，才可以查看下一页";
    public static final String LOGIN_TWO = "请先登录，才可以查看详情";
    public static final String LOGIN_THREE = "请先登录，才可以查看列表信息";

    public static final String BRAND_SEARCH = "7732848203987546112";
    public static final String APPLY_PERSON = "apply_person"; //申请人
    public static final String APPLY_TMGOODSSERVICE = "goodsService"; //商品服务
    public static final String APPLY_TYPE = "apply_type_p"; //申请类别
    public static final String LAY_STATE = "lay_status"; //法律状态
    public static final String APPLY_YEAR = "apply_year"; //申请年份

    public static final String LAW_RULES = "rules"; //法律状态规则

    public static final String BRAND_NAME = "name";
    public static final String BRAND_TYPE = "intCls";
    public static final String BRAND_SERVERNUM = "serverNum";
    public static final String BRAND_STATE = "status";
    public static final String BRAND_APPDATE = "appDate";
    public static final String BRAND_REGNO = "regNo";

    public static final String FILTER_TYPE = "filterType";
    public static final String KEYWORLD = "keyWorld";

    public static final String BAIDU_RN = "1000";

    public static final String AGGREATE_LIST = "dgg";
    public static final String AGGREATE_IMG_DGG = "imgDgg";

    public static final String MIX_TAX = "tax"; //税号
    public static final String MIX_PATENT = "patent"; //专利
    public static final String MIX_BRAND = "brand"; //商标

    public static final String BRAND_APPLICATCN = "applicantCn"; //商标申请人
    public static final String BRAND_IMG_SEARCH = "imgSearch"; //图像搜索
    public static final String BRAND_TMGOODSSERVICE = "tmGoodsService"; //商品服务
    public static final String BRAND_ES_ID = "esId"; //商标唯一确认值
    public static final String BRAND_IMG_URL = "tmiPath"; //imgUrl

    public static final String KEY_ITEM = "nameJs"; //key值选项

    public static final String FT_ALL = "all"; //所有
    public static final String FT_JQ = "jq"; //精确
    public static final String FT_BF = "bf"; //部分
    public static final String FT_JIAZ = "jiaz"; //加字
    public static final String FT_JIANZ = "jianz"; //减字
    public static final String FT_BZ = "bz"; //变字
    public static final String FT_HX = "hx"; //换序
    public static final String FT_PY = "py"; //拼音
    public static final String FT_TSZF = "tszf"; //特殊字符
    public static final String FT_XJZ = "xjz"; //形近字

    public static final String INDEX_NAME = "name"; //企业名
    public static final String COMPANY_ID = "companyId";
    public static final String INDEX_ZLSQH = "applicationnumber"; //专利申请号
    public static final String INDEX_ZLGBH = "piApplyPublishNum"; //专利公布号
    public static final String INDEX_ZLGBR = "piApplyAnnounceDate"; //专利公布日
    public static final String INDEX_TYPE_NUM = "piClassifyNum"; //专利分类号
    public static final String PATENT_APPLICATION_DATE = "applicationDate"; //专利申请日
    public static final String INDEX_ZLFMR = "inventorString"; //专利发明人
    public static final String INDEX_ZLLB = "kindCodeDesc"; //专利类别
    public static final String INDEX_ZLSQR = "assigneestring"; //专利申请人
    public static final String INDEX_ZLMC = "piInventName"; //专利名称
    public static final String INDEX_FLZT = "legalStatusDesc"; //专利法律状态

    //专利列表日期搜索条件
    public static final String PATENT_DATE_TYPE = "patentDateType"; //专利日期类型
    public static final String PATENT_DATE_START = "patentStartDate"; //专利开始时间
    public static final String PATENT_DATE_END = "patentEndDate"; //专利结束时间

    public static boolean F_TRUE = true;
    public static boolean F_FALSE = false;

    public static final String BAIDU_TAGS = "100,10";
    public static final String BAIDU_TAG_LOGIC = "0";

    //old
    public static final String QYGS_ENCOKIND = "qygsDetail.econKind";
    public static final String QYGS_STATUS = "qygsDetail.status";
    public static final String QYGS_REGISTCAPI = "qygsDetail.registCapi";
    public static final String QYGS_STARTDATE = "qygsDetail.startDate";
    public static final String QYGS_PROVINCE = "qygsDetail.province";  //old
    public static final String QYGS_INDUSTRY = "qygsDetail.industry";

    //商标寓意
    public static final String BRAND_MORAL_KEY = "searchKey";

}
