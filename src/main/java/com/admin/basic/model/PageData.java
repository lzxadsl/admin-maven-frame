package com.admin.basic.model;

/**分页数据辅助
 * @author LiZhiXian 
 * @version 2.0
 * @date 2013-5-22 上午9:52:01
 */
public class PageData {
	private int totalSize = 0;
	private int totalPage = 0;
	private int page;//easyui（extjs） 当前页 
	private int rows;//easyui 每页显示条数
	private int limit;//bootstrap（extjs） 每页显示条数
	private int offset;//bootstrap 开始位置
	private int pageSize;//每页显示条数
	private int startRow;//开始行
	/**
	 * 构造方法，默认每页为10条记录，显示页为第一页
	 * 由于不同ui传进来的分页参数属性不一致，最终都将转换成 pageSize 和 startRow 进行计算
	 * @author LiZhiXian
	 * @date 2013-5-22 上午9:51:36
	 */
	public PageData(){
		pageSize = 10;
		startRow = 0;
	}
	
	public int getTotalPage(){
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.startRow = (page - 1) * rows;
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.pageSize = rows;
		this.rows = rows;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.pageSize = limit;
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.startRow = offset;
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize < 0 ? 0 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow < 0 ? 0 : startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	
}
