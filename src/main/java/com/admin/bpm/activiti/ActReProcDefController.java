package com.admin.bpm.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.admin.basic.model.PageData;

/**流程定义
 * @author LiZhiXian
 * @version 2.0
 * @date 2015-11-9 下午5:16:06
 */
@Controller
@RequestMapping(value = "/bpm/processDefinition/*")
public class ActReProcDefController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	
	@RequestMapping("list.htm")
	public String query(){
		return "bpm/processDefination/list";
	}
	
	/**流程定义
	 * @author LiZhiXian
	 * @date 2015-11-9 下午5:17:08
	 * @param model
	 * @param pageData
	 * @return
	 */
	@RequestMapping(value = "ajaxList.json",produces = "application/json")
	public @ResponseBody Map<String, Object> ajaxList(String state, String name,String key,PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		ProcessDefinitionQuery procDefinQuery = repositoryService.createProcessDefinitionQuery();
		if(StringUtils.isNotEmpty(name)) {
			procDefinQuery.processDefinitionName(name);
		}
		if(StringUtils.isNotEmpty(key)) {
			procDefinQuery.processDefinitionName(key);
		}
		if(StringUtils.isNotEmpty(state)){
			if("suspended".equals(state))
				procDefinQuery.suspended();
			else if("active".equals(state))
				procDefinQuery.active();
		}
		List<ProcessDefinition>  listProDef = procDefinQuery.orderByDeploymentId().desc().list();
		
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
			itm.put("isSuspended", processDefinition.isSuspended());
			listPro.add(itm);
		}
		data.put("rows", listPro);
		data.put("total", procDefinQuery.count());
		return data;
	}
	
	/**读取图片文件
	 * @author LiZhiXian
	 * @date 2015-11-10 下午5:05:18
	 * @param type 0代表图片,1代表xml文件
	 * @param deploymentId 流程部署id
	 * @param resourceName 文件名称
	 * @param request 请求对象
	 * @param response 返回浏览器响应对象
	 */
	@RequestMapping(value = "processResource.htm")
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
	 * @author LiZhiXian
	 * @date 2015-11-10 下午6:16:39
	 * @param key 关键字
	 * @return 删除后列表
	 */
	@RequestMapping(value = "delete.do")
	public @ResponseBody String delete(String deploymentId){
		String status = "200";
		try {
			repositoryService.deleteDeployment(deploymentId, true);
		} catch (Exception e) {
			e.printStackTrace();
			status = "500";
		}
		return status;
	}
	
	/**
	 * 挂起或激活流程
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-25 下午5:09:28
	 * @param processDefinitionId 流程定义ID
	 * @param suspendProcessInstances 是否同时挂起该流程下的实例
	 * @param suspensionDate 挂起生效日期,为空立即生效
	 * @return status
	 */
	@RequestMapping("suspOrActiProcess.do")
	public @ResponseBody String suspOrActiProcess(boolean suspended,String processDefinitionId,boolean suspendProcessInstances,String date){
		String status = "200";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date suspensionDate = null;
			if(StringUtils.isNotEmpty(date)){
				sdf.parse(date);
			}
			if(suspended){
				repositoryService.suspendProcessDefinitionById(processDefinitionId, suspendProcessInstances, suspensionDate);
			}else{
				repositoryService.activateProcessDefinitionById(processDefinitionId, suspendProcessInstances, suspensionDate);
			}
		} catch (ParseException e) {
			status = "500";
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * 导入流程定义
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-27 下午2:39:05
	 */
	@RequestMapping("uploadProcDef.do")
	public @ResponseBody String uploadProcDef(MultipartHttpServletRequest request){
		String state = "200";
		try {
			List<MultipartFile> list = request.getFiles("Filedata");
			for(MultipartFile file : list){
				InputStream in = file.getInputStream();
				String fileName = file.getOriginalFilename();
				String extension = FilenameUtils.getExtension(fileName);
				//流程名称，取文件的名称
				String processName = fileName.substring(0,fileName.indexOf("."));
				if (extension.equals("zip") || extension.equals("bar")){
					ZipInputStream zipInputStream = new ZipInputStream(in);
					repositoryService.createDeployment()
								.name(processName)
								.addZipInputStream(zipInputStream).deploy();
				}
				else{
					state = "500";
					//repositoryService.createDeployment().addInputStream(processName, in).deploy();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			state = "500";
		}
		return state;
	}
	@RequestMapping("batchUpload.do")
	public @ResponseBody String batchUpload(MultipartHttpServletRequest request){
		String state = "200";
		List<MultipartFile> list = request.getFiles("Filedata");
		for(MultipartFile file : list){
			String processName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
			System.out.println("------------------------:"+processName);
		}
		return state;
	}
	/**启动流程审批
	 * @author LiZhiXian
	 * @date 2015-11-14 上午11:50:09
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
