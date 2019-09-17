package net.dgg.gspt.dqc.params;

/**
 * 新闻param
 * Created by jiangsh on 2018/8/15 09:43
 */
public class NewsParam {
    private String type; //类别
    private int pageIndex;
    private int pageSize;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
