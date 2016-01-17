package com.admin.ehcache.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.admin.ehcache.ICacheUserService;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

/**
 * 缓存注解测试
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-17 上午9:46:48
 */
@Service
public class CacheUserService implements ICacheUserService {

	/**
	 * spring 缓存注解测试，添加缓存
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-17 上午9:46:48
	 */
	@Override
	@Cacheable(value="defaultCache",key="#id")
	public String getUser(Integer id) {
		System.out.println("获取用户被调用了。。。");
		return "获取用户。。。";
	}

	/**
	 * spring 缓存注解测试，更新缓存
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-17 上午10:32:57
	 */
	@Override
	@CacheEvict(value="defaultCache",key="#id")
	public String addUser(Integer id) {
		System.out.println("添加用户被调用了，准备更新缓存。。。");
		return "添加用户。。。";
	}

	/**
	 * ehcache 缓存注解测试，添加缓存
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-17 上午10:41:12
	 */
	@Override
	@com.googlecode.ehcache.annotations.Cacheable(cacheName="defaultCache")
	public String list(Integer id) {
		System.out.println("添加用户被调用了，使用ehcache。。。"+id);
		return "获取用户类表。。。";
	}

	/**
	 * ehcache 缓存注解测试,更新缓存
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2015-12-17 上午10:41:12
	 */
	@Override
	@TriggersRemove(cacheName="defaultCache",when=When.AFTER_METHOD_INVOCATION,removeAll=true)
	public String update(Integer id) {
		System.out.println("更新用户被调用了，准备更新缓存，使用ehcache。。。"+id);
		return "更新用户。。。";
	}
}
