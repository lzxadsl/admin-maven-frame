package com.admin.authority.service;

import java.util.Map;

import com.admin.authority.model.SysUser;
import com.admin.basic.service.IBaseService;
import com.admin.extend.sql.DataSource;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:10:38
 */
public interface ISysUserService extends IBaseService<SysUser, Integer>{

	public SysUser getUser(Integer id);
	
	public void saveUser(SysUser user);
	
	public void updateUser(SysUser user);
	
	public void transation(SysUser user,String newName);
	
	public SysUser getUserByName(String username);
	
	/**
	 * 从库查询测试 默认是master
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午10:48:22
	 */
	@DataSource(value="slave")
	public Map<String, Object> getSlave(Integer id);
}
