package com.admin.esSearch.service;

import java.util.List;
import java.util.Map;

/**
 * es 服务接口
 * @author lizx
 * @data 2016-6-19上午11:27:48
 */
public interface IEsSearchService {

	/**
	 * 全文查询
	 * @author lizx
	 * @data 2016-6-19上午11:29:43
	 */
	public List<Map<String, Object>> fullSearch(String keyword);
}
