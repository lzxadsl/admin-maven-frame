package com.lzx.elasticsearch.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件工具类
 * @author lizx
 * @data 2016-5-25上午10:53:06
 */
public class PropertiesUtils {

	private static Map<String, Object> propMap = new HashMap<String, Object>();
	
	static{
		System.out.println("-------------初始化配置文件-------------");
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("search.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			Enumeration<?> propertyNames =  prop.propertyNames();
			while (propertyNames.hasMoreElements()) {
				String name = (String) propertyNames.nextElement();
				propMap.put(name, prop.get(name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public PropertiesUtils(){
		System.out.println("--------------------------");
	}
	
	/**
	 * 获取配置文件值
	 * @author lizx
	 * @data 2016-5-25上午11:04:46
	 */
	public static String getValue(String name){
		System.out.println(name);
		return propMap.get(name).toString();
	}
	
	public static void main(String[] args) {
		String value = PropertiesUtils.getValue("basic.es.address");
		System.out.println(value);
	}
}
