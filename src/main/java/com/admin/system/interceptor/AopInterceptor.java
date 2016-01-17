package com.admin.system.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * aop切面拦截器
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-7-15 上午11:27:06
 */
@Aspect
@Service
public class AopInterceptor {

	/**
	 * 定义一个切入点
	 * public 公有方法
	 * 第一星代表任何返回值类型
	 * 搜索位置com.lzx.user.service.impl 下所以服务类下的所以方法,impl后第一个点星是到类，第二个.*(..)类下所有方法  
	 * public * com.lzx.user.service.impl.*.*(..) 匹配com.lzx.user.service.impl下所有类的所以公有方法
	 * *[所有类型返回值] com.admin.controller.[所有包（什么都不写代表包）].*[所有类].*(..)[类下所有方法]
	 * 被拦截的方法如果有加事务，那么@After 和@AfterReturning 中如果报错的话，被拦截的方法也会跟着回滚
	 */
	@SuppressWarnings("unused")
	//@Pointcut("execution(* com.admin.controller..*.*(..))") 
	@Pointcut("execution(* com.admin.authority.service.impl.*.*(..))") 
    private void log(){}
	
  /*  //带参数  
    @Before("log() && args(name)")  
    public void before(String name){  
        System.out.println(name);  
        System.out.println("前置通知");  
    } */
    
    @Before("log()")//等同于@Before("execution(public * com.lzx.user.service.impl.*.*(..))")
    public void addStartLog(JoinPoint joinPoint){
    	Object[] obj = joinPoint.getArgs();//获取被拦截方法参数
    	System.out.println("前置：添加开始日志"+obj);  
    }  
      
    @After("log()")  
    public void addEndLog(){//不管被拦截的方法有没有报错都会执行  
        System.out.println("后置：添加结束日志");  
        
    }  
      
    @AfterReturning("log()")  
    @Transactional
    public void afterReturn(){//被拦截的方法没报错才会执行
        System.out.println("返回后通知");  
    } 
    
    @AfterThrowing("log()")  
    public void afterThrow(){  
        System.out.println("抛出异常后通知");  
    }  
      
    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("进入环绕通知");  
        Object object = pjp.proceed();//执行该方法  
        System.out.println("退出方法");  
        return object;  
    } 
}
