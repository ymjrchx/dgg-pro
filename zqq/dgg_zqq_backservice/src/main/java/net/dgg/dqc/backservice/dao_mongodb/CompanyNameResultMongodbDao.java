package net.dgg.dqc.backservice.dao_mongodb;

import net.dgg.dqc.backservice.entity.CompanyNameResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 刘阳
 * @ClassName <CompanyNameResultMongodbDao>
 * @despriction
 * @create 2018/07/24 16:40
 **/
@Repository
public interface CompanyNameResultMongodbDao extends MongoRepository<CompanyNameResult, String> {


}
