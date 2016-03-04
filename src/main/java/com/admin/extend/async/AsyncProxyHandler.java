package com.admin.extend.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-3 上午11:12:09
 */
public class AsyncProxyHandler implements InvocationHandler{

	//目标对象 (被代理对象)  
    private Object target;  
    /** 
     * 构造方法 
     * @param target 目标对象  
     */  
    public AsyncProxyHandler(Object target) {  
        super();  
        this.target = target;  
    } 
	/**
	 * 
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-3 上午11:12:54
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		IAsyncCallBack callback = getCallBack(args);
		if(callback == null)throw new RuntimeException("找不到回调函数，请检查");
		AsyncRunnableExecutor run = new AsyncRunnableExecutor(target, method, args,callback);
		Thread thread = new Thread(run);
		thread.start();
		return null;
	}

	private IAsyncCallBack getCallBack(Object[] args){
		IAsyncCallBack callBack = null;
		for(Object obj:args){
			if(obj instanceof IAsyncCallBack){
				callBack = (IAsyncCallBack)obj;
				break;
			}
		}
		return callBack;
	}
	
}
