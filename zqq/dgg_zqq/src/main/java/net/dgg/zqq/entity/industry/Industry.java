package net.dgg.zqq.entity.industry;

/**
 * @author 刘阳
 * @ClassName <industry>
 * @despriction
 * @create 2018/10/11 15:24
 * 行业大类
 **/
public class Industry {

    private Long id;

    /**
     * 行业名称
     */
    private String name;

    /**
     * 父级行业id,0顶级分类
     */
    private Long parentId;

    /**
     * 层级（只做2层）
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
