package net.dgg.yk.platform.backend.business.a1.dependencies;

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


    public static final String BASICINFO_CONTACTNO = "qynbDetail.basicInfoData.contactNo";
    public static final String BASICINFO_PHONENUMBER = "qygsDetail.contactInfoList.phoneNumber";
    public static final String BASICINFO_EMAIL = "qygsDetail.contactInfoList.email";
    public static final String BASICINFO_TEL = "briefIntroductionInfo.tel";
    public static final String BASICINFO_FPTT_PHONENO = "fptt.phoneNo";



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
    public static final String All = "all";
    public static final String EMAIL = "email";

    public static final String TREE_KEY = "treeKey";
    public static final String PROMPT = "未找到相关数据";
    public static final String PROMPT_MORE = "您的搜索词太宽泛，建议更换一下搜索词";

    /**
     * 匹配模式开关
     */
    public static final boolean F_FALSE = false;
    public static final boolean F_TRUE = true;

    public static final String QCC_WEBSITE_URL = "https://www.qichacha.com/";
    public static final String QCC_WEBSITE_URL_2 = "http://www.qichacha.comfirm_";
    public static final String STAND_WEBSITE_URL = "https://www.qichamao.com/orgcompany/searchitemdtl/";
    public static final String EQC_WEBSITE_URL = "http://www.eqicha.com/company/";
    public static final String GXZG_WEBSITE_URL = "http://www.gxzg.org.cn/detail/";
    public static final String ZCGL_WEBSITE_URL = "http://jzsc.mohurd.gov.cn/dataservice/query/comp/compDetail/";
    public static final String RESUME_WEBSITE_URL = "http://resume.58guakao.com/Resume/PhoneConvertToImg/";
    public static final String HTML = ".html";
    public static final String TARGET_PREFIX = "pre";
    public static final String TARGET_SUFFIX = "su";

    public static final String AGGREATE_LIST = "agg";

    /**
     * 企业年报
     */
    public static final int QYNB_START = 2013;
    public static final int QYNB_END = 2019;
    public static final String QYNB_REPORTPREFIX = "report";


}