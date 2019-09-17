/**
 * FileName: IMsgEntity
 * Author:   tumq
 * Date:     2018/9/19 10:31
 * Description: 消息实体
 */
package net.dgg.core.message.entity;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈消息实体〉
 *
 * @author tumq
 * @create 2018/9/19
 */
public interface IMsgEntity {

    public void setMsgId(Long msgId);

    public Long getMsgId();

    public String getContent();

    public void setContent(String content);

    public String getSendName();

    public void setSendName(String sender);

    public Date getSendDate();

    public void setSendDate(Date sendDate);
}
