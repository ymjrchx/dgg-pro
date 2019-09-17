package net.dgg.zqq.dto.brandsearch;

import net.dgg.zqq.framework.PTConst;

/**
 * @ClassName: ApproParam
 * @Description: 商标搜索-请求参数
 * @Author: jiangsh
 * @Date: 2018/9/26 14:51
 */
public class ApproParam {

    private String key; //搜索key，取值： 近似商标 nameJs; 精准商标 name; 申请号 regNo; 申请人 applicantCn; 商标/服务 tmGoodsService； 代理公司 tmSection;
    private String keyWord; //关键字（商标名）
    private String filterType; //过滤条件，取值： all 全选； jq 精确； bf 部分； jiaz 加字； jianz 减字； bz 变字； hx 换序； py 拼音； tszf 特殊字符; xjz 形近字；
    //传值多个值直接用“-”分隔

    private String goodsNum; //申请小类别，编辑大类下面的小类的数字号，取值：获取前面数字，如“0120”，多个用“-”分隔；

    private String applyType; //申请类别，传值示例“01类 化学原料-02类 颜料油漆”，多个值直接用“-”分隔；
    private String layStatus; //法律状态
    private String applyYear; //申请年份

    private String applyPerson; //申请人
    private String goods; //规范商品

    private int pageIndex; //开始页
    private int pageSize; //每页多少条

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getLayStatus() {
        return layStatus;
    }

    public void setLayStatus(String layStatus) {
        this.layStatus = layStatus;
    }

    public String getApplyYear() {
        return applyYear;
    }

    public void setApplyYear(String applyYear) {
        this.applyYear = applyYear;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getFilterType() {
        if (filterType == null)
            return PTConst.FT_ALL;
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
}
