package net.dgg.gspt.dqc.services.businessPush;

import net.dgg.gspt.dqc.config.RabbitConfig;
import net.dgg.gspt.dqc.constant.BusinessPushStatus;
import net.dgg.gspt.dqc.dao.UserExtDao;
import net.dgg.gspt.dqc.dao.businessPushRecord.BusinessPushRecordExtDao;
import net.dgg.gspt.dqc.entity.business.User;
import net.dgg.gspt.dqc.entity.businessPushRecord.BusinessPushRecord;
import net.dgg.gspt.dqc.utils.GsonUtils;
import net.dgg.gspt.dqc.utils.PrimaryKeyUtils;
import net.fblock.core.common.KeyWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 刘阳
 * @ClassName <BusinessPushService>
 * @despriction 商机资源信息推送
 * @create 2019/01/07 15:11
 **/
@Service
public class BusinessPushService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${businessPush.type}")
    private String type;

    @Autowired
    private RabbitConfig rabbitConfig;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private UserExtDao userExtDao;
    @Autowired
    private BusinessPushRecordExtDao businessPushRecordExtDao;


    private void send(String jsonStr) {
        logger.info(jsonStr);
        this.rabbitTemplate.convertAndSend(this.rabbitConfig.getBusinessRouterKey(), jsonStr);
    }

    @Transactional
    public Integer queryAndSend() {
        List<User> userList = this.userExtDao.query(new HashMap() {{
            put("pushStatusIsNull", "true");
            put("flag", 1);
        }});
        if (userList.isEmpty()) {
            return 0;
        }
        List<BusinessPushRecord> businessPushRecords = new ArrayList<>(userList.size());
        List<String> userIdList = new ArrayList<>(userList.size());
        for (User user : userList) {
            userIdList.add(user.getUserId());

            BusinessPushRecord record = new BusinessPushRecord();
            record.setId(PrimaryKeyUtils.getId());
            record.setName(user.getNickname());
            record.setPhone(user.getUsername());
            record.setArea(user.getArea());
            record.setCreateTime(new Date());
            record.setStatus(BusinessPushStatus.SUCCESS);
            record.setType(this.type);
            businessPushRecords.add(record);
        }

        this.userExtDao.updateUserStatus(BusinessPushStatus.PUSHED, userIdList);
        this.businessPushRecordExtDao.insertBatch(businessPushRecords);
        this.send(GsonUtils.buildSerGson().toJson(businessPushRecords));
        // this.
        return userIdList.size();
    }


}
