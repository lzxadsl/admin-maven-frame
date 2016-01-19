package com.admin.bpm.activiti.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.admin.basic.model.PageData;


/**流程实例
 * @author linjian
 * @version 2.0
 * @create_date 2015-11-10 下午3:59:08
 */
@Controller
@RequestMapping(value = "/admin/processInstance/*")
public class ACTProcessInstanceController {
	@Autowired
	private RuntimeService runtimeService;
	
	/**流程实例页面
	 * @author linjian
	 * @create_date 2015-11-10 下午4:01:03
	 * @return
	 */
	@RequestMapping(value = "query")
	public String query(){
		return "admin/processInstance/query";
	}
	
	/**获取流程实例列表
	 * @author linjian
	 * @create_date 2015-11-10 下午4:02:01
	 * @param model 模块
	 * @param pageData 分页参数
	 * @return 流程实例json格式数据
	 */
	@RequestMapping(value = "ajaxList",produces = "application/json")
	public @ResponseBody Map<String, Object> ajaxList(ModelMap model, PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		List<ProcessInstance> listPro = runtimeService.createProcessInstanceQuery().list();
		
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		for (ProcessInstance processInstance : listPro) {
			Map<String, Object> itm = new HashMap<String, Object>();
			itm.put("id", processInstance.getId());
			itm.put("name", processInstance.getName());
			itm.put("deploymentId", processInstance.getDeploymentId());
			itm.put("processDefinitionName", processInstance.getProcessDefinitionName());
			itm.put("processDefinitionVersion", processInstance.getProcessDefinitionVersion());
			listData.add(itm);
		}
		data.put("rows", listData);
		data.put("total", "99");
		return data;
	}
}
