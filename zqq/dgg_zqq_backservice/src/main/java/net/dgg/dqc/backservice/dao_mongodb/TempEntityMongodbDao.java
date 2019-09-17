package net.dgg.dqc.backservice.dao_mongodb;

import net.dgg.dqc.backservice.tempEntity.TempEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 刘阳
 * @ClassName <TempEntityMongodbDao>
 * @despriction
 * @create 2018/08/14 10:32
 **/
@Repository
public interface TempEntityMongodbDao extends MongoRepository<TempEntity, String> {

    TempEntity findById(String id);
}
