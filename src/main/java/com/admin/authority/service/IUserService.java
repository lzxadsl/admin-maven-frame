package com.admin.authority.service;

import com.admin.authority.model.User;
import com.admin.basic.service.IBaseService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:10:38
 */
public interface IUserService extends IBaseService<User, Integer>{

	public User getUser(Integer id);
	
	public void saveUser(User user);
	
	public void updateUser(User user);
	
	public void transation(User user,String newName);
	
	public User getUserByName(String username);
}
