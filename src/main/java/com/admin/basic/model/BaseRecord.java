package com.admin.basic.model;

/**
 * 记录用户操作信息父类
 * @author LiZhiXian
 * @version 2.0
 * @date 2015-9-2 下午3:38:02
 */
public abstract class BaseRecord {
	//系统操作员id
	private String user_id;
	//操作员名字
	private String user_name;
	//操作ip
	private String oper_ip;
	//操作时间
	private String oper_time;
	//参数
	private String params;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOper_ip() {
		return oper_ip;
	}
	public void setOper_ip(String oper_ip) {
		this.oper_ip = oper_ip;
	}
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
