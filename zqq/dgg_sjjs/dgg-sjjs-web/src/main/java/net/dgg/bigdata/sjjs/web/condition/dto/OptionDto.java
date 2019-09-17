package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 13:40
 * @Description: 当action的type为5时，数据来源前端显示对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionDto {

    //标签名
    private String labelName;

    //值
    private String value;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
