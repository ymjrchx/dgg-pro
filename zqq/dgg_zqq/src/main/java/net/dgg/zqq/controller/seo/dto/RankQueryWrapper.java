package net.dgg.zqq.controller.seo.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by 李程 on 2018/11/15.
 */
@Getter
@Setter
public class RankQueryWrapper {

    private String start;

    private String end;

    @ApiParam(name = "searchType", value = "搜索类型，支持：brand、patent、all，分别表示：商标、专利、所有", defaultValue = "all")
    private String searchType;

    @ApiParam(name = "pullCount", value = "排名数量", defaultValue = "10")
    @NotNull(message = "拉取数量不能为空")
    private Integer pullCount;

}
