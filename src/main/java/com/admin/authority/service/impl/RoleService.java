package com.admin.authority.service.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.admin.authority.dao.IRoleDao;
import com.admin.authority.model.Role;
import com.admin.authority.service.IRoleService;
import com.admin.basic.service.impl.BaseService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-18 上午10:11:48
 */
@Service
public class RoleService extends BaseService<Role, Integer> implements IRoleService {

	/**
	 * @param sqlSessionFactory
	 */
	@Autowired
	public RoleService(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setDaoClass(IRoleDao.class);
	}

	@Override
	public Role getRole(Integer id) {
		return getDao().get(id);
	}

	@Override
	@Transactional
	public void saveRole(Role Role) {
		getDao().insert(Role);
	}

	@Override
	@Transactional
	public void updateRole(Role role){
		System.out.println("------------------------------执行角色更新操作-----------------------------");
		getDao().update(role);
	}
}
