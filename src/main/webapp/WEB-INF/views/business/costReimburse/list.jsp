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
	<script type="text/javascript" src="dist/lib/PIE_IE678.js"></script>
	<![endif]-->
	<link href="dist/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="dist/lib/bootstrap/extend/table/bootstrap-table.min.css" rel="stylesheet" type="text/css" />
	<link href="dist/css/style.css" rel="stylesheet" type="text/css" />
	<!--[if IE 6]>
	<script>alert('请升级浏览器版本');</script>
	<![endif]-->
	<title>费用报销申请列表</title>
	<meta name="keywords" content="后台管理系统模版，功能齐全">
	<meta name="description" content="工作流后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
  </head>
  
  <body>
  	<!-- 面包屑 -->
  	<ol class="breadcrumb sys_breadcrumb">
	  <li><a href="#"><span class="glyphicon glyphicon-home"></span>首页</a></li>
	  <li><a href="#">业务管理</a></li>
	  <li class="active">费用报销</li>
	</ol>
	
    <div class="panel">
	    <div class="panel-body">
	    	<!-- 搜索项 -->
	        <div class="form-horizontal sys-padding-0" id="serachForm">
	            <div class="form-group">
                    <label class="col-sm-2 control-label right" for="costName">报销单名称：</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="costName" name="costName" type="text"/>
                    </div>
                    <div class="col-sm-1"></div>
                    <label class="col-sm-2 control-label" for="chinaName">报销人：</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="chinaName" name="chinaName" type="text"/>
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
		    	<a href="javascript:void(0)" class="btn btn-info" id="addBtn">
                    <span class="glyphicon glyphicon-plus"></span> 新 增
                </a>
                <a href="javascript:void(0)" class="btn btn-info disabled" id="modiBtn">
                    <span class="glyphicon glyphicon-pencil"></span> 修 改
                </a>
                <a href="javascript:void(0)" class="btn btn-info disabled" id="delBtn">
                    <span class="glyphicon glyphicon-trash"></span> 删 除
                </a>
		    </div>
		    <!-- 表格 -->
		    <div>
		        <table class="table table-striped table-hover" id="bootstrap_table"></table>
		    </div>
	    </div>
	</div>
    
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$('#bootstrap_table').bootstrapTable({
			url:'service/business/costReimburse/ajaxList.json',
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
				{field:'',title:'选择',width:50,checkbox:true},
				{field:"costName",title:"报销单名称",align:"center"},
				{field:"chinaName",title:"报销人",align:"center"},
				{field:"amount",title:"报销金额",align:"center"},
				{field:"state",title:"任务状态",align:"center"},
				{field:"createTime",title:"申请时间",align:"center",
					formatter:function(value){
						return (new Date(value)).format("yyyy-MM-dd hh:mm:ss");     
					}	
				},
				{field:"description",title:"报销说明",align:"center"}
			],
			onPageChange: function (size, number) {
	        },
	        formatNoMatches: function(){
	        	return '无符合条件的记录';
	        }
		});
	  	//查询
		$('#search_btn').click(function(){
			$('#bootstrap_table').bootstrapTable('refresh');
		});
		//重置查询条件
		$('#reset_btn').click(function(){
			$('#serachForm').reSet(false);
		});
		$('#addBtn').click(function(){
  			$.layer_show('费用报销申请','service/business/costReimburse/editnew.htm',800,600,true);
  		});
		//修改
		$('#modiBtn').click(function(){
			var rows = $('#bootstrap_table').bootstrapTable('getSelections');
			if(rows.length != 1){
				layer.alert('请选择要修改的报销单！',{title:'提示',icon:2});
				return;
			}
			if(rows[0].state == '提交申请' || rows[0].state == '被驳回'){
				$.layer_show('费用报销修该','service/business/costReimburse/editnew.htm?id='+rows[0].id,800,600,true);
			}else{
				layer.alert('当前状态不可修改！',{title:'提示',icon:2});
			}
			
		});
		
		//按钮控制
		$('#bootstrap_table').on('check.bs.table uncheck.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
			var rows = $('#bootstrap_table').bootstrapTable('getSelections');
			var delFlag = 'remove',modiFlag = 'remove';
			if(rows.length > 0){
				$.each(rows,function(index,row){
					if(row.state != '被驳回'){
						if(row.state != '完成'){
							delFlag = 'add';
						}
						if(row.state != '提交申请' || rows.length != 1){
							modiFlag = 'add';
						}
					}
				});
			}else{
				delFlag = 'add';
				modiFlag = 'add';
			}
			eval("$('#delBtn')."+delFlag+"Class('disabled');");
			eval("$('#modiBtn')."+modiFlag+"Class('disabled');");
			
        });
		//删除
		$('#delBtn').click(function(){
			var rows = $('#bootstrap_table').bootstrapTable('getSelections');
			if(rows.length < 1){
				layer.alert('请选择要删除的报销单！',{title:'提示',icon:2});
				return;
			}
			var ids = [];
			$.each(rows,function(index,row){
				if(row.state != '完成'){
					ids = [];
					layer.alert('无法删除状态为'+row.state+'的任务！',{title:'提示',icon:2});
					return false;//break
				}
				ids.push(row.id);
			});
			if(ids.length > 0){
				layer.open({
				    content: '确定要删除？',
				    btn:['确认', '取消'],
				    shadeClose: false,
				    title:'提示',
				    icon:3,
				    yes: function(index, layero){
				    	layer.close(index);
				    	var tipMsg = layer.msg('正在删除，请稍等...', {icon: 16,shade:[0.1,'#000'],time:0,offset:'250px'});
				    	$.ajax({
				    		url:'service/business/costReimburse/delete.do',
				    		type:'get',
				    		data:{costId:ids},
				    		dataType:'json',
				    		success: function(ret) {
				    			if(ret == '200'){
				    				layer.alert('删除成功！', {icon: 1});
				    				$('#bootstrap_table').bootstrapTable('refresh');
				    			}else{
				    				layer.alert('删除失败！', {icon: 2});
				    			}
				    			layer.close(tipMsg);
							},
							error: function(xhr, textStatus, errorThrown){
								layer.alert('删除出错啦！', {icon: 2});
						    }
				    	});
				    },cancel: function(index){
				    	
				    }
			 	});
			}
		});
  	});
  </script>
</html>
