package com.admin.extend.sql;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * 数据源切面，注解解析
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-4 上午9:42:34
 */
public class DataSourceAspect {

	/**
	 * 调用前获取注解参数
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午11:27:17
	 */
	public void before(JoinPoint point){
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();
        
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSourceKey(data.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 重置KEY为空
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午11:27:49
	 */
	public void after(){
		DynamicDataSourceHolder.clearDataSourceKey();
	}
}
