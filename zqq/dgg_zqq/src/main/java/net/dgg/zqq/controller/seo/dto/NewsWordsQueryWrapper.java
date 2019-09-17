package net.dgg.zqq.controller.seo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewsWordsQueryWrapper {

    @NotNull(message = "页号不能为空")
    private Integer page;
    
    @NotNull(message = "每页大小不能为空")
    private Integer size;

}
