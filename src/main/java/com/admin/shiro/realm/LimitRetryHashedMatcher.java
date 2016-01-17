package com.admin.shiro.realm;

import org.apache.shiro.authc.AuthenticationInfo; 
import org.apache.shiro.authc.AuthenticationToken; 
import org.apache.shiro.authc.ExcessiveAttemptsException; 
import org.apache.shiro.authc.credential.HashedCredentialsMatcher; 

import com.admin.basic.utils.EhcacheUtil;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 限制登录次数，如果5次出错，锁定1个小时 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-8 下午1:13:31
 */
public class LimitRetryHashedMatcher extends HashedCredentialsMatcher{

	
	@Override 
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) { 
        String username = (String) token.getPrincipal(); 
        //retrycount + 1 
        Object element = EhcacheUtil.getItem(username); 
        if (element == null) { 
            EhcacheUtil.putItem(username,1); 
            element = 0; 
        }else{ 
            int count = Integer.parseInt(element.toString()) + 1; 
            element = count; 
            EhcacheUtil.putItem(username,element); 
        } 
        AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString())); 
        if (retryCount.incrementAndGet() > 5) { 
        	//if retrycount >5 throw 
            throw new ExcessiveAttemptsException("密码输入错误次数超过5次，请1小时后再试！"); 
        } 
        boolean matches = super.doCredentialsMatch(token, info); 
        if(matches){ 
        	//clear retrycount 
            EhcacheUtil.removeItem(username); 
        }else{
        	throw new ExcessiveAttemptsException("密码输入有误，请重新输入！"); 
        } 
        return matches; 
    } 
}
