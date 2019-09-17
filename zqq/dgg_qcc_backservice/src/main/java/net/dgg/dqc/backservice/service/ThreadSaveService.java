package net.dgg.dqc.backservice.service;

import com.google.gson.*;
import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import net.dgg.dqc.backservice.es.EsClientUtils;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 刘阳
 * @ClassName <ThreadSaveService>
 * @despriction
 * @create 2018/07/25 18:41
 **/
@Service
public class ThreadSaveService {

    private AtomicLong all = new AtomicLong(0);

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SyncDataDequeService syncDataDequeService;

    private BulkProcessor bulkProcessor = BulkProcessor.builder(
            EsClientUtils.getClient(),
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                }
            })
            .setBulkActions(10000)
            .setBulkSize(new ByteSizeValue(100, ByteSizeUnit.MB))
            .setFlushInterval(TimeValue.timeValueSeconds(10))
            .build();


    @Async("processExecutor")
    public void threadSave() {
        if (syncDataDequeService.isEmpty()) {
            return;
        }
        long cur = this.all.longValue();
        //logger.info(Thread.currentThread().getName() + "开始保存数据到ES！");
        String index = "company_name", type = "company_name";
        Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, (JsonSerializer<String>) (src, typeOfSrc, context) -> "-".equals(src) ? JsonNull.INSTANCE : new JsonPrimitive(src)).create();

        List<MongoBaseEntity> entityList = syncDataDequeService.get(10000);
        //long curTime = System.currentTimeMillis();

        //cur += this.bulkRequestBuilderSave(index, type, entityList, gson);

        cur += this.bulkProcessorSave(index, type, entityList, gson);

        // System.out.println("耗时："+(System.currentTimeMillis()-curTime));

        System.out.println(Thread.currentThread().getName() + String.format("完成 %s / %s ！", cur, syncDataDequeService.getAll()));
    }


    public long getAll() {
        return this.all.longValue();
    }


    /**
     * 使用BulkProcessor 保存
     *
     * @param entityList
     */
    private long bulkProcessorSave(String index, String type, List<MongoBaseEntity> entityList, Gson gson) {
        long count = 0;
        for (MongoBaseEntity entity : entityList) {
            if (entity == null) {
                continue;
            }
            bulkProcessor.add(new IndexRequest(index, type, entity.getId()).source(gson.toJson(entity), XContentType.JSON));
            this.all.getAndIncrement();
            count++;
        }
        return count;
    }

    /**
     * BulkRequestBuilder 保存   耗时有点高
     *
     * @param index
     * @param type
     * @param entityList
     * @param gson
     * @return
     */
    private long bulkRequestBuilderSave(String index, String type, List<MongoBaseEntity> entityList, Gson gson) {
        long count = 0;
        Client client = EsClientUtils.getClient();
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (MongoBaseEntity entity : entityList) {
            if (entity == null) {
                continue;
            }
            bulkRequestBuilder.add(client.prepareIndex(index, type, entity.getId()).setSource(gson.toJson(entity), XContentType.JSON));
            this.all.getAndIncrement();
            count++;
        }
        if (count > 0) {
            bulkRequestBuilder.execute().actionGet();
        }
        return count;
    }

    /**
     * 单线程保存
     */
    @Async("processExecutor")
    public void threadDeal() {
        while (true) {
            this.threadSave();
        }
    }


}
