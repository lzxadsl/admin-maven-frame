package com.admin.authority.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色实体类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午4:27:39
 */
public class Role implements Serializable{

	private static final long serialVersionUID = 1541162689323241113L;
	
	private Integer id;//主键
	private String name;//角色名
	private Set<Permission> permissionSet = new HashSet<Permission>();//该角色所拥有的权限（用关系表来存储关系）
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Permission> getPermissionSet() {
		return permissionSet;
	}
	public void setPermissionSet(Set<Permission> permissionSet) {
		this.permissionSet = permissionSet;
	}
}
