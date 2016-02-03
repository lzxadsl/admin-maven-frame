package com.admin.authority.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.authority.model.SysRole;
import com.admin.authority.model.SysUser;
import com.admin.authority.service.ISysRoleService;
import com.admin.authority.service.ISysUserService;

/**
 * 事务交叉访问死锁测试
 * @author lzx
 * @version 1.0
 * @date 下午9:07:43
 */
@Service
public class TestQuartz {

	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserService sysUserService;
	
	@SuppressWarnings("static-access")
	@Transactional
	public void method1(int i){
		System.out.println("-------------------------------方法1请求角色更新---------------------------------");
		SysRole role = new SysRole();
		role.setId(3);
		role.setName("vip"+i);
		sysRoleService.updateRole(role);
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------方法1请求用户更新---------------------------------");
		SysUser user = new SysUser();
		user.setId(1);
		user.setUserName("lzx"+i);
		sysUserService.updateUser(user);
		System.out.println("-------------------------------方法1执行完毕---------------------------------");
	}
	
	@Transactional
	public void method2(int i){
		System.out.println("-------------------------------方法2请求用户更新---------------------------------");
		SysUser user = new SysUser();
		user.setId(1);
		user.setUserName("lzx"+i);
		sysUserService.updateUser(user);
		System.out.println("-------------------------------方法2请求角色更新---------------------------------");
		SysRole role = new SysRole();
		role.setId(3);
		role.setName("vip"+i);
		sysRoleService.updateRole(role);
		System.out.println("-------------------------------方法2执行完毕---------------------------------");
	}
}
