package net.dgg.bigdata.manager.role.entity;

import java.util.Date;

/**
 * 用户角色数据传输类
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/13
 * Time:16:41
 */
public class UserRoleDTO {
    //id
    private long id;

    //用户ID
    private long userId;

    //角色ID
    private long roleId;

    //创建人ID
    private long creatorId;

    //创建时间
    private Date createtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
