package com.admin.business.service;

import java.util.List;
import java.util.Map;
import com.admin.basic.service.IBaseService;
import com.admin.business.model.CostItem;
import com.admin.business.model.CostReimburse;

/**
 * 费用报销服务层
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 下午1:38:33
 */
public interface ICostReimburseService extends IBaseService<CostReimburse, Integer>{

	/**
	 * 获取费用报销单对应的费用项
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:07:00
	 */
	public List<CostItem> getCostItems(Integer costId);
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
	
	/**
	 * 保存报销单
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:17:50
	 * @param CostReimburse 费用报销单实体
	 * @param userId 申请人ID
	 */
	public CostReimburse saveCostReimburse(CostReimburse entity,Integer userId);
	
	/**
	 * 更新报销单
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-4 下午2:17:50
	 * @param CostReimburse 费用报销单实体
	 * @param userId 申请人ID
	 */
	public CostReimburse updateCostReimburse(CostReimburse entity,Integer userId);
	
	/**
	 * 删除报销单
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-5 上午9:24:41
	 */
	public void deleteCostReimburse(Integer[] costId);
	
}
