package net.dgg.dqc.backservice.entity.parse.qynb;

import java.util.Date;

/**
 * 企业年报列表（概况）
 * Created by jiangsh on 2018/6/4 16:30
 */
public class Qynb {
    private Integer No;
    private String Year;
    private String Remarks;
    private Boolean HasDetailInfo;
    private Date PublishDate;

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public Boolean getHasDetailInfo() {
        return HasDetailInfo;
    }

    public void setHasDetailInfo(Boolean hasDetailInfo) {
        HasDetailInfo = hasDetailInfo;
    }

    public Date getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(Date publishDate) {
        PublishDate = publishDate;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

}
