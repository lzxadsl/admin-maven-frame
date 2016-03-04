package com.admin.junit;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.admin.authority.model.SysUser;
import com.admin.authority.service.ISysUserService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-4 上午8:46:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/app-*.xml")
public class DataSourceTest {

	@Autowired
	private ISysUserService sysUserService;
	
	@Test
	public void testMast(){
		Map<String, Object> user = sysUserService.getSlave(1);
		System.out.println(user.get("username"));
		SysUser u = sysUserService.get(1);
		System.out.println(u.getUserName());
	}
}
