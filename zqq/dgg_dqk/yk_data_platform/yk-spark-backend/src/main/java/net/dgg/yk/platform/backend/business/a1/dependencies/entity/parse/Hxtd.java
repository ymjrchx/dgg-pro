package net.dgg.yk.platform.backend.business.a1.dependencies.entity.parse;

/**
 * 核心团队
 * Created by jiangsh on 2018/8/20 11:06
 */
public class Hxtd {
    private String tName; //姓名
    private String tRole; //角色
    private String tIntroduce; //介绍

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettRole() {
        return tRole;
    }

    public void settRole(String tRole) {
        this.tRole = tRole;
    }

    public String gettIntroduce() {
        return tIntroduce;
    }

    public void settIntroduce(String tIntroduce) {
        this.tIntroduce = tIntroduce;
    }
}
