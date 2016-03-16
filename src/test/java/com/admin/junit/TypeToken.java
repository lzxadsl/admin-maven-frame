package com.admin.junit;


/**
 * 获取泛型对象的Class类型
 * @author lzx
 * @version 1.0
 * @create_date 下午10:24:06
 */
public class TypeToken<T> {

	final Class<T> type;
	
	@SuppressWarnings("unchecked")
	public TypeToken(){
		this.type = (Class<T>) getClass();
	}
	
	public Class<T> getType(){
		return type;
	}
}
