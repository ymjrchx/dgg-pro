package net.dgg.dqc.backservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.dqc.backservice.fast_dfs.FastDfsService;
import net.dgg.dqc.backservice.utils.SqlUtils;
import net.dgg.dqc.backservice.utils.Tookit;
import net.dgg.dqc.backservice.utils.Toolkit;
import net.fblock.web.common.BaseController;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.bson.Document;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 李程 on 2018/10/12.
 */
@Api(value = "导入商标初审信息到ES", description = "导入数据到知千秋项目ES")
@RestController
@RequestMapping("/sync/zqq")
public class SyncData2ZQQController extends BaseController {

    @Autowired
    FastDfsService fastDfsService;


    private Pattern numeric = Pattern.compile("\\d+");

    ExecutorService executorService = Executors.newFixedThreadPool(50);

    @ApiOperation("批量同步初审公告芒果到ES")
    @RequestMapping(value = "/syncBatch", method = RequestMethod.GET)
    @ResponseBody
    public Object syncBatch(
            @ApiParam(name = "mongoHost", value = "MonGO目标主机", defaultValue = "10.0.0.121") @RequestParam(name = "mongoHost", defaultValue = "10.0.0.121") String mongoHost,
            @ApiParam(name = "mongoPort", value = "MonGO端口", defaultValue = "57017") @RequestParam(name = "mongoPort", defaultValue = "57017") Integer mongoPort,
            @ApiParam(name = "mongoUser", value = "MonGO目标主机", defaultValue = "admin") @RequestParam(name = "mongoUser", defaultValue = "admin") String mongoUser,
            @ApiParam(name = "mongoPasswd", value = "MonGO目标主机", defaultValue = "48bb67d7996f327b") @RequestParam(name = "mongoPasswd", defaultValue = "48bb67d7996f327b") String mongoPasswd,
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port
    ) {
        long start = System.currentTimeMillis();
        try {
            RestClient restClient = RestClient.builder(new HttpHost(host, port, "http")).setMaxRetryTimeoutMillis(1000000).build();
            ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            MongoClient mongoClient = new MongoClient(addrs, credentials);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("shangbiao_db");
            System.out.println("Connect to database successfully");
            Gson gson = new Gson();
            MongoCollection<Document> collection = mongoDatabase.getCollection("chushengonggao_detail_results");
            Iterator<Document> docIterator = collection.find().iterator();
            SubmitBuffer sb = new SubmitBuffer(100000, restClient);
            while (docIterator.hasNext()) {
                JsonObject doc = gson.fromJson(docIterator.next().toJson(), JsonObject.class);
                doc.remove("_id");
                JsonElement ggImgLocal = doc.get("ggImgLocal");
                doc.remove("ggImgLocal");
                if (!ggImgLocal.isJsonObject()) {
                    ggImgLocal = new JsonObject();
                    ((JsonObject) ggImgLocal).addProperty("ggUrl", "http://");
                    ((JsonObject) ggImgLocal).addProperty("ggName", "http://");
                }
                doc.add("ggImgLocalData", ggImgLocal);
                Long annNum = Long.valueOf(doc.get("annNum").getAsString());
                doc.remove("annNum");
                doc.addProperty("annNo", annNum.intValue());

                String docRaw = gson.toJson(doc);
                String esId = MD5(docRaw);
                doc.addProperty("esId", esId);

                Map<String, Object> metadata = new LinkedHashMap<>();
                metadata.put("_index", "dgg_zqq_es_db_first_trial");
                metadata.put("_type", "firstTrial");
                metadata.put("_id", esId);

                Map<String, Object> packageMap = new LinkedHashMap<>();
                packageMap.put("index", metadata);
                sb.writeObject(packageMap, doc);
            }
            sb.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        long end = System.currentTimeMillis();
        return this.getSuccessResponse("导入完毕，总共：" + (end - start) / 1000L / 60L);
    }

    @ApiOperation("导出文件到磁盘")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public Object export(
            @ApiParam(name = "mongoHost", value = "MonGO目标主机", defaultValue = "10.2.1.216") @RequestParam(name = "mongoHost", defaultValue = "10.2.1.216") String mongoHost,
            @ApiParam(name = "mongoPort", value = "MonGO端口", defaultValue = "57017") @RequestParam(name = "mongoPort", defaultValue = "57017") Integer mongoPort,
            @ApiParam(name = "mongoUser", value = "MonGO目标主机", defaultValue = "admin") @RequestParam(name = "mongoUser", defaultValue = "admin") String mongoUser,
            @ApiParam(name = "mongoPasswd", value = "MonGO目标主机", defaultValue = "48bb67d7996f327b") @RequestParam(name = "mongoPasswd", defaultValue = "48bb67d7996f327b") String mongoPasswd,
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port,
            @ApiParam(name = "size", value = "批大小", defaultValue = "50000", required = true) @RequestParam(name = "size", defaultValue = "50000") Integer size,
            @ApiParam(name = "annNo", value = "批大小", defaultValue = "1623") @RequestParam(name = "annNo", required = false) String annNo,
            @ApiParam(name = "path", value = "数据导出目录", defaultValue = "d:\\data", required = true) @RequestParam(name = "path") String path
    ) throws Exception {
        //生成CMD脚本
        PrintWriter o = new PrintWriter(new FileOutputStream(path + "\\import.cmd"));
        o.println("SET HOST_PATH=" + host + ":" + port + "/_bulk?pretty");
        o.println("FOR %%f in (data*) do start curl %HOST_PATH% --data-binary @%%f");
        o.flush();
        o.close();
        //生成CURL
        TransportClient client = Tookit.EsClientUtil.getClient("172.16.0.38:9300", "elasticsearch");
        byte[] curlBytes = IOUtils.toByteArray(SqlUtils.class.getResourceAsStream("/lib/curl.exe"));
        FileOutputStream os = new FileOutputStream(path + "\\curl.exe");
        os.write(curlBytes);
        os.flush();
        os.close();
        List<String> dataFiles = new ArrayList<>();
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("shangbiao_db");
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        MongoCollection<Document> collection = mongoDatabase.getCollection("chushengonggao_detail_results");
        Iterator<Document> docIterator;
        if (StringUtils.isEmpty(annNo)) {
            docIterator = collection.find().iterator();
        } else {
            docIterator = collection.find(Filters.eq("annNum", annNo)).iterator();
        }
        long currentIdx = 0;
        long exportCount = 0L;
        String exportFile = path + "\\data" + currentIdx++;
        dataFiles.add(exportFile);
        FileOutputStream fos = new FileOutputStream(exportFile);
        PrintWriter pw = new PrintWriter(fos);
        while (docIterator.hasNext()) {
            if (++exportCount % Long.valueOf(String.valueOf(size)) == 0L) {
                exportFile = path + "\\data" + currentIdx++;
                dataFiles.add(exportFile);
                pw.flush();
                pw.close();
                fos = new FileOutputStream(exportFile);
                pw = new PrintWriter(fos);
            }
            JsonObject doc = gson.fromJson(docIterator.next().toJson(), JsonObject.class);
            //处理ID字段
            String esId = doc.get("_id").getAsString();
            doc.remove("_id");
            //处理图片字段
            JsonElement ggImgLocal = doc.get("ggImgLocal");
            doc.remove("ggImgLocal");
            if (!ggImgLocal.isJsonObject()) {
                ggImgLocal = new JsonObject();
                ((JsonObject) ggImgLocal).addProperty("ggUrl", "http://");
                ((JsonObject) ggImgLocal).addProperty("ggName", "http://");
                executorService.execute(() ->
                        client.prepareIndex("error_first_trial", "error", esId).setSource(new HashMap() {{
                            put("raw", gson.toJson(doc));
                        }}).get()
                );
                continue;
            }
            doc.add("ggImgLocalData", ggImgLocal);
            //处理期号
            Long annNum = Long.valueOf(doc.get("annNum").getAsString());
            doc.remove("annNum");
            doc.addProperty("annNo", annNum.intValue());
            //处理代理机构字段
            JsonElement agencyElement = doc.get("agency");
            if (agencyElement != null && !agencyElement.isJsonNull()) {
                String agency = doc.get("agency").getAsString();
                doc.remove("agency");
                doc.addProperty("tmSection", agency);
            } else {
                doc.remove("agency");
                doc.addProperty("tmSection", (String) null);
            }
            //处理图片
            if (doc.get("tmImage") != null) {
                JsonElement tmImageElement = doc.get("tmImage");
                if (tmImageElement.isJsonObject()) {
                    String tmImage = doc.get("tmImage").getAsJsonObject().get("clUrl").getAsString();
                    String tmFileName = doc.get("tmImage").getAsJsonObject().get("clName").getAsString();
                    doc.remove("tmImage");
                    doc.addProperty("imageUrl", tmImage);
                    doc.addProperty("tmImageFileName", tmFileName);
                } else {
                    doc.remove("tmImage");
                    doc.addProperty("imageUrl", (String) null);
                }
            } else {
                doc.remove("tmImage");
                doc.addProperty("imageUrl", (String) null);
            }
            //处理分类
            if (doc.get("sbType") != null) {
                String sbType = doc.get("sbType").getAsString();
                doc.remove("sbType");
                sbType = getFirstNumber(sbType);
                if (sbType == null) {
                    doc.addProperty("intCls", (String) null);
                } else {
                    doc.addProperty("intCls", sbType);
                }
            } else {
                doc.remove("sbType");
                doc.addProperty("intCls", (String) null);
            }
            doc.addProperty("esId", esId);
            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("_index", "dgg_zqq_es_db_first_trial");
            metadata.put("_type", "firstTrial");
            metadata.put("_id", esId);
            Map<String, Object> packageMap = new LinkedHashMap<>();
            packageMap.put("index", metadata);
            pw.println(gson.toJson(packageMap));
            pw.println(gson.toJson(doc));
        }
        try {
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        try {
            pw.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        return this.getSuccessResponse("导出完毕");
    }

    @ApiOperation("下载title图片，上传dfs并更新，mongodb")
    @RequestMapping(value = "/updateTitleImage", method = RequestMethod.POST)
    @ResponseBody
    public Object updateTitleImage(
            @ApiParam(name = "mongoHost", value = "MonGO目标主机", defaultValue = "10.2.1.216") @RequestParam(name = "mongoHost", defaultValue = "10.2.1.216") String mongoHost,
            @ApiParam(name = "mongoPort", value = "MonGO端口", defaultValue = "57017") @RequestParam(name = "mongoPort", defaultValue = "57017") Integer mongoPort,
            @ApiParam(name = "mongoUser", value = "MonGO目标主机", defaultValue = "admin") @RequestParam(name = "mongoUser", defaultValue = "admin") String mongoUser,
            @ApiParam(name = "mongoPasswd", value = "MonGO目标主机", defaultValue = "48bb67d7996f327b") @RequestParam(name = "mongoPasswd", defaultValue = "48bb67d7996f327b") String mongoPasswd,
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port
    ) throws Exception {
        TransportClient client = Tookit.EsClientUtil.getClient("172.16.0.38:9300", "elasticsearch");
        List<String> dataFiles = new ArrayList<>();
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("shangbiao_db");
        MongoCollection<Document> collection = mongoDatabase.getCollection("chushengonggao_detail_results");
        Iterator<Document> docIterator = collection.find().iterator();
        AtomicLong countx = new AtomicLong(0L);
        while (docIterator.hasNext()) {
            Document document = docIterator.next();
            String esId = (String) document.get("_id");
            //处理图片
            if (document.get("tmImage") != null) {

                Object tmImage = document.get("tmImage");
                if (tmImage instanceof Document) {
                    Document tmImageDocument = (Document) tmImage;
                    if (tmImageDocument != null) {
                        String clUrl = tmImageDocument.getString("clUrl");
                        updateAndUpdateMongo(collection, client, esId, clUrl, document, countx, tmImageDocument);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return this.getSuccessResponse("更新完毕!");
    }

    private void updateAndUpdateMongo(MongoCollection mongoCollection, TransportClient client, String esId, String clUrl, Document document, AtomicLong countx, Document tmImage) {
        executorService.execute(() -> {
            String fileName = clUrl;
            String mainName = esId;
            String fileExt = null;
            try {
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            } catch (Exception ex) {
                fileExt = "jpg";
            }
            if (StringUtils.isEmpty(fileExt)) {
                fileExt = "jpg";
            }
            try {
                Toolkit.Https https = new Toolkit.Https();
                byte[] fileArray = https.getBinary(clUrl, null, "UTF-8");
                String dfsFileName = fastDfsService.upload(fileArray, mainName, fileExt);
                if (StringUtils.isEmpty(dfsFileName)) {
                    Map<String, Object> doc = new LinkedHashMap<>();
                    doc.put("id", esId);
                    doc.put("fileName", clUrl);
                    doc.put("message", "导入失败");
                    client.prepareIndex("log_error_update", "error", esId).setSource(doc).get();
                } else {
                    tmImage.put("clPath", dfsFileName);
                    mongoCollection.findOneAndUpdate(Filters.eq("_id", esId), new Document("$set", new Document("tmImage", tmImage)));
                    if (countx.incrementAndGet() % 1000 == 0L) {
                        logger.info("已经导入：" + countx.get());
                    }
                }
            } catch (Exception e) {
                ByteArrayOutputStream bin = new ByteArrayOutputStream();
                PrintStream out = new PrintStream(bin);
                e.printStackTrace(out);
                Map<String, Object> doc = new LinkedHashMap<>();
                doc.put("id", esId);
                doc.put("fileName", clUrl);
                doc.put("message", "导入失败");
                client.prepareIndex("log_error_update", "error", esId).setSource(doc).get();
            }
        });
    }


    @ApiOperation("上传文件到FastDFS并记录到ES地址")
    @RequestMapping(value = "/toFastDFS", method = RequestMethod.POST)
    @ResponseBody
    public Object batchUpload(
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port,
            @ApiParam(name = "size", value = "批大小", defaultValue = "10000", required = true) @RequestParam(name = "size", defaultValue = "10000") Integer size,
            @ApiParam(name = "path", value = "数据导出目录", defaultValue = "d:\\data", required = true) @RequestParam(name = "path") String path
    ) throws Exception {
        TransportClient client = getClient("10.2.1.121:9300;10.2.1.122:9300;10.2.1.134:9300", "dcq");
        SearchResponse response = client.prepareSearch("dgg_zqq_es_db_first_trial").setTypes("firstTrial").setSize(size).setTimeout(new TimeValue(20L * 60L * 1000L)).execute().actionGet();
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits();
        long page = total / size + 1;
        for (long i = 0; i < page; i++) {
            if (i > 0) {
                response = client.prepareSearchScroll(response.getScrollId()).execute().actionGet();
                hits = response.getHits();
            }
            List<Map<String, Object>> data = new ArrayList<>();
            Iterator<SearchHit> hitIt = hits.iterator();
            while (hitIt.hasNext()) {
                SearchHit hit = hitIt.next();
                Map<String, Object> source = hit.getSource();
                data.add(source);
            }
            upload(client, data);
        }
        client.prepareClearScroll().setScrollIds(Arrays.asList(response.getScrollId())).execute();
        return new Object();
    }

    private void upload(TransportClient client, List<Map<String, Object>> data) {
        for (Map<String, Object> dat : data) {
            String ggName = (String) ((Map<String, Object>) dat.get("ggImgLocalData")).get("ggName");
            String esId = (String) dat.get("esId");
            String urlPrefix = "http://172.16.0.251/csImage/";
            executorService.execute(() -> {
                try {
                    byte[] fileArray = IOUtils.toByteArray(new URL(urlPrefix + "/" + ggName));
                    String fileName = ggName.substring(0, ggName.lastIndexOf("."));
                    String fileExt = ggName.substring(ggName.lastIndexOf("."));
                    String dfsFileName = fastDfsService.upload(fileArray, fileName, fileExt);
                    ((Map<String, Object>) ((Map<String, Object>) dat.get(0)).get("ggImgLocalData")).put("ggPath", dfsFileName);
                    client.prepareIndex("dgg_zqq_es_db_first_trial", "firstTrial", esId).setSource(dat).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private String getFirstNumber(String txt) {
        if (StringUtils.isNotEmpty(txt)) {
            Matcher numericMatcher = numeric.matcher(txt);
            if (numericMatcher.find()) {
                return String.valueOf(Integer.valueOf(numericMatcher.group()));
            }
        }
        return null;
    }

    @ApiOperation("导出敏感词")
    @RequestMapping(value = "/exportSensitiveWords", method = RequestMethod.POST)
    @ResponseBody
    public Object exportSensitive(
            @ApiParam(name = "filePath", value = "文件路径", defaultValue = "d:\\source") @RequestParam(name = "filePath", defaultValue = "d:\\source") String filePath,
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port,
            @ApiParam(name = "index", value = "ES 索引", defaultValue = "dgg_zqq_es_db_sensitive_words") @RequestParam(name = "index", defaultValue = "dgg_zqq_es_db_sensitive_words") String index,
            @ApiParam(name = "type", value = "ES 类型", defaultValue = "sensitiveWords") @RequestParam(name = "type", defaultValue = "sensitiveWords") String type,
            @ApiParam(name = "size", value = "批大小", defaultValue = "100000", required = true) @RequestParam(name = "size", defaultValue = "100000") Integer size,
            @ApiParam(name = "path", value = "数据导出目录", defaultValue = "d:\\data1", required = true) @RequestParam(name = "path") String path
    ) throws Exception {
        Gson gson = new Gson();
        //生成CMD脚本
        PrintWriter o = new PrintWriter(new FileOutputStream(path + "\\import.cmd"));
        o.println("SET HOST_PATH=" + host + ":" + port + "/_bulk?pretty");
        o.println("FOR %%f in (data*) do start curl %HOST_PATH% --data-binary @%%f");
        o.flush();
        o.close();

        //生成CURL
        byte[] curlBytes = IOUtils.toByteArray(SqlUtils.class.getResourceAsStream("/lib/curl.exe"));
        FileOutputStream os = new FileOutputStream(path + "\\curl.exe");
        os.write(curlBytes);
        os.flush();
        os.close();

        File directory = new File(filePath);
        LinkedList<File> directories = new LinkedList<>();
        directories.add(directory);

        long currentIdx = 0;
        long exportCount = 0L;
        String exportFile = path + "\\data" + currentIdx++;
        FileOutputStream fos = new FileOutputStream(exportFile);
        PrintWriter pw = new PrintWriter(fos);

        while (directories.size() > 0) {
            File currentDirectory = directories.removeFirst();
            File[] files = currentDirectory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    directories.add(file);
                } else {
                    String text = new String(IOUtils.toByteArray(new FileInputStream(file)), "UTF-8");
                    String[] words = text.split("[\\r\\n]+");
                    for (String word : words) {
                        exportCount++;
                        JsonObject doc = new JsonObject();
                        doc.addProperty("keyword", word);
                        doc.addProperty("length", word.length());
                        doc.addProperty("position", exportCount);

                        String esId = MD5(word);
                        doc.addProperty("esId", esId);

                        Map<String, Object> metadata = new LinkedHashMap<>();
                        metadata.put("_index", index);
                        metadata.put("_type", type);
                        metadata.put("_id", esId);

                        Map<String, Object> packageMap = new LinkedHashMap<>();
                        packageMap.put("index", metadata);

                        pw.println(gson.toJson(packageMap));
                        pw.println(gson.toJson(doc));

                    }
                }
            }
        }

        try {
            pw.flush();
        } catch (Exception e) {
        }
        try {
            pw.close();
        } catch (Exception e) {

        }

        return this.getSuccessResponse("导出完毕");
    }


    @ApiOperation("(通用)导出文件到磁盘")
    @RequestMapping(value = "/exportGeneric", method = RequestMethod.POST)
    @ResponseBody
    public Object export(
            @ApiParam(name = "mongoHost", value = "MonGO目标主机", defaultValue = "10.0.0.121") @RequestParam(name = "mongoHost", defaultValue = "10.0.0.121") String mongoHost,
            @ApiParam(name = "mongoPort", value = "MonGO端口", defaultValue = "57017") @RequestParam(name = "mongoPort", defaultValue = "57017") Integer mongoPort,
            @ApiParam(name = "mongoUser", value = "MonGO目标主机", defaultValue = "admin") @RequestParam(name = "mongoUser", defaultValue = "admin") String mongoUser,
            @ApiParam(name = "mongoPasswd", value = "MonGO目标主机", defaultValue = "48bb67d7996f327b") @RequestParam(name = "mongoPasswd", defaultValue = "48bb67d7996f327b") String mongoPasswd,
            @ApiParam(name = "mongoDB", value = "库", defaultValue = "shangbiao_db") @RequestParam(name = "mongoDB", defaultValue = "shangbiao_db") String mongoDB,
            @ApiParam(name = "loginType", value = "登录类型", defaultValue = "rwuser") @RequestParam(name = "loginType", defaultValue = "rwuser") String loginType,
            @ApiParam(name = "mongoCollection", value = "集合", defaultValue = "chushengonggao_detail_results") @RequestParam(name = "mongoCollection", defaultValue = "chushengonggao_detail_results") String mongoCollection,
            @ApiParam(name = "host", value = "ES目标主机", defaultValue = "10.2.1.121") @RequestParam(name = "host", defaultValue = "10.2.1.121") String host,
            @ApiParam(name = "port", value = "ES REST端口", defaultValue = "19200") @RequestParam(name = "port", defaultValue = "19200") Integer port,
            @ApiParam(name = "index", value = "ES 索引", defaultValue = "dgg_zqq_es_db_first_trial") @RequestParam(name = "index", defaultValue = "dgg_zqq_es_db_first_trial") String index,
            @ApiParam(name = "type", value = "ES 类型", defaultValue = "firstTrial") @RequestParam(name = "type", defaultValue = "firstTrial") String type,
            @ApiParam(name = "size", value = "批大小", defaultValue = "100000", required = true) @RequestParam(name = "size", defaultValue = "100000") Integer size,
            @ApiParam(name = "path", value = "数据导出目录", defaultValue = "d:\\data", required = true) @RequestParam(name = "path") String path
    ) throws Exception {
        List<String> dataFiles = new ArrayList<>();
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential(loginType, mongoUser, mongoPasswd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoDB);
        System.out.println("Connect to database successfully");

        Gson gson = new Gson();
        MongoCollection<Document> collection = mongoDatabase.getCollection(mongoCollection);
        Iterator<Document> docIterator = collection.find().iterator();

        long currentIdx = 0;
        long exportCount = 0L;
        String exportFile = path + "\\data" + currentIdx++;
        dataFiles.add(exportFile);
        FileOutputStream fos = new FileOutputStream(exportFile);
        PrintWriter pw = new PrintWriter(fos);
        while (docIterator.hasNext()) {
            if (++exportCount % Long.valueOf(String.valueOf(size)) == 0L) {
                exportFile = path + "\\data" + currentIdx++;
                dataFiles.add(exportFile);
                pw.flush();
                pw.close();
                fos = new FileOutputStream(exportFile);
                pw = new PrintWriter(fos);
            }
            JsonObject doc = gson.fromJson(docIterator.next().toJson(), JsonObject.class);
            doc.remove("_id");
            String docRaw = gson.toJson(doc);
            String esId = MD5(docRaw);
            //doc.addProperty("esId", esId);

            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("_index", index);
            metadata.put("_type", type);
            metadata.put("_id", esId);

            Map<String, Object> packageMap = new LinkedHashMap<>();
            packageMap.put("index", metadata);

            pw.println(gson.toJson(packageMap));
            pw.println(gson.toJson(doc));
        }
        try {
            pw.flush();
        } catch (Exception e) {
        }
        try {
            pw.close();
        } catch (Exception e) {

        }
        //生成CMD脚本
        PrintWriter o = new PrintWriter(new FileOutputStream(path + "\\import.cmd"));
        o.println("SET HOST_PATH=" + host + ":" + port + "/_bulk?pretty");
        o.println("FOR %%f in (data*) do start curl %HOST_PATH% --data-binary @%%f");
        o.flush();
        o.close();

        //生成CURL
        byte[] curlBytes = IOUtils.toByteArray(SqlUtils.class.getResourceAsStream("/lib/curl.exe"));
        FileOutputStream os = new FileOutputStream(path + "\\curl.exe");
        os.write(curlBytes);
        os.flush();
        os.close();

        return this.getSuccessResponse("导出完毕");
    }

    @ApiOperation("上传到FastDFS并更新MongoDBggPath")
    @RequestMapping(value = "/uploadAndUp", method = RequestMethod.POST)
    @ResponseBody
    public Object batchUploadAnd(
            @ApiParam(name = "mongoHost", value = "MonGO目标主机", defaultValue = "10.2.1.217") @RequestParam(name = "mongoHost", defaultValue = "10.2.1.217") String mongoHost,
            @ApiParam(name = "mongoPort", value = "MonGO端口", defaultValue = "57017") @RequestParam(name = "mongoPort", defaultValue = "57017") Integer mongoPort,
            @ApiParam(name = "mongoUser", value = "MonGO目标主机", defaultValue = "admin") @RequestParam(name = "mongoUser", defaultValue = "admin") String mongoUser,
            @ApiParam(name = "mongoPasswd", value = "MonGO目标主机", defaultValue = "48bb67d7996f327b") @RequestParam(name = "mongoPasswd", defaultValue = "48bb67d7996f327b") String mongoPasswd,
            @ApiParam(name = "size", value = "线程池大小", defaultValue = "100", required = true) @RequestParam(name = "size", defaultValue = "100") Integer size,
            @ApiParam(name = "path", value = "扫描路径", defaultValue = "d:\\data", required = true) @RequestParam(name = "path") String path
    ) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        TransportClient client = getClient("172.16.0.38:9300", "elasticsearch");
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        AtomicLong counter = new AtomicLong(0L);
        AtomicLong countx = new AtomicLong(0L);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUser, mongoPasswd.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("shangbiao_db");
        System.out.println("Connect to database successfully");
        Gson gson = new Gson();
        MongoCollection<Document> collection = mongoDatabase.getCollection("chushengonggao_detail_results");

        String root = path;
        LinkedList<File> dirs = new LinkedList<>();
        for (String dirStr : root.split(",")) {
            dirs.addLast(new File(dirStr));
        }
        while (dirs.size() > 0) {
            File dir = dirs.removeFirst();
            System.out.println("begin read...");
            File[] subs = dir.listFiles();
            System.out.println("end read...");
            for (File file : subs) {
                // if (file.isDirectory()) {
                //     dirs.add(file);
                //} else if (file.isFile()) {
                long count;
                if ((count = counter.incrementAndGet()) % 1000 == 0) {
                    logger.info(String.valueOf(count));
                }
                uploadAndSetting(executorService, countx, client, collection, file);
                //}
            }
        }
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("code", 0);
        report.put("msg", "文件扫描成功");
        report.put("data", counter.get());
        return report;
    }


    private void uploadAndSetting(ExecutorService executorService, AtomicLong countx, TransportClient client, MongoCollection<Document> mongoCollection, File file) {
        executorService.execute(() -> {
            String fileName = file.getName();
            String mainName = fileName.substring(0, fileName.lastIndexOf("."));
            String id = mainName.split("@")[0];
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            try {
                byte[] fileArray = IOUtils.toByteArray(new FileInputStream(file));
                boolean reTry = true;
                String dfsFileName = "";
                while (reTry) {
                    dfsFileName = fastDfsService.upload(fileArray, mainName, fileExt);
                    reTry = StringUtils.isEmpty(dfsFileName);
                }
                /**
                if (StringUtils.isEmpty(dfsFileName)) {
                    Map<String, Object> doc = new LinkedHashMap<>();
                    doc.put("id", id);
                    doc.put("fileName", fileName);
                    doc.put("message", "导入失败");
                    client.prepareIndex("log_error", "error", id).setSource(doc).get();
                } else {
                 */
                    Document myDoc = mongoCollection.find(Filters.eq("_id", id)).first();
                    Document ggImgLocal = (Document) myDoc.get("ggImgLocal");
                    ggImgLocal.put("ggPath", dfsFileName);
                    mongoCollection.findOneAndUpdate(Filters.eq("_id", id), new Document("$set", new Document("ggImgLocal", ggImgLocal)));
                    if (countx.incrementAndGet() % 100 == 0L) {
                        logger.info("已经导入：" + countx.get());
                    }
                //}
            } catch (Exception e) {
                ByteArrayOutputStream bin = new ByteArrayOutputStream();
                PrintStream out = new PrintStream(bin);
                e.printStackTrace(out);
                String message = new String(bin.toByteArray());
                //logger.info(message);
                Map<String, Object> doc = new LinkedHashMap<>();
                doc.put("id", id);
                doc.put("fileName", fileName);
                doc.put("message", "导入失败");
                client.prepareIndex("log_error", "error", id).setSource(doc).get();
            }
        });
    }

    private String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    //【获取TransportClient 的方法】
    public static TransportClient getClient(String servers, String cluster) {
        TransportClient tclient;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", cluster).put("client.transport.sniff", true).build();
            tclient = new PreBuiltTransportClient(settings);
            String[] serversArr = servers.split(";");
            if (serversArr == null || serversArr.length == 0) {
                System.out.println("没有配置ES服务器");
                return null;
            } else {
                for (String server : serversArr) {
                    String[] item = server.split(":");
                    InetSocketTransportAddress ista = new InetSocketTransportAddress(InetAddress.getByName(item[0]), Integer.valueOf(item[1]));
                    tclient.addTransportAddress(ista);
                }
            }
            return tclient;
        } catch (Exception e) {
            System.out.println("创建es客户端失败".concat(e.getMessage()));
            return null;
        }
    }

    public static class SubmitBuffer {

        List<Object> bufferedData = new ArrayList<>();
        private ExecutorService executorService = Executors.newFixedThreadPool(10);
        private Gson gson = new Gson();
        private RestClient restClient;
        private int size;

        public SubmitBuffer(int batch_size, RestClient client) {
            this.size = batch_size;
            this.restClient = client;
        }


        public void writeObject(Object... object) {
            bufferedData.addAll(Arrays.asList(object));
            if (bufferedData.size() >= size) {
                try {
                    writeData();
                    bufferedData = new ArrayList<>();
                } catch (Exception e) {
                    e.printStackTrace();
                    bufferedData = new ArrayList<>();
                }
            }
        }

        public void flush() {
            if (!bufferedData.isEmpty()) {
                try {
                    writeData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private void writeData() throws Exception {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(baos);
            for (int i = 0; i < bufferedData.size(); i++) {
                pw.println(gson.toJson(bufferedData.get(i)));
            }
            pw.flush();
            pw.close();
            executorService.execute(() -> {
                try {
                    restClient.performRequest("POST",
                            "/_bulk?pretty"
                            , new HashMap<>(),
                            new ByteArrayEntity(baos.toByteArray(),
                                    ContentType.APPLICATION_JSON));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

    }

}
