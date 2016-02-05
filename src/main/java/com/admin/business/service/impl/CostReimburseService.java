package com.admin.business.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RuntimeService;
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

	@Autowired
	private RuntimeService runtimeService;
	
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
	 * 保存信息并启动流程
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:18:14
	 */
	@Override
	@Transactional
	public CostReimburse saveCostReimburse(CostReimburse entity,Integer userId) {
		Timestamp time = new Timestamp(System.currentTimeMillis()); 
		entity.setCreateTime(time);//申请时间
		entity.setUserId(userId);
		entity.setState("提交申请");
		save(entity);
		if(entity.getItemList() != null && entity.getItemList().size() > 0){
			delCostItems(entity.getId());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", entity.getItemList());
			map.put("costId",entity.getId());
			addCostItems(map);
		}
		//流程KEY
		String prockey = entity.getClass().getSimpleName();
		/**
		 * 业务主键，用于流程关联业务
		 * 规则：实体类名称+冒号+主键ID
		 * 注意：设计流程时，关键字key必须和实体类名称一致
		 */
		String businessKey = prockey +":"+ entity.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId",userId);//设置办理人
		runtimeService.startProcessInstanceByKey(prockey,businessKey,map);
		return entity;
	}

	/**
	 * 删除报销单
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-5 上午9:25:11
	 */
	@Override
	@Transactional
	public void deleteCostReimburse(Integer[] costId) {
		for(Integer id:costId){
			delete(id);//删除报销单信息
			delCostItems(id);//删除对应的费用项
		}
	}

	/**
	 * 更新报销单
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-5 下午1:59:26
	 */
	@Override
	@Transactional
	public CostReimburse updateCostReimburse(CostReimburse entity,
			Integer userId) {
		entity.setState("提交申请");
		update(entity);//更新基本信息
		delCostItems(entity.getId());//删除对应的费用项
		//添加对应的费用项
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
