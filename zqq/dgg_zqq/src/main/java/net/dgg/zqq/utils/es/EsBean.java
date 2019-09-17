package net.dgg.zqq.utils.es;

import java.util.Map;

/**
 * Created by wu on 2017-09-26.
 */
public class EsBean {

    /**
     * 模糊匹配,需要分词
     */
    private Map likeMap;

    /**
     * 完全匹配不分词
     */
    private Map termMap;

    /**
     * 范围内匹配,比如0-100,100-1000
     */
    private Map scopeMap;

    /**
     * 日期处理
     */
    private Map timeMap;


    public Map getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(Map likeMap) {
        this.likeMap = likeMap;
    }

    public Map getTermMap() {
        return termMap;
    }

    public void setTermMap(Map termMap) {
        this.termMap = termMap;
    }

    public Map getScopeMap() {
        return scopeMap;
    }

    public void setScopeMap(Map scopeMap) {
        this.scopeMap = scopeMap;
    }

    public Map getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(Map timeMap) {
        this.timeMap = timeMap;
    }
}
