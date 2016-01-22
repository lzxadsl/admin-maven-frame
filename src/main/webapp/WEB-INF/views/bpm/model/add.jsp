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
	<title>流程模板创建</title>
	<meta name="keywords" content="后台管理系统模版，功能齐全">
	<meta name="description" content="工作流后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
  </head>
  
  <body>
	<div class="panel">
		<div class="panel-body">
			<form class="form-horizontal" role="form">
			   <div class="form-group col-sm-6">
			      <label for="name" class="col-sm-3 control-label">流程名称：</label>
			      <div class="col-sm-9">
			         <input type="text" class="form-control" id="name" name="name" placeholder="请输入流程名称">
			      </div>
			   </div>
			   <div class="form-group col-sm-6">
			      <label for="key" class="col-sm-3 control-label">关键字：</label>
			      <div class="col-sm-9">
			         <input type="text" class="form-control" id="key" name="key" placeholder="流程的唯一标识">
			      </div>
			   </div>
			   <div class="form-group col-sm-12">
			      <label for="description" class="col-sm-3 control-label">描述：</label>
			      <div class="col-sm-9">
			         <textarea class="form-control" name="description" rows="8"></textarea>
			      </div>
			   </div>
			   <div class="form-group col-sm-12">
			      <div class="col-sm-offset-2 col-sm-10">
			         <button class="btn btn-info">创 建</button>
			         <button class="btn btn-info">取 消</button>
			      </div>
			   </div>
			</form>
		</div>
	</div>
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">

  </script>
</html>
