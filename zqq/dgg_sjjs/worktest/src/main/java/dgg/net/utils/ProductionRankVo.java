package dgg.net.utils;

/**
 * @author: lm
 * @Date: 2019/3/2 16:08
 * @Description:
 */

public class ProductionRankVo {

    private Integer rank;
    private String name;
    private double rateStr;

    private Long userId;
    private Double rateDouble;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRateStr() {
        return rateStr;
    }

    public void setRateStr(double rateStr) {
        this.rateStr = rateStr;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getRateDouble() {
        return rateDouble;
    }

    public void setRateDouble(Double rateDouble) {
        this.rateDouble = rateDouble;
    }
}
