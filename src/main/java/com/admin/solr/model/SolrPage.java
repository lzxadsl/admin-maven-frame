package com.admin.solr.model;

import java.util.List;

/**
 * 分页查询
 * @author LiZhiXian
 * @version 1.0
 * @param <T>
 * @date 2015-9-12 上午10:31:40
 */
public class SolrPage<T> {

	private int start;//开始位置
	private int totalSize;//总条数
	private int pageSize;//每页显示条数
	private int currentPage;//当前页
	private int totalPage = 0;//总页数
	private List<T> results;//结果集
	
	/**
	 * 构造方法，默认每页为10条记录，显示页为第一页
	 * @author LiZhiXian
	 * @date 2015-9-12 上午10:51:36
	 */
	public SolrPage(){
		pageSize = 10;
		currentPage = 1;
		start = 0;
	}
	public int getTotalPage() {
		if(pageSize != 0){
			if(totalSize % pageSize != 0){
				totalPage = totalSize / pageSize + 1;
			}else
				totalPage = totalSize /pageSize;
		}
		return totalPage;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
