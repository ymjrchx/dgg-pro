package net.dgg.bigdata.sjjs.web.condition.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.dgg.bigdata.sjjs.web.condition.dto.ActionDto;
import net.dgg.bigdata.sjjs.web.condition.dto.InputDto;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 13:32
 * @Description: 二级分类前端数据来源对象封装
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterOption {

    //分类后的一级框的样式
    private ActionDto action;

    //输入框的样式对象，根据不同输入框选择不同的属性
    private InputDto input;

    // label标签的内容,分类名称	用treebook的name
    private String label;

    //分类 code 				    用treebook的code
    private String value;

    //提示信息					用treebook的description
    private String hint;

    //单位						用treebook的ext1
    private String unit;

    //前端显示状态                用treebook的ext5
    private String status;

    public ActionDto getAction() {
        return action;
    }

    public void setAction(ActionDto action) {
        this.action = action;
    }

    public InputDto getInput() {
        return input;
    }

    public void setInput(InputDto input) {
        this.input = input;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
