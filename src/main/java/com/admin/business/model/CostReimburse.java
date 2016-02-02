package com.admin.business.model;

import java.sql.Timestamp;

/**
 * 费用报销单实体
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 上午10:26:37
 */
public class CostReimburse {

	private Integer id;//主键ID
	private String costName;//报销单名称
	private Integer userId;//报销人ID
	private Double amount;//报销总金额
	private Timestamp createTime;//报销时间
	private String state;//任务状态
	private String description;//报销说明

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
