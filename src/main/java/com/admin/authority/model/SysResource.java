package com.admin.authority.model;

/**
 * 访问资源实体类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午4:12:48
 */
public class SysResource {

    private String id; //主键id 
    
    private String value;  //action url  
    
    private String permission;//shiro permission;  
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}  
    
}
