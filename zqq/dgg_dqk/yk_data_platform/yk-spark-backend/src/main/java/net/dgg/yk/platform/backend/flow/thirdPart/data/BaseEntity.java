package net.dgg.yk.platform.backend.flow.thirdPart.data;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 *
 * @author 徐哲
 * @create 2017-11-01 14:22
 */
@Getter
@Setter
public class BaseEntity implements Serializable {
    /**
     * 序列 化号
     */
    private static final long serialVersionUID = -540287793770056213L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人ID
     */
    private Long createrId;
    /**
     * 创建人部门ID
     */
    private Long createrOrgId;
    /**
     * 创建人姓名
     */
    private String createrName;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 修改人ID
     */
    private Long updaterId;
    /**
     * 修改人姓名
     */
    private String updaterName;
    /**
     * 修改人部门ID
     */
    private Long updaterOrgId;

}