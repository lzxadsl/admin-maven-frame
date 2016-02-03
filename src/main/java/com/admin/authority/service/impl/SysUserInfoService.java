package com.admin.authority.service.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.authority.dao.ISysUserInfoDao;
import com.admin.authority.model.SysUserInfo;
import com.admin.authority.service.ISysUserInfoService;
import com.admin.basic.service.impl.BaseService;

/**
 * 系统用户基本信息
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-3 下午2:08:50
 */
@Service
public class SysUserInfoService extends BaseService<SysUserInfo, Integer> implements ISysUserInfoService{

	/**
	 * @param sqlSessionFactory
	 */
	@Autowired
	public SysUserInfoService(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setDaoClass(ISysUserInfoDao.class);
	}

}
