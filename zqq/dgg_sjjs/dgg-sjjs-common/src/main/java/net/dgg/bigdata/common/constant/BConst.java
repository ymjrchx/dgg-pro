package net.dgg.bigdata.common.constant;

/**
 * @ClassName: BConst
 * @Description: 业务常量
 * @Author: jiangsh
 * @Date: 2018/12/11 11:20
 */
public class BConst {

    public static final boolean F_TRUE = true;
    public static final boolean F_FALSE = false;

    //基本工商
    public static final String COMPANY_NAME = "commercial.companyName"; //公司名称
    public static final String COMPANY_ID = "companyId";  //公司id
    public static final String BUSINESS_SCOPE = "commercial.businessScope"; //经营范围
    public static final String BUSINESS_STATUS = "commercial.businessState"; //营业状态
    public static final String BUSINESS_CB = "commercial.companyBrief"; //企业简介
    public static final String BUSINESS_CTYPE = "commercial.companyType"; //企业类型
    public static final String BUSINESS_CCODE = "commercial.creditCode"; //社会信用代码
    public static final String BUSINESS_RNUM = "commercial.registerNum"; //注册号
    public static final String BUSINESS_OCODE = "commercial.organizationCode"; //组织机构代码
    public static final String BUSINESS_BT = "commercial.businessTimeout"; //营业期限
    public static final String BUSINESS_RORGAN = "commercial.registOrgan"; //登记机关
    public static final String BUSINESS_ADATE = "commercial.approveDate"; //核准日期
    public static final String BUSINESS_MANNUM = "commercial.manNum"; //人员规模
    public static final String REG_MONEY = "commercial.registerMoney"; //注册资金
    public static final String REG_TIME = "commercial.registerTime"; //注册时间
    public static final String PROVINCE = "commercial.province"; //省份
    public static final String TRADEMARK = "commercial.trademark"; //品牌
    public static final String PRODUCT = "commercial.product"; //产品
    public static final String INDUSTRY = "commercial.industry"; //行业
    public static final String TEL = "commercial.companyTel"; //电话号
    public static final String EMAIL = "commercial.email"; //邮箱
    public static final String LEGEN_PERSON = "commercial.representMan"; //法人
    public static final String HINAME = "commercial.shareholders.hiName"; //股东名称
    public static final String ADDRESS = "commercial.registerAddress"; //注册地址
    public static final String MAJOR_NAME = "commercial.mainPeoples.scName"; //名称
    public static final String WEB_SITE_URL = "webSite.webSiteRecords.wHomeUrl"; //网址

    public static final String CLUES_TIMES = "cluesTimes";

    public static final String USERID = "userId";

    //线索信息
    public static final String CLUSE_LEGENPERSON = "legenPerson";
    public static final String NUM = "num";
    public static final String CLUSE_DEPT = "legenDept";
    public static final String CLUSE_STATUS = "status";
    public static final String RECORD_TOWHERE = "toWhere";
    public static final String RECORD_UPDATE_TIME = "updateTime";

    //日期设置
    public static final String DATESUFIX = "-01-01";
    public static final String T_DATESUFIX = "T00:00:00";
    public static final String T_DATESUFIX_END = "T23:59:59";

    //聚合
    public static final String AGGLIST = "agg";

    //数据字典pid
    public static final String COMSTATUSPID = "7701967621643644928"; //企业状态
    public static final String COMREGMONEY = "7761095856105205760"; //注册资金
    public static final String COMSCOPE = "7761095112492855296"; //企业范围
    public static final String COMREGTIME = "7761096413402378240"; //注册时长

    public static final long UPSIZE = 10000; //搜索记录上限

    //线索记录
    public static final String CLUSE_RECORD_COMNAME = "comName"; //公司名
    public static final String CLUSE_RECORD_CHARGEPERSON = "chargePerson"; //负责人
    public static final String CLUSE_RECORD_DEPT = "chargeDept"; //负责人部门
    public static final String CLUSE_RECORD_UPDATE_TIME = "statusUpdateTime"; //状态更新时间
    public static final String CLUSE_RECORD_COMID = "comId"; //公司id

    //公司详情
    public static final String INDEX_TEL = "tel"; //电话
    public static final String INDEX_EMAIL = "email"; //邮箱
    public static final String BASICINFO_CONTACTNO = "qynbDetail.basicInfoData.contactNo";
    public static final String BASICINFO_PHONENUMBER = "qygsDetail.contactInfoList.phoneNumber";
    public static final String BASICINFO_EMAIL = "qygsDetail.contactInfoList.email";
    public static final String BASICINFO_TEL = "briefIntroductionInfo.tel";
    public static final String BASICINFO_FPTT_PHONENO = "fptt.phoneNo";
    public static final String BASICINFO_QYNB_EMAIL = "qynbDetail.basicInfoData.emailAddress";
}
