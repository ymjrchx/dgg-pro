package net.dgg.bigdata.sjjs.web.condition.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.dgg.bigdata.sjjs.web.condition.dto.CategorieDto;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 11:42
 * @Description: 分类数据来源封装
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configs {

    private List<CategorieDto> categories;

    private List<String> hot_list;

    private Map<String,Object> input_values;

    private List<FilterOption> filter_options;

    public List<CategorieDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorieDto> categories) {
        this.categories = categories;
    }

    public List<String> getHot_list() {
        return hot_list;
    }

    public void setHot_list(List<String> hot_list) {
        this.hot_list = hot_list;
    }

    public Map<String, Object> getInput_values() {
        return input_values;
    }

    public void setInput_values(Map<String, Object> input_values) {
        this.input_values = input_values;
    }

    public List<FilterOption> getFilter_options() {
        return filter_options;
    }

    public void setFilter_options(List<FilterOption> filter_options) {
        this.filter_options = filter_options;
    }
}
