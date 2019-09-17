package net.dgg.bigdata.common.entity.condition;

import lombok.Data;
import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 16:11
 * @Description:
 */
@Data
@Table(name = "yk_option")
public class Option {

    //id
    private Long id;

    //标签名
    private String labelName;

    //值
    private String value;

    //所属action_id
    private Long actionId;

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
