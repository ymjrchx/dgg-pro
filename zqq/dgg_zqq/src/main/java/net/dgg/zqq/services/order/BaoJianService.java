package net.dgg.zqq.services.order;

import com.google.gson.Gson;
import lombok.Data;
import net.dgg.zqq.constant.*;
import net.dgg.zqq.dao.order.OrderDao;
import net.dgg.zqq.dao.order.TrademarkAndApplicantDao;
import net.dgg.zqq.dao.order.TrademarkClassDao;
import net.dgg.zqq.dto.baoJian.BaoJianClassDto;
import net.dgg.zqq.dto.baoJian.BaoJianDto;
import net.dgg.zqq.entity.order.Order;
import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import net.dgg.zqq.entity.order.TrademarkClass;
import net.dgg.zqq.services.fileUpload.FileUploadService;
import net.dgg.zqq.utils.GsonUtils;
import net.dgg.zqq.utils.HttpUtility;
import net.dgg.zqq.utils.MapUtils;
import net.dgg.zqq.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * @author 刘阳
 * @ClassName <BaoJianService>
 * @despriction 报件服务
 * @create 2018/11/06 16:17
 **/
@Service
public class BaoJianService {
    @Value("${baojian.url}")
    private String url;
    @Value("${baojian.callbackUrl}")
    private String callbackUrl;
    @Value("${baojian.agentCompany}")
    private String agentCompany;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private TrademarkAndApplicantDao trademarkAndApplicantDao;
    @Autowired
    private TrademarkClassDao trademarkClassDao;
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 发送报件
     *
     * @param orderId
     * @return
     */
    public Long sendBaoJian(Long orderId) {
        Assert.notNull(orderId, "订单ID不能为空！");
        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "未查询到订单！");
        Assert.isTrue(OrderTypeConstant.AUTO.equals(order.getType()), "非智能订单不能报件！");
        List<TrademarkAndApplicant> applicantList = this.trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        TrademarkAndApplicant applicant = applicantList.isEmpty() ? null : applicantList.get(0);
        Assert.notNull(applicant, "未查询到申请人信息！");
        List<TrademarkClass> trademarkClassList = this.trademarkClassDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        Assert.isTrue(!trademarkClassList.isEmpty(), "未查询到商标分类信息");

        BaoJianDto baoJianDto = this.getBaoJianDtoContactInfo(order, applicant, trademarkClassList);

        Long baojianNo = this.postData(baoJianDto);

        return baojianNo;
    }

    /**
     * 新报件
     *
     * @param orderId
     */
    @Transactional
    public void sendBaoJian2(Long orderId) {
        Assert.notNull(orderId, "订单ID不能为空！");
        Order order = this.orderDao.findById(orderId);
        Assert.notNull(order, "未查询到订单！");
        Assert.isTrue(OrderTypeConstant.AUTO.equals(order.getType()), "非智能订单不能报件！");
        Assert.isTrue(Arrays.asList(OrderStatusConstant.PAYED, OrderStatusConstant.IN_PROCESS).contains(order.getStatus()), "订单付款后再能进行报件！");
        Assert.isTrue(BaoJianStatusConstant.WAITING.equals(order.getBaoJianStatus()), "订单报件不能重复发起！");
        List<TrademarkAndApplicant> applicantList = this.trademarkAndApplicantDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        TrademarkAndApplicant applicant = applicantList.isEmpty() ? null : applicantList.get(0);
        Assert.notNull(applicant, "未查询到申请人信息！");
        List<TrademarkClass> trademarkClassList = this.trademarkClassDao.query(new HashMap() {{
            put("orderId", orderId);
        }});
        Assert.isTrue(!trademarkClassList.isEmpty(), "未查询到商标分类信息");

        BaoJianDto baoJianDto = this.getBaoJianDtoContactInfo(order, applicant, trademarkClassList);

        Long baojianNo = this.postData(baoJianDto);
        Assert.notNull(baojianNo, "报件编号获取失败！");
        order.setBaoJianNo(baojianNo);
        order.setBaoJianStatus(BaoJianStatusConstant.SENDED);
        order.setUpdateTime(new Date());
        this.orderDao.update(order);

    }


    // post 数据
    private Long postData(BaoJianDto baoJianDto) {
        Assert.notNull(baoJianDto, "报件数据不能为空");
        Map map = MapUtils.convertBean(baoJianDto);
        Assert.isTrue(!map.isEmpty(), "报件数据不能为空");
        String res = null;
        try {
            res = HttpUtility.postJson(url, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.hasText(res, "报件数据发送无数据返回");
        ReturnResult result = new Gson().fromJson(res, ReturnResult.class);
        Assert.notNull(result != null && result.code != null, "报件返回数据转换失败");
        Assert.isTrue(result.code.intValue() == 0, result.msg);
        Assert.notNull(result.data, "报件编号没有返回");
        return result.data;
    }

    // 设置报件DTO 信息
    private BaoJianDto getBaoJianDtoContactInfo(Order order, TrademarkAndApplicant andApplicant, List<TrademarkClass> trademarkClassList) {
        Assert.notNull(order, "订单不能为空");
        Assert.notNull(andApplicant, "申请人信息不能为空");
        Assert.isTrue(!trademarkClassList.isEmpty(), "商标分类不能为空！");
        // 创建报件实体
        BaoJianDto baoJianDto = new BaoJianDto();
        baoJianDto.setAppContactPerson(order.getContactName()); // 联系人
        baoJianDto.setAppContactTel(order.getContactPhone()); // 联系电话
        baoJianDto.setAppContactZip(andApplicant.getPostalcode()); // 邮编
        Assert.hasText(andApplicant.getBusinessLicencePdfFile(), "营业执照副本文件不能为空");
        baoJianDto.setFileZtKey(fileUploadService.getHost().concat(andApplicant.getBusinessLicencePdfFile())); //  主体资格证明文件(中文) 营业执照
        // 企业
        if (ApplocantTypeConstant.ENTERPRISE.equals(andApplicant.getApplicantType())) {
            baoJianDto.setTmzcrAppTypeId("0"); // 0:法人或其他组织；1:自然人；
            baoJianDto.setCertCode(andApplicant.getBusinessLicenceNo());
            Assert.hasText(baoJianDto.getCertCode(), "申请类型为企业时，统一社会信用代码不能为空！");
        }
        // 个人
        if (ApplocantTypeConstant.PERSON.equals(andApplicant.getApplicantType())) {
            baoJianDto.setAppCardName(CardTypeConstant.ID_CARD);
            baoJianDto.setAppCartNum(andApplicant.getApplicantCardNo());
            baoJianDto.setTmzcrAppTypeId("1"); //0:法人或其他组织；1:自然人；
            Assert.hasText(andApplicant.getApplicantCardPdfFile(), "身份证文件不能为空！");
            baoJianDto.setFileSfKey(fileUploadService.getHost().concat(andApplicant.getApplicantCardPdfFile())); // 身份证
        }
        baoJianDto.setTmzcrAppGjdq("0"); // 0:中国大陆；1：国外；2：中国台湾；3：中国香港；4：中国澳门；
        baoJianDto.setAppCnName(andApplicant.getApplicantName()); //申请人名称
        baoJianDto.setAppCnAddr(andApplicant.getBusinessLicenceAddress());// 申请人地址
        baoJianDto.setBrandType("0");// (0是一般, 1是集体，2是证明)
        baoJianDto.setTmDesignDeclare(andApplicant.getExplain()); // 商标说明
        baoJianDto.setTMimgColorKey(fileUploadService.getHost().concat(andApplicant.getExampleAddress()));  // 彩色商标图样
        Assert.hasText(andApplicant.getProxyFile(), "代理委托书文件不能为空");
        baoJianDto.setFileWtKey(fileUploadService.getHost().concat(andApplicant.getProxyFile())); // 代理委托书

        // 优先权证明
        if (StringUtils.isEmpty(andApplicant.getPriorityFile())) {
            baoJianDto.setPriorityType("0"); // 0：无；1：在先优先权；2：展会优先权；
        } else {
            baoJianDto.setPriorityType("1");
            baoJianDto.setFileYxKey(fileUploadService.getHost().concat(andApplicant.getPriorityFile()));
        }

        List<BaoJianClassDto> baoJianClassDtos = this.createBaoJianClassDtoList(trademarkClassList);
        Gson gson = GsonUtils.buildSerGson();
        gson.serializeNulls();
        baoJianDto.setGoods(gson.toJson(baoJianClassDtos));

        baoJianDto.setAgentCompany(this.agentCompany);
        baoJianDto.setSource(BaojianSource.ZQQ); // 来源
        baoJianDto.setCallBackUrl(this.callbackUrl); // 回调地址
        return baoJianDto;
    }

    //
    private List<BaoJianClassDto> createBaoJianClassDtoList(List<TrademarkClass> trademarkClassList) {
        List<BaoJianClassDto> baoJianClassDtos = new ArrayList<>(trademarkClassList.size());
        for (TrademarkClass trademarkClass : trademarkClassList) {
            BaoJianClassDto dto = new BaoJianClassDto();
            // 三级信息
            dto.setCode(trademarkClass.getClassLevel3Code());
            dto.setName(trademarkClass.getClassLevel3Name());
            // 二级信息
            dto.setSecondCode(trademarkClass.getClassLevel2Code());
            dto.setSecondName(trademarkClass.getClassLevel2Name());
            // 一级信息
            dto.setFirstCode(trademarkClass.getClassLevel1Code());
            dto.setFirstName(trademarkClass.getClassLevel1Name());
            baoJianClassDtos.add(dto);
        }
        return baoJianClassDtos;
    }


    /**
     * 返回结果
     */
    @Data
    private static class ReturnResult {
        private String msg;
        private Integer code;
        private Long data;

    }

}
