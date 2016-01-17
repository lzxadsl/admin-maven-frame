package com.admin.basic.service;

import java.util.List;
import java.util.Map;

import com.admin.basic.model.PageData;

/**
 * 公共接口
 * @author LiZhiXian
 * @version 1.0
 * @param <T,K> T 实体 K 主键类型
 * @date 2015-9-18 上午11:31:32
 */
public interface IBaseService<T,K> {

	public T save(T entity);
	public int saveList(List<T> list);
	public void delete(K id);
	public void deleteList(Map<String, Object> params);
	public T update(T entity);
	public T get(K id);
	public T get(Map<String, Object> params);
	/**
	 * 列表查询
	 */
	public List<T> list();
	/**
	 * 带参数列表查询
	 */
	public List<T> list(Map<String, Object> params);
	
	/**
	 * 列表分页查询
	 * @param page 分页对象
	 */
	public List<T> listPage(PageData pageData);
	/**
	 * 带参数列表分页查询
	 * @param page 分页对象
	 */
	public List<T> listPage(Map<String, Object> params,PageData pageData);
}
