package net.dgg.gspt.dqc.dto.brandsearch;

import net.dgg.gspt.dqc.framework.PTConst;

/**
 * @ClassName: ImgSearchReqParam
 * @Description: 相似图片检索-请求
 * @Author: jiangsh
 * @Date: 2018/10/9 19:59
 */
public class ImgSearchReqParam {
    //官方参数，數據字典接口必传
    private String url; //图片url
    private String pn; //开始条数
    private String rn; //结束条数

    //列表接口相关参数，列表接口按条件传
    private String applyType; //申请类别，传值示例“01类 化学原料-02类 颜料油漆”，多个值直接用“-”分隔；
    private String applyYear; //申请年份
    private String layStatus; //法律状态

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyYear() {
        return applyYear;
    }

    public void setApplyYear(String applyYear) {
        this.applyYear = applyYear;
    }

    public String getLayStatus() {
        return layStatus;
    }

    public void setLayStatus(String layStatus) {
        this.layStatus = layStatus;
    }

    public String getPn() {
        if (pn == null) return "0";
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getRn() {
        if (rn == null) return PTConst.BAIDU_RN;
        if (Integer.valueOf(rn) > 1000) return PTConst.BAIDU_RN;
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
