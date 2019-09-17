package net.dgg.gspt.dqc.services.customerMsg;

import net.dgg.gspt.dqc.constant.CustomerMsgTypeConstant;
import net.dgg.gspt.dqc.dao.customerMsg.CustomerMsgDao;
import net.dgg.gspt.dqc.dao.customerMsg.CustomerMsgExtDao;
import net.dgg.gspt.dqc.entity.customerMsg.CustomerMsg;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author 刘阳
 * @ClassName <CustomerMsgService>
 * @despriction
 * @create 2018/12/18 17:10
 **/
@Service
public class CustomerMsgService {
    @Autowired
    private CustomerMsgDao customerMsgDao;
    @Autowired
    private CustomerMsgExtDao customerMsgExtDao;


    @Transactional
    public void save(CustomerMsg customerMsg) {
        Assert.notNull(customerMsg, "客留信息不能为空！");
        Assert.hasText(customerMsg.getName(), "名称不能为空！");
        Assert.isTrue(customerMsg.getName().length() < 30, "名称不能超过30字符！");
        Assert.hasText(customerMsg.getPhone(), "电话不能为空！");
        Assert.isTrue(customerMsg.getPhone().length() < 30, "电话不能超过30字符！");
        Assert.isTrue(Arrays.asList(CustomerMsgTypeConstant.NORMAL, CustomerMsgTypeConstant.JOIN_IN)
                .contains(customerMsg.getType()), "type数据非法！");
        customerMsg.setId(KeyWorker.nextId());
        customerMsg.setCreateTime(new Date());
        this.customerMsgDao.save(customerMsg);
    }


    @Transactional(readOnly = true)
    public List<Map> pageQuery(Map query) {
        query.put("flag", 1);
        Integer count = this.customerMsgExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.customerMsgExtDao.queryMap(query);
        } else {
            return Collections.emptyList();
        }
    }


}
