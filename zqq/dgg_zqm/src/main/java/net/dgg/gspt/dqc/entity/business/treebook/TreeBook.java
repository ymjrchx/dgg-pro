package net.dgg.gspt.dqc.entity.business.treebook;

import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * 数据字典实体类
 * 对应表名：cms_tree_book
 * @author 汤宏
 *
 */
public class TreeBook extends BaseEntity {
	// 父级字典id
    private Long pid;
    // 父级字典点编码
    private String pcode;
    //字典编码
    private String code;
    //字典名称
    private String name;
    //层级
    private String levels;
    //排序
    private Integer sort;
    //备注描述
    private String description;
    //状态  0-禁用,1-启用
    private Integer status;
    //扩展字段1
    private String ext1;
    //扩展字段2
    private String ext2;
    //扩展字段3
    private String ext3;
    //扩展字段4
    private String ext4;
    //扩展字段5
    private String ext5;


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }
}