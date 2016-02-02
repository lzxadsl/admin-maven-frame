package com.admin.authority.model;

import java.io.Serializable;

/**
 * 权限实体类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午4:31:30
 */
public class SysPermission implements Serializable{

	private static final long serialVersionUID = 1624774962681257787L;
	
	private Integer id;
	private String name;
	
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
	
	
}
