package net.dgg.dqc.backservice.service;

import net.dgg.dqc.backservice.entity.MongoBaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 刘阳
 * @ClassName <SyncDataQuartz>
 * @despriction 存放任务对象的 集合
 * @create 2018/07/27 9:50
 **/
@Service
public class SyncDataDequeService extends ConcurrentLinkedDeque<MongoBaseEntity> {

    private AtomicLong all = new AtomicLong(); // 总数

    public boolean add(MongoBaseEntity entity) {
        all.getAndIncrement();
        return super.add(entity);
    }

    /**
     * 取出指定数量的对象
     *
     * @param num
     * @return
     */
    public List<MongoBaseEntity> get(int num) {
        List<MongoBaseEntity> entityList = new ArrayList<>(num < 0 ? 0 : num);
        if (num == 0 || super.isEmpty()) {
            return entityList;
        }
        for (int i = 0; i < num; i++) {
            MongoBaseEntity entity = super.pollLast();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    public long getAll() {
        return all.longValue();
    }
}
