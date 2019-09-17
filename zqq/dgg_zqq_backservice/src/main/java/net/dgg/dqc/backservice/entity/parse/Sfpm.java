package net.dgg.dqc.backservice.entity.parse;

/**
 * 司法拍卖
 * Created by jiangsh on 2018/8/17 11:46
 */
public class Sfpm {
    private String aName; //名称（标题）
    private String aPrice; //起拍价
    private String aTime; //拍卖时间
    private String aCourt; //委托法院
    private String acHtml; //司法详情Html(保持样式)

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaPrice() {
        return aPrice;
    }

    public void setaPrice(String aPrice) {
        this.aPrice = aPrice;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }

    public String getaCourt() {
        return aCourt;
    }

    public void setaCourt(String aCourt) {
        this.aCourt = aCourt;
    }

    public String getAcHtml() {
        return acHtml;
    }

    public void setAcHtml(String acHtml) {
        this.acHtml = acHtml;
    }
}
