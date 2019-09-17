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
import net.fblock.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderController3
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/16 10:21
 */

@Controller
@RequestMapping("/order")
@Api(description = "智能订单")
public class OrderController3 extends BaseController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/updateAutoOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存智能注册订单", notes = "保存智能注册订单", httpMethod = "POST")
    public Object updateAutoOrder(
            @ApiParam(name = "orderJson",
                    value = "必要订单信息：<br>id:订单ID ,<br>userId:用户ID ,<br>serviceId：服务项ID," +
                            "<br>serviceAttrId:服务属性ID, <br>allNum：服务件数，allPrice:总价" +
                            "<br>contactName:联系人名,<br> contactPhone:联系人电话," +
                            "<br>contactEmail:联系人邮箱,<br>contactTelephone:座机电话,<br>remark:备注" + "" +
                            "<br>auditType:订单审核类型,oder_audit_type_01:免审核,oder_audit_type_02:待审,oder_audit_type_03:快速审核，oder_audit_type_04：只保存" +
                            "", required = true) @RequestParam(required = true) String orderJson,
            @ApiParam(name = "trademarkAndApplicantJson",
                    value = "商标和申请人信息：<br>type: 商标类型，trademark_type1:文字，trademark_type2:图形，trademark_type3:文字及图形,trademark_type4:黑白,trademark_type5:彩色，" +
                            "<br>name：商标名称," +
                            "<br>explain:商标说明," +
                            "<br>registerNo:注册号或申请号" +
                            "<br>exampleType:用户商标图样类型：example_type1:自动生成，example_type2:手动生成," +
                            "<br>exampleAddress:商标图样文件地址," +
                            "<br>classCreateWay:商标类别创建方式：class_create_way1：智能推荐，class_create_way2:自助选择," +
                            "<br>suggestedFirstLevel:智能推荐的一级的领域code或ID," +
                            "<br>suggestedSecondLevel:智能推荐的二级的领域code或ID," +
                            "<br>applicantType:申请人类型,applicant_type1：企业，applicant_type2：个体工商户," +
                            "<br>applicantUserName:申请人名字," +
                            "<br>applicantName:申请人名字或企业名," +
                            "<br>applicantCardNo:申请人身份证号," +
                            "<br>businessLicenceArea:营业执照所在地区," +
                            "<br>postalcode:邮政编码," +
                            "<br>businessLicenceAddress:营业执照详情地址," +
                            "<br>applicantCardFile:身份证复印件," +
                            "<br>businessLicenceFile:营业执照," +
                            "<br>proxyFile:委托书," +
                            "<br>priorityFile:优先权证明," +
                            "<br>templateId:模板ID," +
                            "<br>applicantCardAddress:身份证地址", required = true) @RequestParam(required = true) String trademarkAndApplicantJson,
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
            Assert.hasText(trademarkAndApplicantJson, "商标和申请人信息不能为空！");
            Assert.hasText(trademarkClassListJson, "商标分类不能为空！");
            Gson gson = new Gson();
            Order order = gson.fromJson(orderJson, Order.class);
            TrademarkAndApplicant trademarkAndApplicant = gson.fromJson(trademarkAndApplicantJson, TrademarkAndApplicant.class);
            List<TrademarkClass> trademarkClassList = gson.fromJson(trademarkClassListJson, new TypeToken<List<TrademarkClass>>() {
            }.getType());

            Order orderSys = this.orderService.updateAutoOrder(order, trademarkAndApplicant, trademarkClassList);
            return this.getSuccessResponse(orderSys.getOrderNo());
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getFailResponse("系统异常！");
        }
    }


    /**
     * 智能订单查看详情
     *
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/findOrderById", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单详情", notes = "订单详情", httpMethod = "GET")
    public Object orderInfo(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam Long orderId) {
        try {
            Map map = this.orderService.findOrderById(userId, orderId);
            return this.getSuccessResponse(map);
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            return this.getFailResponse("系统异常");
        }
    }

    /**
     * 修改订单状态为待审
     *
     * @param userId
     * @param orderId
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "订单详情", notes = "订单详情", httpMethod = "GET")
    public Object changeStatus(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
                               @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam Long orderId
    ) {
        try {
            this.orderService.changeStatus(userId, orderId);
            return this.getSuccessResponse("修改状态成功");
        } catch (IllegalArgumentException e) {
            return this.getFailResponse(e.getMessage());
        } catch (Exception e) {
            return this.getFailResponse("系统异常");
        }
    }




}