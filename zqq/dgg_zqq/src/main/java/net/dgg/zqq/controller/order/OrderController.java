package net.dgg.zqq.controller.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.entity.order.Order;
import net.dgg.zqq.entity.order.TrademarkAndApplicant;
import net.dgg.zqq.entity.order.TrademarkClass;
import net.dgg.zqq.services.order.OrderService;
import net.dgg.zqq.services.order.TrademarkAndApplicantService;
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 刘阳
 * @ClassName <OrderController>
 * @despriction 订单
 * @create 2018/10/11 9:11
 **/
@Controller
@RequestMapping("/order")
@Api(description = "订单")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TrademarkAndApplicantService trademarkAndApplicantService;


    /**
     * 查询用户订单信息
     *
     * @param userId
     * @param pageIndex
     * @param pageNum
     * @param code
     * @return
     */
    @RequestMapping(value = "/queryUserOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户订单", notes = "查询用户订单", httpMethod = "GET")
    public Object queryUserOrder(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                 @ApiParam(name = "pageIndex", value = "起始页，大于0", required = true) @RequestParam Integer pageIndex,
                                 @ApiParam(name = "pageNum", value = "每页数据，大于0", required = true) @RequestParam Integer pageNum,
                                 @ApiParam(name = "code", value = "服务所属一级级类别(商标：navigation_trademark 专利：navigation_patent 版权：navigation_copyright)"
                                         , required = false) @RequestParam(required = false) String code,
                                 @ApiParam(name = "orderNo", value = "订单编号") @RequestParam(required = false) String orderNo,
                                 @ApiParam(name = "serviceTypeLevel3Code", value = "服务类型") @RequestParam(required = false) String serviceTypeLevel3Code,
                                 @ApiParam(name = "contactName", value = "申请人") @RequestParam(required = false) String contactName,
                                 @ApiParam(name = "payWay", value = "支付方式") @RequestParam(required = false) String payWay,
                                 @ApiParam(name = "markName", value = "商标名称") @RequestParam(required = false) String markName,
                                 @ApiParam(name = "createTimeStart", value = "下单起始时间") @RequestParam(required = false) String createTimeStart,
                                 @ApiParam(name = "createTimeEnd", value = "下单起始时间") @RequestParam(required = false) String createTimeEnd,
                                 @ApiParam(name = "status", value = "订单状态：order_status_obligation：未付款，" +
                                         "order_status_spend：已付款，" +
                                         "order_status_inprocess：办理中，" +
                                         "order_status_completed：已完结，" +
                                         "order_status_evaluate：已评价") @RequestParam(required = false) String status,
                                 @ApiParam(name = "type", value = "订单类型，order_type1：普通订单，" +
                                         "order_type2：智能注册订单") @RequestParam(required = false) String type
    ) {
        try {
            Map map = this.orderService.queryUserOrder(userId, pageIndex, pageNum, code, orderNo, serviceTypeLevel3Code, contactName, payWay, markName, createTimeStart, createTimeEnd, status, type);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/saveNormalOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存普通订单", notes = "查询用户订单", httpMethod = "POST")
    public Object saveNormalOrder(
            @ApiParam(name = "order", value = "必要订单信息：<br>userId:用户ID ,<br>serviceId：服务项ID," +
                    "<br>serviceAttrId:服务属性ID, <br>allNum：服务件数，allPrice:总价" +
                    "<br>contactName:联系人名,<br> contactPhone:联系人电话," +
                    "<br>contactEmail:联系人邮箱,<br>remark:备注", required = true) @RequestBody Order order) {
        try {
            Order orderSys = this.orderService.saveNormalOrder(order);
            return this.getSuccessResponse(orderSys.getOrderNo());
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/saveAutoOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存智能注册订单", notes = "保存智能注册订单", httpMethod = "POST")
    public Object saveAutoOrder(
            @ApiParam(name = "orderJson",
                    value = "必要订单信息：<br>userId:用户ID ,<br>serviceId：服务项ID," +
                            "<br>serviceAttrId:服务属性ID, <br>allNum：服务件数，allPrice:总价" +
                            "<br>contactName:联系人名,<br> contactPhone:联系人电话," +
                            "<br>contactEmail:联系人邮箱,<br>contactTelephone:座机电话,<br>remark:备注", required = true) @RequestParam(required = true) String orderJson,
            @ApiParam(name = "trademarkAndApplicantJson",
                    value = "商标和申请人信息：<br>type: 商标类型，trademark_type1:文字，trademark_type2:图形，trademark_type3:文字及图形," +
                            "<br>name：商标名称," +
                            "<br>exampleType:用户商标图样类型：example_type1:自动生成，example_type2:手动生成," +
                            "<br>exampleAddress:商标图样文件地址," +
                            "<br>classCreateWay:商标类别创建方式：class_create_way1：智能推荐，class_create_way2:自助选择," +
                            "<br>suggestedFirstLevel:智能推荐的一级的领域code或ID," +
                            "<br>suggestedSecondLevel:智能推荐的二级的领域code或ID," +
                            "<br>applicantType:申请人类型,applicant_type1：企业，applicant_type2：个体工商户," +
                            "<br>applicantName:申请人名字或企业名," +
                            "<br>applicantCardNo:申请人身份证号," +
                            "<br>businessLicenceArea:营业执照所在地区," +
                            "<br>postalcode:邮政编码," +
                            "<br>businessLicenceAddress:营业执照详情地址," +
                            "<br>applicantCardFile:身份证复印件," +
                            "<br>businessLicenceFile:营业执照," +
                            "<br>proxyFile:委托书," +
                            "<br>priorityFile:优先权证明" +
                            "", required = true) @RequestParam(required = true) String trademarkAndApplicantJson,
            @ApiParam(name = "trademarkClassListJson",
                    value = "商标分类信息：<br>[{classLevel1Code: 一级分类编码," +
                            "<br>classLevel1Name:一级分类名称" +
                            "<br>classLevel2Code：二级分类编码," +
                            "<br>classLevel2Name:二级分类名称" +
                            "<br>classLevel3Code:三级分类编码," +
                            "<br>classLevel3Name:三级分类编码}]" +
                            "", required = true) @RequestParam(required = true) String trademarkClassListJson) {
        try {
            Assert.hasText(orderJson, "订单信息不能为空！");
            Assert.notNull(trademarkAndApplicantJson, "商标和申请人信息不能为空！");
            Assert.hasText(trademarkClassListJson, "商标分类不能为空！");
            Gson gson = new Gson();
            Order order = gson.fromJson(orderJson, Order.class);
            TrademarkAndApplicant trademarkAndApplicant = gson.fromJson(trademarkAndApplicantJson, TrademarkAndApplicant.class);
            List<TrademarkClass> trademarkClassList = gson.fromJson(trademarkClassListJson, new TypeToken<List<TrademarkClass>>() {
            }.getType());

            Order orderSys = this.orderService.saveAutoOrder(order, trademarkAndApplicant, trademarkClassList);
            return this.getSuccessResponse(orderSys.getOrderNo());
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    @RequestMapping(value = "/queryUserOrderByNo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过编号查询订单", notes = "通过编号查询订单", httpMethod = "GET")
    public Object saveNormalOrder(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderNo", value = "订单编号", required = true) @RequestParam String orderNo) {
        try {
            Map map = this.orderService.queryUserOrderByNo(userId, orderNo);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/queryApplicant", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询申请人信息", notes = "查询申请人信息", httpMethod = "GET")
    public Object queryApplicant(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                 @ApiParam(name = "type", value = "申请类型(企业：applicant_type1 个体：applicant_type2)") @RequestParam(required = false) String type,
                                 @ApiParam(name = "applicantName", value = "公司名称/申请人名称") @RequestParam(required = false) String applicantName,
                                 @ApiParam(name = "查询数量", value = "查询数量,", required = false) @RequestParam(required = false) Integer limit) {
        try {
            limit = limit == null || limit < 1 ? 5 : limit;
            List<Map> map = this.orderService.queryApplicant(userId, type, applicantName, limit);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/getClassLevelPrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询商标大类和3级分类价格信息", notes = "查询商标大类和3级分类价格信息", httpMethod = "GET")
    public Object getClassLevelPrice(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {
        try {
            Map map = this.orderService.getClassLevelPrice(userId);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/cancelUserOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "取消订单", notes = "取消订单", httpMethod = "GET")
    public Object cancelUserOrder(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                  @ApiParam(name = "orderId", value = "订单Id", required = true) @RequestParam Long orderId) {
        try {
            this.orderService.cancelUserOrder(userId, orderId);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/commentUserOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "评价订单", notes = "评价订单", httpMethod = "POST")
    public Object cancelUserOrder(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                  @ApiParam(name = "orderId", value = "订单Id", required = true) @RequestParam Long orderId,
                                  @ApiParam(name = "level", value = " 1-5代表星级", required = true) @RequestParam Integer level,
                                  @ApiParam(name = "content", value = "评论文字,不超过300", required = true) @RequestParam String content) {
        try {
            this.orderService.commentUserOrder(userId, orderId, level, content);
            return this.getSuccessResponse("success!");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    @RequestMapping(value = "/queryUserOrderPayStatus", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询订单支付状态", notes = "查询订单支付状态", httpMethod = "GET")
    public Object queryUserOrderPayStatus(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                          @ApiParam(name = "orderId", value = "订单编号", required = true) @RequestParam Long orderId) {
        try {
            Map map = this.orderService.queryUserOrderPayStatus(userId, orderId);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }

    /**
     * 小程序—订单详情
     */
    @RequestMapping(value = "/queryOrderInfoByNo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "小程序—订单详情页面", notes = "小程序—订单详情页面", httpMethod = "GET")
    public Object queryOrderInfoByNo(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                                     @ApiParam(name = "orderNo", value = "订单编号", required = true) @RequestParam String orderNo) {
        try {
            Map map = this.orderService.queryOrderByNo(userId, orderNo);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }
}
