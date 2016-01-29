package com.admin.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 工作流测试
 * @author LiZhiXian
 * @version 1.0
 * @date 2016-1-19 上午10:45:28
 */
@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring/app-*.xml")
public class ActivitiTest {

	@Autowired
	private  RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	/**
	 * 流程部署
	 * @author LiZhiXian
	 * @version 1.0
	 * @date 2016-1-19 上午10:47:36
	 */
	@Test
	public void deploymentProcess(){
		//创建部署对象
		DeploymentBuilder deploymentbuilder = repositoryService.createDeployment();
		String path = "";
		try {
			/*Deployment deployment = deploymentbuilder.name("请假申请")//部署名称
							.addZipInputStream(new ZipInputStream(new FileInputStream(new File(path))))
							.deploy();//完成部署
*/			
			Deployment deployment = deploymentbuilder.name("HelloWord流程")//部署名称
					.addClasspathResource("diagrams/helloword.bpmn")
					.addClasspathResource("diagrams/helloword.png")
					.deploy();//完成部署
			System.out.println("-----------------------流程部署成功------------------------");
			System.out.println("流程ID："+deployment.getId());
			System.out.println("流程名称："+deployment.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		Model model = repositoryService.getModel("1");
		System.out.println(model.getDeploymentId());
		repositoryService.getModelEditorSource("1");
	}
	
	@Test
	public void suspendProcess(){
		String date = "2016-1-26 05:18:42";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date suspensionDate = sdf.parse(date);
			repositoryService.suspendProcessDefinitionById("helloword:1:22504",true,null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void activateProcess(){
		String date = "2016-1-26 05:26:42";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date suspensionDate = sdf.parse(date);
			repositoryService.activateProcessDefinitionById("helloword:1:22504",true,null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//runtimeService.activateProcessInstanceById("30001");
	}
	@Test
	public void startProcess(){
		ProcessInstance procInst = runtimeService.startProcessInstanceByKey("helloword");
		procInst.getName();
		System.out.println("流程启动成功");
		System.out.println("实例ID："+procInst.getProcessInstanceId());
		System.out.println("实例ID："+procInst.getId());
	}
	
	@Test
	public void findMyTask(){
		/*List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
		for(Task t:list){
			System.out.println("任务ID："+t.getId());
			System.out.println("任务名称："+t.getName());
			System.out.println("创建时间："+t.getCreateTime());
			System.out.println("办理人："+t.getAssignee());
		}*/
		ProcessDefinitionQuery procDefinQuery = repositoryService.createProcessDefinitionQuery();
		procDefinQuery.suspended();
		//procDefinQuery.processDefinitionName("HelloWord流程");
		List<ProcessDefinition>  listProDef = procDefinQuery.orderByDeploymentId().desc().list();
		System.out.println(listProDef.size());
	}
	
	@Test
	public void complete(){
		//runtimeService.suspendProcessInstanceById("30001");
		String taskId = "50002";//25004,27502,32502 30004,47502
		taskService.complete(taskId);
		
		
	}
	
	@Test
	public void signal(){
		String executionId = "30001";
		runtimeService.signal(executionId);
		System.out.println("任务前进一步："+executionId);
	}
}
