package com.admin.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.zip.ZipInputStream;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
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
			Deployment deployment = deploymentbuilder.name("请假申请")//部署名称
					.addClasspathResource("diagrams/leave.bpmn")
					.addClasspathResource("diagrams/leave.png")
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
		ByteArrayEntity entity = 
	}
	@Test
	public void addModel(){
	    Date d = new Date();
        String str = "Pandy_" + d.getTime() + "";

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
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, str);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, str);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(str);
            modelData.setKey(str);

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));



        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
