package net.dgg.gspt.dqc.entity;


/**
 * @ClassName: CpwsAgg
 * @Description: 裁判文书-类型 数量汇总
 * @Author: jiangsh
 * @Date: 2018/10/8 10:28
 */
public class CpwsAgg {
    private String name; //名称
    private String type; //类型
    private long count; //数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
