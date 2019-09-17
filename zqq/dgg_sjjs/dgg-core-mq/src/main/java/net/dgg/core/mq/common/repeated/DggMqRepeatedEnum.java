package net.dgg.core.mq.common.repeated;

/**
 * Desc:   去重判断枚举
 * Author: Li Xingjiang
 * Date:   2018/9/12 10:54
 * Version: 1.0
 **/
public enum DggMqRepeatedEnum {

    DGG_MQ_REPEATED_REDIS(1), DGG_MQ_REPEATED_MONGO(2), DGG_MQ_REPEATED_NO(3);

    private int val;

    DggMqRepeatedEnum(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
