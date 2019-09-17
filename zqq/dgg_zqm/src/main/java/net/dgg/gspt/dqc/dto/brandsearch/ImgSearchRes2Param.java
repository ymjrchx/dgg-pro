package net.dgg.gspt.dqc.dto.brandsearch;

import java.util.List;

/**
 * @ClassName: ImgSearchResParam
 * @Description: 相似图搜索-响应
 * @Author: jiangsh
 * @Date: 2018/10/9 19:23
 */
public class ImgSearchRes2Param {

    private List<Result> result; //结果集

    private String logId; //唯一的log id，用于问题定位
    private boolean hasMore; //是否还有下一页，返回值：true、false；如果不分页，不用关注该字段
    private long resultNum; //结果数

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public long getResultNum() {
        return resultNum;
    }

    public void setResultNum(long resultNum) {
        this.resultNum = resultNum;
    }

    public static class Result {
        private Brief brief; //备注信息
        private String score; //图片相关性，0-1
        private String contSign; //图片签名，可以用来删除图片或定位问题

        public Brief getBrief() {
            return brief;
        }

        public void setBrief(Brief brief) {
            this.brief = brief;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getContSign() {
            return contSign;
        }

        public void setContSign(String contSign) {
            this.contSign = contSign;
        }
    }

    public static class Brief {
        private String esId;
        private String imgUrl;

        public String getEsId() {
            return esId;
        }

        public void setEsId(String esId) {
            this.esId = esId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
