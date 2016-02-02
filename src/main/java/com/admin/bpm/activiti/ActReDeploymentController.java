package com.admin.bpm.activiti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.admin.basic.model.PageData;

/**流程部署信息
 * @author LiZhiXian
 * @version 2.0
 * @date 2015-11-10 下午6:23:25
 */
@Controller
@RequestMapping(value="/bpm/deployment/*")
public class ActReDeploymentController {
	@Autowired
	private RepositoryService repositoryService;
	
	/**流程部署查询
	 * @author LiZhiXian
	 * @date 2015-11-10 下午6:25:21
	 * @return 查询列表结果
	 */
	@RequestMapping(value = "list.htm")
	public String query(){
		return "bpm/deployment/list";
	}
	
	/**流程部署列表数据
	 * @author LiZhiXian
	 * @date 2015-11-10 下午6:27:46
	 * @param model 模块
	 * @param pageData 分页参数
	 * @return JSON格式的流程数据
	 */
	@RequestMapping(value = "ajaxList.json")
	public @ResponseBody Map<String, Object> ajaxList(String name, PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		DeploymentQuery query = repositoryService.createDeploymentQuery();
		if(StringUtils.isNotEmpty(name)){
			query.deploymentName(name);
		}
		List<Deployment> listDeployment = query.list();
		List<Map<String, Object>> listDept = new ArrayList<Map<String,Object>>();
		for (Deployment deployment : listDeployment) {
			Map<String, Object> itm = new HashMap<String, Object>();
			itm.put("id", deployment.getId());
			itm.put("name", deployment.getName());
			itm.put("deploymentTime", deployment.getDeploymentTime());
			itm.put("category", deployment.getCategory());
			ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery()
							.deploymentId(deployment.getId()).singleResult();
			itm.put("version", procDef.getVersion());
			itm.put("diagramResourceName", procDef.getDiagramResourceName());
			listDept.add(itm);
		}
		data.put("rows", listDept);
		data.put("total",query.count());
		return data;
	}
}
