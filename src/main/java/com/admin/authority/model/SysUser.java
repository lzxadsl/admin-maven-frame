package com.admin.authority.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户实体类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午4:11:02
 */
public class SysUser implements Serializable{

	private static final long serialVersionUID = 8818372704349310291L;
	
	private Integer id;//主键
	private String userName;//用户名
	private String password;//密码
	private String salt;//盐 登入时采用盐+密码进行验证（盐的生成规则可以采用用户名+随机数）
	private Set<SysRole> roleSet = new HashSet<SysRole>();//角色
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<SysRole> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<SysRole> roleSet) {
		this.roleSet = roleSet;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
