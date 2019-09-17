package net.dgg.zqq.controller.payment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dgg.zqq.services.payment.PaymentOrderService;
import net.fblock.web.common.BaseController;
import net.fblock.web.common.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 李程 on 2018/10/9.
 */
@Api(value = "支付", description = "发起支付")
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Autowired
    PaymentOrderService paymentOrderService;

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ApiOperation(value = "创建支付订单", notes = "创建支付订单接口，参数：支付类型、业务订单号、业务类型、订单金额、标题", httpMethod = "POST", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public RestResponse launchPayment(
            @ApiParam(name = "payType", value = "支付方式：wechat-微信扫码支付，alipay-支付宝扫码支付，js-小程序", required = true) @RequestParam(name = "payType") String payType,
            @ApiParam(name = "businessNo", value = "业务订单号", required = true) @RequestParam(name = "businessNo") String businessNo,
            @ApiParam(name = "businessType", value = "业务类型", required = true) @RequestParam(name = "businessType") String businessType,
            @ApiParam(name = "businessBody", value = "业务说明", required = true) @RequestParam(name = "businessBody") String businessBody,
            @ApiParam(name = "fee", value = "交易金额", required = true) @RequestParam(name = "fee") String fee,
            @ApiParam(name = "sign", value = "签名", required = true) @RequestParam(name = "sign") String sign,
            @ApiParam(name = "openid", value = "用户id") @RequestParam(name = "openid", required = false) String openid) {
        if (StringUtils.isEmpty(businessNo)) {
            return this.getFailResponse("业务编号不能为空!");
        } else if (StringUtils.isEmpty(businessType)) {
            return this.getFailResponse("业务类型不能为空!");
        } else if (StringUtils.isEmpty(businessBody)) {
            return this.getFailResponse("业务标题不能为空");
        } else if (StringUtils.isEmpty(fee) || !fee.matches("\\d+(\\.\\d+)?")) {
            return this.getFailResponse("订单金额不能为空，且只能为数字");
        } else if (StringUtils.isEmpty(payType)) {
            return this.getFailResponse("支付类型不能为空");
        }
        String realSign = paymentOrderService.generateSign(businessNo, businessType, fee, businessBody);
        if (!realSign.equals(sign)) {
            return this.getFailResponse("签名验证失败，请不要修改订单金额、业务类型、业务号、业务标题");
        } else {
            Map<String, Object> payment = paymentOrderService.generateTrade(businessNo, businessType, payType, fee, businessBody, openid);
            if (payment != null) {
                return this.getSuccessResponse(payment);
            } else {
                return this.getFailResponse("创建支付订单失败!");
            }
        }
    }

}
