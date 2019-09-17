package net.dgg.zqq.entity.news;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @ClassName: NewsLabel
 * @Description: TODO
 * @Author: zxc
 * @Date: 2018/11/9 11:29
 */
public class NewsLabel extends BaseEntity {

    private Long newsId;

    private String newsLabel;

    public NewsLabel() {
    }

    public NewsLabel(Long newsId, String newsLabel) {
        this.newsId = newsId;
        this.newsLabel = newsLabel;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsLabel() {
        return newsLabel;
    }

    public void setNewsLabel(String newsLabel) {
        this.newsLabel = newsLabel;
    }
}