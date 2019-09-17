package net.dgg.gspt.dqc.entity.businessPushRecord;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 刘阳
 * @ClassName <BusinessPushRecord>
 * @despriction 商机资源信息推送记录
 * @create 2019/01/07 15:58
 **/
@Data
public class BusinessPushRecord implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名字
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 区域
     */
    private String area;

    /**
     * 来源
     */
    private String type;

    /**
     * 状态
     */
    private String status;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 行业
     */
    private String industry;

    /**
     * 商标名字
     */
    private String trademarkName;


}
