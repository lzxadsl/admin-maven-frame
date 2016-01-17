package com.admin.system.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * 缓存方法拦截器
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-16 下午3:51:23
 */
public class MethodCacheInterceptor implements MethodInterceptor,InitializingBean{

	private final Logger logger = Logger.getLogger(MethodCacheInterceptor.class);
	
	private Cache cache;
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * bean 被初始化完成后执行
	 * <p>Spring提供了当一个Bean初始化后执行方法的扩展点：InitializingBean。</p>
	 * 这里的初始化指的就是当该Bean的属性被注入完成后（注意：这里并不是所有属性都需要被设置），
	 * 所以InitializingBean接口提供的方法名称也很形象：afterPropertiesSet()
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-16 下午3:54:47
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug(cache + " A cache is required. Use setCache(Cache) to provide one.");
		
	}

	/**
	 * 核心处理逻辑
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-16 下午3:54:47
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Object result = null;
		Element element = null;
        synchronized (this) {
            element = cache.get(cacheKey);
            if (element == null) {//该方法缓存不存在
            	logger.debug(cacheKey + "加入到缓存： " + cache.getName());
                // 调用实际的方法
                result = invocation.proceed();
                //对象要被序列
                element = new Element(cacheKey, (Serializable)result);
                cache.put(element);
            } else {
            	logger.debug(cacheKey + "使用缓存： " + cache.getName());
            }
        }
		return element.getObjectValue();
	}

	/**
	 * 获取按指定规则生成的缓存key
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-16 下午4:03:23
	 * @param targetName 完整类路径
	 * @param methodName 方法名
	 * @param arguments 参数
	 * @return 完整方法名称
	 */
	private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        logger.debug("完整方法名称："+sb.toString());
        return sb.toString();
    }
}
