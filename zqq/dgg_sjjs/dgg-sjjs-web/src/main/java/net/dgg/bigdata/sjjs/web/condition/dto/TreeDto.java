package net.dgg.bigdata.sjjs.web.condition.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Auther: 陈万国
 * @Date: 2018/12/10 11:52
 * @Description: 用于描述树结构
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeDto {
    //一级
    private String first_level;

    //二级
    private List<String> second_level;

    public String getFirst_level() {
        return first_level;
    }

    public void setFirst_level(String first_level) {
        this.first_level = first_level;
    }

    public List<String> getSecond_level() {
        return second_level;
    }

    public void setSecond_level(List<String> second_level) {
        this.second_level = second_level;
    }
}
