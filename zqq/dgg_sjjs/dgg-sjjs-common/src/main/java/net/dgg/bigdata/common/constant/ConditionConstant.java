package net.dgg.bigdata.common.constant;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/11 10:40
 * @Description: 条件常量
 */
public interface ConditionConstant {

    /**
     * 有客分类code
     */
    String CONDITION_CODE = "yk_classification";

    /**
     * 省份code
     */
    String CHINA_PROVINCE = "yk_province";

    /**
     * 行业名称
     */
    String INDUSTRY = "industry";

    /**
     * 下拉数据code
     */
    String INPUT_VALUES_CODE = "yk_select";

    /**
     * 下拉数据code
     */
    String INPUT_VALUES_CODE1 = "yk_com";

    /**
     * code参数名称
     */
    String CODE = "code";

    /**
     * pcode参数名称
     */
    String PCODE = "pcode";

    String HOT = "hot";

    /**
     * action type的父code
     */
    String ACTION_TYPE_PCODE = "yk_action_type";

    /**
     * input type的父code
     */
    String INPUT_TYPE_PCODE = "yk_input_type";

    /**
     * 数字0
     */
    Integer INT_0 = 0;

    /**
     * 数字1
     */
    Integer INT_1 = 1;

    //数字2
    Integer INT_2 = 2;
    /**
     * 数字5
     */
    Integer INT_5 = 5;

    /**
     * 数字20
     */
    Integer INT_20 = 20;

    /**
     * 数字999
     */
    Integer INT_999 = 999;

    /**
     * 数字1000
     */
    Integer INT_1000 = 1000;



    /**
     * 条件组type—0
     * 表示历史信息
     */
    Integer CONDITION_TYPE_0 = 0;
    /**
     * 条件组type—1
     * 表示条件组
     */
    Integer CONDITION_TYPE_1 = 1;

    /**
     * 参数industryId
     */
    String INDUSTRY_ID = "industryId";

    /**
     * 分类信息缓存rediskey
     */
    String CONFIGS_REDIS_KEY = "net.dqk.configs";

    /**
     * 行业模板redis缓存key
     */
    String INDUSTRY_REDIS_KEY="net.dqk.industry";

    /**
     * 企业搜索redis缓存key
     */
    String COM_SEARCH_TREEBOOK="net.dqk.comSearchTreeBook";


    /**
     * 转线索map的key
     */
    String CLUES = "clues";

    /**
     * 资源中心成功dode值
     */
    String SUCCESS_CODE = "200";

    /**
     * 资源中心失败dode值
     */
    String FAIL_CODE = "500";


    /**
     * es的电话参数
     */
    String COMPANYTEL = "companyTel";

    /**
     * 转线索的电话参数
     */
    String CONTACTNO = "contactNo";

    /**
     * es的公司id参数
     */
    String COMPANYID = "companyId";

    /**
     * es的公司工商信息参数
     */
    String COMMERCIAL = "commercial";

    /**
     * 创建人id
     */
    String CREATERID = "createrId";

}
