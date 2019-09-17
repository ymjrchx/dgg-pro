package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.entity.CompanyNameResult;
import net.dgg.dqc.backservice.entity.ImportDataRecord;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 刘阳
 * @ClassName <SyncDataService>
 * @despriction 同步数据的service
 * @create 2018/07/27 9:41
 **/
@Service
public class SyncDataService {
    private long mongoQueryBath = 1000; // 单次查询数量

    private long mongoQueryThreadNum = 10; //mongo最大查询线程


    private long all = 0;


    @Autowired
    private ThreadQueryMongoService threadQueryMongoService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ImportDataRecordService importDataRecordService;
    @Autowired
    private ThreadSaveService threadSaveService;


    /**
     * 开始同步
     */
    public Long startSync(Date queryStart, Date queryEnd) {

        this.all = this.count(queryStart, queryEnd);

        long queryNum = all / mongoQueryBath + (all % mongoQueryBath == 0 ? 0 : 1); // 查询总次数 也是 页数
        long queryNumPerThread = queryNum / mongoQueryThreadNum + (queryNum % mongoQueryThreadNum == 0 ? 0 : 1); // 每个线程 查询的页数
        long finalThreadNum = queryNum / queryNumPerThread + (queryNum % queryNumPerThread == 0 ? 0 : 1); // 最后实际需要线程数量

        // 创建 数据导入记录
        ImportDataRecord record = this.createImportDataRecord(queryStart, queryEnd, this.all);
        this.threadSaveService.setRecordId(record.getId());

        // 开始多线程查询
        for (long i = 1; i <= finalThreadNum; i++) {
            Criteria criteria = this.getCriteria(queryStart, queryEnd);
            threadQueryMongoService.query((i - 1) * queryNumPerThread + 1, i * queryNumPerThread, mongoQueryBath, criteria);
        }

        return this.all;
    }

    public long getAll() {
        return this.all;
    }


    public long count(Date queryStart, Date queryEnd) {
        return mongoTemplate.count(this.getQuery(queryStart, queryEnd), CompanyNameResult.class);
    }

    private Query getQuery(Date queryStart, Date queryEnd) {
        Query query = new Query();
        query.addCriteria(this.getCriteria(queryStart, queryEnd));
        return query;
    }

    private Criteria getCriteria(Date queryStart, Date queryEnd) {
        return Criteria.where("allTime.enterTime.collectTime").gte(String.valueOf(queryStart.getTime())).lt(String.valueOf(queryEnd.getTime()));
    }

    private ImportDataRecord createImportDataRecord(Date queryStart, Date queryEnd, Long count) {
        ImportDataRecord record = new ImportDataRecord();
        record.setId(KeyWorker.nextId());
        record.setComplete(0);
        record.setImportStartTime(new Date());
        record.setQueryTimeStart(queryStart);
        record.setQueryTimeEnd(queryEnd);
        record.setQueryCount(count);
        this.importDataRecordService.save(record);
        return record;
    }


}
