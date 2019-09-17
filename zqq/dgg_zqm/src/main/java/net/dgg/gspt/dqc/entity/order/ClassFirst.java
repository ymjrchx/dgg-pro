package net.dgg.gspt.dqc.entity.order;

/**
 * @author 刘阳
 * @ClassName <ClassFirst>
 * @despriction 商标一级分类
 * @create 2018/09/27 15:21
 **/
public class ClassFirst {
    /**
     * ID
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 编号
     */
    private String number;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0不在首页显示 1在首页显示
     */
    private Integer isShow;

    /**
     * 0标记为无色 1标记为红色
     */
    private Integer isColour;


    /**
     * 是否为推荐位: 0非推荐位 1推荐位
     */
    private Integer isRecommend;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsColour() {
        return isColour;
    }

    public void setIsColour(Integer isColour) {
        this.isColour = isColour;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }
}
