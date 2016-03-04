package com.admin.extend.sql;

/**
 * 数据源key获取、存放
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-4 上午9:04:58
 */
public class DynamicDataSourceHolder {

	public static final ThreadLocal<String> holder = new ThreadLocal<String>();
	
	public static void putDataSourceKey(String name) {
        holder.set(name);
    }

    public static String getDataSouceKey() {
        return holder.get();
    }
    
    public static void clearDataSourceKey(){
    	holder.remove();
    }
}
