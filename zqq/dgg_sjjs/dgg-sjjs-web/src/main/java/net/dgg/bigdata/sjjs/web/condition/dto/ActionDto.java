package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 13:37
 * @Description: 分类样式封装
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionDto {
    //类型
    private Integer type;

    //如果类型为5 则查询数据库确认options的值
    private List<OptionDto> options;

    //type的值
    private String typeValue;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
