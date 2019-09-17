package net.dgg.zqq.dto.user;

/**
 * Created by gene on 2017/9/28.
 * desc:查询后台用户返回数据dto
 */
public class UserSysListReturnDto {
    private String userId; //用户id
    private String employeeNo;//工号
    private String username;//用户名
    private String status;//状态 0:未启用，1:已启用
    private String deptId;//部门id
    private String deptName;//部门名称
    private String trueName;//用户名

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
    
    
}
