package com.admin.ehcache;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-17 上午9:45:48
 */
public interface ICacheUserService {

	public String getUser(Integer id);
	public String addUser(Integer id);
	
	public String list(Integer id);
	public String update(Integer id);
}
