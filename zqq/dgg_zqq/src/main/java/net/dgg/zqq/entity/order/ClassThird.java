package net.dgg.zqq.entity.order;

/**
 * @author 刘阳
 * @ClassName <ClassThird>
 * @despriction 三级分类
 * @create 2018/09/27 16:07
 **/
public class ClassThird extends ClassFirst {

    private Long secondId;

    private String secondNumber;

    public Long getSecondId() {
        return secondId;
    }

    public void setSecondId(Long secondId) {
        this.secondId = secondId;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }
}
