package net.dgg.bigdata.manager.user.entity;

/**
 * Created by IntelliJ IDEA.
 * Developer:Liu Yao
 * Date:2018/3/7
 * Time:17:02
 */
public class UserDTO {
    //ID
    private long id;

    //登录名，即员工工号
    private String loginName;

    //员工姓名
    private String name;

    //性别
    private String sex;

    //所在部门名称
    private String orgName;

    //角色名称
    private String roleName;

    //用户状态
    private String locked;
    
    public UserDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", orgName='" + orgName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", locked='" + locked + '\'' +
                '}';
    }
}
