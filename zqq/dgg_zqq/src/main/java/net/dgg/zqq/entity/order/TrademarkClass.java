package net.dgg.zqq.entity.order;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <TrademarkClass>
 * @despriction 商标分类
 * @create 2018/09/27 9:54
 **/
public class TrademarkClass extends BaseEntity {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商标ID
     */
    private Long trademarkId;

    /**
     * 一级分类ID
     */
    private String classLevel1Id;
    /**
     * 一级分类编码
     */
    private String classLevel1Code;

    /**
     * 一级分类名称
     */
    private String classLevel1Name;

    /**
     * 二级分类编码
     */
    private String classLevel2Code;

    /**
     * 二级分类名称
     */
    private String classLevel2Name;

    /**
     * 三级分类ID
     */
    private String classLevel3Id;

    /**
     * 三级分类编码
     */
    private String classLevel3Code;

    /**
     * 三级分类名称
     */
    private String classLevel3Name;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(Long trademarkId) {
        this.trademarkId = trademarkId;
    }

    public String getClassLevel1Code() {
        return classLevel1Code;
    }

    public void setClassLevel1Code(String classLevel1Code) {
        this.classLevel1Code = classLevel1Code;
    }

    public String getClassLevel1Name() {
        return classLevel1Name;
    }

    public void setClassLevel1Name(String classLevel1Name) {
        this.classLevel1Name = classLevel1Name;
    }

    public String getClassLevel2Code() {
        return classLevel2Code;
    }

    public void setClassLevel2Code(String classLevel2Code) {
        this.classLevel2Code = classLevel2Code;
    }

    public String getClassLevel2Name() {
        return classLevel2Name;
    }

    public void setClassLevel2Name(String classLevel2Name) {
        this.classLevel2Name = classLevel2Name;
    }

    public String getClassLevel3Code() {
        return classLevel3Code;
    }

    public void setClassLevel3Code(String classLevel3Code) {
        this.classLevel3Code = classLevel3Code;
    }

    public String getClassLevel3Name() {
        return classLevel3Name;
    }

    public void setClassLevel3Name(String classLevel3Name) {
        this.classLevel3Name = classLevel3Name;
    }

    public String getClassLevel1Id() {
        return classLevel1Id;
    }

    public void setClassLevel1Id(String classLevel1Id) {
        this.classLevel1Id = classLevel1Id;
    }

    public String getClassLevel3Id() {
        return classLevel3Id;
    }

    public void setClassLevel3Id(String classLevel3Id) {
        this.classLevel3Id = classLevel3Id;
    }
}
