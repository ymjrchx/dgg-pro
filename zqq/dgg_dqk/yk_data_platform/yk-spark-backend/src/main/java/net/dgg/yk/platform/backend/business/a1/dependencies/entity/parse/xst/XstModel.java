package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.xst;

/**
 * @ClassName: XstModel
 * @Description: 相似图条件存储
 * @Author: jiangsh
 * @Date: 2018/9/19 16:07
 */
public class XstModel {
    private String type; //类别
    private String status; //状态
    private String appPerson; //申请人
    private String appNo; //申请号
    private String imgName; //图片名称
    private String imgUrl; //图片地址

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppPerson() {
        return appPerson;
    }

    public void setAppPerson(String appPerson) {
        this.appPerson = appPerson;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    @Override
    public String toString() {
        return "XstModel{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", appPerson='" + appPerson + '\'' +
                ", appNo='" + appNo + '\'' +
                '}';
    }
}
