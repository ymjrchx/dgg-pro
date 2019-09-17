package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * Created by jiangsh on 2018/7/24 11:55
 */
public class Bzxr {
    private String no; //案号
    private String time; //立案时间
    private String court; //法院
    private String num; //执行标的

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
