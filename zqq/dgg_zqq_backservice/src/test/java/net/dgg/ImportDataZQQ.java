package net.dgg;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.dgg.dqc.backservice.StartApplication;
import net.dgg.dqc.backservice.fast_dfs.FastDfsService;
import net.dgg.dqc.backservice.utils.Tookit;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 李程 on 2018/10/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImportDataZQQ {

    private static final Log log = LogFactory.getLog(ImportDataZQQ.class);

    @Autowired
    FastDfsService fastDfsService;

    ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Before
    public void before() {
        log.info("开始调试----------------------------------");
    }

    @Test
    public void doError() {

        String mongoHost = "10.2.1.218";
        Integer mongoPort = 57017;
        String mongoUsername = "admin";
        String mongoPassword = "48bb67d7996f327b";

        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        AtomicLong counter = new AtomicLong(0L);
        List<ServerAddress> mongoAddresses = new ArrayList<>();
        mongoAddresses.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential("rwuser", mongoUsername, mongoPassword.toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(mongoAddresses, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("shangbiao_db");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("chushengonggao_detail_results");
        int size = 10000;
        TransportClient client = Tookit.EsClientUtil.getClient("172.16.0.38:9300", "elasticsearch");
        String pathPrefix = "D:\\csImage1626";
        String[] paths = new String[]{pathPrefix};
        boolean reTry = true;
        while (reTry) {
            SearchResponse response = client.prepareSearch("log_error").setTypes("error").setSize(size).get();
            SearchHits hits = response.getHits();
            reTry = hits.getTotalHits() > 0;
            Iterator<SearchHit> it = hits.iterator();
            while (it.hasNext()) {
                SearchHit hit = it.next();
                String docId = hit.getId();
                Map<String, Object> rec = hit.getSource();
                String fileName = (String) rec.get("fileName");
                String mainName = fileName.substring(0, fileName.lastIndexOf("."));
                String id = mainName.split("@")[0];
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
                File file = null;
                for (String path : paths) {
                    file = new File(path + "\\" + fileName);
                    if (file.exists()) {
                        break;
                    }
                }
                if (file != null) {
                    File importFile = file;
                    Runnable execution = () -> {
                        boolean tryAgain = true;
                        while (tryAgain) {
                            try {
                                byte[] bs = IOUtils.toByteArray(new FileInputStream(importFile));
                                String dfsFileName = fastDfsService.upload(bs, mainName, fileExt);
                                if (StringUtils.isNotEmpty(dfsFileName)) {
                                    //更新Mongo记录
                                    Document myDoc = mongoCollection.find(Filters.eq("_id", id)).first();
                                    Document ggImgLocal = (Document) myDoc.get("ggImgLocal");
                                    ggImgLocal.put("ggPath", dfsFileName);
                                    mongoCollection.findOneAndUpdate(Filters.eq("_id", id), new Document("$set", new Document("ggImgLocal", ggImgLocal)));
                                    System.out.println(counter.incrementAndGet());
                                    client.prepareDelete("log_error", "error", docId).get();
                                    tryAgain = false;
                                } else {
                                    tryAgain = true;
                                }
                            } catch (Exception e) {
                                tryAgain = true;
                            }
                        }
                    };
                    executorService.execute(execution);
                    //execution.run();
                }
            }
        }
    }

    @After
    public void after() {
        log.info("结束------------------------------------------");
    }

}
