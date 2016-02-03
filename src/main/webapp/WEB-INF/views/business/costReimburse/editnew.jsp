<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<LINK rel="Bookmark" href="/favicon.ico" >
	<LINK rel="Shortcut Icon" href="/favicon.ico" />
	<!--[if lt IE 9]>
	<script type="text/javascript" src="dist/lib/html5.js"></script>
	<script type="text/javascript" src="dist/lib/respond.min.js"></script>
	<script type="text/javascript" src="dist/lib/PIE-2.0beta1/PIE_IE678.js"></script>
	<![endif]-->
	<link href="dist/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="dist/lib/bootstrap/extend/table/bootstrap-table.min.css" rel="stylesheet" type="text/css" />
	<link href="dist/css/style.css" rel="stylesheet" type="text/css" />
	<!--[if IE 6]>
	<script>alert('请升级浏览器版本');</script>
	<![endif]-->
	<title>费用报销申请</title>
	<meta name="keywords" content="后台管理系统模版，功能齐全">
	<meta name="description" content="工作流后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
  </head>
  
  <body>
    <div class="panel">
		<div class="panel-body">
			<form class="form-horizontal" role="form" >
			   <div class="form-group col-sm-6">
			      <label for="name" class="col-sm-3 control-label"><span style="color: red;">* </span>报销单名称：</label>
			      <div class="col-sm-9">
			         <input type="text" class="form-control" id="costName" name="costName" datatype="*1-16" nullmsg="请输入报销单名称" placeholder="请输入报销单名称">
			      </div>
			   </div>
			   <div class="form-group col-sm-6">
			      <label for="key" class="col-sm-3 control-label"><span style="color: red;">* </span>报销金额：</label>
			      <div class="col-sm-9">
			         <input type="text" class="form-control" id="amount" name="amount" datatype="n1-16" nullmsg="请输入报销金额" errormsg="报销金额只能为数字类型" placeholder="请输入报销金额">
			      </div>
			   </div>
			   <div class="form-group col-sm-6">
			      <label for="description" class="col-sm-3 control-label">描述：</label>
			      <div class="col-sm-9">
			         <textarea class="form-control" name="description" rows="4"></textarea>
			      </div>
			   </div>
			   
			   <div class="form-group">
			   	  <div class="col-sm-3"></div>
			      <div class="col-sm-9">
			         <button class="btn btn-info" type="button" id="submit">提 交</button>
			         <button class="btn btn-info" type="button" onclick="$.layer_close()">取 消</button>
			      </div>
			   </div>
			   
			</form>
		</div>
	</div>
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/Validform/5.3.2/Validform.min.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
	$(function(){
		$("form").customValidform({
    		btnSubmit:'#submit',
    		showAllError:true,
    		beforeSubmit:function(form){
    			form.ajaxSubmitForm({
		    		url:'service/business/costReimburse/save.do',
		    		onSubmitSuccess:function(data){
		    			if(data.status=='200'){
		    				parent.$('#model_table').bootstrapTable('refresh');
		    				window.top.creatIframe('process-editor/modeler.html?modelId='+data.modelId,'流程设计');
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
		    			}else{
		    				layer.alert('保存失败！', {icon: 2,title:'提示'});
		    			}
		    		},
		    		onSubmitError:function(xhr, textStatus, errorThrown){
		    			layer.alert('出错啦！', {icon: 2,title:'提示'});
		    		}
		    	});
				return false;
    		}
		});
		
	});
  </script>
</html>
