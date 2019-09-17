package net.dgg.zqq.entity.payment;

import lombok.*;

import java.util.Date;

/**
 * Created by 李程 on 2018/10/9.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrder {

    //ID，唯一标识
    private String paymentId;

    //业务编号
    private String businessNo;

    //业务类型
    private String businessType;

    //支付订单号
    private String tradeNo;

    //支付类型
    //wechat-微信支付
    //alipay-支付宝
    private String payType;

    //支付金额
    private Double fee;

    //支付状态
    //NEW新建
    //SUCCESS成功
    //失败FAILURE
    private String tradeStatus;

    //创建人
    private String creatorId;

    //创建时间
    private Date createTime;

    //最后更新人
    private String updatorId;

    //最后更新时间
    private Date updateTime;

}
