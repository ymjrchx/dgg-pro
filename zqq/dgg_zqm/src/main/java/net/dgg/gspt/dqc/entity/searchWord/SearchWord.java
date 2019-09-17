package net.dgg.gspt.dqc.entity.searchWord;


import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * @ClassName:
 * @Description:
 * @Author: huangl
 * @Date: 2018/11/27 11:05
 */
public class SearchWord extends BaseEntity {

    /**
     * 搜索词
     */
    private String word;
    /**
     * 次数
     */
    private Integer time;

    /**
     * 搜索时 ip地址
     */
    private String ip;
    /**
     * 是否展示 0不显示  1显示
     */
    private Integer flag;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
