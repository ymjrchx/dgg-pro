package net.dgg.gspt.dqc.entity.business.search;

/**
 * @ClassName: Measure
 * @Description: Measure响应值
 * @Author: jiangsh
 * @Date: 2018/10/10 19:27
 */
public class Measure {
    private float score; //分数
    private String cause; //原因

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Measure() {
    }

    public Measure(float score, String cause) {
        this.score = score;
        this.cause = cause;
    }
}
