package com.admin.controller.authority;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-9 上午8:18:49
 */
public class TestClass {

	static int i = 0;
	public TestClass(){
		System.out.println("测试类构造函数执行。。。");
	}
	
	static{
		System.out.println("测试类静态方法执行。。。");
	}
	
	public static void test(){
		i++;
		System.out.println("测试类test方法被调用了"+i+"次。。。");
	}
}
