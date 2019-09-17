package net.dgg.zqq.controller.seo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by 李程 on 2018/11/9.
 */
@Data
public class UserAccessEventWrapper implements Serializable {

    @NotNull(message = "页面代码pageCode不能为空")
    private PageCode pageCode;

    @NotNull(message = "activeKey不能为空")
    private String activeKey;

    @NotNull(message = "URL不能为空")
    private String url;

    @NotNull(message = "参数列表parameter不能空")
    private Map<String, Object> parameter;

    private Long currentTime;
}
