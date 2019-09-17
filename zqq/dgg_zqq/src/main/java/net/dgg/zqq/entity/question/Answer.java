package net.dgg.zqq.entity.question;

import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <Answer>
 * @despriction 问题答案
 * @create 2018/09/27 10:33
 **/
public class Answer extends BaseEntity {
    /**
     * 所属问题ID
     */
    private Long questionId;
    /**
     * 答案文本
     */
    private String answer;

    /**
     * 区分是内部回答还是回答创建
     */
    private String answerWay;

    /**
     * 禁用启用
     */
    private String status;

    /**
     * 0删除  1正常
     */
    private Integer flag;


    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerWay() {
        return answerWay;
    }

    public void setAnswerWay(String answerWay) {
        this.answerWay = answerWay;
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
}
