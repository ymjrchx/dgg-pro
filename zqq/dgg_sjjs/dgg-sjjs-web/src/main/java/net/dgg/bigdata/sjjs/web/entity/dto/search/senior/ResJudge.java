package net.dgg.bigdata.sjjs.web.entity.dto.search.senior;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @ClassName: ResJudge
 * @Description: TODO
 * @Author: jiangsh
 * @Date: 2018/12/14 16:56
 */
public class ResJudge {
    private BoolQueryBuilder boolQueryBuilder;
    private boolean flag = true;

    public BoolQueryBuilder getBoolQueryBuilder() {
        return boolQueryBuilder;
    }

    public void setBoolQueryBuilder(BoolQueryBuilder boolQueryBuilder) {
        this.boolQueryBuilder = boolQueryBuilder;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
