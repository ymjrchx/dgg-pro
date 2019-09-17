package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mongodb.CompanyNameResultMongodbDao;
import net.dgg.dqc.backservice.dao_mongodb.SpringDataPageable;
import net.dgg.dqc.backservice.entity.CompanyNameResult;
import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘阳
 * @ClassName <ThreadQueryMongoService>
 * @despriction mongo多线程查询service
 * @create 2018/07/27 9:39
 **/
@Service
public class ThreadQueryMongoService {


    int s1 = 21166458;
    long start = 21166000;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SyncDataDequeService syncDataDequeService;
    @Autowired
    private CompanyNameResultMongodbDao companyNameResultMongodbDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     *
     */
    @Async("processExecutor")
    public void query(Long startPageNum, Long endPageNum, Long pageSize) {
        //logger.info(Thread.currentThread().getName() + "开始执行！");
        long st = start / pageSize;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagesize(pageSize.intValue());
        for (Long s = startPageNum; s <= endPageNum; s++) {
            if (s <= st) {
                continue;
            }
            pageable.setPagenumber(s.intValue());
            Page<CompanyNameResult> page = companyNameResultMongodbDao.findAll(pageable);
            List<CompanyNameResult> companyNameResultList = page.getContent();
            for (MongoBaseEntity entity : companyNameResultList) {
                syncDataDequeService.add(entity);
            }
            // logger.info(Thread.currentThread().getName() + String.format("执行进度 %s / %s", s, endPageNum));
        }
        //logger.info(Thread.currentThread().getName() + "执行完毕！");
    }


    @Async("processExecutor")
    public void query(Long startPageNum, Long endPageNum, Long pageSize, Criteria criteria) {
        //logger.info(Thread.currentThread().getName() + "开始执行！");
        Query query = new Query();
        query.addCriteria(criteria);
        query.limit(pageSize.intValue());

        for (Long s = startPageNum; s <= endPageNum; s++) {
            query.skip(Long.valueOf((s - 1) * pageSize).intValue());
            List<CompanyNameResult> companyNameResultList = this.mongoTemplate.find(query, CompanyNameResult.class);
            for (MongoBaseEntity entity : companyNameResultList) {
                if (entity == null) {
                    continue;
                }
                syncDataDequeService.add(entity);
            }
            // logger.info(Thread.currentThread().getName() + String.format("执行进度 %s / %s", s, endPageNum));
        }
        //logger.info(Thread.currentThread().getName() + "执行完毕！");
    }


}
