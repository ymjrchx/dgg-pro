package net.dgg.yk.platform.backend.flow.thirdPart.data;


import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典实体类
 * 对应表名：cms_tree_book
 *
 * @author 汤宏
 */
@Getter
@Setter
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



}