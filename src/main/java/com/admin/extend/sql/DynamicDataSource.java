package com.admin.extend.sql;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源扩展
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-3-4 上午9:02:13
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	/**
	 * 获取数据源KEY
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-3-4 上午9:02:49
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSouceKey();
	}

}
