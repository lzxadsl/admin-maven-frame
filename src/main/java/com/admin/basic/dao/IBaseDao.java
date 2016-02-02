package com.admin.basic.dao;

import java.util.List;
import java.util.Map;

/**
 * 映射文件Mapper接口
 * @author LiZhiXian
 * @version 1.0
 * @param <T,K> T 实体 K 主键类型
 * @date 2015-9-18 上午11:24:11
 */
public interface IBaseDao<T,K> {

	public void insert(T entity);
	public int insertList(List<T> list);
	public void delete(K id);
	public void deleteList(Map<String, Object> params);
	public void update(T entity);
	public void updateField(Map<String, Object> params);
	public T get(K id);
	//public T get(Map<String, Object> params);
	public List<T> selectList(Map<String, Object> params);
	public List<T> selectPageList(Map<String, Object> params);
}
