package com.admin.authority.service;

import com.admin.authority.model.SysRole;
import com.admin.basic.service.IBaseService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:10:38
 */
public interface IRoleService extends IBaseService<SysRole, Integer>{

	public SysRole getRole(Integer id);
	
	public void saveRole(SysRole role);
	
	public void updateRole(SysRole role);
}
