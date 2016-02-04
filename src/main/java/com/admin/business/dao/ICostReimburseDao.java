package com.admin.business.dao;

import java.util.List;
import java.util.Map;

import com.admin.basic.dao.IBaseDao;
import com.admin.business.model.CostItem;
import com.admin.business.model.CostReimburse;

/**
 * 费用报销
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 上午11:26:32
 */
public interface ICostReimburseDao extends IBaseDao<CostReimburse, Integer>{

	/**
	 * 获取费用报销单对应的费用项
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:07:00
	 */
	public List<CostItem> selectCostItems(Integer costId);
	/**
	 * 删除报销单对应的费用项
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:08:48
	 */
	public void delCostItems(Integer costId);
	/**
	 * 增加费用项
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:09:03
	 */
	public void addCostItems(Map<String, Object> map);
}
