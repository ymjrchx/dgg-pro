package net.dgg.dqc.backservice.entity.parse;

/**
 * 新闻model
 * Created by jiangsh on 2018/8/15 09:30
 */
public class NewsModel {

    private String title; //标题
    private String pubdate; //发布时间
    private String articleSub; //原创
    private String content; //内容
    private String newsType; //类别
    private String titleImg; //图片url
    private String part; //部分content

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getArticleSub() {
        return articleSub;
    }

    public void setArticleSub(String articleSub) {
        this.articleSub = articleSub;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
