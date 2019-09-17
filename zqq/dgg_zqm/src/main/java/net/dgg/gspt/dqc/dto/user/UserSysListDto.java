package net.dgg.gspt.dqc.dto.user;

import java.io.Serializable;

/**
 * Created by gene on 2017/9/12.
 * desc:后台用户查询列表接口model
 */
public class UserSysListDto implements Serializable {
    private String userId; //用户id
    private String employeeNo;//工号
    private String username;//用户名
    private String status;//状态 0:未启用，1:已启用
    private String detail; //备注
    private Integer startCount; //开始条数
    private Integer endCount;//结束条数
    private String trueName;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
    
}
