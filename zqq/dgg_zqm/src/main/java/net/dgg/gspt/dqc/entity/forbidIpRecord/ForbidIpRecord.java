package net.dgg.gspt.dqc.entity.forbidIpRecord;

import net.dgg.gspt.dqc.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <ForbidIpRecord>
 * @despriction Ip 禁用记录
 * @create 2018/10/21 12:22
 **/
public class ForbidIpRecord extends BaseEntity {

    /**
     * ip 或用户ID
     */
    private String ipOrUserId;

    /**
     * 访问次数
     */
    private Integer times;

    /**
     * 类型
     */
    private String type;

    /**
     * 1 正常  0 删除
     */
    private Integer flag;


    public String getIpOrUserId() {
        return ipOrUserId;
    }

    public void setIpOrUserId(String ipOrUserId) {
        this.ipOrUserId = ipOrUserId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
