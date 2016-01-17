package com.admin.system.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 白名单全局常量
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-11-13 下午1:31:34
 */
public class WhiteListConstant {
	
	private final static WhiteListConstant wlc = new WhiteListConstant();
	
	private WhiteListConstant(){
		super();
	}
	//白名单列表
	private List<String> whiteList = new ArrayList<String>(); 
	//是否全部不拦截
	private boolean noIntercept = false;
	/**
	 * 获取实例
	 */
	public static WhiteListConstant getInstance(){
		return wlc;
	}
	/**
	 * 判断该访问地址是否在白名单中
	 * @param path 访问地址路径
	 */
	public boolean isExist(String path){
		boolean exist = false;
		if(noIntercept || whiteList.contains(path)){
			exist = true;
		}
		return exist;
	}
	/**
	 * 获取白名单列表
	 */
	public List<String> getWhiteList() {
		return whiteList;
	}
	/**
	 * 设置白名单列表（预留）
	 */
	public void setWhiteList(List<String> whiteList) {
		if(this.whiteList.size() < 1){
			this.whiteList = whiteList;
		}
	}
	/**
	 * 初始化白名单列表
	 */
	public void initWhiteList(){
		try {
			Properties prop = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/power.properties");
			prop.load(in);
			String strList = prop.get("whiteList") == null ? "" : String.valueOf(prop.get("whiteList"));
			if(strList != null && "/*".equals(strList)){
				noIntercept = true;
			}else{
				List<String> wList = Arrays.asList(strList.split(";"));
				this.whiteList = wList;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
