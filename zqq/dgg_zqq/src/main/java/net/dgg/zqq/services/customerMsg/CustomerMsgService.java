package net.dgg.zqq.services.customerMsg;


import net.dgg.zqq.dao.customerMsg.CustomerMsgDao;
import net.dgg.zqq.dao.customerMsg.CustomerMsgExtDao;
import net.dgg.zqq.entity.customerMsg.CustomerMsg;
import net.fblock.core.common.KeyWorker;
import org.apache.commons.lang3.StringUtils;
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

        // 电话存在就 不 存
        Integer count = this.customerMsgDao.count(new HashMap() {{
            put("phone", customerMsg.getPhone());
        }});
        if (count > 0) {
            return;
        }

        if (StringUtils.isNotEmpty(customerMsg.getTrademarkName())) {
            Assert.isTrue(customerMsg.getTrademarkName().length() < 30, "商标名字不能超过30字符！");
        }
        if (StringUtils.isNotEmpty(customerMsg.getIndustry())) {
            Assert.isTrue(customerMsg.getTrademarkName().length() < 30, "行业不能超过30字符！");
        }

        customerMsg.setId(KeyWorker.nextId());
        customerMsg.setCreateTime(new Date());
        customerMsg.setUpdateTime(new Date());
        customerMsg.setArea(null);
        customerMsg.setFlag(null);
        this.customerMsgDao.save(customerMsg);
    }


    @Transactional(readOnly = true)
    public List<Map> pageQuery(Map query) {
        Integer count = this.customerMsgExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.customerMsgExtDao.queryMap(query);
        } else {
            return Collections.emptyList();
        }
    }


}
