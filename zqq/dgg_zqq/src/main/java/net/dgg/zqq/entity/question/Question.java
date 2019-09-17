package net.dgg.zqq.entity.question;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <Question>
 * @despriction 问题
 * @create 2018/09/27 10:22
 **/
public class Question extends BaseEntity {
    /**
     * 编号
     */
    private String number;


    /**
     * 服务大类编码
     */
    private String typeLevel1Code;

    /**
     * 服务大类
     */
    private String typeLevel1Name;

    /**
     * 服务大类编码
     */
    private String typeLevel2Code;



    /**
     * 服务大类编码
     */
    private String typeLevel3Code;

    /**
     * 服务大类
     */
    private String typeLevel3Name;

    /**
     * 问题概述
     */
    private String question;

    /**
     * 问题补充
     */
    private String questionAppend;


    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 最优答案ID
     */
    private String bestAnswerId;

    /**
     * 答案条数
     */
    private Integer answerCount;

    /**
     * 1:内部创建 2:外部创建
     */
    private String createWay;

    /**
     * 是否推荐 0推荐  1不推荐
     */
    private Integer recommend;

    /**
     * 浏览次数
     */
    private Integer viewTimes;

    /**
     * 禁用启用
     */
    private String status;

    /**
     * 0删除  1正常
     */
    private Integer flag;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeLevel1Code() {
        return typeLevel1Code;
    }

    public void setTypeLevel1Code(String typeLevel1Code) {
        this.typeLevel1Code = typeLevel1Code;
    }

    public String getTypeLevel1Name() {
        return typeLevel1Name;
    }

    public void setTypeLevel1Name(String typeLevel1Name) {
        this.typeLevel1Name = typeLevel1Name;
    }

    public String getTypeLevel2Code() { return typeLevel2Code; }

    public void setTypeLevel2Code(String typeLevel2Code) { this.typeLevel2Code = typeLevel2Code; }

    public String getTypeLevel3Code() {
        return typeLevel3Code;
    }

    public void setTypeLevel3Code(String typeLevel3Code) {
        this.typeLevel3Code = typeLevel3Code;
    }

    public String getTypeLevel3Name() {
        return typeLevel3Name;
    }

    public void setTypeLevel3Name(String typeLevel3Name) {
        this.typeLevel3Name = typeLevel3Name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionAppend() {
        return questionAppend;
    }

    public void setQuestionAppend(String questionAppend) {
        this.questionAppend = questionAppend;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getBestAnswerId() {
        return bestAnswerId;
    }

    public void setBestAnswerId(String bestAnswerId) {
        this.bestAnswerId = bestAnswerId;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public String getCreateWay() {
        return createWay;
    }

    public void setCreateWay(String createWay) {
        this.createWay = createWay;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }
}
