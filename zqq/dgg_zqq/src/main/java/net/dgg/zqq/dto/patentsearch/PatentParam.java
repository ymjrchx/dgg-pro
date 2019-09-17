package net.dgg.zqq.dto.patentsearch;

/**
 * @ClassName: PatentParam
 * @Description: 专利搜索-请求参数封装
 * @Author: jiangsh
 * @Date: 2018/9/29 10:56
 */
public class PatentParam {

    private String type; //类别，发明公开、发明授权、发明、实用新型、外观设计； 若多个，之间用“-”隔开；

    private String keyWord; //关键字，若多个，之间用“、”隔开；

    private String dateType; //日期类型, 传值情况：  申请日：applicationDate， 公开/公告日：publicationDate， 优先权日：piApplyAnnounceDate
    private String dataStart; //日期起
    private String dataEnd; //日期止

    private String layStatus; //法律状态，传值情况：有权; 无权; 在审， 默认所有，传 “有权-无权-在审”

    private String applyPerson; //发明人
    private String applyDate; //申请日
//    private String typeNum; //分类号

    private int pageIndex;
    private int pageSize;

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

//    public String getTypeNum() {
//        return typeNum;
//    }
//
//    public void setTypeNum(String typeNum) {
//        this.typeNum = typeNum;
//    }

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

    public String getType() {
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

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }

    public String getLayStatus() {
        return layStatus;
    }

    public void setLayStatus(String layStatus) {
        this.layStatus = layStatus;
    }
}
