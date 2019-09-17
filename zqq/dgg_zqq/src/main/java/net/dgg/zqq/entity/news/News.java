package net.dgg.zqq.entity.news;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <News>
 * @despriction 资讯
 * @create 2018/09/27 11:05
 **/
public class News extends BaseEntity {
    /**
     * 编号
     */
    private Long number;

    /**
     * 服务大类
     */
    private String typeLevel1Name;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String label;

    /**
     * 来源
     */
    private String origin;
    /**
     * 文本内容
     */
    private String newsPhoto;
    /**
     * 文本内容
     */
    private String content;

    /**
     * 浏览次数
     */
    private Integer viewTimes;
    /**
     * 点赞次数
     */
    private Integer praiseTimes;

    /**
     * 分类
     */
    private String type;

    /**
     * 禁用 启用
     */
    private String status;

    /**
     * 0删除  1正常
     */
    private Integer flag;


//    public String getTypeLevel1Code() {
//        return typeLevel1Code;
//    }
//
//    public void setTypeLevel1Code(String typeLevel1Code) {
//        this.typeLevel1Code = typeLevel1Code;
//    }

    public String getTypeLevel1Name() {
        return typeLevel1Name;
    }

    public void setTypeLevel1Name(String typeLevel1Name) {
        this.typeLevel1Name = typeLevel1Name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getNewsPhoto() {
        return newsPhoto;
    }

    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    public Integer getPraiseTimes() {
        return praiseTimes;
    }

    public void setPraiseTimes(Integer praiseTimes) {
        this.praiseTimes = praiseTimes;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
