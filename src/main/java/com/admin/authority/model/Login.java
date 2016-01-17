package com.admin.authority.model;

/**
 * 登入实体
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午2:20:46
 */
public class Login {

	private String username;//用户名
	private String password;//密码
	private boolean rememberMe;//当权限配置成 xxx=user时，如果为true即使没进行身份验证也能通过
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
