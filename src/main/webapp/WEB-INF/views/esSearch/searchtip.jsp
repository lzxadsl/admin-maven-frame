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
	<title>搜索控件</title>
	<meta name="keywords" content="后台管理系统模版，功能齐全">
	<meta name="description" content="工作流后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
  </head>
  
  <body>
  	<!-- 面包屑 -->
  	<ol class="breadcrumb sys_breadcrumb">
	  <li><a href="#"><span class="glyphicon glyphicon-home"></span>首页</a></li>
	  <li><a href="#">搜索引擎</a></li>
	  <li class="active">搜索控件</li>
	</ol>
	
    <div class="panel" style="top: 40px;bottom: 0;position: absolute;width: 100%;">
	    <div class="panel-body">
	    	<!-- 搜索项 -->
	        <div class="form-horizontal sys-padding-0" id="serachForm">
	            <div class="form-group">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <input class="form-control" id="search" type="text"/>
                    </div>
                    <div class="col-sm-4"></div>
                </div>
	        </div>
	    </div>
	</div>
    
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript" src="dist/js/search-tip.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$('#search').search_tip({
    		url:'service/es/esFullSearch.json',
    		textField:'ilab',
    		/* formatter:function(rec){
    			if(rec && rec.ilab && rec.ilab.length > 10){
    				
    			}
    		}, */
    		onSelect : function(keyword,rec){
    			console.log(keyword);
    		},
    		onSearchClick:function(keyword,rec){
    			console.log('v:'+keyword);
    		}
    	});
  	});
  </script>
</html>
