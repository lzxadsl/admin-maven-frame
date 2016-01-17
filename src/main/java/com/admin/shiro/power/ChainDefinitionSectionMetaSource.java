package com.admin.shiro.power;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;

/**
 * URL访问过滤规则
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-12-4 下午4:04:53
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{

	private String filterChainDefinitions;  

	/** 
     * 默认premission字符串 
     */  
    public static final String PREMISSION_STRING="perms[\"{0}\"]";
    
	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();  
        //加载默认的url  
        ini.load(filterChainDefinitions);  
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,  
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接  
        section.put("/**","roles[admin]");
		return section;
	}

	/** 
     * 通过配置文件filterChainDefinitions对默认的url过滤定义 
     * @param filterChainDefinitions 默认的url过滤定义 
     */  
    public void setFilterChainDefinitions(String filterChainDefinitions) {  
        this.filterChainDefinitions = filterChainDefinitions;  
    }  

    
	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
