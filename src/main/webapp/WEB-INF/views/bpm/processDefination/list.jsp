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
	<title>已部署流程定义列表</title>
	<meta name="keywords" content="后台管理系统模版，功能齐全">
	<meta name="description" content="工作流后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
  </head>
  
  <body>
  	<!-- 面包屑 -->
  	<ol class="breadcrumb sys-breadcrumb">
	  <li><a href="#"><span class="glyphicon glyphicon-home"></span>首页</a></li>
	  <li><a href="#">工作流管理</a></li>
	  <li class="active">流程定义</li>
	</ol>
	<div>
	    <div class="panel">
		    <div class="panel-body">
		    	<!-- 搜索项 -->
		        <div class="form-horizontal sys-padding-0" id="serachForm">
		            <div class="form-group">
	                    <label class="col-sm-2 control-label right" for="name">流程名称：</label>
	                    <div class="col-sm-3">
	                        <input class="form-control" id="name" name="name" type="text"/>
	                    </div>
	                	<div class="col-sm-1"></div>
	                    <label class="col-sm-2 control-label" for="key">关键字：</label>
	                    <div class="col-sm-3">
	                        <input class="form-control" id="key" name="key" type="text"/>
	                    </div>
	                    <div class="col-sm-1"></div>
	                </div>
	                <div class="form-group sys-center">
	                    <button type="button" class="btn btn-success sys-margin-horizontal-10" id="search_btn">
	                        <i class="glyphicon glyphicon-search"></i> 查  询
	                    </button>
	                    <button type="button" class="btn btn-success sys-margin-horizontal-10" id="reset_btn">
	                        <i class="glyphicon glyphicon-refresh"></i> 重  置
	                    </button>
	                </div>
		        </div>
			    <!-- 功能按钮 -->
			    <div class="col-sm-12 sys-btn-bar">
			    	<a href="javascript:void(0)" id="new_model" class="btn btn-info">
	                    <span class="glyphicon glyphicon-plus"></span> 挂 起
	                </a>
	                <a href="#" class="btn btn-info">
	                    <span class="glyphicon glyphicon-pencil"></span> 编辑模型
	                </a>
	                <a href="#" class="btn btn-info">
	                    <span class="glyphicon glyphicon-trash"></span> 删除模型
	                </a>
			    </div>
			    <!-- 表格 -->
			    <div class="col-sm-12 sys-padding-0">
			        <table class="table table-striped table-hover" id="model_table"></table>
			    </div>
		    </div>
		</div>
    
    </div>
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table-zh-CN.min.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$('#model_table').bootstrapTable({
			url:'service/bpm/processDefinition/ajaxList.json',
			striped: true,
	        clickToSelect: true,
	        pagination: true,
	        pageSize: 10,
	        sidePagination:'server',
			//pageNumber:1,
			pageList: [10, 20, 50, 100, 200, 500],
		    queryParamsType: 'limit', 
		    queryParams: function (params){
				//获取查询条件
				$('#serachForm').getSearchParams(params);
				return params;
			},
			columns: [
				{field:'',title:'复选框',width:50,checkbox:true},
				{field:"name",title:"流程名称",align:"center",valign:"middle",sortable:"true"},
				{field:"key",title:"关键字",align:"center",valign:"middle",sortable:"true"},
				{field:"sourceName",title:"文件名",align:"center",valign:"middle",sortable:"true",
					formatter:function(value){
						return value;     
					}
				},
				{field:"version",title:"版本",align:"center"},
				{field:"category",title:"分类",align:"center"},
				{field:"detail",title:"操作",align:"center",sortable:"true",
					formatter:function(value,row,rowIndex){
						var strHtml = '<a href="process-editor/modeler.html?modelId='+row.id+'" target="_blank">启动流程</a>&nbsp;|&nbsp;'
										+'<a href="javascript:void(0);" onclick="showResource('+row.deploymentId+',\''+row.diagramResourceName+'\')">流程图片</a>&nbsp;|&nbsp;'			 
										+'<a href="javascript:void(0);" onclick="removeDeploye('+row.deploymentId+')">删除</a>';
						return strHtml;
					}
				}
				
			],
			onPageChange: function (size, number) {
	        },
	        formatNoMatches: function(){
	        	return '无符合条件的记录';
	        }
		});
  		
  		//查询
		$('#search_btn').click(function(){
			$('#model_table').bootstrapTable('refresh');
		});
		//重置查询条件
		$('#reset_btn').click(function(){
			$('#serachForm').reSet(false);
		});
	
  		$('#new_model').click(function(){
  			$.layer_show('创建模型','service/bpm/model/add.htm',600,510);
  		});
  	});
  	//查看流程图片
  	function showResource(deploymentId,sourceName){
  		var win = $.layer_show('流程图','service/bpm/processDefinition/processResource.htm?type=0&deploymentId='+deploymentId+'&resourceName='+sourceName,800,610);
  		layer.full(win);
  	}
  	//部署模型
  	function deployModel(){
  		layer.open({
		    content: '确定要部署？',
		    btn:['确认', '取消'],
		    shadeClose: false,
		    title:'提示',
		    yes: function(index, layero){
		    	layer.close(index);
		    	$.ajax({
		    		url:'service/bpm/model/deployment.do',
		    		type:'get',
		    		data:{id:id},
		    		dataType:'json',
		    		success: function(ret) {
		    			if(ret == '200'){
		    				layer.alert('部署成功！', {icon: 6});
		    				$('#model_table').bootstrapTable('refresh');
		    			}else{
		    				layer.alert('部署失败！', {icon: 5});
		    			}
					},
					error: function(xhr, textStatus, errorThrown){
						layer.alert('部署出错啦！', {icon: 5});
				    }
		    	});
		    },cancel: function(index){
		    	
		    }
	 	});
  	}
  	//删除模型
  	function removeDeploye(deploymentId){
  		layer.open({
		    content: '确定要删除？',
		    btn:['确认', '取消'],
		    shadeClose: false,
		    title:'提示',
		    yes: function(index, layero){
		    	layer.close(index);
		    	$.ajax({
		    		url:'service/bpm/processDefinition/delete.do',
		    		type:'get',
		    		data:{deploymentId:deploymentId},
		    		dataType:'json',
		    		success: function(ret) {
		    			if(ret == '200'){
		    				layer.alert('删除成功！', {icon: 6});
		    				$('#model_table').bootstrapTable('refresh');
		    			}else{
		    				layer.alert('删除失败！', {icon: 5});
		    			}
					},
					error: function(xhr, textStatus, errorThrown){
						layer.alert('删除出错啦！', {icon: 5});
				    }
		    	});
		    },cancel: function(index){
		    	
		    }
	 	});
  	}
  </script>
</html>
