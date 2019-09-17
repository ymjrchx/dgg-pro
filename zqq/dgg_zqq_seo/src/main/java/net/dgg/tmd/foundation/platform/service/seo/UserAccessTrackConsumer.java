package net.dgg.tmd.foundation.platform.service.seo;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dgg.tmd.foundation.platform.service.seo.dto.UserAccessEventWrapper;
import net.dgg.tmd.foundation.platform.stack.mq.MessageQueueService;
import net.dgg.tmd.foundation.platform.stack.ssh.SecureShellService;
import net.dgg.tmd.foundation.platform.utils.FreemarkerUtils;
import net.dgg.tmd.foundation.platform.utils.Toolkit;
import net.dgg.tmd.foundation.platform.utils.es.EsUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 李程 on 2018/11/9.
 */
@Service
@Slf4j
public class UserAccessTrackConsumer implements CommandLineRunner {

    @Value("${app.user.access.event}")
    String userAccessEvent;

    @Value("${nature.messagequeue.host}")
    String mqHost;

    @Value("${nature.messagequeue.username}")
    String mqUserName;

    @Value("${nature.messagequeue.password}")
    String mqPassword;

    @Value("${nature.messagequeue.port}")
    Integer mqPort;

    @Value("${nature.messagequeue.vhost}")
    String mqVh;

    @Value("${seo.api.url}")
    private String api;

    //上传服务器对象
    @Value("${seo.upload.path.root}")
    private String rootPath;

    @Value("${seo.upload.host.add}")
    private String host;

    @Value("${seo.upload.host.port}")
    private Integer port;

    @Value("${seo.upload.host.user}")
    private String user;

    @Value("${seo.upload.host.password}")
    private String password;

    SecureShellService sftp;

    @Autowired
    RestTemplate restTemplate;

    private Gson gson;

    private JsonParser jsonParser = new JsonParser();

    @PostConstruct
    public void init() {
        sftp = SecureShellService.builder().rootPath(rootPath).host(host).port(port).user(user).password(password).build();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat("yyy-MM-dd");
        gson = gsonBuilder.create();
    }

    public String getQueueName() {
        return userAccessEvent;
    }

    @Override
    public void run(String... args) throws Exception {
        MessageQueueService messageQueueService = MessageQueueService.builder().vhost(mqVh).password(mqPassword).username(mqUserName).port(mqPort).host(mqHost).queue(userAccessEvent).build();
        Channel channel = messageQueueService.getChannel();
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String text;
                UserAccessEventWrapper userAccessEvent;
                String keyWordLogIndex = "dgg_zqq_es_db_search_log";
                String keyWordLogType = "keyWordSearchLog";

                String keyWordDistinctLogIndex = "dgg_zqq_es_db_search_distinct_log";
                String keyWorkLogDistinctType = "keyWordSearchDistinctLog";

                try {
                    text = new String(body, "UTF-8");
                    userAccessEvent = gson.fromJson(text, UserAccessEventWrapper.class);
                    Long current = Clock.systemUTC().millis();
                    log.info("{} 接收到生成任务 {}", Toolkit.DateUtils.format(current, "yyyy-MM-dd HH:mm:ss"), userAccessEvent);
                    Map<String, Object> parameter = userAccessEvent.getParameter();
                    String keyWord;
                    Map<String, Object> searchLog = new LinkedHashMap<>();
                    switch (userAccessEvent.getPageCode()) {
                        case TRADE_MARK_DETAIL:
                            String type = (String) parameter.get("type");
                            String appNo = (String) parameter.get("appNo");
                            //generateInfoData(type, appNo, userAccessEvent);
                            break;
                        case TRADE_MARK_SEARCH:

                            keyWord = (String) parameter.get("keyWord");
                            searchLog.put("keyWord", keyWord);
                            searchLog.put("year", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "yyyy"));
                            searchLog.put("month", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "MM"));
                            searchLog.put("date", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "dd"));
                            searchLog.put("time", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "yyyyMMddHHmmss"));
                            searchLog.put("timestamp", userAccessEvent.getCurrentTime());
                            searchLog.put("searchType", "brand");
                            EsUtils.add(keyWordLogIndex, keyWordLogType, Toolkit.StringHelper.uuid(), searchLog);

                            EsUtils.add(keyWordDistinctLogIndex, keyWorkLogDistinctType, Toolkit.StringHelper.md5(keyWord), searchLog);

                            //generateSearchListData(keyWord, userAccessEvent);
                            break;
                        case PATENT_DETAIL:
                            generatePatDet(userAccessEvent);
                            break;
                        case PATENT_SEARCH:

                            keyWord = (String) parameter.get("keyWord");
                            searchLog.put("keyWord", keyWord);
                            searchLog.put("year", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "yyyy"));
                            searchLog.put("month", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "MM"));
                            searchLog.put("date", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "dd"));
                            searchLog.put("time", Toolkit.DateUtils.format(userAccessEvent.getCurrentTime(), "yyyyMMddHHmmss"));
                            searchLog.put("timestamp", userAccessEvent.getCurrentTime());
                            searchLog.put("searchType", "patent");

                            EsUtils.add(keyWordLogIndex, keyWordLogType, Toolkit.StringHelper.uuid(), searchLog);

                            EsUtils.add(keyWordDistinctLogIndex, keyWorkLogDistinctType, Toolkit.StringHelper.md5(keyWord), searchLog);

                            //generatePatentSearchListData(userAccessEvent);
                            break;
                    }
                    Long end = Clock.systemUTC().millis();
                    log.info("{} 完成生成任务，{}秒", Toolkit.DateUtils.format(end, "yyyy-MM-dd HH:mm:ss"), (end - current) / 1000D);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    log.error("{}", e);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(getQueueName(), autoAck, consumer);
    }

    @SneakyThrows
    private void generateInfoData(String type, String appNo, UserAccessEventWrapper wrapper) {
        int taskId = 0;
        JsonArray bodies = new JsonArray();
        Map<String, Object> ctx = new LinkedHashMap<>();
        ctx.put("type", type);
        ctx.put("appNo", appNo);
        Long current = Clock.systemUTC().millis();
        String YYYY = Toolkit.DateUtils.format(current, "yyyy");
        String YYYYMM = Toolkit.DateUtils.format(current, "yyyy-MM");
        String YYYYMMDD = Toolkit.DateUtils.format(current, "yyyy-MM-dd");
        String typeStr = type;
        String dirName = YYYY.concat("/").concat(YYYYMM).concat("/").concat(YYYYMMDD);
        String mainName = YYYYMMDD.concat("-brand-details-").concat(Toolkit.StringHelper.uuid());

        String fileName = mainName.concat(".html");
        String fullName = dirName.concat("/").concat(fileName);

        String dataFileName = mainName.concat(".data");
        String fullDataFileName = dirName.concat("/").concat(dataFileName);

        String releaseFileName = mainName.concat(".release");
        String fullReleaseFileName = dirName.concat("/").concat(releaseFileName);

        if (taskId++ == 0) {
            Map<String, Object> param = new LinkedHashMap<>();
            param.put("type", type);
            param.put("appNo", appNo);
            String searchListURL = api.concat("/brandSearch/brandDetail?type=" + type + "&appNo=" + appNo);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-FROM", "INNER");
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataElement = responseObject.get("data");
                if (dataElement.isJsonArray()) {
                    JsonArray dataArray = dataElement.getAsJsonArray();
                    if (dataArray.size() > 0) {
                        JsonObject firstData = dataArray.get(0).getAsJsonObject();
                        Map<String, Object> map = new LinkedHashMap<>();
                        firstData.keySet().forEach(field -> {
                            JsonElement element = firstData.get(field);
                            if (element != null && !element.isJsonNull()) {
                                map.put(field, element.getAsString());
                            }
                        });
                        ctx.put("map", map);
                    }
                }
            }
        }
        String template = new String(IOUtils.toByteArray(sftp.getClass().getResourceAsStream("/templates/search_detail.ftl")), "UTF-8");
        List<Map> maps = Lists.newArrayList(bodies.iterator()).stream().map(element -> gson.fromJson(jsonParser.parse(jsonParser.parse(element.toString()).getAsString()).getAsJsonObject(), Map.class)).collect(Collectors.toList());
        ctx.put("bodies", maps);
        String html = FreemarkerUtils.renderBy(fileName, template, ctx);
        sftp.upload(new ByteArrayInputStream(html.getBytes("UTF-8")), fullName);
        sftp.upload(new ByteArrayInputStream(gson.toJson(bodies).getBytes("UTF-8")), fullDataFileName);
        sftp.upload(new ByteArrayInputStream("标识".getBytes("UTF-8")), fullReleaseFileName);
    }

    @SneakyThrows
    private void generateSearchListData(String keyWord, UserAccessEventWrapper wrapper) {
        int taskId = 0;

        JsonArray bodies = new JsonArray();
        Map<String, Object> ctx = new LinkedHashMap<>();
        ctx.put("keyWord", keyWord);
        Long current = Clock.systemUTC().millis();
        String YYYY = Toolkit.DateUtils.format(current, "yyyy");
        String YYYYMM = Toolkit.DateUtils.format(current, "yyyy-MM");
        String YYYYMMDD = Toolkit.DateUtils.format(current, "yyyy-MM-dd");

        String dirName = YYYY.concat("/").concat(YYYYMM).concat("/").concat(YYYYMMDD);
        String mainName = YYYYMMDD.concat("-brand-search-").concat(Toolkit.StringHelper.uuid());

        String fileName = mainName.concat(".html");
        String fullName = dirName.concat("/").concat(fileName);

        String dataFileName = mainName.concat(".data");
        String fullDataFileName = dirName.concat("/").concat(dataFileName);

        String releaseFileName = mainName.concat(".release");
        String fullReleaseFileName = dirName.concat("/").concat(releaseFileName);

        if (taskId++ == 0) {
            Map<String, Object> param = new LinkedHashMap<>();
            param.put("key", "name");
            param.put("keyWord", keyWord);
            param.put("filterType", "all");
            param.put("pageIndex", 1);
            param.put("pageSize", 20);
            param.putAll(wrapper.getParameter());
            String searchListURL = api.concat("/brandSearch/searchList");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-FROM", "INNER");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity entity = new HttpEntity(param, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataEl = responseObject.get("data");
                if (dataEl.isJsonObject()) {
                    JsonObject data = dataEl.getAsJsonObject();
                    Long totalCount = data.get("totalCount").getAsLong();
                    if (totalCount > 0L) {
                        JsonArray listData = data.get("listData").getAsJsonArray();
                        List<Map<String, Object>> list = new ArrayList<>();
                        for (int i = 0; i < listData.size(); i++) {
                            JsonObject json = listData.get(i).getAsJsonObject();
                            Map<String, Object> it = gson.fromJson(json, Map.class);
                            list.add(it);
                        }
                        ctx.put("list", list);
                    }
                }
            }
        }

        if (taskId++ == 1) {
            Map<String, Object> param = new LinkedHashMap<>();
            param.put("key", "name");
            param.put("keyWord", keyWord);
            param.put("filterType", "all");
            param.put("pageIndex", 1);
            param.put("pageSize", 20);
            param.putAll(wrapper.getParameter());
            String searchListURL = api.concat("/brandSearch/approximate");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            httpHeaders.set("X-FROM", "INNER");
            HttpEntity entity = new HttpEntity(param, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataEl = responseObject.get("data");
                if (dataEl.isJsonObject()) {
                    JsonObject data = dataEl.getAsJsonObject();
                    if (data.get("apply_type_p") != null && data.get("apply_type_p").isJsonArray()) {
                        JsonArray apply_type_pData = data.get("apply_type_p").getAsJsonArray();
                        List<Map<String, Object>> apply_type_p = new ArrayList<>();
                        for (int i = 0; i < apply_type_pData.size(); i++) {
                            JsonElement json = apply_type_pData.get(i);
                            Map<String, Object> it = gson.fromJson(json, Map.class);
                            apply_type_p.add(it);
                        }
                        ctx.put("apply_type_p", apply_type_p);
                    }
                    if (data.get("apply_type_p_SMALL") != null && data.get("apply_type_p_SMALL").isJsonArray()) {
                        JsonArray apply_type_p_SMALLData = data.get("apply_type_p_SMALL").getAsJsonArray();
                        List<Map<String, Object>> apply_type_p_SMALL = new ArrayList<>();
                        for (int i = 0; i < apply_type_p_SMALLData.size(); i++) {
                            JsonElement json = apply_type_p_SMALLData.get(i);
                            Map it = gson.fromJson(json, Map.class);
                            apply_type_p_SMALL.add(it);
                        }
                        ctx.put("apply_type_p_SMALL", apply_type_p_SMALL);
                    }
                    JsonArray apply_yearData = data.get("apply_year").getAsJsonArray();
                    List<Map<String, Object>> apply_year = new ArrayList<>();
                    for (int i = 0; i < apply_yearData.size(); i++) {
                        JsonElement json = apply_yearData.get(i);
                        Map<String, Object> it = gson.fromJson(json, Map.class);
                        apply_year.add(it);
                    }
                    ctx.put("apply_year", apply_year);
                    JsonArray lay_statusData = data.get("lay_status").getAsJsonArray();
                    List<Map<String, Object>> lay_status = new ArrayList<>();
                    for (int i = 0; i < lay_statusData.size(); i++) {
                        JsonElement json = lay_statusData.get(i);
                        Map<String, Object> it = gson.fromJson(json, Map.class);
                        lay_status.add(it);
                    }
                    ctx.put("lay_status", lay_status);
                }
            }
        }

        if (taskId++ == 2) {
            String searchListURL = api.concat("/trademarkConsult/selectPrice");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("X-FROM", "INNER");
            HttpEntity entity = new HttpEntity(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataEl = responseObject.get("data");
                if (dataEl.isJsonArray()) {
                    JsonArray data = dataEl.getAsJsonArray();
                    List<Map<String, Object>> listData = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        JsonElement json = data.get(i);
                        Map<String, Object> it = gson.fromJson(json, Map.class);
                        listData.add(it);
                    }
                    ctx.put("price", listData);
                }
            }
        }

        String template = new String(IOUtils.toByteArray(sftp.getClass().getResourceAsStream("/templates/search_list.ftl")), "UTF-8");
        List<Map> maps = Lists.newArrayList(bodies.iterator()).stream().map(element -> gson.fromJson(jsonParser.parse(jsonParser.parse(element.toString()).getAsString()).getAsJsonObject(), Map.class)).collect(Collectors.toList());
        ctx.put("bodies", maps);
        String html = FreemarkerUtils.renderBy(fileName, template, ctx);
        sftp.upload(new ByteArrayInputStream(html.getBytes("UTF-8")), fullName);
        sftp.upload(new ByteArrayInputStream(gson.toJson(bodies).getBytes("UTF-8")), fullDataFileName);
        sftp.upload(new ByteArrayInputStream("标识".getBytes("UTF-8")), fullReleaseFileName);

    }


    @SneakyThrows
    private void generatePatentSearchListData(UserAccessEventWrapper wrapper) {
        int taskId = 0;
        JsonArray bodies = new JsonArray();
        Map<String, Object> ctx = new LinkedHashMap<>();
        ctx.put("keyWord", wrapper.getParameter().get("keyWord"));
        Long current = Clock.systemUTC().millis();
        String YYYY = Toolkit.DateUtils.format(current, "yyyy");
        String YYYYMM = Toolkit.DateUtils.format(current, "yyyy-MM");
        String YYYYMMDD = Toolkit.DateUtils.format(current, "yyyy-MM-dd");

        String dirName = YYYY.concat("/").concat(YYYYMM).concat("/").concat(YYYYMMDD);
        String mainName = YYYYMMDD.concat("-patent-search-").concat(Toolkit.StringHelper.uuid());

        String fileName = mainName.concat(".html");
        String fullName = dirName.concat("/").concat(fileName);

        String dataFileName = mainName.concat(".data");
        String fullDataFileName = dirName.concat("/").concat(dataFileName);

        String releaseFileName = mainName.concat(".release");
        String fullReleaseFileName = dirName.concat("/").concat(releaseFileName);

        if (taskId++ == 0) {
            //获取搜索接口数据并加入渲染上下文
            Map<String, Object> param = new LinkedHashMap<>();
            param.putAll(wrapper.getParameter());
            String searchListURL = api.concat("/patentSearch/searchList");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            httpHeaders.set("X-FROM", "INNER");
            HttpEntity entity = new HttpEntity(param, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataEl = responseObject.get("data");
                if (dataEl.isJsonObject()) {
                    JsonObject data = dataEl.getAsJsonObject();
                    Long totalCount = data.get("totalCount").getAsLong();
                    if (totalCount > 0L) {
                        JsonArray listData = data.get("listData").getAsJsonArray();
                        List<Map<String, Object>> list = new ArrayList<>();
                        for (int i = 0; i < listData.size(); i++) {
                            JsonObject json = listData.get(i).getAsJsonObject();
                            Map<String, Object> it = gson.fromJson(json, Map.class);
                            list.add(it);
                        }
                        ctx.put("list", list);
                    }
                }
            }
        }

        String template = new String(IOUtils.toByteArray(sftp.getClass().getResourceAsStream("/templates/patent_list.ftl")), "UTF-8");
        List<Map> maps = Lists.newArrayList(bodies.iterator()).stream().map(element -> gson.fromJson(jsonParser.parse(jsonParser.parse(element.toString()).getAsString()).getAsJsonObject(), Map.class)).collect(Collectors.toList());
        ctx.put("bodies", maps);
        String html = FreemarkerUtils.renderBy(fileName, template, ctx);
        sftp.upload(new ByteArrayInputStream(html.getBytes("UTF-8")), fullName);
        sftp.upload(new ByteArrayInputStream(gson.toJson(bodies).getBytes("UTF-8")), fullDataFileName);
        sftp.upload(new ByteArrayInputStream("标识".getBytes("UTF-8")), fullReleaseFileName);

    }

    @SneakyThrows
    private void generatePatDet(UserAccessEventWrapper wrapper) {
        int taskId = 0;
        JsonArray bodies = new JsonArray();
        Map<String, Object> ctx = new LinkedHashMap<>();
        ctx.put("key", wrapper.getParameter().get("key"));
        ctx.put("val", wrapper.getParameter().get("val"));
        Long current = Clock.systemUTC().millis();
        String YYYY = Toolkit.DateUtils.format(current, "yyyy");
        String YYYYMM = Toolkit.DateUtils.format(current, "yyyy-MM");
        String YYYYMMDD = Toolkit.DateUtils.format(current, "yyyy-MM-dd");
        String dirName = YYYY.concat("/").concat(YYYYMM).concat("/").concat(YYYYMMDD);
        String mainName = YYYYMMDD.concat("-patent-details-").concat(Toolkit.StringHelper.uuid());

        String fileName = mainName.concat(".html");
        String fullName = dirName.concat("/").concat(fileName);

        String dataFileName = mainName.concat(".data");
        String fullDataFileName = dirName.concat("/").concat(dataFileName);

        String releaseFileName = mainName.concat(".release");
        String fullReleaseFileName = dirName.concat("/").concat(releaseFileName);

        if (taskId++ == 0) {
            String searchListURL = api.concat("/patentSearch/detail?key=" + wrapper.getParameter().get("key") + "&val=" + wrapper.getParameter().get("val"));
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-FROM", "INNER");
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(searchListURL, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            bodies.add(responseBody);
            JsonObject responseObject = jsonParser.parse(responseBody).getAsJsonObject();
            Integer code = responseObject.get("code").getAsInt();
            if (code.intValue() == 0) {
                JsonElement dataElement = responseObject.get("data");
                if (dataElement.isJsonArray()) {
                    JsonArray dataArray = dataElement.getAsJsonArray();
                    if (dataArray.size() > 0) {
                        JsonObject firstData = dataArray.get(0).getAsJsonObject();
                        Map<String, Object> map = gson.fromJson(firstData, Map.class);
                        ctx.put("map", map);

                        String template = new String(IOUtils.toByteArray(sftp.getClass().getResourceAsStream("/templates/patent_detail.ftl")), "UTF-8");
                        List<Map> maps = Lists.newArrayList(bodies.iterator()).stream().map(element -> gson.fromJson(jsonParser.parse(jsonParser.parse(element.toString()).getAsString()).getAsJsonObject(), Map.class)).collect(Collectors.toList());
                        ctx.put("bodies", maps);
                        String html = FreemarkerUtils.renderBy(fileName, template, ctx);
                        sftp.upload(new ByteArrayInputStream(html.getBytes("UTF-8")), fullName);
                        sftp.upload(new ByteArrayInputStream(gson.toJson(bodies).getBytes("UTF-8")), fullDataFileName);
                        sftp.upload(new ByteArrayInputStream("标识".getBytes("UTF-8")), fullReleaseFileName);

                    }
                }
            }
        }

    }

}
