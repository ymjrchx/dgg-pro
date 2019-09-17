package net.dgg.bigdata.sjjs.web.condition.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.dgg.bigdata.sjjs.web.condition.dto.ConditionGroupDto;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 14:21
 * @Description: 历史保存条件组
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConditionGroups {
    private List<ConditionGroupDto> data;

    public List<ConditionGroupDto> getData() {
        return data;
    }

    public void setData(List<ConditionGroupDto> data) {
        this.data = data;
    }
}
