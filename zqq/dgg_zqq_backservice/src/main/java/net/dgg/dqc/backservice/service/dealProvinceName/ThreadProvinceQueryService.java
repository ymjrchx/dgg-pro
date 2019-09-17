package net.dgg.dqc.backservice.service.dealProvinceName;

import net.dgg.dqc.backservice.entity.dealPorvinceName.Address;
import net.dgg.dqc.backservice.framework.mongo.MongoQuery;
import net.dgg.dqc.backservice.quartz.dealProvinceName.DealTask;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 刘阳
 * @ClassName <ThreadQueryService>
 * @despriction
 * @create 2018/07/13 15:40
 **/
@Service
public class ThreadProvinceQueryService {

    Logger logger = LoggerFactory.getLogger(ThreadProvinceQueryService.class);

    @Autowired
    private DealTask dealTask;

    @Async("processExecutor")
    public void queryAddressAndAddTask(MongoQuery mongoQuery, int skip, int limit) {
        MongoCursor<Address> cursor = mongoQuery.getC()
                .find("{},{id:1,dengji_zhusuo:1,address:1}")
                .skip(skip)
                .limit(limit)
                .as(Address.class);
        while (cursor.hasNext()) {
            Address address = cursor.next();
            dealTask.addAddress(address);
        }
    }

}
