package net.dgg.gspt.dqc.params;

/**
 * Created by jiangsh on 2018/7/16 15:47
 */
public class ResumeParam {

    private int pageIndex;
    private int pageSize;

    private String type; //类型
    private String province; //省份
    private String register; //注册情况

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
