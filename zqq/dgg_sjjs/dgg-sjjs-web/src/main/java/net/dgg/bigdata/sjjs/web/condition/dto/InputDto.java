package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 13:43
 * @Description: 输入框属性样式前端显示数组
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputDto {
    //类型
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOptionsFrom() {
        return optionsFrom;
    }

    public void setOptionsFrom(String optionsFrom) {
        this.optionsFrom = optionsFrom;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMaxKeywordLength() {
        return maxKeywordLength;
    }

    public void setMaxKeywordLength(Integer maxKeywordLength) {
        this.maxKeywordLength = maxKeywordLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
