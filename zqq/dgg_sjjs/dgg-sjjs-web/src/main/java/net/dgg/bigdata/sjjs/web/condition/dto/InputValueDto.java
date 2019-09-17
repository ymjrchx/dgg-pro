package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 11:55
 * @Description: 输入框数据来源前端显示对象，
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputValueDto {

    private String name;

    //如果是树则将数据放到这个对象组装组装
    private List<TreeDto> treeDtos;

    //如果是单一的字符串数组则将数据放到这个对象组装
    private List<String> strings;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeDto> getTreeDtos() {
        return treeDtos;
    }

    public void setTreeDtos(List<TreeDto> treeDtos) {
        this.treeDtos = treeDtos;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
