package com.admin.authority.dao;

import com.admin.authority.model.SysUser;
import com.admin.basic.dao.IBaseDao;

/**
 * 用户管理
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 下午1:42:23
 */
public interface ISysUserDao extends IBaseDao<SysUser, Integer>{

	/**
	 * 根据用户名查询用户
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-6 下午11:07:26
	 */
	public SysUser selectUserByName(String username);
}
