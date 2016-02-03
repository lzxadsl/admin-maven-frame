package com.admin.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.admin.authority.model.SysUser;
import com.admin.basic.model.PageData;
import com.admin.business.model.CostReimburse;
import com.admin.business.service.ICostReimburseService;

/**
 * 费用报销控制层
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-2-2 下午1:42:05
 */
@Controller
@SessionAttributes("user")
@RequestMapping("/business/costReimburse/*")
public class CostReimburseController {

	@Autowired
	private ICostReimburseService costReimburseService;
	
	/**
	 * 查询列表
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-2 下午1:51:24
	 */
	@RequestMapping("list.htm")
	public String list(){
		return "business/costReimburse/list";
	}
	
	/**
	 * 列表数据加载
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-2 下午1:59:56
	 */
	@RequestMapping("ajaxList.json")
	public @ResponseBody Map<String, Object> ajaxList(String name,String state,@ModelAttribute("user") SysUser user,PageData pageData){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user.getId());//限制只能查自己
		if(StringUtils.isNotBlank(name)){//申请人名称
			params.put("name",name);
		}
		if(StringUtils.isNotBlank(state)){//任务状态
			params.put("state",state);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CostReimburse> list = costReimburseService.listPage(params, pageData);
		map.put("rows",list);
		map.put("total",pageData.getTotalSize());
		return map;
	}
	/**
	 * 跳转新增、修改、明细
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-2 下午2:28:46
	 */
	@RequestMapping("{page}.htm")
	public ModelAndView jumpPage(Integer id,ModelAndView model,@PathVariable String page){
		if(id != null){
			model.addObject("obj",costReimburseService.get(id));
		}
		model.setViewName("business/costReimburse/"+page);
		return model;
	}
	
	/**
	 * 保存操作
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-2-3 下午4:05:16
	 */
	@RequestMapping(value="save.do",method=RequestMethod.POST)
	public @ResponseBody String save(CostReimburse entity){
		String state = "200";
		try {
			costReimburseService.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			state = "500";
		}
		return state;
	}
}
