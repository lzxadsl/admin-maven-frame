package com.admin.bpm.activiti;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**流程管理首页
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-10-31 下午3:57:57
 */
@Controller
@RequestMapping(value = "/bpm/index/*")
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
