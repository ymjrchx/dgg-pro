package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 14:25
 * @Description:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndustryModelDto {
    //行业分类名称
    private String applicable_industry_name;

    //排序
    private Integer order;

    //条件组
    private List<ConditionGroupDto> condition_groups;

    public String getApplicable_industry_name() {
        return applicable_industry_name;
    }

    public void setApplicable_industry_name(String applicable_industry_name) {
        this.applicable_industry_name = applicable_industry_name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<ConditionGroupDto> getCondition_groups() {
        return condition_groups;
    }

    public void setCondition_groups(List<ConditionGroupDto> condition_groups) {
        this.condition_groups = condition_groups;
    }
}
