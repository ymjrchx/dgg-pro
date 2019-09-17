package net.dgg.bigdata.manager.user.entity;

/**
 * 组织机构数据传输类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/7
 * Time:9:38
 */
public class OrganizationDTO {
    //组织机构ID
    private long id;

    //组织机构名称
    private String name;

    //祖先ID
    private long pId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }
}
