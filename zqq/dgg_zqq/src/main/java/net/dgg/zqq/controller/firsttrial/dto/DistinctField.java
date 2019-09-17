package net.dgg.zqq.controller.firsttrial.dto;

import lombok.Getter;

/**
 * Created by 李程 on 2018/10/26.
 */
@Getter
public enum DistinctField {

    tmSection("代理机构"), applicationCn("申请人");

    private String name;

    DistinctField(String name) {
        this.name = name;
    }
}
