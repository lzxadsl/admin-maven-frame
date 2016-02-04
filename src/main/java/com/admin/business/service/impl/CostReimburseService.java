package com.admin.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.basic.service.impl.BaseService;
import com.admin.business.dao.ICostReimburseDao;
import com.admin.business.model.CostItem;
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

	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:10:41
	 */
	@Override
	public List<CostItem> getCostItems(Integer costId) {
		return this.getMapper(ICostReimburseDao.class).selectCostItems(costId);
	}

	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:10:41
	 */
	@Override
	public void delCostItems(Integer costId) {
		this.getMapper(ICostReimburseDao.class).delCostItems(costId);
	}

	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:10:41
	 */
	@Override
	public void addCostItems(Map<String, Object> map) {
		this.getMapper(ICostReimburseDao.class).addCostItems(map);
	}

	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:18:14
	 */
	@Override
	@Transactional
	public CostReimburse saveCostReimburse(CostReimburse entity) {
		save(entity);
		if(entity.getItemList() != null && entity.getItemList().size() > 0){
			delCostItems(entity.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", entity.getItemList());
			map.put("costId",entity.getId());
			addCostItems(map);
		}
		return entity;
	}

}
