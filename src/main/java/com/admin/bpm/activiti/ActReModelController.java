package com.admin.bpm.activiti;

import java.io.ByteArrayInputStream;
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
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.admin.basic.model.PageData;

/**流程模块
 * @author LiZhiXian
 * @version 2.0
 * @date 2015-11-3 下午4:59:21
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value="/bpm/model/*")
public class ActReModelController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping("list.htm")
	public String query(){
		return "bpm/model/list";
	}
	
	/**查询流程模块
	 * @author LiZhiXian
	 * @date 2015-11-9 下午3:54:13
	 * @param model
	 * @param pageData
	 * @return
	 */
	@RequestMapping(value = "ajaxList.json")
	public @ResponseBody Map<String, Object> ajaxList(ModelMap model,String name,String key,PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		int s = pageData.getOffset();
		int e = pageData.getLimit();
		ModelQuery query = repositoryService.createModelQuery();
		if(StringUtils.isNotEmpty(name)){
			query.modelName(name);
		}
		if(StringUtils.isNotEmpty(key)){
			query.modelKey(key);
		}
		
		List<Model> listModel = query.orderByCreateTime().desc().listPage(s, e);
		data.put("rows", listModel);
		data.put("total",query.count());
		return data;
	}
	
	/**部署流程定义
	 * @author LiZhiXian
	 * @date 2015-11-10 下午5:47:05
	 * @param id 流程模板的ID
	 * @return status
	 */
	@RequestMapping(value = "deployment.do")
	public @ResponseBody String deployment(String id){
		Model modelData = repositoryService.getModel(id);
		ObjectNode modelNode = null;
		String status = "200";
		try {
			modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
		} catch (JsonProcessingException e) {
			status = "500";
			e.printStackTrace();
		} catch (IOException e) {
			status = "500";
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
			status = "500";
			e.printStackTrace();
		}  
		return status;
	}
	
	/**
	 * 导入流程模型
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-29 上午9:37:15
	 */
	@RequestMapping(value="uploadProcModel.do")
	public @ResponseBody String uploadProcModel(MultipartHttpServletRequest request){
		String state = "200";
		try {
			List<MultipartFile> list = request.getFiles("Filedata");
			for(MultipartFile file : list){
				BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
				//转成BpmnModel
				BpmnModel bpmnModel = xmlConverter.convertToBpmnModel(new InputStreamSource(file.getInputStream()), true, true,"UTF-8");
				//转成JSON
				BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
				ObjectNode objectNode = jsonConverter.convertToJson(bpmnModel);
		        //获取流程名称
				String name = StringUtils.defaultString(objectNode.get("properties").get("name").toString()).replaceAll("\"","");
				String key = StringUtils.defaultString(objectNode.get("properties").get("process_id").toString()).replaceAll("\"","");
		        //创建模型
		        Model modelData = repositoryService.newModel();
		        ObjectNode modelObjectNode = objectMapper.createObjectNode();
		        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, StringUtils.defaultString(name));
			    modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			    modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,"");
		        modelData.setMetaInfo(modelObjectNode.toString());
			    modelData.setName(name);
			    modelData.setKey(key);
			    //保存模型
			    repositoryService.saveModel(modelData);
			    //保存资源
			    repositoryService.addModelEditorSource(modelData.getId(),objectNode.toString().getBytes("UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			state = "500";
		}
		return state;
	}
	/**新建模型
	 * @author LiZhiXian
	 * @date 2015-11-11 下午1:59:19
	 * @param name 模型名称
	 * @param key 模型关键字
	 * @param description 描述
	 * @param request 浏览器对象
	 * @param response 浏览器对象
	 * @return 返回创建结果
	 */
	@RequestMapping(value = "create.do")
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
		      rdata.put("modelId", modelData.getId());
		    } catch (Exception e) {
		      e.printStackTrace();
		      rdata.put("status", "500");
		    }
		return rdata;
	}
	
	/**删除流程模型
	 * @author LiZhiXian
	 * @date 2015-11-11 下午4:18:54
	 * @param id 模型id
	 * @return 删除结果
	 */
	@RequestMapping(value = "delete.do")
	public @ResponseBody String delete(@RequestParam("id") String id){
		repositoryService.deleteModel(id);
		return "200";
	}
	
    /**
	 * 导出model的xml文件
	 */
	@RequestMapping(value = "export/{modelId}")
	public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
	      	BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
	      	byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

	      	ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
	      	IOUtils.copy(in, response.getOutputStream());
	      	String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	      	response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	      	response.flushBuffer();
	    } catch (Exception e) {
	    	logger.error("导出model的xml文件失败：modelId={}", modelId, e);
	    }
	}
	  
	/**弹出框编辑内容
	 * @author LiZhiXian
	 * @date 2015-11-12 上午10:00:35
	 * @return 编辑页面元素
	 */
	@RequestMapping(value = "add.htm")
	public String add(){
		return "bpm/model/add";
	}
}
