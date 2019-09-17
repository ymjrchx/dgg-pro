package net.dgg.gspt.dqc.dto.brandsearch;

/**
 * @ClassName: Kv
 * @Description: KV键值
 * @Author: jiangsh
 * @Date: 2018/10/10 19:27
 */
public class Kv {
    private String key;
    private Object val;
    private String code;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Kv() {
    }

    public Kv(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
