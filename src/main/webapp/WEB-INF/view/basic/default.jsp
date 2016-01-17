<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <script type="text/javascript" src="js/import.inc.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$('#order_status1').bootstrapSelect({
    			url:'user/getUser.htm',
    			valueField:'user_id',
    			textField:'username',
    			emptyText:'',
    			separator:'_',
    			enabled:true,
    			downBorder:true,
    			//multiple:true,
    			filterRemote:{
    				field:'username'
    			},
    			formatter:function(rec){
    				rec['username'] = rec.username+'_';
    				return rec;
    			},
    			unSelect:function(val,rec){
    				console.log($('#order_status1').bootstrapSelect('getValue'));
    			},
    			onSelect:function(val,rec){
    				console.log('comb2:'+$('#order_status2').bootstrapSelect('getValue'));
    				console.log($('#order_status1').bootstrapSelect('getValue'));
    				$('#order_status2').bootstrapSelect('destroy');
    			},
    			onBeforeLoad:function(params){
    				//params['username'] = 'lzx';
    			}
    		});
    		
    		$('#order_status2').bootstrapSelect({
    			//data:[{id:1,text:'lzx'},{id:2,text:'lsl'}],
    			//enabled:true,
    			//disabled:true,
    			//multiple:true,
    			onSelect:function(rec){
    				//console.log($('#order_status2').bootstrapSelect('getValue'));
    				console.log($('#order_status2').bootstrapSelect('getTextForVal','lzx2'));
    			}
    		}); 
    		$('#order_status2').bootstrapSelect('select','4');
    		//console.log($('#order_status1').bootstrapSelect('select','2,3'));
    		/* jQuery.event.add($('#order_status1'),'cusEvent',function(){
    			console.log('zx');
			}); */
			$('#order_status1').bind('cusEvent',function(){
				alert('zdysj');
			});
    		$('#load_btn').click(function(){
    			$('#order_status1').bootstrapSelect('reload',{
    				params:{id:'lzx'}
    			});
    			$('#order_status1').trigger('cusEvent');
    		});
    		$('#reportTable').bootstrapTable({
				method: 'get',
				cache: false,
				height: 400,
				striped: true,
				//pagination: true,
				//pageSize: 20,
				//pageNumber:1,
				//pageList: [10, 20, 50, 100, 200, 500],
				search: true,
				showColumns: true,
				showRefresh: true,
				search: true,
				editable:true,
				clickToSelect: true,
				columns: [
					[
						{colspan:2,title:"列1",align:"center",valign:"middle"},
						{colspan:6,title:"列2",align:"center",valign:"middle"},
						{rowspan:2,field:"user_lastlogintime",title:"lastlogintime",align:"center",valign:"middle",sortable:"true"}
					],
					[
					{field:"user_email",edit:false,title:"email",align:"center",valign:"middle",sortable:"true"},
					{field:"user_company",edit:{
										type:'select',
					        			url:'user/getUser.htm',
					        			valueField:'user_id',
					        			textField:'username',
					        			onSelect:function(val,rec){
					        				console.log(val,rec);
					        			}
					},title:"company",align:"center",valign:"middle",sortable:"true",width:"200px"},
					{field:"user_dates",edit:{
						type:'date',
						required:true,
						click:function(){
							alert('wbdjl');
						}
					},title:"date",align:"center",valign:"middle",sortable:"true"},
					/* {field:"user_lastlogintime",title:"lastlogintime",align:"center",valign:"middle",sortable:"true"}, */
					{field:"user_version",title:"version",align:"center",valign:"middle",sortable:"true"},
					{field:"user_isv2",title:"isv2",align:"center",valign:"middle",sortable:"true"},
					{field:"userstatus_usertype",title:"usertype",align:"center",valign:"middle",sortable:"true"},
					{field:"userstatus_package_id",title:"package_id",align:"center",checkbox:true},
					{field:"userstatus_end_time",title:"end_time",align:"center",sortable:"true",formatter:function(value,row,rowIndex){
						var strHtml = '<a href="javascript:void(0);" onclick="removeRow('+rowIndex+')">删除</a>';
						return strHtml;
					},edit:false}
					]
				],
				//data:[],
				data : [{"user_email":"20dai.rikon@gmail.com","user_company":"2","user_dates":"2014-10-13 13:35:51","user_lastlogintime":"0","user_version":"0","user_isv2":"0","userstatus_usertype":"0","userstatus_package_id":"100014","userstatus_end_time":"NULL"},{"user_email":"20140416@xxxxddffg.com","user_company":"3","user_dates":"2014-10-13 13:35:51","user_lastlogintime":"0","user_version":"1","user_isv2":"0","userstatus_usertype":"0","userstatus_package_id":"100001","userstatus_end_time":"NULL"},{"user_email":"20083matsumoto-hs@polus.co.jp","user_company":"1","user_dates":"2014-10-31 13:02:47","user_lastlogintime":"1414726074","user_version":"0","user_isv2":"1","userstatus_usertype":"0","userstatus_package_id":"100014","userstatus_end_time":"0"},{"user_email":"20.2.n0.5@gmail.com","user_company":"","user_dates":"2014-11-21 11:00:10","user_lastlogintime":"1416564204","user_version":"0","user_isv2":"0","userstatus_usertype":"0","userstatus_package_id":"100014","userstatus_end_time":"0"}],
				onPageChange: function (size, number) {
                },
                formatNoMatches: function(){
                	return '无符合条件的记录';
                },
                onClickRow:function(item,el){
                	console.log('行被点击');
                }
			}); 
			
			$('#datetimepicker').datetimepicker({
			    format: 'yyyy-mm-dd hh:ii:ss',
			    language:'zh-CN'
			});
			$('#datetimepicker1').datetimepicker();
			$('#modibtn').click(function(){
				
				var data = {
					
				};
				$('#reportTable').bootstrapTable('append',data);
				setTimeout(function (){
    				console.log($('#reportTable').bootstrapTable('getModiDatas'));
    			},60);
				
			});
			$('#del_btn').click(function(){
				$('#reportTable').bootstrapTable('removeRow',3);
				console.log($('#reportTable').bootstrapTable('getData'));
			});
    	});
    	function removeRow(rowIndex){
			$('#reportTable').bootstrapTable('removeRow',rowIndex);
		}
    </script>
  </head>
  
  <body>
  	
    <ul id="myTab" class="nav nav-tabs">
	   <li >
	      <a href="#home" data-toggle="tab">
	         W3Cschool Home
	      </a>
	   </li>
	   <li class="active"><a href="#ios" data-toggle="tab">iOS</a></li>
	   <li class="dropdown">
	      <a href="#" id="myTabDrop1" class="dropdown-toggle" 
	         data-toggle="dropdown">Java 
	         <b class="caret"></b>
	      </a>
	      <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
	         <li><a href="#jmeter" tabindex="-1" data-toggle="tab">jmeter</a></li>
	         <li><a href="#ejb" tabindex="-1" data-toggle="tab">ejb</a></li>
	      </ul>
	   </li>
	</ul>
	<div id="myTabContent" class="tab-content">
	   <div class="tab-pane fade" id="home">
	   		<form role="form" method="post" action="test/test.htm" >
			   <div class="form-group">
			      选择1
			      <input name="status1" class="form-control" id="order_status1" style="width:190px;" placeholder="请输入名称" value="2"/>
			   </div>
			   <div class="form-group">
			      <label for="inputfile">文件输入</label>
			      <input type="file" id="inputfile">
			      <p class="help-block">这里是块级帮助文本的实例。</p>
			      <input type="text" value="2012-05-15 21:05" id="datetimepicker" class="form-control">
			      <div class="input-append date" id="datetimepicker1" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
					    <input size="16" type="text" value="12-02-2012" readonly>
					    <span class="add-on"><i class="icon-th"></i></span>
				  </div>
				  <input type="time" name="set_order_time" id="set_order_time" value="20:10:20" id="order_time" class="form-control">
			   </div>
			   <div class="checkbox">
			      <label>选择2</label>
			     <!--  <input name="status2"  id="order_status2" style="width:190px;" /> -->
			     <select name="status2" style="width:190px;" id="order_status2" class="form-control" value="1">
            	 	<option value=1>lzx1</option>
            	 	<option value=2>lzx2</option>
            	 	<option value=10>lzx</option>
            	 	<option value=3>lsl</option>
            	 	<option value=4>hello</option>
            	 	<option value=5>she</option>
            	 </select>
			   </div>
			   <span style="border-radius:4px;display:block;width:125px;height:34px;overflow:hidden;border: 1px solid #ccc;">
			     <input type="text" style="float:left;width:100px;height:32px;" class="form-control"/>
			     <a href="javascript:;" style="float:right;position:relative;display:inline-block;width:20px;height:33px;background:url('bootstrap/plus/images/menu_arrows.png') no-repeat 0 center;"></a>
			   </span>
			   <button type="submit" class="btn btn-default">提交</button>
			</form>
	   		
			<button type="button" id="load_btn" class="btn btn-default">重新加载</button>
			
		    <!-- <span style="display:block;overflow:hidden;border-top:0px;">
				 <select class="form-control" style="position:absolute;" name="cmbno" id="cmbno">
					<option value="" selected>请选择</option>
					<option value='F000000001'>F000000001</option>
					<option value='F000000003'>F000000003</option>
				 </select>
				 <input type="text" value="请选择" id="cmbnoText" style="padding-left:17px;margin-top:2px;height:30px;border-left:0px;border-top:0px;border-right:0px;border-bottom:0px;position:absolute;bordert:0;">
		    </span> -->
	   </div>
	   
	   <div class="tab-pane fade in active" id="ios">
	   	  <button type="button" class="btn btn-default" id="modibtn">获取修改值</button>
	   	  <button type="button" id="del_btn" class="btn btn-default">删除</button>
	   	  
	      <table class="table table-striped table-hover" id="reportTable"></table>
	   </div>
	   <div class="tab-pane fade " id="jmeter">
	     	 <style type="text/css">
				.combobx {overflow:hidden;position:relative;}
				.combobxB {position:absolute;left:0;top:1px;clip:rect(1px auto auto 81px);}
				/*FF下调整一点点.combobxB {width:100px;position:absolute;left:0;top:0;clip:rect(2px auto auto 81px);}*/
				.combobxA {width:100px;}
			</style>
			
			<div class="combobx">
				<input type="text" class="combobxA">
				<select class="combobxB form-control">
					<option>小学</option>
					<option>中学</option>
					<option>大学</option>
				</select>
			</div>
	   </div>
	   <div class="tab-pane fade" id="ejb">
	      <p>Enterprise Java Beans（EJB）是一个创建高度可扩展性和强大企业级应用程序的开发架构，部署在兼容应用程序服务器（比如 JBOSS、Web Logic 等）的 J2EE 上。
	      </p>
	   </div>
	</div>
	
  </body>
</html>
