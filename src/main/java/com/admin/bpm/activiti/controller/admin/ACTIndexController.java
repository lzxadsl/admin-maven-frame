package com.admin.bpm.activiti.controller.admin;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**流程管理首页
 * @author linjian
 * @version 1.0
 * @create_date 2015-10-31 下午3:57:57
 */
@Controller
@RequestMapping(value = "/admin/index/*")
public class ACTIndexController {
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping(value = "index")
	public String index(ModelMap model){
		List<ProcessDefinition>  listProDef = repositoryService.createProcessDefinitionQuery().list();
		model.put("listProDef", listProDef);
		return "admin/index";
	}
}
