package com.admin.extend.async;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-3 上午10:54:37
 */
public interface IAsyncCallBack {

	/**
	 * 成功回调
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-3 上午10:55:13
	 */
	public void success(Object result);
	/**
	 * 失败回调
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-3 上午10:55:21
	 */
	public void error();
	
	/**
	 * 调用前
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-3 上午11:23:01
	 */
	public void before();
	
}
