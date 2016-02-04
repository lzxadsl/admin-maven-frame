package com.admin.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.admin.authority.model.SysUser;
import com.admin.authority.model.SysUserInfo;
import com.admin.authority.service.ISysUserInfoService;
import com.admin.authority.service.ISysUserService;
import com.admin.business.model.CostItem;
import com.admin.business.model.CostReimburse;
import com.admin.business.service.ICostReimburseService;


/**
 * 
 * @author lzx
 * @version 1.0
 * @date 下午6:45:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/app-*.xml")
public class JunitTest {

	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysUserInfoService sysUserInfoService;
	
	@Autowired
	private ICostReimburseService costReimburseService;
	@Test
	public void userTest(){
		//SysUser user = sysUserService.getUserByName("lzx");
		//System.out.println(user.getRoleSet().iterator().next());
		SysUserInfo userInfo = sysUserInfoService.get(1);
		System.out.println(userInfo.getChinaName());
		SysUser user = sysUserService.get(1);
		SysUserInfo userInfo1 = user.getUserInfo();
		System.out.println(userInfo1.getChinaName());
	}
	@Test
	public void costReimTest(){
		CostReimburse entity = new CostReimburse();
		entity.setChinaName("王包强");
		entity.setCostName("报销单");
		costReimburseService.save(entity);
		System.out.println(entity);
	}
	@Test
	public void costItemTest(){
		List<CostItem> list = new ArrayList<CostItem>();
		for(int i=0;i<2;i++){
			CostItem item = new CostItem();
			//item.setCostId(2);
			item.setItemName("报销");
			item.setCategory("10");
			item.setAmount("100.056");
			list.add(item);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("costId",10);
		costReimburseService.addCostItems(map);
		System.out.println(list);
	}
}
