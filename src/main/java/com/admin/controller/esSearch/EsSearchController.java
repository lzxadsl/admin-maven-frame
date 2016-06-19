package com.admin.controller.esSearch;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admin.basic.utils.StringUtils;
import com.admin.esSearch.service.IEsSearchService;

/**
 * es测试
 * @author lizx
 * @data 2016-6-5下午2:09:09
 */
@Controller
@RequestMapping("/es/*")
public class EsSearchController {

	@Autowired
	private IEsSearchService esSearchService;
	
	/**
	 * 跳转es搜索页
	 * @author lizx
	 * @data 2016-6-19上午10:16:19
	 */
	@RequestMapping("esSearch.htm")
	public String esSearch(){
		return "/esSearch/searchtip";
	}
	
	/**
	 * 全文搜引
	 * @author lizx
	 * @data 2016-6-19上午10:32:00
	 * @param keyword 关键词
	 */
	@RequestMapping("esFullSearch.json")
	public @ResponseBody List<Map<String, Object>> esFullSearch(String keyword){
		List<Map<String, Object>> result = esSearchService.fullSearch(keyword);
		return result;
	}
}
