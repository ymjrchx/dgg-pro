package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.dao_mongodb.CompanyNameResultMongodbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CompanyNameResultMongodbDao companyNameResultMongodbDao;
    @Autowired
    private ThreadQueryMongoService threadQueryMongoService;

    /**
     * 开始同步
     */
    public void startSync() {
        this.all = companyNameResultMongodbDao.count();
        long queryNum = all / mongoQueryBath + (all % mongoQueryBath == 0 ? 0 : 1); // 查询总次数 也是 页数
        long queryNumPerThread = queryNum / mongoQueryThreadNum + (queryNum % mongoQueryThreadNum == 0 ? 0 : 1); // 每个线程 查询的页数
        long finalThreadNum = queryNum / queryNumPerThread + (queryNum % queryNumPerThread == 0 ? 0 : 1); // 最后实际需要线程数量
        for (long i = 1; i <= finalThreadNum; i++) {
            threadQueryMongoService.query((i - 1) * queryNumPerThread + 1, i * queryNumPerThread, mongoQueryBath);
        }
    }

    public long getAll() {
        return this.all;
    }

}
