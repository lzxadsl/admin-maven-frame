package com.admin.bpm.activiti.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.admin.basic.model.PageData;

/**用户任务
 * @author linjian
 * @version 2.0
 * @create_date 2015-11-14 下午12:03:40
 */
@Controller
@RequestMapping(value = "/admin/task/*")
public class ACTRuTaskController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping("query")
	public String query(){
		return "admin/task/query";
	}
	
	@RequestMapping(value = "ajaxList",produces = "application/json")
	public @ResponseBody Map<String, Object> ajaxList(ModelMap model, PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		List<Task>  listTask = taskService.createTaskQuery().list();
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		for (Task task : listTask) {
			Map<String, Object> itm = new HashMap<String, Object>();
			itm.put("id", task.getId());
			itm.put("name", task.getName());
			itm.put("owner", task.getOwner());
			itm.put("assignee", task.getAssignee());
			listData.add(itm);
		}
		data.put("rows", listData);
		data.put("total", listTask.size());
		return data;
	}
	
	@RequestMapping("myTaskQuery")
	public String myTaskQuery(ModelMap model,String userId){
		model.put("userId", userId);
		return "admin/task/myTaskQuery";
	}
	
	@RequestMapping(value = "ajaxMyTask",produces = "application/json")
	public @ResponseBody Map<String, Object> ajaxMyTask(ModelMap model,String userId,PageData pageData){
		Map<String, Object> data = new HashMap<String, Object>();
		List<Task>  listTask = taskService.createTaskQuery().taskAssignee(userId).list();
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		for (Task task : listTask) {
			Map<String, Object> itm = new HashMap<String, Object>();
			itm.put("id", task.getId());
			itm.put("name", task.getName());
			itm.put("owner", task.getOwner());
			itm.put("assignee", task.getAssignee());
			listData.add(itm);
		}
		data.put("rows", listData);
		data.put("total", listTask.size());
		return data;
	}
	
	/**流程图形化形式查看当前状态
	 * @author linjian
	 * @create_date 2015-11-14 下午5:28:30
	 * @param taskId 任务编号
	 * @param model 模块载体
	 * @return 状态显示
	 */
	@RequestMapping(value = "viewTaskState")
	public String viewTaskState(String taskId,ModelMap model){
		Map<String, Object> taskImg = new HashMap<String, Object>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		String currentActivitiId = processInstance.getActivityId();
		ProcessDefinitionEntity pdEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		ActivityImpl ai = pdEntity.findActivity(currentActivitiId);
		taskImg.put("x", ai.getX());
		taskImg.put("y", ai.getY());
		taskImg.put("width", ai.getWidth());
		taskImg.put("height", ai.getHeight());
		taskImg.put("proDefId", task.getProcessDefinitionId());
		model.put("taskImg", taskImg);
		return "admin/task/viewTaskState";
	}
	
	/**认领任务，任务一被认领，其他人将会看不到该任务
	 * @author linjian
	 * @create_date 2015-11-14 下午5:35:57
	 * @param taskId 任务
	 * @param assignee
	 * @return
	 */
	@RequestMapping(value = "claim")
	public String claim(String taskId,String assignee){
		taskService.claim(taskId, assignee);
		return "admin/task/query";
	}
	
	/**完成当前任务
	 * @author linjian
	 * @create_date 2015-11-16 下午1:19:31
	 * @param taskId 任务编号
	 * @param assignee 完成的受让人
	 * @param candidate 完成的候选人
	 * @param candidateGroup 完成的用户组
	 * @return 任务列表
	 */
	@RequestMapping(value = "complete")
	public String complete(String taskId,String assignee,String candidate,String candidateGroup,Map<String, Object> formProperties){
		if(StringUtils.isNotBlank(taskId)){
			if(formProperties == null){
				formProperties = new HashMap<String, Object>();
			}
			formProperties.put("isAngree", 1);
			taskService.complete(taskId,formProperties);
		}
		return "admin/task/query";
	}
	
}
