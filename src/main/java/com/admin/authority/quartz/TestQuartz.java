package com.admin.authority.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.authority.model.Role;
import com.admin.authority.model.User;
import com.admin.authority.service.IRoleService;
import com.admin.authority.service.IUserService;

/**
 * 事务交叉访问死锁测试
 * @author lzx
 * @version 1.0
 * @create_date 下午9:07:43
 */
@Service
public class TestQuartz {

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	
	@SuppressWarnings("static-access")
	@Transactional
	public void method1(int i){
		System.out.println("-------------------------------方法1请求角色更新---------------------------------");
		Role role = new Role();
		role.setId(3);
		role.setName("vip"+i);
		roleService.updateRole(role);
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------方法1请求用户更新---------------------------------");
		User user = new User();
		user.setId(1);
		user.setUsername("lzx"+i);
		userService.updateUser(user);
		System.out.println("-------------------------------方法1执行完毕---------------------------------");
	}
	
	@Transactional
	public void method2(int i){
		System.out.println("-------------------------------方法2请求用户更新---------------------------------");
		User user = new User();
		user.setId(1);
		user.setUsername("lzx"+i);
		userService.updateUser(user);
		System.out.println("-------------------------------方法2请求角色更新---------------------------------");
		Role role = new Role();
		role.setId(3);
		role.setName("vip"+i);
		roleService.updateRole(role);
		System.out.println("-------------------------------方法2执行完毕---------------------------------");
	}
}
