package net.dgg.bigdata.common.entity.condition;

import lombok.Data;
import net.dgg.base.baseDao.Table;

import java.util.Date;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/5 16:04
 * @Description:
 */
@Data
@Table(name = "yk_input")
public class Input {

    //id
    private Long id;

    //类型
    private Integer inputType;

    //数据来源
    private String optionsFrom;

    //提示信息
    private String placeholder;

    //最大(数字)
    private Integer max;

    //最小(数字)
    private Integer min;

    //最大关键词长度
    private Integer maxKeywordLength;

    //最大长度
    private Integer maxLength;

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
