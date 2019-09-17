package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse.qyfj;

/**
 * 企业附加信息-企业新闻信息
 * Created by jiangsh on 2018/6/5 10:40
 */
public class NewsInfo {
    private String KeyNo;
    private String ImageUrl;
    private String Title;
    private String Source;
    private String PublishTime;
    private String Description;
    private String Url;

    public String getKeyNo() {
        return KeyNo;
    }

    public void setKeyNo(String keyNo) {
        KeyNo = keyNo;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
