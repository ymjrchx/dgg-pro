package net.dgg.gspt.dqc.params;

/**
 * @ClassName: ResourceParam
 * @Description: 资源生成器param
 * @Author: jiangsh
 * @Date: 2018/9/6 14:21
 */
public class ResourceParam {
    private String city;
    private String industry;

    private int pageSize;
    private int pageIndex;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
