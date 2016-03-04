package com.admin.extend.async;

import java.lang.reflect.Method;

/**
 * 异步线程
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-3 上午10:51:04
 * @param T 返回值参数类型
 */
public class AsyncRunnableExecutor implements Runnable{

	//回调接口
	private final IAsyncCallBack callBack;
	//要执行异步的目标对象
	private final Object target;
	//目标对象对应的方法
	private final Method method;
	//方法对应的参数
	private final Object[] args;
	
	public AsyncRunnableExecutor(Object target, Method method, Object[] args,IAsyncCallBack callback){
		this.callBack = callback;
		this.target = target;
		this.method = method;
		this.args = args;
	}
	
	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-3 上午10:51:33
	 */
	@Override
	public void run() {
		Object result = null;
		boolean success = false;
		try {
			callBack.before();
			result = method.invoke(target, args);
			success = true;
			Thread.sleep(5000);
		} catch (Exception e) {
			callBack.error();
		}
		//成功回调
		if (success) {
			// 不要捕获在onSuccess中业务逻辑自身的异常
			callBack.success(result);
		}
	}

}
