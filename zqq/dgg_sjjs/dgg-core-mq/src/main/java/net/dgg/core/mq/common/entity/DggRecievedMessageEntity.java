package net.dgg.core.mq.common.entity;

import net.dgg.core.mq.common.bean.DggRecievedMessageKeyEntity;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 接收到的消息记录
 *
 * @author nature
 * @create 2018-01-17 9:23
 */
public class DggRecievedMessageEntity {

    public static final String COLLECTION_NAME = "recieved_message";

    /**
     * 消息ID
     */
    @Id
    private DggRecievedMessageKeyEntity recievedMessageRecordKey;

    /**
     * 处理状态，未处理为0；已处理为1
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    public DggRecievedMessageKeyEntity getRecievedMessageRecordKey() {
        return recievedMessageRecordKey;
    }

    public void setRecievedMessageRecordKey(DggRecievedMessageKeyEntity recievedMessageRecordKey) {
        this.recievedMessageRecordKey = recievedMessageRecordKey;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
