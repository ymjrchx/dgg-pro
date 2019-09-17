package net.dgg.zqq.services;

import net.dgg.zqq.constant.ForbidTypeConstant;
import net.dgg.zqq.constant.StatusConstant;
import net.dgg.zqq.constant.UserAndIpCount;
import net.dgg.zqq.dao.UserExtDao;
import net.dgg.zqq.dao.forbidIpRecord.ForbidIpRecordDao;
import net.dgg.zqq.dao.forbidIpRecord.ForbidIpRecordExtDao;
import net.dgg.zqq.entity.forbidIpRecord.ForbidIpRecord;
import net.dgg.zqq.framework.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <ForbidIpRecordService>
 * @despriction
 * @create 2018/10/21 13:13
 **/
@Service
public class ForbidIpRecordService {
    @Autowired
    private ForbidIpRecordDao forbidIpRecordDao;
    @Autowired
    private ForbidIpRecordExtDao forbidIpRecordExtDao;
    @Autowired
    private UserExtDao userExtDao;


    @Transactional
    public void save(ForbidIpRecord forbidIpRecord) {
        this.forbidIpRecordDao.save(forbidIpRecord);
    }


    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.forbidIpRecordExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.forbidIpRecordExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    @Transactional
    public void cancelForbid(Long id) {
        Assert.notNull(id, "Id不能为空！");
        ForbidIpRecord record = this.forbidIpRecordDao.findById(id);
        Assert.notNull(record, "未查询到记录！");
        record.setFlag(0);
        record.setUpdateTime(new Date());
        this.forbidIpRecordDao.update(record);
        switch (record.getType()) {
            case ForbidTypeConstant.IP:
                this.cancelIp(record.getIpOrUserId());
                break;
            case ForbidTypeConstant.USER:
                this.cancelUser(record.getIpOrUserId());
                break;
        }

    }

    @Transactional
    public void cancelUser(String userId) {
        Assert.hasText(userId, "用户ID不能为空！");
        this.userExtDao.updateStatus(userId, StatusConstant.ENABLE);
    }

    public void cancelIp(String ip) {
        Assert.hasText(ip, "IP不能为空！");
        String key = UserAndIpCount.ipDisableKeyPrefix.concat(ip);
        RedisUtils.del(key);
    }

}
