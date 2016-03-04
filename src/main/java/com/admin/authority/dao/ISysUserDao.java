package com.admin.authority.dao;

import java.util.Map;
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
	
	/**
	 * 从库查询测试
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午10:48:22
	 */
	public Map<String, Object> getSlave(Integer id);
}
