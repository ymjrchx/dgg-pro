package net.dgg.gspt.dqc.entity.operationRecord;

import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * @ClassName: OperationRecord
 * @Description: TODO
 * @Author: huangL
 * @Date: 2019/1/3 9:41
 */
public class OperationRecord extends BaseEntity {

    private String userId;

    private String operationCode;

    private String content;

    private int flag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
