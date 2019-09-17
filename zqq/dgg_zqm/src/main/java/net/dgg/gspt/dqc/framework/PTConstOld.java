package net.dgg.gspt.dqc.framework;

/**
 * 项目常量设置
 * Created by wu on 2017/7/12.
 */
public class PTConstOld {

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

    public static String TRKKKEY_EXPIRE = "treeKey.expire";

    public static String SMS_VERIFY_EXPIRE = "sms.verify.expire";

    /**
     * 分页查询请求条数
     */
    public static int PAGE_SIZE = 100;

    public static final String COMPANY_ID = "companyId";

    /**
     * 首页相关
     */
    public static final String TEL = "更多号码"; //更多号码
    public static final String INDEX_NAME = "name"; //企业名
    public static final String INDEX_OPER_NAME = "qygsDetail.operName"; //法人名
    public static final String INDEX_STOCK_NAME = "qygsDetail.partnersList.stockName"; //股东名
    public static final String INDEX_GG_NAME = "qygsDetail.employeesList.name"; //高管
    public static final String INDEX_ADDRESS = "qygsDetail.address"; //地址
    public static final String INDEX_TEL = "tel"; //电话
    //    public static final String INDEX_EMAIL = "qygsDetail.contactInfoList.email"; //邮箱
    public static final String INDEX_EMAIL = "email"; //邮箱
    public static final String INDEX_WEBSITE_NAME = "qygsDetail.contactInfoList.webSiteName"; //网站名称
    public static final String INDEX_BUSI_SCOPE = "qygsDetail.scope"; //经营范围
    public static final String INDEX_PRODUCT_NAME = "jyzk.products.name"; //产品名称

    public static final String INDEX_LOGO = "logo"; //logo url
    public static final String INDEX_SHXYDM = "qygsDetail.creditCode"; //社会信用代码

    public static final String INDEX_ZLSQH = "applicationnumber"; //专利申请号
    public static final String INDEX_ZLGBH = "piApplyPublishNum"; //专利公布号
    public static final String INDEX_ZLGBR = "piApplyAnnounceDate"; //专利公布日
    public static final String INDEX_ZLFMR = "inventorString"; //专利发明人
    public static final String INDEX_ZLLB = "kindCodeDesc"; //专利类别
    public static final String INDEX_ZLSQR = "assigneestring"; //专利申请人
    public static final String INDEX_ZLMC = "piInventName"; //专利名称




    public static final String BASICINFO_CONTACTNO = "qynbDetail.basicInfoData.contactNo";
    public static final String BASICINFO_PHONENUMBER = "qygsDetail.contactInfoList.phoneNumber";
    public static final String BASICINFO_EMAIL = "qygsDetail.contactInfoList.email";
    public static final String BASICINFO_TEL = "briefIntroductionInfo.tel";
    public static final String BASICINFO_FPTT_PHONENO = "fptt.phoneNo";
    public static final String BASICINFO_QYNB_EMAIL = "qynbDetail.basicInfoData.emailAddress";



    /**
     * 列表相关
     */
    public static final String QYGS_ENCOKIND = "qygsDetail.econKind";
    public static final String QYGS_STATUS = "qygsDetail.status";
    public static final String QYGS_REGISTCAPI = "qygsDetail.registCapi";
    public static final String QYGS_STARTDATE = "qygsDetail.startDate";
    public static final String QYGS_PROVINCE = "qygsDetail.province";  //old
    public static final String QYGS_INDUSTRY = "qygsDetail.industry";

    /**
     * 数据字典相关
     */
    public static final String CODE_QXFW = "1212";
    public static final String CODE_ORG_TYPE = "org_type";
    public static final String CODE_QY_TYPE = "qy_type";
    public static final String CODE_QY_STATUS = "qy_status";
    public static final String CODE_ZCZB = "zczb";
    public static final String CODE_CL_DATE = "cl_date";
    public static final String CODE_PROVINCE = "7453706779315408896";
    public static final String CODE_INDUSTRY = "industry";
    public static final String CODE_GJSX = "gjsx";

    public static final String CODE_REGCAPI_CODE = "regCapiCon";
    public static final String TREEBOOK_ROOT_PID = "1";
    public static final String TREEBOOK_BRAND_HEALTH = "lay_status";
    public static final String TREEBOOK_JUDGE = "7726213051681193984";
    public static final String All = "all";
    public static final String EMAIL = "email";

    public static final String TREE_KEY = "treeKey";
    public static final String PROMPT = "未找到相关数据";
    public static final String PROMPT_MORE = "您的搜索词太宽泛，建议更换一下搜索词";

    public static final String CON_REASUE = "resumeTime";
    public static final String CON_REASUE_KEY = "hideEndTime";

    public static final String MIX_TAX = "tax"; //税号
    public static final String MIX_PATENT = "patent"; //专利
    public static final String MIX_BRAND = "brand"; //商标
    public static final String MIX_CPWS = "cpws"; //裁判文书
    public static final String MIX_CPWS_TS = "cpwsts"; //裁判文书-服务接口


    /**
     * 新闻
     */
    public static final String NEWS_TYPE = "newsType";

    /**
     * 匹配模式开关
     */
    public static final boolean F_FALSE = false;
    public static final boolean F_TRUE = true;
    public static final String SHOULD = "should";
    public static final String MUST = "must";


    public static final String QCC_WEBSITE_URL = "https://www.qichacha.com/";
    public static final String EQC_WEBSITE_URL = "http://www.eqicha.com/company/";
    public static final String ZCGL_WEBSITE_URL = "http://jzsc.mohurd.gov.cn/dataservice/query/comp/compDetail/";
    public static final String RESUME_WEBSITE_URL = "http://resume.58guakao.com/Resume/PhoneConvertToImg/";
    public static final String TARGET_PREFIX = "pre";
    public static final String TARGET_SUFFIX = "su";

    public static final String AGGREATE_LIST = "agg";
    public static final String AGGREATE_LIST_CPWS = "aggCpws";
    public static final String AGGREATE_LIST_CPWS_MAX = "aggCpwsMax";

    public static final String CPWS_XS = "刑事";
    public static final String CPWS_MS = "民事";
    public static final String CPWS_XZ = "行政";
    public static final String CPWS_PC = "赔偿";
    public static final String CPWS_ZX = "执行";

    //es存储的文书字段
    public static final String CPWS_CASE_REASON = "anyouTree"; //案由
    public static final String CPWS_COURT = "courtLevel"; //执行法院
    public static final String CPWS_JUDGEDATE = "judgeDate"; //裁定年份
    public static final String CPWS_TRIALROUND = "trialRound"; //审理程序
    public static final String CPWS_CASENAME = "docType"; //文书类型
    public static final String CPWS_AREA = "courtsTree"; //地域

    //文书聚合相关
    public static final String CPWS_1_CP_CASE2 = "cp_case2"; //案由
    public static final String CPWS_1_COUNTLEVEL = "cp_courtLevel"; //法院层级
    public static final String CPWS_1_CP_YEAR = "cp_year"; //裁定年份
    public static final String CPWS_1_CP_PROGRAM = "cp_program"; //审理程序
    public static final String CPWS_1_CP_TYPE = "cp_type"; //文书类型
    public static final String CPWS_1_CP_AREA = "cp_court"; //地域

    public static final String BRAND_MEASURE = "https://api.zhiqianqiu.com/predicate/registerPassingRate"; //注册通过率地址

    //商标
    public static final String BRAND_TYPE = "intCls";
    public static final String BRAND_REGNO = "regNo";
}
