package com.admin.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.admin.authority.model.SysUser;
import com.admin.authority.model.SysUserInfo;
import com.admin.authority.service.ISysUserInfoService;
import com.admin.authority.service.ISysUserService;


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
}
