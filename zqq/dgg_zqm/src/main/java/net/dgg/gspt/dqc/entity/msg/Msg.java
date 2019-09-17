package net.dgg.gspt.dqc.entity.msg;

import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <Msg>
 * @despriction 消息
 * @create 2018/09/27 11:10
 **/
public class Msg extends BaseEntity {

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 类别
     */
    private String type;

    /**
     *
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 0 未读 1 已读
     */
    private Integer read;

    /**
     * 0删除  1正常
     */
    private Integer flag;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
