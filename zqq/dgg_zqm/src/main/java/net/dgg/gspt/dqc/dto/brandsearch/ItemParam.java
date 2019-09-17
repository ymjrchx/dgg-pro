package net.dgg.gspt.dqc.dto.brandsearch;

/**
 * 相关选项请参数封装
 * Created by jiangsh on 2018/8/21 11:32
 */
public class ItemParam {

    private String keyWord;
    private String type;

    private int pageIndex;
    private int pageSize;


    public String getType() {
        if (type == null) type = "tax";
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
