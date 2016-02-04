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
  			<form class="form-horizontal" role="form">
  				<input type="hidden" name="id" id="costId" value="${obj.id}"/>
  				<fieldset>
					 <legend>基本信息</legend>
					 <div class="form-group">
	  					<label class="col-sm-2 control-label"><span style="color: red;">* </span>报销单名称：</label>
					    <div class="col-sm-3">
					       <input type="text" class="form-control" name="costName" value="${obj.costName}" datatype="*1-16" nullmsg="请输入报销单名称" placeholder="请输入报销单名称">
					    </div>
					    <div class="col-sm-1"></div>
					    <label class="col-sm-2 control-label"><span style="color: red;">* </span>报销总金额：</label>
					    <div class="col-sm-3">
					       <input type="text" class="form-control" name="amount" id="totalAmount" readonly="readonly" value="${obj.amount}" datatype="n1-16" nullmsg="请输入报销金额" errormsg="报销金额只能为数字类型" placeholder="请输入报销金额">
					    </div>
					    <div class="col-sm-1"></div>
	  				</div>
	  				<div class="form-group">
	  					<label for="description" class="col-sm-2 control-label">描述：</label>
				        <div class="col-sm-9">
				           <textarea class="form-control" name="description" value="${obj.description}" rows="4"></textarea>
				        </div>
				        <div class="col-sm-1"></div>
	  				</div>
	  				
				</fieldset>
				<fieldset>
					 <legend>费用项</legend>
					
					 <div class="col-sm-12 sys-btn-bar">
			            <button class="btn btn-info" type="button" id="addCostBtn">
			           		<span class="glyphicon glyphicon-plus"></span> 添 加
			            </button>
			         </div>
				     <!-- 表格 -->
				     <div>
				        <table class="table table-striped table-hover" id="costItemsTable"></table>
				     </div>
					 <div class="form-group" style="margin-top:10px;">
				        <div class="col-sm-12 sys-center">
				           <button class="btn btn-info sys-margin-horizontal-10" type="button" id="submitBtn">提 交</button>
				           <button class="btn btn-info sys-margin-horizontal-10" type="button" onclick="$.layer_close()">取 消</button>
				        </div>
				     </div>
				</fieldset>
	  		</form>
  		</div>
  	</div>
  </body>
  <script type="text/javascript" src="dist/lib/jquery/1.9.1/jquery.min.js"></script> 
  <script type="text/javascript" src="dist/lib/Validform/5.3.2/Validform.min.js"></script>
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table.min.js"></script>
  <script type="text/javascript" src="dist/lib/bootstrap/extend/table/bootstrap-table-edit.js"></script>
  <script type="text/javascript" src="dist/lib/layer/2.1/layer.js"></script>
  <script type="text/javascript" src="dist/js/admin-frame.js"></script>
  <script type="text/javascript">
	$(function(){
		//费用项表格
		$('#costItemsTable').bootstrapTable({
			method: 'get',
			url:'service/business/costReimburse/costItemList.json',
			queryParams:{costId:$('#costId').val()},
			editable:true,
			clickToSelect: true,
			height:200,
			columns: [
				{field:'itemName',title:'费用项目',align:'center',width:'25%'},
				{field:'category',title:'类别',align:'center',width:'25%'},
				{field:'amount',title:'金额',align:'center',width:'25%',edit:{
					blur:function(){
						//总金额
						var totalAmount = $('#costItemsTable').bootstrapTable('getColTotal',2);
						$('#totalAmount').val(totalAmount);
					}
				}},
				{field:'operation',title:'操作',align:'center',formatter:function(value,row,rowIndex){
					var strHtml = '<a href="javascript:void(0);" onclick="removeItem('+rowIndex+')">删除</a>';
					return strHtml;
				},edit:false}
				
			]
		});
		$("form").customValidform({
    		btnSubmit:'#submitBtn',
    		showAllError:true,
    		beforeSubmit:function(form){
    			var costItems = $('#costItemsTable').bootstrapTable('getData');
    			var tipMsg = '';
    			if(costItems.length < 1 ){
    				tipMsg = '请填写费用项信息';
    			}else{
    				$.each(costItems,function(index,row){
    					if(!row.itemName){
    						tipMsg = '费用项列表第【'+(index+1)+'】行，费用项目不能为空，请检查！';
    					}
    					if(row.amount && !row.amount.match(/^[0-9].*$/)){
    						tipMsg = '费用项列表第【'+(index+1)+'】行，金额必须为数字类型！';
    					}
    				});
    			}
    			if(tipMsg != ''){
    				layer.alert(tipMsg, {icon: 2,title:'提示'});
    			}else{
    				form.ajaxSubmitForm({
    		    		url:'service/business/costReimburse/save.do',
    		    		params:{itemList:costItems},
    		    		paramType:'string',
    		    		onSubmitSuccess:function(data){
    		    			if(data.status=='200'){
    		    				parent.$('#bootstrap_table').bootstrapTable('refresh');
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
    			}
				return false;
    		}
		});
	});
	//添加费用项
	$('#addCostBtn').click(function(){
		$('#costItemsTable').bootstrapTable('append',{});
	});
	//删除费用项
	function removeItem(rowIndex){
		$('#costItemsTable').bootstrapTable('removeRow',rowIndex);
		//总金额
		var totalAmount = $('#costItemsTable').bootstrapTable('getColTotal',2);
		$('#totalAmount').val(totalAmount);
	}
  </script>
</html>
