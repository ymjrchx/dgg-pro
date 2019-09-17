package net.dgg.bigdata.common.entity.condition;

import lombok.Data;
import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 15:21
 * @Description: 行业模板实体
 */
@Data
@Table(name = "yk_industry_model")
public class IndustryModel {

    //id
    private Long id;

    //行业分类名称
    private String applicableIndustryName;

    //排序
    private Integer sort;

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
