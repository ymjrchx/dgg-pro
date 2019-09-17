package net.dgg.gspt.dqc.entity.business.search;

/**
 * @ClassName: BrandHealth
 * @Description: 商标健康检查
 * @Author: jiangsh
 * @Date: 2018/11/2 10:06
 */
public class BrandHealth {
    private String regNo; //注册号
    private String type; //商标类别
    private String result; //结果

    public BrandHealth() {
    }

    public BrandHealth(String regNo, String type, String result) {
        this.regNo = regNo;
        this.type = type;
        this.result = result;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
