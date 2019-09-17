package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <CommentRecord>
 * @despriction 用户订单评论信息
 * @create 2018/09/27 10:17
 **/
public class CommentRecord extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 服务所属一级级类别code
     */
    private String serviceTypeLevel1Code;
    /**
     * 服务所属三级类别code
     */
    private String serviceTypeLevel3Code;

    /**
     * 1-5代表星级
     */
    private Integer level;

    /**
     * 评论文字
     */
    private String content;

    /**
     * 是否有效 记入统计  0 否  1是
     */
    private Integer flag;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceTypeLevel1Code() {
        return serviceTypeLevel1Code;
    }

    public void setServiceTypeLevel1Code(String serviceTypeLevel1Code) {
        this.serviceTypeLevel1Code = serviceTypeLevel1Code;
    }

    public String getServiceTypeLevel3Code() {
        return serviceTypeLevel3Code;
    }

    public void setServiceTypeLevel3Code(String serviceTypeLevel3Code) {
        this.serviceTypeLevel3Code = serviceTypeLevel3Code;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
