package net.dgg.bigdata.manager.common.util;

import net.dgg.core.utils.bean.DggRestResponse;

import java.util.List;

/**
 * Layui 表格返回数据结构体
 */
public class LayuiTableResponse extends DggRestResponse {
    

    private Object count;
    
    public LayuiTableResponse data(Object count,List<?> data) {
        this.count = count;
        this.setData(data);
        return this;
    }
    
    public LayuiTableResponse data(List<?> data) {
        this.count = 0;
        this.setData(data);
        return this;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }
    

}
