package net.dgg.bigdata.common.constant;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/26 11:59
 * @Description:
 */
public enum ConditionEnum {
    NO_CONFIRM("NO_CONFIRM", "未确认"),
    HAS_INTENT("HAS_INTENT", "有意向"),
    NO_INTENT("NO_INTENT", "无意向"),
    HAS_DEAL("HAS_DEAL", "已成交"),
    NO_DEAL("NO_DEAL", "未成交"),
    CLUE_ERROR("ERROR","线索错误"),

    //客户类型
    CUSTOMER_COMPANY("company","公司"),
    CUSTOMER_PERSON("person","个人"),

    ;

    private String key;

    private String value;

    ConditionEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String forKeyToValue(String key) {
        for (ConditionEnum cEnum : ConditionEnum.values()) {
            if (cEnum.getKey().equals(key)) {
                return cEnum.getValue();
            }
        }
        return null;
    }

    public static String forValueTokey(String value) {
        for (ConditionEnum cEnum : ConditionEnum.values()) {
            if (cEnum.getValue().equals(value)) {
                return cEnum.getKey();
            }
        }
        return null;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
