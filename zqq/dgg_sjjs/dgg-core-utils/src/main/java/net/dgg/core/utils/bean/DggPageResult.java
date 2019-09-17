package net.dgg.core.utils.bean;

import java.util.List;

/**
 * Desc:   分页返回结果
 * Author: Li Xingjiang
 * Date:   2018/9/11 16:18
 * Version: 1.0
 **/
public class DggPageResult<T> {

    /**
     * 总记录数
     */
    private long total;
    /**
     * 分页数据
     */
    private List<T> tList;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> gettList() {
        return tList;
    }

    public void settList(List<T> tList) {
        this.tList = tList;
    }
}
