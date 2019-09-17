package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mongodb.CompanyNameResultMongodbDao;
import net.dgg.dqc.backservice.dao_mongodb.SpringDataPageable;
import net.dgg.dqc.backservice.entity.CompanyNameResult;
import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SyncDataDequeService syncDataDequeService;
    @Autowired
    private CompanyNameResultMongodbDao companyNameResultMongodbDao;

    /**
     *
     */
    @Async("processExecutor")
    public void query(Long startPageNum, Long endPageNum, Long pageSize) {
        //logger.info(Thread.currentThread().getName() + "开始执行！");
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagesize(pageSize.intValue());
        for (Long s = startPageNum; s <= endPageNum; s++) {
            pageable.setPagenumber(s.intValue());
            Page<CompanyNameResult> page = companyNameResultMongodbDao.findAll(pageable);
            List<CompanyNameResult> companyNameResultList = page.getContent();
            for (MongoBaseEntity entity : companyNameResultList) {
                syncDataDequeService.add(entity);
            }
            //logger.info(Thread.currentThread().getName() + String.format("执行进度 %s / %s", s, endPageNum));
        }
        //logger.info(Thread.currentThread().getName() + "执行完毕！");
    }


}
