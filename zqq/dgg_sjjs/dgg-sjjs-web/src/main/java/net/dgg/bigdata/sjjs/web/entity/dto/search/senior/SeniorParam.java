package net.dgg.bigdata.sjjs.web.entity.dto.search.senior;

import java.util.List;

/**
 * @ClassName: SeniorParam
 * @Description: 高级搜索请求参数
 * @Author: jiangsh
 * @Date: 2018/12/12 11:11
 */
public class SeniorParam {

    private String judge; //判断值，所有 must; 任意 should;
    private List<SO> param; //参数集
    private List<Next> nexts; //多层级

    private int pageIndex;
    private int pageSize;

    public static class Next {
        private String nJudge; // //判断值，所有 all; 任意 any;
        private List<SO> nParam; //参数集

        public String getnJudge() {
            return nJudge;
        }

        public void setnJudge(String nJudge) {
            this.nJudge = nJudge;
        }

        public List<SO> getnParam() {
            return nParam;
        }

        public void setnParam(List<SO> nParam) {
            this.nParam = nParam;
        }
    }

    public static class SO {
        private String one;
        private String two;
        private String three;

        public String getOne() {
            return one;
        }

        public void setOne(String one) {
            this.one = one;
        }

        public String getTwo() {
            return two;
        }

        public void setTwo(String two) {
            this.two = two;
        }

        public String getThree() {
            return three;
        }

        public void setThree(String three) {
            this.three = three;
        }
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

    public List<Next> getNexts() {
        return nexts;
    }

    public void setNexts(List<Next> nexts) {
        this.nexts = nexts;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public List<SO> getParam() {
        return param;
    }

    public void setParam(List<SO> param) {
        this.param = param;
    }
}
