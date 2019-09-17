package net.dgg.bigdata.common.entity.condition;

import lombok.Data;
import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 16:59
 * @Description: action 实体
 */
@Data
@Table(name = "yk_action")
public class Action {

    //id
    private Long id;

    //类型
    private Integer actionType;

    //类型对应的数据 type_value
    private String typeValue;

    //说明
    private String description;

    //所属分类id
    private Long treebookId;

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
