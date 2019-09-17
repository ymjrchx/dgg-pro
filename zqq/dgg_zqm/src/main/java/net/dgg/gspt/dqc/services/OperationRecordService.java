package net.dgg.gspt.dqc.services;

import net.dgg.gspt.dqc.dao.operationRecord.OperationRecordDao;
import net.dgg.gspt.dqc.entity.operationRecord.OperationRecord;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OperationRecordService
 * @Description: TODO
 * @Author: huangL
 * @Date: 2019/1/3 10:27
 */
@Service
public class OperationRecordService {

    @Autowired
    private OperationRecordDao operationRecordDao;

    /**
     * 保存用户操作记录
     */
    @Transactional
    public void saveRecord(String userId, String operationCode, String content) {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setId(KeyWorker.nextId());
        operationRecord.setUserId(userId);
        operationRecord.setOperationCode(operationCode);
        operationRecord.setContent(content);
        operationRecord.setFlag(1);
        operationRecord.setCreateTime(new Date());
        this.operationRecordDao.save(operationRecord);
    }

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.operationRecordDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.operationRecordDao.queryMap(query);
        }
        return Collections.emptyList();
    }
}
