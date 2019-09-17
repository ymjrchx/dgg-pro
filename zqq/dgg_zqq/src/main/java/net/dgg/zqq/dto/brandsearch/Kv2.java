package net.dgg.zqq.dto.brandsearch;

/**
 * @ClassName: Kv
 * @Description: KV键值
 * @Author: jiangsh
 * @Date: 2018/10/10 19:27
 */
public class Kv2 {
    private String key;
    private int val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Kv2() {
    }

    public Kv2(String key, int val) {
        this.key = key;
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
