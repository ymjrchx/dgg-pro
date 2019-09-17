package net.dgg.zqq.services.order;

import net.dgg.zqq.constant.*;
import net.dgg.zqq.dao.UserExtDao;
import net.dgg.zqq.dao.msg.MsgDao;
import net.dgg.zqq.dao.order.*;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.msg.Msg;
import net.dgg.zqq.entity.order.*;
import net.dgg.zqq.services.SmsMsgService;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static net.dgg.zqq.constant.MsgStatusConstant.PASS_CHECK;

/**
 * @ClassName:
 * @Description: 订单展示
 * @Author: huangl
 * @Date: 2018/10/9 15:06
 */

@Service
public class OrderDisplayService {

    @Autowired
    OrderExtDao orderExtDao;
    @Autowired
    MsgDao msgDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    private AssessDao assessDao;
    @Autowired
    private TrademarkAndApplicantExtDao trademarkAndApplicantExtDao;
    @Autowired
    private ApplicantTemplateDao applicantTemplateDao;
    @Autowired
    private OrderRecordDao orderRecordDao;
    @Autowired
    private BaoJianService baoJianService;
    @Autowired
    private SmsMsgService smsMsgService;
    @Autowired
    private UserExtDao userExtDao;
    @Autowired
    private OrderService orderService;

    /**
     * 根据query查询接口
     */
    public List<Map> pageQuery(Map query) {
        Integer count = this.orderExtDao.count(query);
        query.put("count", count);
        if (count > 0) {
            return this.orderExtDao.queryMap(query);
        }
        return Collections.emptyList();
    }

    /**
     * 根据id 改变该评论审核状态
     */

    @Transactional
    public void updateStatus(Long id, String status) {
        Assert.hasText(status, "请选择状态值");
        Order order = this.orderDao.findById(id);
        String userId = order.getUserId();
        String orderNo = order.getOrderNo();
        User user = this.userExtDao.findUserById(userId);
       /* if (status.equals(OrderStatusConstant.IN_PROCESS)) //办理中
        {
            *//*String msgT1 = "您的订单已开始办理，稍后将有商务人员与你取得联系，请保持手机畅通。";
            String title1 = "订单办理信息";
            this.createPayedMsg(userId, msgT1, user.getUsername(), title1);*//*
            orderService.sendOrderMsg(user.getUsername(),order);
        }
        if (status.equals(OrderStatusConstant.OVER))//已完成
        {
            String msgT2 = "您的订单已办理完成，感谢你对我们的信任。";
            String title2 = "订单完成信息";
            this.createPayedMsg(userId, msgT2, user.getUsername(), title2);
        }*/
        // 普通订单
        if (OrderTypeConstant.NORMAL.equals(order.getType())) {
            this.orderExtDao.updateStatus(id, status);
        }
        // 智能订单
        if (OrderTypeConstant.AUTO.equals(order.getType())) {
            // Long baojianNo = this.baoJianService.sendBaoJian(order.getId());
            // order.setBaoJianNo(baojianNo);
            order.setStatus(status);
            order.setUpdateTime(new Date());
            this.orderDao.update(order);
        }
        orderService.sendOrderMsg(user.getUsername(), order);

    }

    /**
     * 创建支付成功消息
     */
    /*@Transactional
    public void createPayedMsg(String userId, String msgTemplate, String phone, String title) {
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(msgTemplate);
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle(title);
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/

    public Order findById(Long id) {
        return this.orderDao.findById(id);
    }

    /**
     * 审核智能订单
     *
     * @param id
     * @param content
     * @param photo
     * @param operate
     */
    @Transactional
    public void examine(Long id, String content, String photo, Integer operate) {
        Assert.notNull(id, "订单id不能为空");
        Order order = orderDao.findById(id);
        Assert.notNull(order, "订单id传递错误");
        //判断当前订单是否是未审核状态
        Assert.isTrue(OrderStatusConstant.WAITING_AUDIT.equals(order.getStatus()), "该订单不是代审核状态");
        Assert.isTrue(operate != null && (operate == 0 || operate == 1), "操作参数传递错误");
        Assess assess = new Assess();
        assess.setId(KeyWorker.nextId());
        assess.setOrderId(id);
        Assert.isTrue(StringUtils.hasText(content) && content.length() < 300, "content不能为空，长度不能大于300");
        assess.setContent(content);
        /* Assert.isTrue(StringUtils.hasText(photo) && photo.length() < 1000, "photo不能为空，长度不能大于1000");*/
        assess.setPhoto(photo);
        assess.setCreateTime(new Date());
        User user = this.userExtDao.findUserById(order.getUserId());
        if (operate == 1) {
            //审核通过
            //根据订单查询trademarkAndAppliucant
            TrademarkAndApplicant trademarkAndApplicant = trademarkAndApplicantExtDao.findByOrderId(id);
            Assert.notNull(trademarkAndApplicant, "数据出现错误");

            //根据trademarkAndApplicant查询申请模板
            if (trademarkAndApplicant.getTemplateId() != null) {
                ApplicantTemplate applicantTemplate = applicantTemplateDao.findById(trademarkAndApplicant.getTemplateId());
                Assert.notNull(applicantTemplate, "数据出现错误");
                //根据模板覆盖trademarkAndAppliucant中模板的相关信息
                /*trademarkAndApplicant.setApplicantType(applicantTemplate.getApplicantType());//
                trademarkAndApplicant.setApplicantUserName(applicantTemplate.getApplicantUserName());//
                trademarkAndApplicant.setApplicantCardNo(applicantTemplate.getApplicantCardNo());//
                trademarkAndApplicant.setBusinessLicenceNo(applicantTemplate.getBusinessLicenceNo());//
                trademarkAndApplicant.setApplicantCardAddress(applicantTemplate.getApplicantCardAddress());//
                trademarkAndApplicant.setBusinessLicenceArea(applicantTemplate.getBusinessLicenceArea()); //
                trademarkAndApplicant.setPostalcode(applicantTemplate.getPostalcode());  //
                trademarkAndApplicant.setBusinessLicenceAddress(applicantTemplate.getBusinessLicenceAddress()); //
                trademarkAndApplicant.setApplicantCardFile(applicantTemplate.getApplicantCardFile());//
                trademarkAndApplicant.setBusinessLicenceFile(applicantTemplate.getBusinessLicenceFile());//
                trademarkAndApplicant.setApplicantName(applicantTemplate.getApplicantName());//
                trademarkAndApplicant.setPriorityFile(applicantTemplate.getPriorityFile());//
                trademarkAndApplicant.setProxyFile(applicantTemplate.getProxyFile());//
                */

                //模板信息覆盖trademarkAndApplicant
                net.dgg.zqq.utils.BeanUtils.copyPropertiesIgnoreNull(applicantTemplate, trademarkAndApplicant, "id", "createrId", "createTime", "updateTime", "updaterId", "proxyFile", "priorityFile");
                //模板信息覆盖订单
                order.setContactName(applicantTemplate.getContactName());
                order.setContactPhone(applicantTemplate.getContactPhone());
                order.setContactEmail(applicantTemplate.getContactEmail());
            }

            order.setStatus(OrderStatusConstant.NOT_PAY);
            assess.setResult(AssessResultConstant.PASS);
            createAuditSuccessMsg(user.getUsername(), order);
        } else {
            //审核不通过
            order.setStatus(OrderStatusConstant.AUDIT_FAILD);
            assess.setResult(AssessResultConstant.Fail);
            orderService.sendOrderMsg(user.getUsername(), order);
        }

        //修改订单状态
        orderDao.update(order);

        //保存审核结果
        assessDao.save(assess);

        //保存操作记录
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(KeyWorker.nextId());
        orderRecord.setOperation(OrderOperationConstant.ASSESS);
        orderRecord.setOrderId(order.getId());
        orderRecord.setStatus(order.getStatus());
        orderRecord.setAuditor(order.getUserId());
        orderRecord.setCreateTime(new Date());
        this.orderRecordDao.save(orderRecord);

    }

    @Transactional
    public void deleteCheckFile(String groupPath) {

    }

    /**
     * 订单审核通过后消息
     */
    @Transactional
    public void createAuditSuccessMsg(String phone, Order order) {
        String msgTemplate = "尊敬的用户，您在知千秋办理的%s，已于%s%s，订单尾号%s，请登录知千秋个人中心（https://www.zhiqianqiu.com）查看。知千秋竭诚为您服务，您的满意是对我们最大的肯定。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(order.getUserId());
        msg.setRead(0);
        String orderNo = order.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        msg.setContent(String.format(msgTemplate, order.getServiceName(), sf.format(date), PASS_CHECK, orderNo.substring(orderNo.length() - 6, orderNo.length())));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("审核成功信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }

    /**
     * 订单审核未通过后消息
     */
   /* @Transactional
    public void createAuditFailMsg(String userId, String orderNo, String phone) {
        String msgTemplate = "您的订单，商标审核未通过，请尽快登录个人中心进行修改，并重新提交。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("审核失败信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/

}
