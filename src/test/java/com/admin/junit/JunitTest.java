package com.admin.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.admin.authority.model.SysUser;
import com.admin.authority.service.IUserService;


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
	private IUserService userService;
	
	@Test
	public void userTest(){
		//userService.getUserByName("lzx");
		SysUser user = userService.get(1);
		System.out.println(user.getUserName());
	}
}
