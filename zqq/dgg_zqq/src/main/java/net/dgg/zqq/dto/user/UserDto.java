package net.dgg.zqq.dto.user;

import java.io.Serializable;

public class UserDto implements Serializable{
	private String loginName; //登录名
	private String phoneNo; //手机号
	private String loginPwd; //登录密码
	private String smsVerifyCode; //短信验证码
	private String imageVerifyCode;  //图片验证码

	private String startCount;  //开始条数
	private String endCount; //结束条数
	private String employeeNo; //员工编号
	private String username; // 用户名
	private String newLoginPwd; //新密码
	private String userId; //用户id
	private String oldLoginPwd;//旧密码
	private String nickname;
    private String email;
    private String userCompany;
    private String userPosition;
    private String industry;
    private String type; //
    private int time; //有效期天数

	private Integer status;//用户状态，0，禁用。1，启用

	private String keyWord;//查询关键字，注册用户

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public String getStartCount() {
		return startCount;
	}

	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}

	public String getEndCount() {
		return endCount;
	}

	public void setEndCount(String endCount) {
		this.endCount = endCount;
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

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getSmsVerifyCode() {
		return smsVerifyCode;
	}
	public void setSmsVerifyCode(String smsVerifyCode) {
		this.smsVerifyCode = smsVerifyCode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getImageVerifyCode() {
		return imageVerifyCode;
	}
	public void setImageVerifyCode(String imageVerifyCode) {
		this.imageVerifyCode = imageVerifyCode;
	}

	public String getNewLoginPwd() {
		return newLoginPwd;
	}

	public void setNewLoginPwd(String newLoginPwd) {
		this.newLoginPwd = newLoginPwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	
}
