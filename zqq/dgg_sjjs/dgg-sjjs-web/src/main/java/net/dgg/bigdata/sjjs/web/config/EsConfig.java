package net.dgg.bigdata.sjjs.web.config;

/**
 * Created by jiangsh on 2018/12/14 14:57
 */
public interface EsConfig {

    //备注：除YK_COMMERCIAL外，其他INDEX / TYPE 一样
    /**
     * 年报
     */
     String YK_YEAR_REPORT = "dgg_yk_year_report";

    /**
     * 新闻
     */
    String YK_NEWS = "dgg_yk_news";

    /**
     * 基本工商信息
     */
    String YK_COMMERCIAL = "dgg_yk_commercial";

    /**
     * 基本工商-父文档
     */
    String YK_COMMERCIAL_PARENT = "dgg_yk_parent_clues";

    /**
     * 线索转换
     */
    String YK_COMMERCIAL_CHILD = "dgg_yk_clues";

    /**
     * 线索记录
     */
    String YK_COMMERCIAL_CLUES_RECORD = "dgg_yk_record";

    /**
     * 企业详情
     */
    String YK_COMPANY = "dgg_yk_company";

}
