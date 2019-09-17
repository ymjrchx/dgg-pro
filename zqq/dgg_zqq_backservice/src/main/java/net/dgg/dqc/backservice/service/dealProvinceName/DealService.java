package net.dgg.dqc.backservice.service.dealProvinceName;

import com.mongodb.BasicDBObject;
import net.dgg.dqc.backservice.entity.dealPorvinceName.Address;
import net.dgg.dqc.backservice.framework.mongo.MongoFactory;
import net.dgg.dqc.backservice.framework.mongo.MongoQuery;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 刘阳
 * @ClassName <DealService>
 * @despriction
 * @create 2018/07/06 13:54
 **/
@Service
public class DealService {

    Logger logger = LoggerFactory.getLogger(DealService.class);
    private static int bath = 80000; // 批量

    private MongoCollection eqcCon;

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    @Lazy
    private ThreadProvinceQueryService threadQueryService;


    // db.getCollection('test_detail_results').find({$and:[{province:{$not:/市*区/}},{province:{$not:/市*县/}},{province:{$not:/市*市/}}]},{dengji_zhusuo:1,province:1})
    @Transactional
    public void query() {
        provinceService.initTreeBookMap();
        MongoCollection eqcCon = MongoFactory.getColByDb("qichacha_com", "detail_results");
        Long all = eqcCon.getDBCollection().count();
        Long cir = all / bath + (all % bath == 0 ? 0 : 1);
        MongoQuery mongoQuery = MongoQuery.build(eqcCon);
        for (int i = 0; i < cir; i++) {
            threadQueryService.queryAddressAndAddTask(mongoQuery, i * bath, bath);
        }
    }


    /**
     * 更新
     *
     * @param address
     * @return
     */
    public boolean updateProvence(Address address) {
        return MongoQuery.build(eqcCon).getC().getDBCollection().update(new BasicDBObject().append("_id", address.getId()), new BasicDBObject().append("$set", new BasicDBObject().append("province", address.getProvince()))).getN() > 0 ? true : false;
    }

}
