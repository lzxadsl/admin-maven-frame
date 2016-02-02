package com.admin.authority.model;

import java.sql.Date;

/**
 * 系统用户基本信息表
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 下午3:21:33
 */
public class SysUserInfo {

	private Integer id;//主键ID
	private Integer userId;//用户ID
	private String chinaName;//中文名称
	private String simpleName;//简称
	private Integer deptmentId;//所属部门
	private Date birthday;//生日
	private String tel;//联系方式
	private Integer sex;//性别
	private String address;//居住地址
	private String area;//所属地区
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getChinaName() {
		return chinaName;
	}
	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public Integer getDeptmentId() {
		return deptmentId;
	}
	public void setDeptmentId(Integer deptmentId) {
		this.deptmentId = deptmentId;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
