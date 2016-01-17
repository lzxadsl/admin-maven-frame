package com.admin.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.admin.authority.quartz.TestQuartz;

/**
 * 首页控制器
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-13 下午3:52:08
 */
@Controller
@RequestMapping(value="/index/*")
public class IndexController {

	@Autowired
	private TestQuartz testQuartz;
	
	/**
	 * 跳转到首页
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-13 下午3:54:33
	 */
	@RequestMapping(value="index.htm")
	public String index(){
		//testQuartz.method1(1);
		return "index";
	}
	
	@RequestMapping(value="index1.htm")
	public String index1(){
		testQuartz.method2(2);
		return "index";
	}
}
