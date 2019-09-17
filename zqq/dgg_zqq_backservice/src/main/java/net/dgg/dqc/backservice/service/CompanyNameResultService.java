package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mongodb.CompanyNameResultMongodbDao;
import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author 刘阳
 * @ClassName <CompanyNameResultService>
 * @despriction
 * @create 2018/07/24 16:42
 **/
@Service
public class CompanyNameResultService {
    @Autowired
    private CompanyNameResultMongodbDao companyNameResultMongodbDao;


    public long countMongodb() {
        return companyNameResultMongodbDao.count();
    }

    public MongoBaseEntity findOneFromMongoDb(String id) {
        Assert.hasText(id, "id不能为空！");
        return companyNameResultMongodbDao.findOne(id);
    }


}
