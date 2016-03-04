package com.admin.authority.service.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.admin.authority.dao.ISysUserDao;
import com.admin.authority.model.SysUser;
import com.admin.authority.service.ISysUserService;
import com.admin.basic.service.impl.BaseService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:11:48
 */
@Service
public class SysUserService extends BaseService<SysUser, Integer> implements ISysUserService {

	/**
	 * @param sqlSessionFactory
	 */
	@Autowired
	public SysUserService(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setDaoClass(ISysUserDao.class);
	}

	@Override
	public SysUser getUser(Integer id) {
		return getDao().get(id);
	}

	@Override
	@Transactional
	public void saveUser(SysUser user) {
		getDao().insert(user);
	}

	@Override
	@Transactional
	public void updateUser(SysUser user){
		System.out.println("执行更新操作...........");
		getDao().update(user);
		//getDao().insert(user);
		
	}

	@Override
	@Transactional
	public void transation(SysUser user,String newName){
		getDao().insert(user);
		user.setUserName(newName);
		//updateUser(user);
		//throw new Error("...........");
	}

	@Override
	public SysUser getUserByName(String username) {
		return this.getMapper(ISysUserDao.class).selectUserByName(username);
	}

	/**
	 * 从库查询测试
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午10:49:06
	 */
	@Override
	public Map<String, Object> getSlave(Integer id) {
		return this.getMapper(ISysUserDao.class).getSlave(id);
	}
}
