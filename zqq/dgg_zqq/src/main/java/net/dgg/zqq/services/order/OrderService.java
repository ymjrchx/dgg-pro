package net.dgg.zqq.services.order;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import net.coobird.thumbnailator.Thumbnails;
import net.dgg.zqq.constant.*;
import net.dgg.zqq.controller.treebook.TranslateUtil;
import net.dgg.zqq.dao.UserExtDao;
import net.dgg.zqq.dao.msg.MsgDao;
import net.dgg.zqq.dao.order.*;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceAttrRelationDao;
import net.dgg.zqq.dao.serviceAndAttr.ServiceItemDao;
import net.dgg.zqq.entity.business.User;
import net.dgg.zqq.entity.msg.Msg;
import net.dgg.zqq.entity.order.*;
import net.dgg.zqq.entity.payment.PaymentOrder;
import net.dgg.zqq.entity.serviceAndAttr.ServiceAttr;
import net.dgg.zqq.entity.serviceAndAttr.ServiceItem;
import net.dgg.zqq.framework.redis.RedisUtils;
import net.dgg.zqq.services.ServiceItem.ServiceItemService;
import net.dgg.zqq.services.SmsMsgService;
import net.dgg.zqq.services.fast_dfs.FastDfsService;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.services.payment.PaymentCallbackAdapter;
import net.dgg.zqq.services.payment.PaymentOrderService;
import net.dgg.zqq.utils.BeanUtils;
import net.dgg.zqq.utils.MapUtils;
import net.dgg.zqq.utils.OrderNoUtils;
import net.fblock.core.common.KeyWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static net.dgg.zqq.constant.MsgStatusConstant.*;
import static net.dgg.zqq.constant.OrderStatusConstant.*;

/**
 * @author 刘阳
 * @ClassName <OrderService>
 * @despriction 订单服务
 * @create 2018/10/11 10:23
 **/
@Service
public class OrderService implements PaymentCallbackAdapter {

    @Value("${trademarkClass.classLevel1Price}")
    private BigDecimal classLevel1Price;
    @Value("${trademarkClass.classLevel3Price}")
    private BigDecimal classLevel3Price;


    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderExtDao orderExtDao;
    @Autowired
    private TranslateUtil translateUtil;
    @Autowired
    private ServiceItemDao serviceItemDao;
    @Autowired
    private ServiceAttrDao serviceAttrDao;
    @Autowired
    private ServiceAttrRelationDao serviceAttrRelationDao;
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private TrademarkAndApplicantDao trademarkAndApplicantDao;
    @Autowired
    private TrademarkClassDao trademarkClassDao;
    @Autowired
    private CommentRecordDao commentRecordDao;
    @Autowired
    private PayRecordDao payRecordDao;
    @Autowired
    private MsgDao msgDao;
    @Autowired
    private UserExtDao userExtDao;
    @Autowired
    private ApplicantTemplateDao applicantTemplateDao;
    @Autowired
    private SmsMsgService smsMsgService;
    @Autowired
    private ApplicantTemplateService applicantTemplateService;
    @Autowired
    private OrderRecordDao orderRecordDao;
    @Autowired
    private AssessDao assessDao;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private FastDfsService fastDfsService;
    @Autowired
    private TrademarkClassExtDao trademarkClassExtDao;
    @Autowired
    private ServiceItemService serviceItemService;


    /**
     * @param userId  用户
     * @param pageNum
     * @param code    服务一级类别
     * @return
     */
    public Map queryUserOrder(String userId, Integer pageIndex, Integer pageNum, String code, String orderNo, String serviceTypeLevel3Code, String contactName,
                              String payWay, String markName, String createTimeStart, String createTimeEnd, String status, String type) {

        Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        Assert.notNull(pageIndex, "页码 不能为空！");
        Assert.notNull(pageNum, "每页数据数目 不能为空！");
        //Assert.hasText(code, "code 不能为空！");
        Assert.isTrue(pageIndex > 0, "pageIndex 只能输入大于0的数字");
        Assert.isTrue(pageNum > 0, "pageNum 只能输入大于0的数字");
        Map map = new HashMap();
        map.put("flag", 1);
        map.put("userId", userId);
        map.put("start", (pageIndex - 1) * pageNum);
        map.put("limit", pageNum);
        map.put("serviceTypeLevel1Code", code);
        map.put("orderNo", orderNo);
        map.put("serviceTypeLevel3Code", serviceTypeLevel3Code);
        map.put("contactName", contactName);
        map.put("payWay", payWay);
        map.put("markName", markName);
        map.put("createTimeStart", createTimeStart);
        map.put("createTimeEnd", createTimeEnd);
        map.put("status", status);
        map.put("type", type);

        Integer count = orderExtDao.count(map);
        List<Map> userOrderList = 0 == count.intValue() ? Collections.emptyList() : orderExtDao.queryMap(map);

        String[] transColumn = {"typeLevel1CodeName", "payWay", "serviceTypeLevel3Code", "status", "baoJianStatus"};
        translateUtil.translateList(transColumn, userOrderList);

        for (Map map1 : userOrderList) {
            Long orderId = Long.parseLong(String.valueOf(map1.get("id")));
            List<Map> classList = this.getTradeMarkClass(orderId);
            map1.put("classList", classList);
            map1.put("host", this.fileUploadService.getHost());
            List itemlist = this.serviceItemService.getServiceItemByCode((String) map1.get(serviceTypeLevel3Code));
            map1.put("serviceItem", itemlist);
        }

        map.put("dateMap", userOrderList);
        map.put("count", count);
        return map;
    }

    /**
     * 保存普通订单
     *
     * @param order
     * @return
     */
    @Transactional
    public Order saveNormalOrder(Order order) {
        order.setType(OrderTypeConstant.NORMAL);
        this.checkOrder(order);
        this.addOrderDetail(order);
        order.setId(KeyWorker.nextId());
        String orderNo = OrderNoUtils.getOrderNo();
        order.setOrderNo(orderNo);
        order.setInvoiceFee(BigDecimal.ZERO);
        order.setAllTrademarkClassPrice(BigDecimal.ZERO);
        // 计算总价
        BigDecimal allPrice = this.getAllPrice(order);

        Assert.isTrue(allPrice.compareTo(order.getAllPrice()) == 0, "价格与后台价格计算不符，请刷新页面！");
        order.setAllPrice(allPrice);
        order.setComment(0);
        order.setStatus(NOT_PAY);
        order.setFlag(1);

        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        User user = this.userExtDao.findUserById(order.getUserId());
        this.orderDao.save(order);
        // 创建消息
        this.sendOrderMsg(user.getUsername(), order);
        return order;
    }

    /**
     * 保存智能订单
     *
     * @param order
     * @return
     */
    @Transactional
    public Order saveAutoOrder(Order order, TrademarkAndApplicant trademarkAndApplicant, List<TrademarkClass> trademarkClassList) {
        order.setType(OrderTypeConstant.AUTO);
        // 验证 订单 商标 分类 信息
        this.checkOrder(order);
        Assert.notNull(order.getInvoiceFee(), "发票税费不能为空！");
        order.setId(KeyWorker.nextId());
        this.checkTrademarkAndApplicant(trademarkAndApplicant);
        trademarkAndApplicant.setId(KeyWorker.nextId());
        trademarkAndApplicant.setOrderId(order.getId());
        this.checkTrademarkClassList(order.getId(), trademarkAndApplicant.getId(), trademarkClassList);
        this.addOrderDetail(order);

        // 计算商标分类总价
        BigDecimal allTrademarkClassPrice = this.getAllTrademarkClassPrice(trademarkClassList, order.getServiceOfficialPrice());
        order.setAllTrademarkClassPrice(allTrademarkClassPrice);

        // 计算总价
        BigDecimal allPrice = this.getAllPrice(order);
        Assert.isTrue(allPrice.compareTo(order.getAllPrice()) == 0, "价格与后台价格计算不符，请返回服务选择页面查看！");
        order.setAllPrice(allPrice);
        order.setComment(0);
        order.setStatus(NOT_PAY);
        order.setFlag(1);

        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setBaoJianStatus(BaoJianStatusConstant.WAITING); //待报件
        this.orderDao.save(order);

        trademarkAndApplicant.setCreateTime(new Date());
        this.trademarkAndApplicantDao.save(trademarkAndApplicant);


        for (TrademarkClass trademarkClass : trademarkClassList) {
            this.trademarkClassDao.save(trademarkClass);
        }
        // 创建消息
        //this.createNotPayMsg(order.getUserId(), order.getOrderNo());

        return order;
    }

    /**
     * 保存智能订单2
     *
     * @param order
     * @param trademarkAndApplicant
     * @param trademarkClassList
     * @return
     */
    @Transactional
    public Order saveAutoOrder2(Order order, TrademarkAndApplicant trademarkAndApplicant, List<TrademarkClass> trademarkClassList) {
        order.setType(OrderTypeConstant.AUTO);
        // 验证 订单 商标 分类 信息
        this.checkOrder(order);
        Assert.notNull(order.getInvoiceFee(), "发票税费不能为空！");
        order.setId(KeyWorker.nextId());
        this.checkTrademarkAndApplicant(trademarkAndApplicant);
        trademarkAndApplicant.setId(KeyWorker.nextId());
        trademarkAndApplicant.setOrderId(order.getId());
        this.checkTrademarkClassList(order.getId(), trademarkAndApplicant.getId(), trademarkClassList);
        this.addOrderDetail(order);

        // 计算商标分类总价
        BigDecimal allTrademarkClassPrice = this.getAllTrademarkClassPrice(trademarkClassList, order.getServiceOfficialPrice());
        order.setAllTrademarkClassPrice(allTrademarkClassPrice);

        // 智能订单 服务总费 为需要乘以商标大类数目
        if (OrderTypeConstant.AUTO.equals(order.getType())) {
            BigDecimal bigClassNum = this.getBigClassNum(trademarkClassList);
            order.setAllServicePrice(bigClassNum.multiply(order.getServicePrice()));
        }

        // 计算总价
        BigDecimal allPrice = this.getAllPrice(order);
        Assert.isTrue(allPrice.compareTo(order.getAllPrice()) == 0, "价格与后台价格计算不符，请返回服务选择页面查看！");
        order.setAllPrice(allPrice);
        order.setComment(0);
        // 设置订单状态
        this.setOrderStatus(order);

        order.setFlag(1);
        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setBaoJianStatus(BaoJianStatusConstant.WAITING); //待报件


        // 创建订单信息模版
        ApplicantTemplate template = this.createOrCheckTemplate(order, trademarkAndApplicant);
        // 新建时 设置模版ID
        if (trademarkAndApplicant.getTemplateId() == null) {
            trademarkAndApplicant.setTemplateId(template.getId());
        }

        // 营业执照 img 转 PDF
        if (!StringUtils.isEmpty(trademarkAndApplicant.getBusinessLicenceFile())) {
            String pdf = this.createPdfFile(trademarkAndApplicant.getBusinessLicenceFile());
            trademarkAndApplicant.setBusinessLicencePdfFile(pdf);
        }

        // 身份证 IMG 转PDF
        if (!StringUtils.isEmpty(trademarkAndApplicant.getApplicantCardFile())) {
            String cardPdf = this.createPdfFile(trademarkAndApplicant.getApplicantCardFile());
            trademarkAndApplicant.setApplicantCardPdfFile(cardPdf);
        }

        this.orderDao.save(order);

        trademarkAndApplicant.setCreateTime(new Date());
        this.trademarkAndApplicantDao.save(trademarkAndApplicant);
        for (TrademarkClass trademarkClass : trademarkClassList) {
            this.trademarkClassDao.save(trademarkClass);
        }

        User user = this.userExtDao.findUserById(order.getUserId());

        // 创建消息 发送短信
        if (!(order.getStatus().equals(WAITING_AUDIT) || order.getStatus().equals(WAITING_SUBMIT))) {
            this.sendOrderMsg(user.getUsername(), order);
        }

        // 不是只保存就创建订单提交记录
        this.createOrderRecord(order);

        return order;
    }


    /**
     * 修改智能订单
     *
     * @param order
     * @param trademarkAndApplicant
     * @param trademarkClassList
     * @return
     */
    @Transactional
    public Order updateAutoOrder(Order order, TrademarkAndApplicant trademarkAndApplicant, List<TrademarkClass> trademarkClassList) {
        order.setType(OrderTypeConstant.AUTO);
        Assert.notNull(order.getId(), "修改订单时ID不能为空");
        Order orderSys = orderDao.findById(order.getId());
        Assert.notNull(orderSys, "订单ID传递错误");
        Assert.isTrue(Arrays.asList(OrderStatusConstant.WAITING_SUBMIT, OrderStatusConstant.AUDIT_FAILD).contains(orderSys.getStatus()), "只有待提交和未通过审核的订单才能修改");

        // 验证 订单 商标 分类 信息
        this.checkOrder(order);
        Assert.isTrue(orderSys.getUserId().equals(order.getUserId()), "不是同一个用户，不能修改订单");
        Assert.notNull(order.getInvoiceFee(), "发票税费不能为空！");
        this.checkTrademarkAndApplicant(trademarkAndApplicant);

        //删除系统中老TrademarkClass
        List<TrademarkAndApplicant> list = trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", order.getId());
        }});
        Assert.isTrue(list != null && list.size() > 0, "未查询到系统中的trademarkAndApplicant");
        TrademarkAndApplicant trademarkAndApplicantSys = list.get(0);
        List<TrademarkClass> trademarkClassListSys = trademarkClassDao.query(new HashMap() {{
            put("orderId", order.getId());
            put("trademarkId", trademarkAndApplicantSys.getId());
        }});
        if (trademarkClassListSys != null && trademarkClassListSys.size() > 0) {
            for (TrademarkClass trademarkClass : trademarkClassListSys) {
                trademarkClassDao.deleteById(trademarkClass.getId());
            }
        }

        //验证新的TrademarkClassList
        this.checkTrademarkClassList(order.getId(), trademarkAndApplicantSys.getId(), trademarkClassList);

        //判断老文件是否删除
        //this.checkFile(trademarkAndApplicant, trademarkAndApplicantSys);

        //添加订单细节信息
        this.addOrderDetail(order);

        // 计算商标分类总价
        BigDecimal allTrademarkClassPrice = this.getAllTrademarkClassPrice(trademarkClassList, order.getServiceOfficialPrice());
        order.setAllTrademarkClassPrice(allTrademarkClassPrice);

        // 智能订单 服务总费 为需要乘以商标大类数目
        if (OrderTypeConstant.AUTO.equals(order.getType())) {
            BigDecimal bigClassNum = this.getBigClassNum(trademarkClassList);
            order.setAllServicePrice(bigClassNum.multiply(order.getServicePrice()));
        }

        // 计算总价
        BigDecimal allPrice = this.getAllPrice(order);
        Assert.isTrue(allPrice.compareTo(order.getAllPrice()) == 0, "价格与后台价格计算不符，请返回服务选择页面查看！");
        order.setAllPrice(allPrice);
        order.setComment(0);

        // 营业执照 img 转 PDF
        if (!StringUtils.isEmpty(trademarkAndApplicant.getBusinessLicenceFile())
                && !trademarkAndApplicant.getBusinessLicenceFile().equals(trademarkAndApplicantSys.getBusinessLicenceFile())) {
            String pdf = this.createPdfFile(trademarkAndApplicant.getBusinessLicenceFile());
            trademarkAndApplicant.setBusinessLicencePdfFile(pdf);
        }

        // 身份证 IMG 转PDF
        if (!StringUtils.isEmpty(trademarkAndApplicant.getApplicantCardFile()) &&
                !trademarkAndApplicant.getApplicantCardFile().equals(trademarkAndApplicantSys.getApplicantCardFile())) {
            String cardPdf = this.createPdfFile(trademarkAndApplicant.getApplicantCardFile());
            trademarkAndApplicant.setApplicantCardPdfFile(cardPdf);
        }

        // 设置订单状态
        this.setOrderStatus(order);
        order.setFlag(1);
        order.setBaoJianStatus(BaoJianStatusConstant.WAITING); //待报件
        orderSys.setUpdateTime(new Date());
        BeanUtils.copyPropertiesIgnoreNull(order, orderSys, "id", "orderNo", "createTime", "createrId", "createrOrgId", "createrName", "updateTime", "updaterId", "updaterName", "updaterOrgId");

        //修改系统中的trademarkAndApplicant
        trademarkAndApplicantSys.setUpdaterId(order.getUserId());
        trademarkAndApplicantSys.setUpdateTime(new Date());

        // 创建（修改）订单信息模版
        ApplicantTemplate template = this.createOrCheckTemplate(order, trademarkAndApplicant);
        // 新建时 设置模版ID
        if (trademarkAndApplicant.getTemplateId() == null) {
            trademarkAndApplicant.setTemplateId(template.getId());
        }
        BeanUtils.copyPropertiesIgnoreNull(trademarkAndApplicant, trademarkAndApplicantSys, "id", "createTime", "createrId", "updateTime", "updaterId");

        this.orderDao.update(orderSys);
        this.trademarkAndApplicantDao.update(trademarkAndApplicantSys);
        for (TrademarkClass trademarkClass : trademarkClassList) {
            this.trademarkClassDao.save(trademarkClass);
        }
        User user = this.userExtDao.findUserById(order.getUserId());
        // 创建消息 发送短信
        if (!(order.getStatus().equals(WAITING_AUDIT) || order.getStatus().equals(WAITING_SUBMIT))) {
            this.sendOrderMsg(user.getUsername(), order);
        }

        // 不是只保存就创建订单提交记录
        this.createOrderRecord(orderSys);

        return orderSys;
    }

    /**
     * 创建消息 发送短信
     *
     * @param order
     */
    /*private void sendMsg(Order order, String phone) {
        switch (order.getAuditType()) {
            case AuditTypeConstant.SAVE_ONLY:
                break;
            case AuditTypeConstant.NOT_AUDIT:
                this.sendNotPayMsg(order.getUserId(), phone);
                break;
            default:
                this.createOrderSubmitMsg(order.getUserId(), order.getOrderNo(), phone);
        }
    }*/

    /**
     * 校验是否删除老文件
     *
     * @param trademarkAndApplicant
     * @param trademarkAndApplicantSys
     */
    private void checkFile(TrademarkAndApplicant trademarkAndApplicant, TrademarkAndApplicant trademarkAndApplicantSys) {
        if (StringUtils.hasText(trademarkAndApplicantSys.getApplicantCardFile()) && StringUtils.hasText(trademarkAndApplicant.getApplicantCardFile()) &&
                !trademarkAndApplicantSys.getApplicantCardFile().equals(trademarkAndApplicant.getApplicantCardFile())) {
            Boolean flag = fastDfsService.deleteFile(trademarkAndApplicantSys.getApplicantCardFile());
            Assert.isTrue(flag, "身份证文件路径错误或被篡改");
        }

        if (StringUtils.hasText(trademarkAndApplicantSys.getBusinessLicenceFile()) && StringUtils.hasText(trademarkAndApplicant.getBusinessLicenceFile())
                && !trademarkAndApplicantSys.getBusinessLicenceFile().equals(trademarkAndApplicant.getBusinessLicenceFile())) {
            Boolean flag = fastDfsService.deleteFile(trademarkAndApplicantSys.getBusinessLicenceFile());
            Assert.isTrue(flag, "营业执照文件路径错误或被篡改");

            if (StringUtils.hasText(trademarkAndApplicantSys.getBusinessLicencePdfFile())) {
                Boolean flag1 = fastDfsService.deleteFile(trademarkAndApplicantSys.getBusinessLicencePdfFile());
                Assert.isTrue(flag1, "营业执照pdf文件路径错误或被篡改");
            }

            // img 转 PDF
            String pdf = this.createPdfFile(trademarkAndApplicant.getBusinessLicenceFile());
            trademarkAndApplicant.setBusinessLicencePdfFile(pdf);
        }

        if (StringUtils.hasText(trademarkAndApplicantSys.getProxyFile()) && StringUtils.hasText(trademarkAndApplicant.getProxyFile())
                && !trademarkAndApplicantSys.getProxyFile().equals(trademarkAndApplicant.getProxyFile())) {
            Boolean flag = fastDfsService.deleteFile(trademarkAndApplicantSys.getProxyFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        }

        if (StringUtils.hasText(trademarkAndApplicantSys.getPriorityFile()) && StringUtils.hasText(trademarkAndApplicant.getPriorityFile()) && !trademarkAndApplicantSys.getPriorityFile().equals(trademarkAndApplicant.getPriorityFile())) {
            Boolean flag = fastDfsService.deleteFile(trademarkAndApplicantSys.getPriorityFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        }
    }


    /**
     * 创建订单提交记录
     *
     * @param order
     */
    private void createOrderRecord(Order order) {
        if (!AuditTypeConstant.SAVE_ONLY.equals(order.getAuditType())) {
            // 创建操作记录
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setId(KeyWorker.nextId());
            orderRecord.setOperation(OrderOperationConstant.SUBMIT);
            orderRecord.setOrderId(order.getId());
            orderRecord.setStatus(order.getStatus());
            orderRecord.setAuditor(order.getUserId());
            orderRecord.setCreateTime(new Date());
            this.orderRecordDao.save(orderRecord);
        }
    }

    /**
     * 设置订单状态
     *
     * @param order
     */
    private void setOrderStatus(Order order) {
        switch (order.getAuditType()) {
            case AuditTypeConstant.SAVE_ONLY:
                order.setStatus(OrderStatusConstant.WAITING_SUBMIT);
                break;
            case AuditTypeConstant.NOT_AUDIT:
                order.setStatus(NOT_PAY);
                break;
            default:
                order.setStatus(OrderStatusConstant.WAITING_AUDIT);
        }
    }

    // 验证
    private void checkOrder(Order order) {
        Assert.notNull(order, "订单不能为空！");
        Assert.hasText(order.getUserId(), "订单所属用户Id不能为空！");
        Assert.isTrue(RedisUtils.exists(order.getUserId()), "用户未登录或登录已过期！");
        Assert.notNull(order.getServiceId(), "服务项ID不能为空！");
        /* Assert.notNull(order.getServiceAttrId(), "服务属性ID不能为空");*/
        Assert.notNull(order.getAllNum(), "服务件数不能为空！");
        Assert.isTrue(order.getAllNum() >= 1, "服务件数不能小于1！");
        Assert.hasText(order.getContactName(), "联系人不能为空！");
        Assert.isTrue(order.getContactName().length() <= 50, "联系人字符长度不能超过50字符！");
        Assert.hasText(order.getContactPhone(), "联系人电话不能为空！");
        Assert.isTrue(order.getContactPhone().length() < 30, "联系人电话字符长度不能超过30字符！");
        Assert.hasText(order.getContactEmail(), "联系人邮箱不能为空！");
        Assert.isTrue(order.getContactEmail().length() <= 30, "联系人邮箱字符长度不能超过50字符！");
        if (!StringUtils.isEmpty(order.getRemark())) {
            Assert.isTrue(order.getRemark().length() <= 500, "备注不能超过500字符!");
        }
        // 订单2 智能订单
        if (OrderTypeConstant.AUTO.equals(order.getAuditType())) {
            Assert.hasText(order.getAuditType(), "审核类型不能为空！");
            Assert.isTrue(Arrays.asList(AuditTypeConstant.AUDIT, AuditTypeConstant.FAST_AUDIT,
                    AuditTypeConstant.NOT_AUDIT, AuditTypeConstant.SAVE_ONLY).contains(order.getAuditType()), "审核类型数据错误！");
        }
    }

    // 完善订单 服务 价格 信息
    private void addOrderDetail(Order order) {
        // 设置 服务信息
        ServiceItem serviceItem = this.serviceItemDao.findById(order.getServiceId());
        Assert.notNull(serviceItem, "未查询到该服务项");
        Assert.isTrue(StatusConstant.ENABLE.equals(serviceItem.getStatus()), "该服务项已被禁用！");
        Assert.isTrue(serviceItem.getFlag() != null && 1 == serviceItem.getFlag().intValue(), "该服务项已被删除！");
        order.setServiceName(serviceItem.getName());
        order.setServiceTypeLevel1Code(serviceItem.getTypeLevel1Code());
        order.setServiceTypeLevel2Code(serviceItem.getTypeLevel2Code());
        order.setServiceTypeLevel3Code(serviceItem.getTypeLevel3Code());


        order.setServicePrice(serviceItem.getServicePrice());
        // 智能 类别订单 服务的官费不计入总价  服务官费作为智能订单商标大类的基础价格
        //order.setServiceOfficialPrice(OrderTypeConstant.AUTO.equals(order.getType()) ? BigDecimal.ZERO : serviceItem.getOfficialPrice());
        order.setServiceOfficialPrice(serviceItem.getOfficialPrice());


        // 设置服务属性信息
        if (order.getServiceAttrId() != null) {
            ServiceAttr serviceAttr = this.serviceAttrDao.findById(order.getServiceAttrId());
            Assert.notNull(serviceAttr, "服务属性未查询到！");
            Assert.isTrue(StatusConstant.ENABLE.equals(serviceAttr.getStatus()), "该服务属性已被禁用！");
            Assert.isTrue(serviceAttr.getFlag() != null && 1 == serviceAttr.getFlag().intValue(), "该服务属性已被禁用！");
            Integer count = this.serviceAttrRelationDao.count(new HashMap() {{
                put("serviceItemId", serviceItem.getId());
                put("serviceAttrId", serviceAttr.getId());
            }});
            Assert.isTrue(count > 0, "此属性未与此服务绑定！");
            order.setServiceAttrCode(serviceAttr.getCode());
            order.setServiceAttrName(serviceAttr.getName());
            order.setServiceAttrServicePrice(serviceAttr.getServicePrice());
            order.setServiceAttrOfficialPrice(serviceAttr.getOfficialPrice());
        } else {
            order.setServiceAttrServicePrice(BigDecimal.ZERO);
            order.setServiceAttrOfficialPrice(BigDecimal.ZERO);
        }

        //计算服务费总价
        BigDecimal allServicePrice = new BigDecimal(order.getAllNum()).multiply(order.getServicePrice().add(order.getServiceAttrServicePrice()));
        order.setAllServicePrice(allServicePrice);

        //计算  官费总价
        BigDecimal allAttrPrice = new BigDecimal(order.getAllNum()).multiply(order.getServiceOfficialPrice().add(order.getServiceAttrOfficialPrice()));
        order.setAllOfficialPrice(allAttrPrice);


        // 智能注册订单 官费总价 需要根据商标分类来计算 服务与服务属性无效
        if (OrderTypeConstant.AUTO.equals(order.getType()) /*&& YesAndNoConstant.NO.equals(serviceItem.getAutoAddServicePrice())*/) {
            order.setAllOfficialPrice(BigDecimal.ZERO);
            //order.setAllServicePrice(BigDecimal.ZERO);
        }

    }

    // 验证 商标和申请人信息
    private void checkTrademarkAndApplicant(TrademarkAndApplicant trademarkAndApplicant) {
        //  验证 商标 信息
        Assert.hasText(trademarkAndApplicant.getType(), "商标类型不能为空！");
        Assert.isTrue(Arrays.asList(TrademarkTypeConstant.TEXT, TrademarkTypeConstant.IMG,
                TrademarkTypeConstant.TEXT_AND_IMG, TrademarkTypeConstant.BLACK_AND_WHITE,
                TrademarkTypeConstant.COLOR).contains(trademarkAndApplicant.getType()), "商标类型数据错误！");
        if (Arrays.asList(TrademarkTypeConstant.TEXT, TrademarkTypeConstant.TEXT_AND_IMG)
                .contains(trademarkAndApplicant.getType())) {
            Assert.hasText(trademarkAndApplicant.getName(), "商标名称不能为空！");
        }
        Assert.hasText(trademarkAndApplicant.getExampleType(), "用户商标图样类型不能为空！");
        Assert.isTrue(Arrays.asList(TrademarkExample.SYS, TrademarkExample.USER)
                .contains(trademarkAndApplicant.getExampleType()), "用户商标图样类型数据错误！");
        Assert.hasText(trademarkAndApplicant.getExampleAddress(), "商标图样文件地址不能为空！");
        Assert.hasText(trademarkAndApplicant.getClassCreateWay(), "商标类别创建方式不能为空！");
        Assert.isTrue(Arrays.asList(TrademarkClassCreateWay.RECOMMEND, TrademarkClassCreateWay.USER_CHOISE)
                .contains(trademarkAndApplicant.getClassCreateWay()), "商标类别创建方式数据错误！");
        // Assert.hasText(trademarkAndApplicant.getSuggestedFirstLevel(), "智能推荐的一级的领域ID不能为空！");
        // Assert.hasText(trademarkAndApplicant.getSuggestedSecondLevel(), "智能推荐的二级的领域ID不能为空！");

        // 验证申请人信息
        Assert.hasText(trademarkAndApplicant.getApplicantType(), "申请人类型不能为空！");
        Assert.isTrue(Arrays.asList(ApplocantTypeConstant.ENTERPRISE, ApplocantTypeConstant.PERSON)
                .contains(trademarkAndApplicant.getApplicantType()), "申请人类型数据错误！");
        Assert.hasText(trademarkAndApplicant.getApplicantName(), "申请人名字或企业名不能为空！");
        if (ApplocantTypeConstant.PERSON.equals(trademarkAndApplicant.getApplicantType())) {
            Assert.hasText(trademarkAndApplicant.getApplicantCardNo(), "申请人身份证号不能为空！");
        }
        //Assert.hasText(trademarkAndApplicant.getBusinessLicenceArea(), "营业执照所在地区不能为空！");
        Assert.hasText(trademarkAndApplicant.getPostalcode(), "邮政编码不能为空！");
        Assert.hasText(trademarkAndApplicant.getBusinessLicenceAddress(), "营业执照详情地址不能为空！");
        Assert.hasText(trademarkAndApplicant.getBusinessLicenceNo(), "营业执照社会代码不能为空！");

        if (ApplocantTypeConstant.PERSON.equals(trademarkAndApplicant.getApplicantType())) {
            Assert.hasText(trademarkAndApplicant.getApplicantCardFile(), "身份证复印件不能为空！");
        }
        Assert.hasText(trademarkAndApplicant.getBusinessLicenceFile(), "营业执照不能为空！");
        Assert.hasText(trademarkAndApplicant.getProxyFile(), "委托书不能为空！");
        //  Assert.hasText(trademarkAndApplicant.getPriorityFile(), "优先权证明不能为空！");


        // 订单2
        Assert.hasText(trademarkAndApplicant.getExplain(), "商标说明不能为空！");
        Assert.isTrue(trademarkAndApplicant.getExplain().length() <= 100, "商标说明不能超过100字符！");
        if (!StringUtils.isEmpty(trademarkAndApplicant.getRegisterNo())) {
            Assert.isTrue(trademarkAndApplicant.getRegisterNo().length() < 30, "注册号不能超过30字符！");
        }

    }

    // 验证 商标分类
    private void checkTrademarkClassList(Long orderId, Long trademarkId, List<TrademarkClass> trademarkClassList) {
        Assert.notNull(trademarkClassList, "商标分类信息不能为空！");
        Assert.notEmpty(trademarkClassList, "商标分类信息不能为空！");
        Date date = new Date();
        for (TrademarkClass trademarkClass : trademarkClassList) {
            Assert.hasText(trademarkClass.getClassLevel1Code(), "一级分类编码不能为空！");
            Assert.hasText(trademarkClass.getClassLevel1Name(), "一级分类名称不能为空！");
            //Assert.hasText(trademarkClass.getClassLevel2Code(), "二级分类编码不能为空！");
            //Assert.hasText(trademarkClass.getClassLevel2Name(), "二级分类名称不能为空！");
            //Assert.hasText(trademarkClass.getClassLevel3Code(), "三级分类编码不能为空！");
            Assert.hasText(trademarkClass.getClassLevel3Name(), "三级分类名称不能为空！");
            trademarkClass.setId(KeyWorker.nextId());
            trademarkClass.setOrderId(orderId);
            trademarkClass.setTrademarkId(trademarkId);
            trademarkClass.setCreateTime(date);
        }
    }

    // 计算商标分类 总价
    private BigDecimal getAllTrademarkClassPrice(List<TrademarkClass> trademarkClassList, BigDecimal bigClassPrice) {
        Map<String, Integer> classLevel1CodePriceMap = new HashMap<>(trademarkClassList.size());
        for (TrademarkClass trademarkClass : trademarkClassList) {
            Integer number = classLevel1CodePriceMap.get(trademarkClass.getClassLevel1Code());
            number = number == null ? 0 : number;
            number++;
            classLevel1CodePriceMap.put(trademarkClass.getClassLevel1Code(), number);
        }
        BigDecimal allPrice = BigDecimal.ZERO;
        for (Integer num : classLevel1CodePriceMap.values()) {
            allPrice = allPrice.add(this.getPrice(num, bigClassPrice));
        }
        return allPrice;
    }

    // 计算大类下 小类的总价格
    private BigDecimal getPrice(Integer num, BigDecimal bigClassPrice) {
        return BigDecimal.ZERO.add(bigClassPrice).add(new BigDecimal(num <= 10 ? 0 : (num - 10)).multiply(this.classLevel3Price));
    }


    /**
     * @param userId
     * @param orderNo
     * @return
     */
    public Map queryUserOrderByNo(String userId, String orderNo) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.hasText(orderNo, "订单编号不能为空！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("userId", userId);
            put("orderNo", orderNo);
        }});
        Assert.isTrue(!orderList.isEmpty(), String.format("未查询到编号为%s的客户的订单！", orderNo));
        Order order = new Order(), orderSys = orderList.get(0);
        order.setId(orderSys.getId());
        order.setOrderNo(orderSys.getOrderNo());
        order.setAllPrice(orderSys.getAllPrice());

        order.setServiceName(orderSys.getServiceName());
        order.setServiceTypeLevel3Code(orderSys.getServiceTypeLevel3Code());
        order.setCreateTime(order.getCreateTime());
        order.setStatus(order.getStatus());

        Map map = MapUtils.convertBean(orderSys);
        String allPrice = String.valueOf(order.getAllPrice());
        map.put("sign", this.paymentOrderService.generateSign(orderSys.getOrderNo(), this.getBusinessType(orderSys.getServiceTypeLevel1Code()), allPrice, orderSys.getServiceName()));
        map.put("serviceTypeLevel1Code", this.getBusinessType(orderSys.getServiceTypeLevel1Code()));
        map.put("allPrice", allPrice);
        List<TrademarkAndApplicant> andApplicants = this.trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderSys.getId());
        }});
        map.put("statusName", this.translateUtil.translateCode(order.getStatus()));

        // 商标信息
        Map applicantMap = new HashMap();
        TrademarkAndApplicant applicant = andApplicants.isEmpty() ? null : andApplicants.get(0);
        applicantMap.put("exampleAddress", applicant == null ? null : applicant.getExampleAddress());
        applicantMap.put("name", applicant == null ? null : applicant.getName());
        applicantMap.put("host", fileUploadService.getHost());
        map.put("trademark", applicantMap);

        // 分类信息
        List<Map> classList = this.getTradeMarkClass(orderSys.getId());
        map.put("classList", classList);

        return map;
    }

    /**
     * @param applicantName
     * @param userId
     * @param type
     * @return
     */
    public List<Map> queryApplicant(String userId, String type, String applicantName, Integer limit) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("applicantType", type);
        map.put("applicantName", applicantName);
        map.put("start", 0);
        map.put("limit", limit);
        List<Map> mapList = applicantDao.queryApplicantMap(map);
        return mapList;
    }

    // 计算总价
    private BigDecimal getAllPrice(Order order) {
        return BigDecimal.ZERO
                .add(order.getAllServicePrice())
                .add(order.getAllOfficialPrice())
                .add(order.getInvoiceFee())
                .add(order.getAllTrademarkClassPrice());
    }


    /**
     * 获取价格配置
     *
     * @return
     */
    public Map getClassLevelPrice(String userId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Map map = new HashMap();
        map.put("classLevel1Price", this.classLevel1Price);
        map.put("classLevel3Price", this.classLevel3Price);
        return map;
    }


    /**
     * 取消订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    @Transactional
    public void cancelUserOrder(String userId, Long orderId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("userId", userId);
            put("id", orderId);
        }});
        Order order = orderList.isEmpty() ? null : orderList.get(0);
        Assert.notNull(order, "未查询到此用户订单");
        Assert.isTrue(order.getFlag() != null && order.getFlag().intValue() == 1, "此订单已被删除！");
        order.setFlag(0);
        order.setUpdateTime(new Date());
        this.orderDao.update(order);
    }

    /**
     * 评价记录
     *
     * @param userId
     * @param orderId
     * @param level
     * @param content
     */
    @Transactional
    public void commentUserOrder(String userId, Long orderId, Integer level, String content) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");
        Assert.notNull(level, "星级不能为空！");
        Assert.isTrue(level > 0 && level < 6, "星级只能是1-5！");
        Assert.hasText(content, "评论文字不能为空！");
        Assert.isTrue(content.length() < 300, "评论文字长度不能超过300！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("userId", userId);
            put("id", orderId);
        }});
        Order order = orderList.isEmpty() ? null : orderList.get(0);
        Assert.notNull(order, "未查询到此用户订单");
        Assert.isTrue(order.getFlag() != null && order.getFlag().intValue() == 1, "此订单已被删除！");
        Assert.isTrue(OrderStatusConstant.OVER.equals(order.getStatus()), "此订单非完成状态，无法评价！");

        order.setStatus(OrderStatusConstant.COMMENTED);
        order.setComment(1);
        order.setUpdateTime(new Date());

        // 创建评论
        CommentRecord record = new CommentRecord();
        record.setId(KeyWorker.nextId());
        record.setOrderId(orderId);
        record.setUserId(userId);
        record.setLevel(level);
        record.setContent(content);
        record.setFlag(0);
        record.setServiceTypeLevel1Code(order.getServiceTypeLevel1Code());
        record.setServiceTypeLevel3Code(order.getServiceTypeLevel3Code());
        record.setCreateTime(new Date());

        this.orderDao.update(order);
        this.commentRecordDao.save(record);
    }

    /**
     * 查询订单支付状态
     *
     * @param userId
     * @param orderId
     */
    public Map queryUserOrderPayStatus(String userId, Long orderId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");
        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "未查询到订单！");
        String key = "status", payStatusKey = "payStatus";
        Map reMap = new HashMap();
        reMap.put("userId", userId);
        reMap.put("orderId", orderId);
        reMap.put(key, order.getStatus());
        if (NOT_PAY.equals(order.getStatus())) {
            Integer count = this.payRecordDao.count(new HashMap() {{
                put("userId", userId);
                put("orderId", orderId);
            }});
            reMap.put(payStatusKey, count > 0 ? PayStatusConstant.FAILD : PayStatusConstant.NOT_PAY);
            return reMap;
        }

        reMap.put(payStatusKey, PayStatusConstant.SUCCESS);
        return reMap;
    }


    @Override
    public void onSuccess(String businessNo, String businessType, PaymentOrder paymentOrder) {
        Assert.hasText(businessNo, "订单编号不能为空！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("orderNo", businessNo);
        }});
        Order order = orderList.isEmpty() ? null : orderList.get(0);
        Assert.notNull(order, String.format("编号：%s的订单未找到！", businessNo));
        order.setStatus(PAYED);
        order.setUpdateTime(new Date());
        this.orderDao.update(order);

        // 创建支付记录
        PayRecord payRecord = new PayRecord();
        payRecord.setId(KeyWorker.nextId());
        payRecord.setUserId(order.getUserId());
        payRecord.setOrderId(order.getId());
        payRecord.setPayWay(paymentOrder == null ? null : paymentOrder.getPayType());
        payRecord.setMoney(order.getAllPrice());
        payRecord.setResult(PayStatusConstant.SUCCESS);
        payRecord.setCreateTime(new Date());
        this.payRecordDao.save(payRecord);
        User user = this.userExtDao.findUserById(order.getUserId());
        this.sendOrderMsg(user.getUsername(), order);
    }

    @Override
    public void onFailure(String businessNo, String busenessType, PaymentOrder paymentOrder) {
        Assert.hasText(businessNo, "订单编号不能为空！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("orderNo", businessNo);
        }});
        Order order = orderList.isEmpty() ? null : orderList.get(0);
        Assert.notNull(order, String.format("编号：%s的订单未找到！", businessNo));
        // 不修改订单状态

        // 创建支付记录
        PayRecord payRecord = new PayRecord();
        payRecord.setId(KeyWorker.nextId());
        payRecord.setUserId(order.getUserId());
        payRecord.setOrderId(order.getId());
        payRecord.setPayWay(paymentOrder == null ? null : paymentOrder.getPayType());
        payRecord.setMoney(order.getAllPrice());
        payRecord.setResult(PayStatusConstant.FAILD);
        payRecord.setCreateTime(new Date());
        this.payRecordDao.save(payRecord);

        // 创建 支付操作记录
        this.createPayOperateRecord(order);
    }


    @Transactional
    public void sendOrderMsg(String phone, Order order) {
        String msgTemplate = "尊敬的用户，您在知千秋办理的%s，已于%s%s，订单尾号%s，请登录知千秋个人中心（https://www.zhiqianqiu.com）查看。知千秋竭诚为您服务，您的满意是对我们最大的肯定。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(order.getUserId());
        msg.setRead(0);
        String orderNo = order.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String content = null;
        //未付款
        if (order.getStatus().equals(NOT_PAY)) {
            content = CREATE_SUCCESS;
            msg.setTitle("未付款信息");
        }
        //已付款
        if (order.getStatus().equals(PAYED)) {
            content = PAY_SUCCESS;
            msg.setTitle("付款成功信息");
        }
        //办理中
        if (order.getStatus().equals(IN_PROCESS)) {
            content = HANDLING;
            msg.setTitle("订单办理信息");
        }
        //办理完成
        if (order.getStatus().equals(OVER)) {
            content = HANDLE_SUCCESS;
            msg.setTitle("订单完成信息");
        }
        //审核失败
        if (order.getStatus().equals(AUDIT_FAILD)) {
            content = NOPASS_CHECK;
            msg.setTitle("审核失败信息");
        }
        //免审核
//        if (order.getAuditType().equals(AuditTypeConstant.NOT_AUDIT)){
//            content = PASS_CHECK;
//            msg.setTitle("创建成功");
//        }
        msg.setContent(String.format(msgTemplate, order.getServiceName(), sf.format(date), content, orderNo.substring(orderNo.length() - 6, orderNo.length())));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }

    /**
     * 创建未支付消息
     */
    /*@Transactional
    public void createNotPayMsg(String userId, String orderNo) {
        String msgTemplate = "您的订单已创建，请进入个人中心订单页完成支付，订单%s，如已支付请忽略。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("未付款信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);

    }*/

    /**
     * 未付款发送短信（免审核）
     */
    /*@Transactional
    public void sendNotPayMsg(String userId, String phone) {
        String msgTemplate = "您的订单已创建完成，请进入个人中心订单页查看详情。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(msgTemplate);
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("订单提交信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/

    /**
     * 创建提交审核 消息
     */
    /*@Transactional
    public void createOrderSubmitMsg(String userId, String orderNo, String phone) {
        String msgTemplate = "您的订单已创建，请进入个人中心订单页等待审核。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("订单提交信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/


    /**
     * 创建审核成功后 未支付 消息
     */
   /* @Transactional
    public void createAuditSuccessMsg(String userId, String orderNo, String phone) {
        String msgTemplate = "您的订单，商标审核已经通过，请尽快登录个人中心查看、付款。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("审核成功信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/


    /**
     * 创建支付成功消息
     */
    /*@Transactional
    public void createPayedMsg(String userId, String orderNo, String phone) {
        String msgTemplate = "您的订单已付款，稍后将有商务人员与你取得联系，请保持手机畅通。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("支付成功信息");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }*/

    /**
     * 创建申报成功成功消息
     */
    @Transactional
    public void createApplySuccessMsg(String userId, String orderNo, String phone) {
        String msgTemplate = "您的订单%s已申报到商标局。";
        Msg msg = new Msg();
        msg.setId(KeyWorker.nextId());
        msg.setUserId(userId);
        msg.setRead(0);
        msg.setContent(String.format(msgTemplate, orderNo));
        msg.setType(MsgTypeConstant.ORDER);
        msg.setTitle("申报成功");
        msg.setFlag(1);
        msg.setCreateTime(new Date());
        this.msgDao.save(msg);
        this.smsMsgService.sendSmsMsg("【知千秋】".concat(msg.getContent()), phone);
    }

    /**
     * 返回当前用户的订单信息
     */
    public Map selectInfo(String userId) {
        Assert.isTrue(RedisUtils.exists(userId), "当前登陆已过期！");
        List<Order> list = this.orderDao.query(new HashMap() {{
            put("userId", userId);
        }});
        User user = this.userExtDao.findUserById(userId);
        String username = user.getUsername();
        List<Order> newList = new ArrayList<Order>();
        for (int i = 0; i < list.size(); i++) {
            Order order1 = new Order();
            Order order = list.get(i);
            order1.setOrderNo(order.getOrderNo());
            order1.setAllPrice(order.getAllPrice());
            order1.setCreateTime(order.getCreateTime());
            order1.setInvoiceStatus(order.getInvoiceStatus());
            newList.add(order1);
        }
        Map map = new HashMap();
        map.put("orderList", newList);
        map.put("username", username);
        return map;
    }

    /**
     * 智能订单详细信息
     *
     * @param userId
     * @param orderId
     * @return
     */
    public Map queryOrderInfo(String userId, Long orderId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");
        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "未查询到订单！");
        String host = fileUploadService.getHost();
        List recordList = this.orderRecordDao.queryMap(new HashMap() {{
            put("orderId", orderId);
        }});
        if (order.getType().equals(OrderTypeConstant.NORMAL)) {  //智能订单还是普通订单 判断 如果是普通订单直接返回
            Map map1 = MapUtils.convertBean(order);
            map1.put("operate", recordList);
            return map1;
        }
        List<TrademarkAndApplicant> tralist = this.trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        TrademarkAndApplicant tra = tralist.get(0);
        ApplicantTemplate applicantTemplate = this.applicantTemplateService.findInfoById(tra.getTemplateId());
        List<Assess> assessList = this.assessDao.query(new HashMap() {{
            put("orderId", orderId);
            put("start", 0);
            put("limit", 1);
        }});
        Map map = new HashMap();
        List assList = new ArrayList();
        if (assessList.size() > 0) {
            Assess assess = assessList.get(0);
            String[] photoUrl = assess.getPhoto().split(",");
            map.put("assContent", assess.getContent());
            for (int i = 0; i < photoUrl.length; i++) {
                assList.add(host.concat(photoUrl[i]));
            }
            map.put("assList", assList);
        }

        String[] transColumn = {"operation"};
        translateUtil.translateList(transColumn, recordList);


        map.put("operate", recordList);
        //申请主体
        if (order.getStatus().equals(OrderStatusConstant.WAITING_SUBMIT) || order.getStatus().equals(OrderStatusConstant.WAITING_AUDIT)) {
            map.put("contactName", applicantTemplate.getContactName());
            map.put("contactPhone", applicantTemplate.getContactPhone());
            map.put("contactEmail", applicantTemplate.getContactEmail());
        } else {
            map.put("contactName", order.getContactName());
            map.put("contactPhone", order.getContactPhone());
            map.put("contactEmail", order.getContactEmail());
        }
        map.put("applicantName", applicantTemplate.getApplicantName());
        map.put("businessLicenceAddress", applicantTemplate.getBusinessLicenceAddress());
        map.put("postalcode", applicantTemplate.getPostalcode());
        map.put("applicantCardNo", applicantTemplate.getApplicantCardNo());
        map.put("businessLicenceFile", host.concat(applicantTemplate.getBusinessLicenceFile()));
        if (!net.dgg.zqq.utils.StringUtils.isNullOrEmpty(applicantTemplate.getApplicantCardFile())) {
            map.put("applicantCardFile", host.concat(applicantTemplate.getApplicantCardFile()));
        }

        //注册商标信息
        map.put("name", tra.getName());
        map.put("explain", tra.getExplain());
        map.put("registerNo", tra.getRegisterNo());
        map.put("exampleAddress", host.concat(tra.getExampleAddress()));
        map.put("proxyFile", host.concat(tra.getProxyFile()));
        List<TrademarkClass> list = this.trademarkClassDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        map.put("Trademarkclass", list);
        map.put("orderStatusName", translateUtil.translateCode(order.getStatus()));
        map.put("orderStatus", order.getStatus());
        map.put("orderNo", order.getOrderNo());
        map.put("orderId", order.getId());
        map.put("allPrice", order.getAllPrice());
        return map;

    }


    /**
     * 更新报件状态
     *
     * @param baoJianNo
     * @param status
     * @return
     */
    @Transactional
    public void updateBaoJianStatus(Long baoJianNo, String status, String reason) {
        Assert.notNull(baoJianNo, "报件编号不能为空！");
        Assert.notNull(status, "报件状态不能为空！");
        Assert.isTrue(Arrays.asList(BaoJianStatusConstant.CHECK_FAIL, BaoJianStatusConstant.CHECK_SUCCESS,
                BaoJianStatusConstant.BAO_JIAN_FAIL, BaoJianStatusConstant.BAO_JIAN_SUCCESS).contains(status), "报件状态非法！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("baoJianNo", baoJianNo);
        }});
        Order order = orderList.isEmpty() ? null : orderList.get(0);
        Assert.notNull(order, "未查询到订单！");
        order.setBaoJianStatus(status);
        order.setBaoJianReason(reason == null || reason.length() < 255 ? reason : reason.substring(0, 255));
        order.setUpdateTime(new Date());
        this.orderDao.update(order);

        // 商标局提交成功 创建 操作记录
        if (BaoJianStatusConstant.BAO_JIAN_SUCCESS.equals(status)) {
            OrderRecord record = new OrderRecord();
            record.setId(KeyWorker.nextId());
            record.setStatus(order.getStatus());
            record.setOperation(OrderOperationConstant.DECLARE);
            record.setOrderId(order.getId());
            record.setCreateTime(new Date());
            this.orderRecordDao.save(record);
        }

    }

    /**
     * 创建模版
     *
     * @return
     */
    private ApplicantTemplate createOrCheckTemplate(Order order, TrademarkAndApplicant applicant) {
        Assert.notNull(order, "订单不能为空！");
        Assert.notNull(applicant, "商标申请人信息不能为空！");

        // 订单是根据模版创建,更新
        if (applicant.getTemplateId() != null) {
            ApplicantTemplate template = this.applicantTemplateDao.findById(applicant.getTemplateId());
            Assert.notNull(template, "未查询到模版！");
            if (template.getFlag() != null && template.getFlag() == 1) {
                // 复制订单联系人信息
                template.setContactName(order.getContactName());
                template.setContactPhone(order.getContactPhone());
                template.setContactEmail(order.getContactEmail());
                //修改模板时判断老文件是否删除
                //this.checkTemplateFile(applicant, template);

                // 复制商标和申请人信息
                BeanUtils.copyPropertiesIgnoreNull(applicant, template, "priorityFile", "proxyFile", "id", "createTime", "createrId", "updateTime", "updaterId");
                template.setUpdaterId(order.getUserId());
                template.setUpdateTime(new Date());
                // 根据申请类型清理多余数据
                if (ApplocantTypeConstant.ENTERPRISE.equals(template.getApplicantType())) {
                    template.setApplicantCardNo(null);
                    template.setApplicantCardFile(null);
                    template.setApplicantUserName(null);
                    template.setApplicantCardAddress(null);
                }
                this.applicantTemplateDao.update(template);
                return template;
            }

        }

        // 订单没有使用模版
        ApplicantTemplate template = new ApplicantTemplate();
        template.setContactName(order.getContactName());
        template.setContactEmail(order.getContactEmail());
        template.setContactPhone(order.getContactPhone());
        BeanUtils.copyPropertiesIgnoreNull(applicant, template, "priorityFile", "proxyFile", "id", "createTime", "createrId", "updateTime", "updaterId");
        template.setCreaterId(order.getUserId());
        template.setFlag(1);
        template.setStatus(StatusConstant.ENABLE);
        List<ApplicantTemplate> templateList = this.applicantTemplateDao.query(MapUtils.convertBean(template));
        if (templateList.isEmpty()) {
            template.setId(KeyWorker.nextId());
            template.setCreaterId(order.getUserId());
            template.setCreateTime(order.getCreateTime());
            template.setTemplateName(applicant.getApplicantName());
            template.setStatus(StatusConstant.ENABLE);
            template.setFlag(1);
            //判断是否有默认
            Boolean falg = hasDefault(template.getCreaterId());
            template.setDefaultSign(falg ? 0 : 1);

            this.applicantTemplateDao.save(template);
        } else {
            applicant.setTemplateId(templateList.get(0).getId());
            this.trademarkAndApplicantDao.update(applicant);
        }
        return template;
    }

    /**
     * 查看是否存在默认
     *
     * @param createrId
     * @return
     */
    private Boolean hasDefault(String createrId) {
        //设置默认
        Integer count = applicantTemplateDao.count(new HashMap() {{
            put("flag", 1);
            put("status", StatusConstant.ENABLE);
            put("createrId", createrId);
            put("defaultSign", 1);
        }});

        return (count == null || count < 1) ? false : true;

    }

    /**
     * 更新模板时判断是否要删除老文件
     *
     * @param applicant
     * @param template
     */
    private void checkTemplateFile(TrademarkAndApplicant applicant, ApplicantTemplate template) {
        if (StringUtils.hasText(template.getApplicantCardFile()) && StringUtils.hasText(applicant.getApplicantCardFile())
                && !template.getApplicantCardFile().equals(applicant.getApplicantCardFile())) {
            Boolean flag = fastDfsService.deleteFile(template.getApplicantCardFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        } else if (StringUtils.hasText(template.getApplicantCardFile()) && !StringUtils.hasText(applicant.getApplicantCardFile())) {
            Boolean flag = fastDfsService.deleteFile(template.getApplicantCardFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        }

        if (StringUtils.hasText(template.getBusinessLicenceFile()) && StringUtils.hasText(applicant.getBusinessLicenceFile())
                && !template.getBusinessLicenceFile().equals(applicant.getBusinessLicenceFile())) {
            Boolean flag = fastDfsService.deleteFile(template.getBusinessLicenceFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        } else if (StringUtils.hasText(template.getBusinessLicenceFile()) && !StringUtils.hasText(applicant.getBusinessLicenceFile())) {
            Boolean flag = fastDfsService.deleteFile(template.getBusinessLicenceFile());
            Assert.isTrue(flag, "文件路径错误或被篡改");
        }
    }

    public Map findOrderById(String userId, Long orderId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");

        Map map = new HashMap();
        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "未查询到订单！");

        //TrademarkAndApplicant trademarkAndApplicant = trademarkAndApplicantExtDao.findByOrderId(order.getId());
        List<TrademarkAndApplicant> list = trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        Assert.isTrue(list != null && list.size() > 0, "id传递错误");
        TrademarkAndApplicant trademarkAndApplicant = list.get(0);
        Assert.notNull(trademarkAndApplicant, "未查询到trademarkAndApplicant！");
        //判断是否存在模板  有的话 用模板覆盖trademarkerAndApplicant
        if (trademarkAndApplicant.getTemplateId() != null) {
            ApplicantTemplate applicantTemplate = applicantTemplateDao.findById(trademarkAndApplicant.getTemplateId());
            Assert.notNull(applicantTemplate, "模板id错误");
            BeanUtils.copyPropertiesIgnoreNull(applicantTemplate, trademarkAndApplicant, "id", "proxyFile", "priorityFile", "status", "flag", "defaultSign", "createTime", "createrId", "updateTime", "updaterId");
            order.setContactName(applicantTemplate.getContactName());
            order.setContactPhone(applicantTemplate.getContactPhone());
            order.setContactEmail(applicantTemplate.getContactEmail());
        }

        List<TrademarkClass> trademarkClassList = trademarkClassDao.query(new HashMap() {{
            put("orderId", order.getId());
            put("trademarkId", list.get(0).getId());
        }});

        map.put("order", order);
        map.put("trademarkAndApplicant", trademarkAndApplicant);
        map.put("trademarkClassList", trademarkClassList);
        map.put("fsHost", fileUploadService.getHost());
        return map;

    }


    /**
     * 修改订单状态为待审
     *
     * @param userId
     * @param orderId
     */
    @Transactional
    public void changeStatus(String userId, Long orderId) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.notNull(orderId, "订单ID不能为空！");

        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "订单ID传递错误！");
        order.setStatus(OrderStatusConstant.WAITING_AUDIT);
        this.orderDao.update(order);

        //保存操作记录
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setId(KeyWorker.nextId());
        orderRecord.setOperation(OrderOperationConstant.SUBMIT);
        orderRecord.setOrderId(order.getId());
        orderRecord.setStatus(order.getStatus());
        orderRecord.setAuditor(order.getUserId());
        orderRecord.setCreateTime(new Date());
        this.orderRecordDao.save(orderRecord);

    }

    /**
     * 创建PDf 文件并上传文件服务器
     *
     * @param groupPath
     * @return
     */
    private String createPdfFile(String groupPath) {
        Assert.hasText(groupPath, "文件地址不能为空！");
        byte[] bytes = this.fastDfsService.getFile(groupPath);
        Assert.isTrue(bytes != null && bytes.length > 0, "从文件服务器获取文件字节获取失败！");

        try {
            bytes = this.zipImg(bytes, new Float(PageSize.A4.getWidth()).intValue(), new Float(PageSize.A4.getHeight()).intValue());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.isTrue(false, "文件压缩失败！");
        }

        //取得图像的宽和高。
        Image img = null;
        try {
            img = Image.getInstance(bytes);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.notNull(img, "Img创建失败！");
        float width = img.getWidth();
        float height = img.getHeight();
        img.setAbsolutePosition(0.0F, 0.0F);//取消偏移
        //img.scalePercent(24f);
        img.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());

        //img转pdf
        Document doc = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pw = null;
        boolean add = false;
        try {
            pw = PdfWriter.getInstance(doc, outputStream);
            doc.open();
            doc.add(img);
            add = true;
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            pw.flush();
            doc.close();
            pw.close();
        }
        Assert.isTrue(add, "Img添加到PDF失败！");
        String path = this.fastDfsService.upload(outputStream.toByteArray(), this.getFileNameFromGroupPath(groupPath), "pdf");
        return path;
    }

    private String getFileNameFromGroupPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String getBusinessType(String typeCode) {
        return typeCode.indexOf("_") > -1 ? typeCode.substring(typeCode.indexOf("_") + 1) : typeCode;
    }


    private List<Map> getTradeMarkClass(Long orderId) {
        Assert.notNull(orderId, "订单ID不能为空！");
        return this.trademarkClassExtDao.queryAllClassLevel1(orderId);
    }

    /**
     * 创建支付操作记录
     *
     * @param order
     * @return
     */

    @Transactional
    public void createPayOperateRecord(Order order) {
        Assert.notNull(order, "订单不能为空！");
        Assert.notNull(order.getId(), "订单ID不能为空！");

        // 确认申报    支付订单
        List<String> opeCodes = Arrays.asList(OrderOperationConstant.CONFIRM, OrderOperationConstant.PAYMENT);
        List<OrderRecord> orderRecords = new ArrayList<>(opeCodes.size());
        Date date = new Date();
        for (String opeCode : opeCodes) {
            OrderRecord record = new OrderRecord();
            record.setId(KeyWorker.nextId());
            Assert.hasText(order.getStatus(), "订单状态不能为空！");
            record.setStatus(order.getStatus());
            record.setOperation(opeCode);
            record.setOrderId(order.getId());
            record.setCreateTime(date);
            orderRecords.add(record);
        }

        for (OrderRecord record : orderRecords) {
            this.orderRecordDao.save(record);
        }
    }

    /**
     * 获取商标大类的数量
     *
     * @return
     */
    private BigDecimal getBigClassNum(List<TrademarkClass> trademarkClassList) {
        Set<String> codeSet = new HashSet<>(trademarkClassList.size());
        for (TrademarkClass trademarkClass : trademarkClassList) {
            codeSet.add(trademarkClass.getClassLevel1Code());
        }
        return new BigDecimal(codeSet.size());
    }


    /**
     * 压缩图片文件
     *
     * @param bytes
     * @return
     */
    private byte[] zipImg(byte[] bytes, int width, int height) throws IOException {
        BigDecimal byteSize = new BigDecimal(bytes.length);
        BigDecimal sizeLimit = new BigDecimal(2 * 1024 * 1024); // 2M的大小
        BigDecimal zipLimit = new BigDecimal(0.9F);
        int result = byteSize.divide(sizeLimit).compareTo(zipLimit);
        if (result <= 0) {
            return bytes;
        }
        // 超过比例 开始压缩
        // 计算压缩比
        BigDecimal zipRatio = sizeLimit.multiply(zipLimit).divide(byteSize, 4, BigDecimal.ROUND_HALF_UP);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(bytes))
                .size(width, height).outputFormat("jpg").outputQuality(zipRatio.floatValue()).toOutputStream(out);
        return out.toByteArray();
    }


    /**
     * 小程序—订单详情
     */
    public Map queryOrderByNo(String userId, String orderNo) {
        Assert.hasText(userId, "用户ID不能为空！");
        Assert.isTrue(RedisUtils.exists(userId), "客户未登录或登录过期！");
        Assert.hasText(orderNo, "订单编号不能为空！");
        List<Order> orderList = this.orderDao.query(new HashMap() {{
            put("userId", userId);
            put("orderNo", orderNo);
        }});
        Assert.isTrue(!orderList.isEmpty(), String.format("未查询到编号为%s的客户的订单！", orderNo));
        Order order = new Order(), orderSys = orderList.get(0);
        order.setOrderNo(orderSys.getOrderNo());
        order.setAllPrice(orderSys.getAllPrice());
        order.setServiceName(orderSys.getServiceName());
        order.setServiceTypeLevel3Code(orderSys.getServiceTypeLevel3Code());
        order.setCreateTime(order.getCreateTime());
        order.setStatus(order.getStatus());
        order.setContactName(orderSys.getContactName());
        order.setContactPhone(orderSys.getContactPhone());
        Map map = MapUtils.convertBean(order);
        return map;

    }

}
