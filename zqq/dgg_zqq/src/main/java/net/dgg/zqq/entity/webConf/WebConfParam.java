package net.dgg.zqq.entity.webConf;


import net.dgg.zqq.entity.BaseEntity;

/**
 * @author 刘阳
 * @ClassName <WebConfParam>
 * @despriction web 配置参数  实体
 * @create 2018/08/09 9:56
 **/

public class WebConfParam extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 代码code
     */
    private String code;
    /**
     * 配置值
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
