<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
		<!--[if lt IE 9]>
		<script type="text/javascript" src="dist/lib/html5.js"></script>
		<script type="text/javascript" src="dist/lib/respond.min.js"></script>
		<script type="text/javascript" src="dist/lib/PIE_IE678.js"></script>
		<![endif]-->
		<link href="dist/css/H-ui.min.css" rel="stylesheet" type="text/css" />
		<link href="dist/css/H-ui.login.css" rel="stylesheet" type="text/css" />
		<link href="dist/css/style.css" rel="stylesheet" type="text/css" />
		<link href="dist/lib/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet" type="text/css" />
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<title>后台登录</title>
		<meta name="keywords" content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
		<meta name="description" content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
	</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div class="loginBox">
    <form:form id="loginform" class="form form-horizontal" action="shiro/doLogin.htm" modelAttribute="loginCommand" method="post">
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-8">
          <input id="username" name="username" value="${login.username}" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-8">
          <input id="password" name="password" value="${login.password}" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
          <img src="servlet/authImage.do" id="authImage"> <a id="kanbuq" href="javascript:;">看不清，换一张</a> 
        </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态
          </label>
          <c:if test="${not empty error}">
	          <span id="error" style="color:red;">&nbsp;&nbsp;${error}</span>
	      </c:if>
	      <c:if test="${not empty isempty}">
	          <span id="error" style="color:red;">&nbsp;&nbsp;用户名或密码不能为空！</span>
	      </c:if>
        </div>
      </div>
      
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input type="button" id="loginbtn" class="btn btn-success radius size-L"  value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form:form>
  </div>
</div>
<div class="footer">Copyright 你的公司名称 by H-ui.admin.v2.3</div>
<script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="dist/js/H-ui.js"></script> 
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
  //登入
  $('#loginbtn').click(function(){
  	$(this).attr('disabled','disabled');
  	$(this).attr('value','登入中...');
  	$('#loginform').submit();
  });
  //刷新验证码
  $('#kanbuq').click(function(){
  	$('#authImage').attr('src',chgUrl('servlet/authImage.do'));
  });
})();
//时间戳
//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function chgUrl(url){
	var timestamp = (new Date()).valueOf();
	//url = url.substring(0,17);
	if((url.indexOf("&")>=0)){
	 url = url + "×tamp=" + timestamp;
	}else{
	 url = url + "?timestamp=" + timestamp;
	}
	return url;
}
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>