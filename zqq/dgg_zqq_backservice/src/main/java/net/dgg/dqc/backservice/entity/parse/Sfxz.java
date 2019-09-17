package net.dgg.dqc.backservice.entity.parse;

/**
 * 司法协助
 * Created by jiangsh on 2018/8/17 10:12
 */
public class Sfxz {
    private String shZhiXing; //被执行人
    private String shEquityNum; //股权数额
    private String shNoticeNum; //执行通知文号
    private String shType; //类型状态

    public String getShZhiXing() {
        return shZhiXing;
    }

    public void setShZhiXing(String shZhiXing) {
        this.shZhiXing = shZhiXing;
    }

    public String getShEquityNum() {
        return shEquityNum;
    }

    public void setShEquityNum(String shEquityNum) {
        this.shEquityNum = shEquityNum;
    }

    public String getShNoticeNum() {
        return shNoticeNum;
    }

    public void setShNoticeNum(String shNoticeNum) {
        this.shNoticeNum = shNoticeNum;
    }

    public String getShType() {
        return shType;
    }

    public void setShType(String shType) {
        this.shType = shType;
    }
}
