package com.admin.extend.async;

import java.lang.reflect.Proxy;

/**
 * 异步处理调用入口
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-3 下午1:51:36
 */
public class AsyncHandel{

	//target必须要实现接口
	private final Object target;
	
	/**
	 * 构造函数
	 * @param target 被代理对象
	 */
	public AsyncHandel(Object target){
		this.target = target;
	}
	
	public Object proxy(){
		AsyncProxyHandler h = new AsyncProxyHandler(target);
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),h);
	}
	
	public static void main(String[] args) {
		/*
		AsyncHandel handel = new AsyncHandel(new UserDao());
		IUserDao proxy = (IUserDao) handel.proxy();

		proxy.save("保时捷",new IAsyncCallBack() {

			@Override
			public void success(Object result) {
				System.out.println("调用成功，打印返回值："+result);
			}

			@Override
			public void error() {
				System.out.println("------调用出错啦------");
			}

			@Override
			public void before() {
				System.out.println("------调用前执行------");
			}
		});
		System.out.println("----------------------------------");
		*/
	}
}
