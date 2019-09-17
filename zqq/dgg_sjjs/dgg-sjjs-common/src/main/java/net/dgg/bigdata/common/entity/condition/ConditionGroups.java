package net.dgg.bigdata.common.entity.condition;

import lombok.Data;
import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 14:51
 * @Description: 条件组实体
 */
@Data
@Table(name = "yk_condition_groups")
public class ConditionGroups {

    //id
    private Long id;

    //名称
    private String name;

    //使用次数
    private Integer usedCount;

    //排序
    private Integer sort;

    //条件
    private String filter;

    //提示信息
    private String info;

    //所在行业模板id
    private Long industryId;

    //历史信息或行业模板
    private Integer type;

    //创建人id
    private Long createrId;

    //创建者姓名+工号
    private String createrName;

    //创建人部门id
    private Long createrOrgId;

    //创建时间
    private Date createTime;

    //创建人部门id
    private Long updaterId;

    //修改人姓名+工号
    private String updaterName;

    //最后修改人部门id
    private Long updaterOrgId;

    //最后修改时间
    private Date updateTime;


}
