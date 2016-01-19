package com.admin.bpm.activiti.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.admin.basic.model.PageData;

/**流程定义
 * @author linjian
 * @version 2.0
 * @create_date 2015-11-9 下午5:16:06
 */
@Controller
@RequestMapping(value = "/admin/processDefinition/*")
public class ACTReProcessDefinitionController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	
	@RequestMapping("query")
	public String query(){
		return "admin/processDefination/query";
	}
	
	/**流程定义
	 * @author linjian
	 * @create_date 2015-11-9 下午5:17:08
	 * @param model
	 * @param pageData
	 * @return
	 */
	@RequestMapping(value = "ajaxList",produces = "application/json")
	public @ResponseBody Map<String, Object> ajaxList(ModelMap model, PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		List<ProcessDefinition>  listProDef = repositoryService.createProcessDefinitionQuery().list();
		List<Map<String, Object>> listPro = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition processDefinition : listProDef) {
			Map<String, Object> itm = new HashMap<String, Object>();
			itm.put("id", processDefinition.getId());
			itm.put("name", processDefinition.getName());
			itm.put("category", processDefinition.getCategory());
			itm.put("version", processDefinition.getVersion());
			itm.put("key", processDefinition.getKey());
			itm.put("sourceName", processDefinition.getResourceName());
			itm.put("diagramResourceName", processDefinition.getDiagramResourceName());
			itm.put("deploymentId", processDefinition.getDeploymentId());
			listPro.add(itm);
		}
		data.put("rows", listPro);
		data.put("total", "99");
		return data;
	}
	
	/**读取图片文件
	 * @author linjian
	 * @create_date 2015-11-10 下午5:05:18
	 * @param type 0代表图片,1代表xml文件
	 * @param deploymentId 流程部署id
	 * @param resourceName 文件名称
	 * @param request 请求对象
	 * @param response 返回浏览器响应对象
	 */
	@RequestMapping(value = "processResource")
	public void processResource(int type,String deploymentId,
			String resourceName,
			HttpServletRequest request,HttpServletResponse response){
		InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
		response.setContentType("image/png");
		try {
			OutputStream stream = response.getOutputStream();
			byte[] data = new byte[1024];
			int len = -1;
			len = (in.read(data));
			while(len != -1){
				stream.write(data,0,len);
				len = (in.read(data));
			}
			stream.flush();
			in.close();
	        stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "processDefResource")
	public void processDefResource(String proDefId,
			HttpServletRequest request,HttpServletResponse response){
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(proDefId).singleResult();
		InputStream in = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
		response.setContentType("image/png");
		try {
			OutputStream stream = response.getOutputStream();
			byte[] data = new byte[1024];
			int len = -1;
			len = (in.read(data));
			while(len != -1){
				stream.write(data,0,len);
				len = (in.read(data));
			}
			stream.flush();
			in.close();
	        stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**删除部署
	 * @author linjian
	 * @create_date 2015-11-10 下午6:16:39
	 * @param key 关键字
	 * @return 删除后列表
	 */
	@RequestMapping(value = "delete")
	public String delete(String deploymentId){
		repositoryService.deleteDeployment(deploymentId, true);
		return "admin/processDefination/query";
	}
	
	/**启动流程审批
	 * @author linjian
	 * @create_date 2015-11-14 上午11:50:09
	 * @param deploymentId 部署的流程id
	 * @return 流程列表
	 */
	@RequestMapping(value = "startProcess")
	public String startProcess(String deploymentId){
		//qingjiaonlyprocess1114
		runtimeService.startProcessInstanceByKey("qingjiaonlyprocess1114");
		return "admin/processDefination/query";
	}
	
}
