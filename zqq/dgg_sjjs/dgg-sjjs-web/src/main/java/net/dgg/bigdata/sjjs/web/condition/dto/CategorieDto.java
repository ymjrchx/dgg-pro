package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 11:49
 * @Description: 分类输出对象，来源treebook
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategorieDto {
    //一级属性名称
    private String name;

    //二级属性code
    private List<String> dimensions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }
}
