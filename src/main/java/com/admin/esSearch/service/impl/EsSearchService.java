package com.admin.esSearch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.admin.esSearch.service.IEsSearchService;
import com.lzx.elasticsearch.entity.EsQueryResult;
import com.lzx.elasticsearch.jest.JestUtil;

/**
 * @author lizx
 * @data 2016-6-19上午11:30:11
 */
@Service
public class EsSearchService implements IEsSearchService {

	private final static String INDEX = "lzxes";
	@Override
	public List<Map<String, Object>> fullSearch(String keyword) {
		String query = "{\"query\":{\"filtered\":{\"query\": {\"bool\": {\"must\": [{\"query_string\": {\"default_field\":\"ilab\",\"query\": \""+(keyword!=null?keyword:"*")+"\"}}]}}}}}";
		System.out.println(query);
		String json = JestUtil.searchIndex(INDEX, query);
		EsQueryResult result = JestUtil.wrapSearchResult(json, false);
		//SearchResult result = JestUtil.searchIndexHandle(INDEX, null, query);
		//List<Hit<Map<String,Object>, Void>> hits = result.getHits(new TypeToken<Map<String,Object>>().getType());
		return result.getList();
	}

}
