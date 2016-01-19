package com.admin.bpm.activiti.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.admin.basic.model.PageData;

/**流程模块
 * @author linjian
 * @version 2.0
 * @create_date 2015-11-3 下午4:59:21
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value="/admin/model/*")
public class ACTReModelController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("query")
	public String query(){
		return "admin/model/query";
	}
	
	/**查询流程模块
	 * @author linjian
	 * @create_date 2015-11-9 下午3:54:13
	 * @param model
	 * @param pageData
	 * @return
	 */
	@RequestMapping(value = "ajaxList")
	public @ResponseBody Map<String, Object> ajaxList(ModelMap model, PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		int s = 0;
		int e = 1;
		if(pageData != null){
			s = (pageData.getPage() - 1) * pageData.getPageSize();
			e = pageData.getPageSize();
		}
		List<Model> listModel = repositoryService.createModelQuery().listPage(s, e);
		data.put("rows", listModel);
		data.put("total", "99");
		return data;
	}
	
	/**部署流程定义
	 * @author linjian
	 * @create_date 2015-11-10 下午5:47:05
	 * @param id 流程模板的ID
	 * @return 部署后的流程列表
	 */
	@RequestMapping(value = "deployment")
	public String deployment(String id){
		Model modelData = repositoryService.getModel(id);
		ObjectNode modelNode = null;
		try {
			modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		byte[] bpmnBytes = null;            
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);            
		bpmnBytes = new BpmnXMLConverter().convertToXML(model); 
		try {
			String bpmnString=new String(bpmnBytes,"UTF-8");
			String processName = modelData.getName() + ".bpmn20.xml";            
			repositoryService.createDeployment().name(modelData.getName())
					.addString(processName, bpmnString)
					.deploy();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return "admin/model/query";
	}
	
	/**新建模型
	 * @author linjian
	 * @create_date 2015-11-11 下午1:59:19
	 * @param name 模型名称
	 * @param key 模型关键字
	 * @param description 描述
	 * @param request 浏览器对象
	 * @param response 浏览器对象
	 * @return 返回创建结果
	 */
	@RequestMapping(value = "create")
	public @ResponseBody Map<String, String> create(@RequestParam("name") String name,@RequestParam("key") String key,@RequestParam("description") String description,
			HttpServletRequest request,HttpServletResponse response){
			Map<String, String> rdata = new HashMap<String, String>();
			rdata.put("status", "200");
		 try {
		      ObjectMapper objectMapper = new ObjectMapper();
		      ObjectNode editorNode = objectMapper.createObjectNode();
		      editorNode.put("id", "canvas");
		      editorNode.put("resourceId", "canvas");
		      ObjectNode stencilSetNode = objectMapper.createObjectNode();
		      stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		      editorNode.put("stencilset", stencilSetNode);
		      Model modelData = repositoryService.newModel();
		      ObjectNode modelObjectNode = objectMapper.createObjectNode();
		      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		      modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		      description = StringUtils.defaultString(description);
		      modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		      modelData.setMetaInfo(modelObjectNode.toString());
		      modelData.setName(name);
		      modelData.setKey(StringUtils.defaultString(key));
		      repositoryService.saveModel(modelData);
		      repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		      //response.sendRedirect(request.getContextPath() + "/modeler/modeler.html?modelId=" + modelData.getId());
		      rdata.put("modelId", modelData.getId());
		    } catch (Exception e) {
		      e.printStackTrace();
		      rdata.put("status", "500");
		    }
		return rdata;
	}
	
	/**删除流程模型
	 * @author linjian
	 * @create_date 2015-11-11 下午4:18:54
	 * @param id 模型id
	 * @return 删除结果
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody String delete(@RequestParam("id") String id){
		repositoryService.deleteModel(id);
		return "200";
	}
	
	/**弹出框编辑内容
	 * @author linjian
	 * @create_date 2015-11-12 上午10:00:35
	 * @return 编辑页面元素
	 */
	@RequestMapping(value = "editWin")
	public String editWin(){
		return "admin/model/editWin";
	}
}
