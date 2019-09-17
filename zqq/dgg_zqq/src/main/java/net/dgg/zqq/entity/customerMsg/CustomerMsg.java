package net.dgg.zqq.entity.customerMsg;

import lombok.Data;
import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <CustomerMsg>
 * @despriction 客留信息
 * @create 2018/12/18 16:45
 **/
@Data
public class CustomerMsg extends BaseEntity {

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 商标名字
     */
    private String trademarkName;

    /**
     * 行业
     */
    private String industry;


    /**
     * 地区
     */
    private String area;

    /**
     * fal
     */
    private Integer flag;


}
