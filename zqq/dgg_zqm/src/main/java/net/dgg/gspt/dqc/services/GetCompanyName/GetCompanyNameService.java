package net.dgg.gspt.dqc.services.GetCompanyName;

import com.google.gson.Gson;
import lombok.Data;
import net.dgg.gspt.dqc.services.searchWord.SearchWordService;
import net.dgg.gspt.dqc.utils.HttpUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <GetCompanyNameService>
 * @despriction 公司取名
 * @create 2018/12/17 15:56
 **/
@Service
public class GetCompanyNameService {

    @Value("${getCompanyName.url}")
    private String url;

    /**
     * 获取 基础信息(包含公司的字义详解和名字总评)
     */
    private static String QUERY_INFO = "/query_info";

    /**
     * 周易信息(公司的周易解释)
     */
    private static String QUERY_ZHOUYI = "/query_zhouyi";

    /**
     * 起名
     */
    private static String COMPANY_NAMED = "company_named";
    @Autowired
    private SearchWordService searchWordService;


    /**
     * 获取公司名字
     *
     * @return
     */
    @Transactional
    public GetCompanyRes getGetCompanyName(String industry, String ip) {
        Assert.hasText(industry, "行业不能为空！");
        Map<String, Object> param = new HashMap<>();
        param.put("industry", industry);
        String resStr = null;
        try {
            resStr = HttpUtility.postRest(this.url.concat(COMPANY_NAMED), param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "查询失败！");
        GetCompanyRes res = new Gson().fromJson(resStr, GetCompanyRes.class);
        Assert.notNull(res, "查询结果转化失败！");
        Assert.isTrue(res.getStatus() != null && res.getStatus().intValue() == 0, "查询失败！");
        this.searchWordService.search(industry, ip);
        return res;
    }

    /***
     *  获取企业基础信息
     * @param name
     * @param industry
     * @return
     */
    @Transactional
    public GetCompanyInfoRes queryInfo(String name, String industry, String ip) {
        Assert.hasText(name, "企业名字不能为空!");
        Assert.hasText(industry, "行业不能为空！");
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("industry", industry);
        String resStr = null;
        try {
            resStr = HttpUtility.postRest(this.url.concat(QUERY_INFO), param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "查询失败！");
        GetCompanyInfoRes res = new Gson().fromJson(resStr, GetCompanyInfoRes.class);
        Assert.notNull(res, "查询结果转化失败！");
        Assert.isTrue(res.getStatus() != null && res.getStatus().intValue() == 0, "查询失败！");
        this.searchWordService.search(name.concat(industry), ip);
        return res;
    }

    @Transactional
    public Object queryZhouyi(String name, String ip) {
        Assert.hasText(name, "企业名字不能为空!");
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        String resStr = null;
        try {
            resStr = HttpUtility.postRest(this.url.concat(QUERY_ZHOUYI), param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(resStr, "查询失败！");
        GetCompanyZyRes res = new Gson().fromJson(resStr, GetCompanyZyRes.class);
        Assert.notNull(res, "查询结果转化失败！");
        Assert.isTrue(res.getStatus() != null && res.getStatus().intValue() == 0, "查询失败！");
        this.searchWordService.search(name, ip);
        return res;
    }


    @Data
    private static class BaseRes {
        private Integer status;
        private String msg;
    }

    /**
     * 取名
     */
    @Data
    public static class GetCompanyRes extends BaseRes {
        private List<String> data;
    }

    @Data
    public static class GetCompanyInfoRes extends BaseRes {
        private Expain data;

        @Data
        public static class Expain {
            private String ziyi;
            private String zongping;
        }
    }

    @Data
    public static class GetCompanyZyRes extends BaseRes {
        private ExplainZy data;

        @Data
        public static class ExplainZy {

            private Integer dongyao;
            private String summ;
            private List<BenguabianguaEntity> benguabiangua;

            @Data
            public static class BenguabianguaEntity {
                private Integer fenshu;
                private String shuoming;
                private String yuanwen;
                private String chuantong;
                private String duanyitianji;
                private String beisongshaoyong;
                private String taiwanfupeirong;
                private int xuhao;
                private String xiagua;
                private String guaming;
                private String shanggua;
                private String guashuoming;
            }
        }
    }

}
