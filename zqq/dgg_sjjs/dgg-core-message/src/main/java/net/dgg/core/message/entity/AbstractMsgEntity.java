/**
 * FileName: AbstractMsgEntity
 * Author:   tumq
 * Date:     2018/12/13 9:15
 * Description: 抽象消息实体
 */
package net.dgg.core.message.entity;

import net.dgg.core.utils.common.DggKeyWorker;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈抽象消息实体〉
 *
 * @author tumq
 * @create 2018/12/13
 */
public class AbstractMsgEntity implements IMsgEntity{

    private String content;//内容
    private Long msgId;  //消息ID
    private String sendName; //消息发送者
    private Date sendDate;//发送时间

    public AbstractMsgEntity(){
        msgId = DggKeyWorker.nextId();
    }


    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Long getMsgId() {
        return msgId;
    }

    @Override
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    @Override
    public String getSendName() {
        return sendName;
    }

    @Override
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    @Override
    public Date getSendDate() {
        return sendDate;
    }

    @Override
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}