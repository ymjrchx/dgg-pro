package net.dgg.gspt.dqc.entity.order;

/**
 * @author 刘阳
 * @ClassName <ClassSecond>
 * @despriction 商标二级分类
 * @create 2018/09/27 15:41
 **/
public class ClassSecond extends ClassFirst {
    /**
     * 一级分类ID
     */
    private Long firstId;

    /**
     * 一级分类编号
     */
    private String firstNumber;


    public Long getFirstId() {
        return firstId;
    }

    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }
}
