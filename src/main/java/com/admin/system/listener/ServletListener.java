package com.admin.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.admin.system.constant.WhiteListConstant;

/**
 * tomcate启动监听器
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-11-13 上午11:29:31
 */
public class ServletListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce){
		
	}
	/**
	 * 服务器启动时初始化系统信息
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WhiteListConstant.getInstance().initWhiteList();
		System.out.println("------------白名单初始化完毕--------------");
	}
}
