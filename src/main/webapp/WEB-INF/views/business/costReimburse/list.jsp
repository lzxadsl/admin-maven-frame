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
	            <div class="form-group col-sm-6">
                    <label class="col-sm-3 control-label right" for="name">申请人：</label>
                    <div class="col-sm-9">
                        <input class="form-control" id="name" name="name" type="text"/>
                    </div>
                </div>
                <div class="form-group col-sm-6">
                    <label class="col-sm-3 control-label" for="state">任务状态：</label>
                    <div class="col-sm-9">
                        <input class="form-control" id="stat" name="state" type="text" placeholder="192.168.1.161"/>
                    </div>
                </div>
                <div class="form-group col-sm-12 sys_center">
                    <button type="button" class="btn btn-success sys-margin-horizontal-10" id="search_btn">
                        <i class="glyphicon glyphicon-search"></i> 查  询
                    </button>
                    <button type="button" class="btn btn-success sys-margin-horizontal-10" id="reset_btn">
                        <i class="glyphicon glyphicon-refresh"></i> 重  置
                    </button>
                </div>
	        </div>
	        <!-- 表格 -->
		    <div>
		        <table class="table table-striped table-hover" id="bootstrap_table"></table>
		    </div>
		    <!-- 功能按钮 -->
		    <div class="col-sm-12">
		    	<a href="#" class="btn btn-info">
                    <span class="glyphicon glyphicon-plus"></span> 新 增
                </a>
                <a href="#" class="btn btn-info">
                    <span class="glyphicon glyphicon-pencil"></span> 修 改
                </a>
                <a href="#" class="btn btn-info">
                    <span class="glyphicon glyphicon-trash"></span> 删 除
                </a>
		    </div>
	    </div>
	</div>
    
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$('#model_table').bootstrapTable({
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
				{field:"cost_name",title:"报销单名称",align:"center",valign:"middle",sortable:"true"},
				{field:"key",title:"报销人",align:"center",valign:"middle",sortable:"true"},
				{field:"createTime",title:"报销日期",align:"center",valign:"middle",sortable:"true"},
				{field:"version",title:"版本",align:"center"},
				{field:"category",title:"分类",align:"center"},
				{field:"detail",title:"操作",align:"center",sortable:"true",
					formatter:function(value,row,rowIndex){
						//var strHtml = '<a href="javascript:void(0);" onclick="removeRow('+rowIndex+')">删除</a>';
						return value;
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
			$('#bootstrap_table').bootstrapTable('refresh');
		});
		//重置查询条件
		$('#reset_btn').click(function(){
			$('#serachForm').reSet(false);
		});
  	});
  </script>
</html>
