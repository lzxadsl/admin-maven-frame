package com.admin.authority.quartz;

import com.admin.basic.quartz.BaseQuartz;

/**
 * 任务调度测试
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-13 下午1:33:17
 */
public class UserQuartz extends BaseQuartz{

	static int i = 1;
	public UserQuartz(){
		System.out.println("--------------类被创建------------------");
	}
	/**
	 * 调度方法
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-13 下午1:39:14
	 */
	@Override
	public void execute() {
		System.out.println("----------------调度执行----------------"+i++);
	}
}
