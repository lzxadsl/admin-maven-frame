package com.admin.authority.service;

import com.admin.authority.model.SysUser;
import com.admin.basic.service.IBaseService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:10:38
 */
public interface IUserService extends IBaseService<SysUser, Integer>{

	public SysUser getUser(Integer id);
	
	public void saveUser(SysUser user);
	
	public void updateUser(SysUser user);
	
	public void transation(SysUser user,String newName);
	
	public SysUser getUserByName(String username);
}
