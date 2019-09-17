package net.dgg.zqq.services.invoice;

import net.dgg.zqq.constant.InvoiceResultConstant;
import net.dgg.zqq.constant.InvoiceStatusConstant;
import net.dgg.zqq.dao.invoice.InvoiceDao;
import net.dgg.zqq.dao.order.OrderDao;
import net.dgg.zqq.entity.invoice.Invoice;
import net.dgg.zqq.entity.order.Order;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/9 13:58
 */

@Service
public class InvoiceService {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    OrderDao orderDao;

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.invoiceDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.invoiceDao.queryMap(query);
        }
        return Collections.emptyList();
    }


    /**
     * 根据id 改变该发票审核结果 成功
     */
    @Transactional
    public void updateResultT(Long id) {
        Invoice invoice = this.invoiceDao.findById(id);
        invoice.setResult(InvoiceResultConstant.RESULT_SUCCESS);
        this.invoiceDao.update(invoice);
        Order order = this.orderDao.findById(Long.valueOf(invoice.getOrderId()));
        order.setInvoiceStatus(InvoiceStatusConstant.SUCCESS);
        this.orderDao.update(order);
    }

    /**
     * 根据id 改变该发票审核结果 失败
     */
    @Transactional
    public void updateResultF(Long id) {
        Invoice invoice = this.invoiceDao.findById(id);
        invoice.setResult(InvoiceResultConstant.RESULT_FAIL);
        this.invoiceDao.update(invoice);
        Order order = this.orderDao.findById(Long.valueOf(invoice.getOrderId()));
        order.setInvoiceStatus(InvoiceStatusConstant.FAIL);
        this.orderDao.update(order);
    }


    /**
     * 发票申请
     */
    @Transactional
    public void invoiceApply(Invoice invoice) {
        //根据订单编号查询订单状态
        Assert.isTrue(RedisUtils.exists(invoice.getUserId()), "当前登陆已过期！");
        List<Order> list = this.orderDao.query(new HashMap() {{
            put("Id", invoice.getOrderId());
        }});
        Order order = list.get(0);
        String invoiceStatus = order.getInvoiceStatus();
        Assert.isTrue(!(invoiceStatus.equals(InvoiceStatusConstant.APPLYING) || invoiceStatus.equals(InvoiceStatusConstant.SUCCESS)), "请勿重复申请！");
        Assert.notNull(invoice.getTitle(), "请填写发票抬头");
        Assert.isTrue(invoice.getTitle().length() > 30, "发票抬头不能过长！");
        Assert.notNull(invoice.getAddress(), "请填写邮寄地址");
        Assert.isTrue(invoice.getAddress().length() > 30, "邮寄地址不能过长！");
        Assert.notNull(invoice.getRecipients(), "请填写收件人");
        Assert.isTrue(invoice.getRecipients().length() > 10, "收件人不能过长！");
        Assert.notNull(invoice.getPhoneNo(), "请填写手机号");
        Assert.isTrue(invoice.getPhoneNo().matches("^1(3|4|5|7|8)\\d{9}$"), "请输入正确的手机号");
        invoice.setId(KeyWorker.nextId());
        invoice.setOrderId(String.valueOf(order.getId()));
        invoice.setUserId(order.getUserId());
        invoice.setResult(InvoiceResultConstant.RESULT_WAITTING);
        this.invoiceDao.save(invoice);
        order.setInvoiceStatus(InvoiceStatusConstant.APPLYING);
        this.orderDao.update(order);
    }
}
