package com.admin.business.service.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.basic.service.impl.BaseService;
import com.admin.business.dao.ICostReimburseDao;
import com.admin.business.model.CostReimburse;
import com.admin.business.service.ICostReimburseService;

/**
 * 费用报销服务层
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 下午1:39:04
 */
@Service
public class CostReimburseService extends BaseService<CostReimburse, Integer> implements ICostReimburseService{

	/**
	 * @param sqlSessionFactory
	 */
	@Autowired
	public CostReimburseService(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setDaoClass(ICostReimburseDao.class);
	}

}
