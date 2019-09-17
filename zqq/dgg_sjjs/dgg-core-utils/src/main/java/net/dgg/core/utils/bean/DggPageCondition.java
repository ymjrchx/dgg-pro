package net.dgg.core.utils.bean;

/**
 * Desc:   分页查询条件实体
 * Author: Li Xingjiang
 * Date:   2018/9/11 16:17
 * Version: 1.0
 **/
public class DggPageCondition {

    /**
     * 页数
     */
    protected Integer page;
    /**
     * 分页条数
     */
    protected Integer limit;
    /**
     * 开始记录数
     */
    protected Integer start;

    public DggPageCondition() {
    }

    public DggPageCondition(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    /**
     * 计算分页开始数
     *
     * @return
     */
    public Integer calculationStart() {
        int startNum = 0;
        if (page != 0) {
            startNum = (page - 1) * limit;
        }
        return startNum;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        if (page == 0) {
            start = 0;
        } else {
            start = (page - 1) * limit;
        }
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }


}
