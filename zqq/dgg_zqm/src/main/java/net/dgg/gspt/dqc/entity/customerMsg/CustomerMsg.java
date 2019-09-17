package net.dgg.gspt.dqc.entity.customerMsg;

import lombok.Data;
import net.dgg.gspt.dqc.entity.BaseEntity;

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
     *
     */
    private String phone;

    /**
     * 信息类型
     */
    private String type;

    /**
     * 所在城市
     */
    private String city;

}
