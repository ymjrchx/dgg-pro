package net.dgg.bigdata.sjjs.web.condition.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.dgg.bigdata.sjjs.web.condition.dto.IndustryModelDto;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 14:24
 * @Description: 行业模板条件组
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recommend {
    private List<IndustryModelDto> data;

    public List<IndustryModelDto> getData() {
        return data;
    }

    public void setData(List<IndustryModelDto> data) {
        this.data = data;
    }
}
