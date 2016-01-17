package com.admin.basic.utils;

import java.lang.reflect.Field;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.util.Locale;
import java.util.Map;  
import java.util.TreeMap;
/**
 * JavaBean工具类
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-9-11 下午4:12:56
 */
public class BeanUtil {

		
	/**
	 * 将Bean转换成Map
	 * @author LiZhiXian
	 * @param model 实体对象
	 * @param convertNullValue 为false时只转换值不为空的属性,为true时转换全部属性
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> convert2Map(Object model,boolean convertNullValue){ 
		Map<String, Object> resultMap = new TreeMap<String, Object>();  
		try {
	        // 获取实体类的所有属性，返回Field数组  
	        Field[] field = model.getClass().getDeclaredFields();  
	        for (int i = 0; i < field.length; i++) { // 遍历所有属性  
	            String name = field[i].getName(); // 获取属性的名字  
	            // 获取属性的类型  
	            //String type = field[i].getGenericType().toString();  
	            Method m = model.getClass().getMethod("get" + UpperCaseField(name));
				Object value =  m.invoke(model); // 调用getter方法获取属性值  
                if(convertNullValue){
                	resultMap.put(name, value);  
                }
                else{
                	//只转换值不为空的属性
                	if (value != null) {  
	                    resultMap.put(name, value);  
	                }
                } 
	        } 
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
        
        return resultMap;  
    }  
  
	/**
	 * 将Map转换成Bean
	 * @author LiZhiXian
	 * @param map 
	 * 注意：map属性必须大写，如：NAME
	 * @param model 实体对象
	 * @return Object
	 */
	public static Object convert2Bean(Map<String,Object> map, Object model) {  
        Class<? extends Object> classz = model.getClass();  
        Method[] methods = classz.getMethods();  
        for (Method method : methods) {  
            String methodName = method.getName();  
            if (methodName.startsWith("set")) {  
                String propertyName = methodName.substring(3).toUpperCase(Locale.getDefault());  
                Object value = map.get(propertyName);  
                try {  
                    method.invoke(model, value);  
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();  
                } catch (InvocationTargetException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return model;  
    }  
	

	/**
	 * 转化字段首字母为大写  
	 * @param fieldName 字段名
	 */
    private static String UpperCaseField(String fieldName) {  
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName  
                .substring(0, 1).toUpperCase());  
        return fieldName;  
    } 
    
}
