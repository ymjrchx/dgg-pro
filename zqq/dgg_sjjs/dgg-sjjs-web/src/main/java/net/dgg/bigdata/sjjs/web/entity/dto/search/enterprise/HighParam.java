package net.dgg.bigdata.sjjs.web.entity.dto.search.enterprise;

/**
 * @ClassName: HighParam
 * @Description: TODO
 * @Author: jiangsh
 * @Date: 2019/1/21 10:52
 */
public class HighParam {
    private String keyWord;
    private int count;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getCount() {
        if (count <= 0) return 5;
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
