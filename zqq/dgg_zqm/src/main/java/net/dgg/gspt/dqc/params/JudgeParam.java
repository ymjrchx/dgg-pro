package net.dgg.gspt.dqc.params;

/**
 * @ClassName: JudgeParam
 * @Description: 裁判文书参数
 * @Author: jiangsh
 * @Date: 2018/10/26 10:22
 */
public class JudgeParam {

    private String keyWord; //搜索关键字

    //数据字典条件
    private String actionCause; //案由
    private String courtLevel; //法院层级
    private String judgeYear; //裁判年份
    private String proceedings; //审理程序
    private String judgePro; //文书类型
    private String area; //地域
    //    private String type; //文书类型 caseType
//    private String antistop; //关键词

    private int pageIndex; //初始页
    private int pageSize; //每页多少条

    private String judgeDateSort; //裁判日期排序， 1 升序； 0 降序；

    public String getJudgeDateSort() {
        return judgeDateSort;
    }

    public void setJudgeDateSort(String judgeDateSort) {
        this.judgeDateSort = judgeDateSort;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAntistop() {
//        return antistop;
//    }
//
//    public void setAntistop(String antistop) {
//        this.antistop = antistop;
//    }

    public String getActionCause() {
        return actionCause;
    }

    public void setActionCause(String actionCause) {
        this.actionCause = actionCause;
    }

    public String getCourtLevel() {
        return courtLevel;
    }

    public void setCourtLevel(String courtLevel) {
        this.courtLevel = courtLevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getJudgeYear() {
        return judgeYear;
    }

    public void setJudgeYear(String judgeYear) {
        this.judgeYear = judgeYear;
    }

    public String getProceedings() {
        return proceedings;
    }

    public void setProceedings(String proceedings) {
        this.proceedings = proceedings;
    }

    public String getJudgePro() {
        return judgePro;
    }

    public void setJudgePro(String judgePro) {
        this.judgePro = judgePro;
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
