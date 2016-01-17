<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <!--支持 浏览器内核 webkit、ie-comp IE兼容、ie-stand IE标准 -->
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <!--开启IE兼容模式  -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <!--移动设备支持， width=device-width 自适应宽度,initial-scale=1.0 初始缩放比例为1:1,user-scalable 用户是否可以手动缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-transform"/>
    <title>系统出错页面</title>
    <link type="image/vnd.microsoft.icon" rel="shortcut icon" href="images/favicon.ico">
  </head>
  
  <body>
  	<div style="height: 300px; width: 300px;" align="center" >
	   <div id="fly" style="position: absolute;left: 16px; top: 200px;" align="center" >
			<img  src="images/500.png">
			<c:choose>
			   <c:when test="${error != null && error != ''}">  
			                        错误信息: ${error}
			   </c:when>
			   <c:otherwise> 
			  		系统出现了未知错误，请联系相关技术人员！
			   </c:otherwise>
			</c:choose>
	   </div>
   	</div>
  </body>
</html>
