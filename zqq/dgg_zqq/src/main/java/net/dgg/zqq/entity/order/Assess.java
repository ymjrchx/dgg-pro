package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/14 17:13
 */
public class Assess extends BaseEntity {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 审查文字
     */
    private String content;

    /**
     * 审查图片
     */
    private String photo;

    /**
     * 评估结果
     */
    private String result;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
