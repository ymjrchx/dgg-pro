package net.dgg.zqq.entity.industry;

/**
 * @author 刘阳
 * @ClassName <IndustryCategorysThird>
 * @despriction
 * @create 2018/10/11 15:28
 * 行业商标数据
 **/
public class IndustryCategorysThird {

    private Long id;

    /**
     * 一级商标编号
     */
    private String firstCateNo;

    /**
     * 二级商标编号
     */
    private String secondCateNo;

    /**
     * 三级商标编号
     */
    private String thirdNo;

    /**
     * 行业领域二级id
     */
    private Long industryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstCateNo() {
        return firstCateNo;
    }

    public void setFirstCateNo(String firstCateNo) {
        this.firstCateNo = firstCateNo;
    }

    public String getSecondCateNo() {
        return secondCateNo;
    }

    public void setSecondCateNo(String secondCateNo) {
        this.secondCateNo = secondCateNo;
    }

    public String getThirdNo() {
        return thirdNo;
    }

    public void setThirdNo(String thirdNo) {
        this.thirdNo = thirdNo;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }
}
