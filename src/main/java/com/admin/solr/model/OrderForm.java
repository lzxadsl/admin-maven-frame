package com.admin.solr.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 订单
 * @author lizhixian
 * @version 1.0
 * @date 2015-9-11 上午10:55:21
 */
public class OrderForm {

	private Integer id;
	private Integer iid;
	private String title;
	private Double price;
	private Integer num;
	private String type;
	private Timestamp created;
	private Integer user_id;
	private Date timeout_action_time;
	private Double sell_type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getTimeout_action_time() {
		return timeout_action_time;
	}
	public void setTimeout_action_time(Date timeout_action_time) {
		this.timeout_action_time = timeout_action_time;
	}
	public Double getSell_type() {
		return sell_type;
	}
	public void setSell_type(Double sell_type) {
		this.sell_type = sell_type;
	}
	
}
