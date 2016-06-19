package com.admin.controller.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * es测试
 * @author lizx
 * @data 2016-6-5下午2:09:09
 */
@Controller
@RequestMapping("/es/*")
public class EsController {

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
	 */
	@RequestMapping("esFullSearch.json")
	public @ResponseBody List<Map<String, Object>> esFullSearch(){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		return result;
	}
}
