package com.admin.business.model;

/**
 * 费用项
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 下午2:59:21
 */
public class CostItem {

	private Integer id;//主键id
	private Integer costId;//报销单id
	private String itemName;//费用项目
	private String category;//类别
	private String amount;//金额
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCostId() {
		return costId;
	}
	public void setCostId(Integer costId) {
		this.costId = costId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
