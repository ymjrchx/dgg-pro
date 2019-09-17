package net.dgg.dqc.backservice.entity.parse;

/**
 * 作品著作权
 * Created by jiangsh on 2018/8/20 14:38
 */
public class Zpzzq {
    private String pwName; //作品名称
    private String pwregisterNum; //登记号
    private String pwCategory; //类别
    private String pwFinishDate; //创作完成日期
    private String pwPublishDate; //次发表日期
    private String pwRegisterDate; //登记日期
    private String pwauthor; //著作人

    public String getPwName() {
        return pwName;
    }

    public void setPwName(String pwName) {
        this.pwName = pwName;
    }

    public String getPwregisterNum() {
        return pwregisterNum;
    }

    public void setPwregisterNum(String pwregisterNum) {
        this.pwregisterNum = pwregisterNum;
    }

    public String getPwCategory() {
        return pwCategory;
    }

    public void setPwCategory(String pwCategory) {
        this.pwCategory = pwCategory;
    }

    public String getPwFinishDate() {
        return pwFinishDate;
    }

    public void setPwFinishDate(String pwFinishDate) {
        this.pwFinishDate = pwFinishDate;
    }

    public String getPwPublishDate() {
        return pwPublishDate;
    }

    public void setPwPublishDate(String pwPublishDate) {
        this.pwPublishDate = pwPublishDate;
    }

    public String getPwRegisterDate() {
        return pwRegisterDate;
    }

    public void setPwRegisterDate(String pwRegisterDate) {
        this.pwRegisterDate = pwRegisterDate;
    }

    public String getPwauthor() {
        return pwauthor;
    }

    public void setPwauthor(String pwauthor) {
        this.pwauthor = pwauthor;
    }
}
