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
	                <div class="form-group">
	                    <label class="col-sm-2 control-label right" for="state">状态：</label>
	                    <div class="col-sm-3">
	                    	<select class="form-control" id="state" name="state" defaultIndex=0> 
						      <option value="" selected>--全部--</option> 
						      <option value="suspended">挂起</option> 
						      <option value="active">活动</option> 
						    </select>
	                    </div>
	                	<div class="col-sm-7"></div>
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
			    	<a href="javascript:void(0)" id="suspend" class="btn btn-info">
	                    <span class="glyphicon glyphicon-remove-sign"></span> 挂 起
	                </a>
	                <a href="javascript:void(0)" class="btn btn-info" id="active">
	                    <span class="glyphicon glyphicon-ok-sign"></span> 激 活
	                </a>
	                <a href="javascript:void(0)" class="btn btn-info" id="import">
	                    <span class="glyphicon glyphicon-open"></span> 导 入
	                </a>
			    </div>
			    <!-- 表格 -->
			    <div class="col-sm-12 sys-padding-0">
			        <table class="table table-striped table-hover" id="procDef_table"></table>
			    </div>
		    </div>
		</div>
		<!-- 激活、挂起信息项 -->
    	<div style="display:none;padding:10px;width:400px;" class="container" id="layerContent">
    		<div class="form-group">
    			<span for="name" id="text1">何时{state}此流程状态？</span>
    		</div>
    		<div class="form-group">
			   <label class="checkbox-inline">
			      <input type="radio" id="nowCheckbox" name="radioVal" checked="checked" value="now"> 现在
			   </label>
			</div>
			<div class="form-group">
				<label class="checkbox-inline">
			      <input type="radio" id="setCheckbox" name="radioVal" value="set"> 设定时间
			    </label>
			    <label class="checkbox-inline">
			    	<input type="date" class="form-control" id="suspensionDate" name="suspensionDate" value="">
			    </label>
			</div>
			<div class="form-group">
				<label class="checkbox-inline">
			      <input type="checkbox" id="suspendProcessInstances" name="suspendProcessInstances" value="true"> 
			      <span id="text2">同时{state}和此流程定义相关的流程实例</span>
			    </label>
			</div>
		   	<div class="form-group">
		   		<button type="button" id="submit" class="btn btn-info">提 交</button>
		   		<button type="button" id="cancel" class="btn btn-info">取 消</button>
	        </div>
    	</div>
    	
    </div>
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table-zh-CN.min.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/lib/uploadify/jquery.uploadify.min.js"></script> 
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$('#procDef_table').bootstrapTable({
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
				{field:"isSuspended",title:"状态",align:"center",
					formatter:function(value,row,rowIndex){
						var status = '<span class="label label-success" style="padding: .4em .6em .3em;">活动</span>';
						if(value)status = '<span class="label label-danger" style="padding: .4em .6em .3em;">挂起</span>';
						return status;
					}	
				},
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
			$('#procDef_table').bootstrapTable('refresh');
		});
		//重置查询条件
		$('#reset_btn').click(function(){
			$('#serachForm').reSet(false);
		});
		//挂起
		$('#suspend').click(function(){
			$('#text1').html($('#text1').html().replace(/{state}/g,'挂起'));
			$('#text2').html($('#text2').html().replace(/{state}/g,'挂起'));
			modifyState('挂起',true);
		});
		//激活
		$('#active').click(function(){
			$('#text1').html($('#text1').html().replace(/{state}/g,'激活'));
			$('#text2').html($('#text2').html().replace(/{state}/g,'激活'));
			modifyState('激活',false);
		});
		//弹出框页面元素事件
		$('input[name="radioVal"]').change(function(){
  			var value = $(this).val();
  			if(value == 'set'){
  				$('#suspensionDate').val($.dateUtil.getCurrentDate(false));
  			}else{
  				$('#suspensionDate').val('');
  			}
  		});
		//日期点击事件
  		$('#suspensionDate').click(function(){
  			$('input[name="radioVal"]:eq(1)').attr('checked','checked');
  			$(this).val($.dateUtil.getCurrentDate(false));
  		});
  		
		//导入
  		$('#import').click(function(){
  			$('body').uploadFile({
  				winTitle:'流程定义导入',
  				headText:'请选择包含图片和 (.bpmn20.xml或.bpmn)的zip或bar文件。',
  				uploader:'<%=path%>/service/bpm/processDefinition/batchUpload.do',
  				onSuccess:function(file, data, response,queueSize){
  					if(data == '200'){
  						console.log(queueSize);
  						if(queueSize > 1){
  							$('#uploadify').uploadify('upload');
  						}else{
  							layer.alert('导入成功！', {icon: 1});
  	  						//layer.closeAll();
  	  						//$('#procDef_table').bootstrapTable('refresh');
  						}
  						
  					}else{
  						layer.alert('导入失败！', {icon: 2});
  					}
  				}
  			});
  			
  		});
		
  	});
  	//查看流程图片
  	function showResource(deploymentId,sourceName){
  		var win = $.layer_show('流程图','service/bpm/processDefinition/processResource.htm?type=0&deploymentId='+deploymentId+'&resourceName='+sourceName,800,610);
  		layer.full(win);
  	}
  	//修改流程状态,isSuspended true 为已经被挂起
  	function modifyState(state,isSuspended){
  		var rows = $('#procDef_table').bootstrapTable('getSelections');
  		
		if(rows.length != 1){
			layer.alert('请选择一项要'+state+'的流程！',{title:'提示',icon:2});
			return;
		}
		
		if(rows[0].isSuspended == isSuspended){
			layer.alert('当前已经是【'+state+'】状态！',{title:'提示',icon:2});
			return;
		}
		//设置初始值
		$('#suspensionDate').val('');
		$('input[name="radioVal"]:eq(0)').attr('checked','checked');
		$('#suspendProcessInstances').attr('checked',true);
		
  		var win = layer.open({
  		    type: 1,
  		    title:state+'流程定义',
  		    area: ['420px', '280px'], //宽高
  		    content: $('#layerContent')
  		});
  		$('#cancel').unbind('click').click(function(){
			layer.close(win);
		});
  		//提交
  		$('#submit').unbind('click').click(function(){
  	  		layer.open({
  			    content: '确定要'+state+'？',
  			    btn:['确认', '取消'],
  			    shadeClose: false,
  			    title:'提示',
  			    icon:3,
  			    yes: function(index, layero){
  			    	layer.close(index);
  			    	$.ajax({
  			    		url:'service/bpm/processDefinition/suspOrActiProcess.do',
  			    		type:'get',
  			    		data:{
  			    			suspended:isSuspended,
  			    			processDefinitionId:rows[0].id,
  			    			suspensionDate:$('#suspensionDate').val(),
  			    			suspendProcessInstances:$('#suspendProcessInstances').val()
  			    		},
  			    		dataType:'json',
  			    		success: function(ret) {
  			    			if(ret == '200'){
  			    				layer.alert(state+'成功！', {icon: 1});
  			    				layer.close(win);
  			    				$('#procDef_table').bootstrapTable('refresh');
  			    			}else{
  			    				layer.alert(state+'失败！', {icon: 2});
  			    			}
  						},
  						error: function(xhr, textStatus, errorThrown){
  							layer.alert(state+'出错啦！', {icon: 2});
  					    }
  			    	});
  			    },cancel: function(index){
  			    	
  			    }
  		 	});
  		});
  	}
  	//删除模型
  	function removeDeploye(deploymentId){
  		layer.open({
		    content: '确定要删除？',
		    btn:['确认', '取消'],
		    shadeClose: false,
		    title:'提示',
		    icon:3,
		    yes: function(index, layero){
		    	layer.close(index);
		    	$.ajax({
		    		url:'service/bpm/processDefinition/delete.do',
		    		type:'get',
		    		data:{deploymentId:deploymentId},
		    		dataType:'json',
		    		success: function(ret) {
		    			if(ret == '200'){
		    				layer.alert('删除成功！', {icon: 1});
		    				$('#procDef_table').bootstrapTable('refresh');
		    			}else{
		    				layer.alert('删除失败！', {icon: 2});
		    			}
					},
					error: function(xhr, textStatus, errorThrown){
						layer.alert('删除出错啦！', {icon: 2});
				    }
		    	});
		    },cancel: function(index){
		    	
		    }
	 	});
  	}
  </script>
</html>
