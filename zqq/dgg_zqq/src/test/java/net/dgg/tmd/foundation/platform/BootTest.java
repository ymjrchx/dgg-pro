package net.dgg.tmd.foundation.platform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.zqq.services.common.DatabaseService;
import net.dgg.zqq.services.predicate.BrandPredicateService;
import net.dgg.zqq.services.trademark.TrademarkService;
import net.dgg.zqq.utils.IKAnalysisHelper;
import net.dgg.zqq.utils.ThreadHelper;
import net.dgg.zqq.utils.es.EsClientUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李程 on 2018/10/22.
 */
@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class BootTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BrandPredicateService brandPredicateService;

    @Autowired
    TrademarkService trademarkService;

    @Autowired
    DatabaseService databaseService;

    Gson gson;

    JsonParser jsonParser;

    @Before
    public void before() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat("YYYY-MM-DD");
        gson = gsonBuilder.create();
        jsonParser = new JsonParser();
        log.info("开始测试->");
    }

    @Test
    @SneakyThrows
    public void testIK() {
        String analysisText = "待分析文本，带很多符号;hello+[av]-3D支持！";
        List<String> analysis_edTexts = IKAnalysisHelper.spliteSingleChineseAndSingleWord(analysisText);
        log.info("{}", analysis_edTexts);
    }

    @Test
    @SneakyThrows
    public void testWeb() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ResultActions action = mockMvc.perform(
                MockMvcRequestBuilders.post("/term/queryPage")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("name=")
        );
        StringWriter o = new StringWriter();
        action.andDo(MockMvcResultHandlers.print(o));
        log.info("测试结果：{}", o.toString());
    }

    @Test
    @SneakyThrows
    public void testPassRate() {
        ThreadHelper.putThreadContextVar("industry", "");
        Object obj = brandPredicateService.predicateByName("中环");
        log.debug("{}", obj);
    }

    @Test
    public void testRegex() {
        /**
         log.info("{}", "01_txt".matches("\\d+_[^_]+"));
         log.info("{}", "01txt".matches("\\d+[^\\d]+"));
         Map<String, Object> condition = new LinkedHashMap<>();
         condition.put("intCls", "01我的类");
         trademarkService.queryPage(condition);
         */
    }

    @Test
    @SneakyThrows
    public void downloadDefaultImage() {
        //log.info(Base64.getEncoder().encodeToString(new Toolkit.Https().getBinary("http://localhost:8088/_nuxt/img/default.8c011e1.png", null, "UTF-8")));
    }

    @Test
    @SneakyThrows
    public void testSftp() {
        // ByteArrayInputStream is = new ByteArrayInputStream("你好".getBytes("UTF-8"));
        // sftp.upload(is, "/20181112/a.txt");
    }

    @Test
    @SneakyThrows
    public void testJdbc() {
        Map<String, Object> query = new LinkedHashMap<>();
        Map<String, Object> order = new LinkedHashMap<>();
        query.put("createtime > ", "2018");
        order.put("payment_id", "desc");
        List<Map<String, Object>> list = databaseService.query("payment_order", query, order);
        list.stream().map(gson::toJson).forEach(log::info);
    }

    @Test
    public void testEsCount() {
        TransportClient client = EsClientUtils.getClient();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("dgg_dqc_es_db_brand2").setTypes("companyBrand2").addAggregation(AggregationBuilders.terms("intCls").field("intCls").size(5).order(Terms.Order.count(false)));
        SearchResponse response = searchRequestBuilder.get();
    }

    @After
    public void end() {
        log.info("-结束测试");
    }

}
