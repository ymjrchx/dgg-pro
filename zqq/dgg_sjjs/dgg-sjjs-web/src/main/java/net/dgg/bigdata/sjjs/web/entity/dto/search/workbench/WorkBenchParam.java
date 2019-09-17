package net.dgg.bigdata.sjjs.web.entity.dto.search.workbench;

/**
 * @ClassName: WorkBenchParam
 * @Description: 工作台基本搜索参数
 * @Author: jiangsh
 * @Date: 2018/12/17 15:19
 */
public class WorkBenchParam {

    private String key; //搜索key，传值：按人员 emp， 按部门 dept
    private String val; //对应的val值,userId的值
    private String startDate; //开始日期,格式： 2015-01-01
    private String endDate; //结束日期

    private String status; //线索状态，传“已成交” 或者 “有意向”

    private int pageIndex;
    private int pageSize;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
