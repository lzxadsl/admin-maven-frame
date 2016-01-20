$.strapmes=new function(){
	this.method_me=null;
	this.init=function(){
		var id=guid();
		var type = arguments[0] ? arguments[0] : 'info';
		var width = arguments[1] ? arguments[1] : '300px';
		$("#"+id).remove();
		$('body').css('padding-right','0px');
		var html="";
		html+="<div class=\"modal fade\" style=\"margin-top:300px;\" id=\""+id+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">"+
				"<div class=\"modal-dialog\" style=\"width: "+width+";\">"+
					"<div class=\"modal-content\">"+
						"<div class=\"modal-header\">"+
							"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>"+
							"<h4 class=\"modal-title\" id=\"myModalLabel\" ></h4>"+
						"</div>"+
						"<div class=\"modal-body\">"+
							"<div class=\"messager-icon-type\"></div>"+
							"<div class=\"mes-con\"></div>"+
							"<div style=\"clear:both;\"></div>"+
						"</div>"+
						"<div class=\"modal-footer\">"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>";
		$("body").append(html);
		var img_str='';
		if(type=='info'){
			img_str="url('dist/images/messager_icons.png') no-repeat scroll 0 0";
		}else if(type=='error'){
			img_str="url('dist/images/messager_icons.png') no-repeat scroll -64px 0";
		}
		$("#"+id).find(".modal-body").find(".messager-icon-type").css({
			  'float': 'left',
			  'width': '32px',
			  'height': '32px',
			  'margin': '0 10px 10px 0',
			  'background': img_str
		});
		$('body').removeAttr('style');
		return id;
	};
	this.close=function() {
		$('.modal').modal('hide');
	};
	this.msgclose=function() {
		$('.modalmsg').modal('hide').remove();
	};
	this.msg = function() {
		if(arguments.length<1){
			return;
		}

		var content = arguments[0] ? arguments[0] : '';
		var title = arguments[1] ? arguments[1] : '提示';
		var type = arguments[2] ? arguments[2] : 'info';
		var method = arguments[3] ? arguments[3] : '';
		var width = arguments[4] ? arguments[4] : '300px';
		if(method&&jQuery.isFunction(method)){
			this.method_me=method;
		}else{
			this.method_me=function(){};
		}
		var id=this.init(type,width);
		$("#"+id).find('.modal-header').remove();
		$("#"+id).find('.modal-footer').remove();
		$("#"+id).find(".modal-body").find(".mes-con").html(content);
		$("#"+id).modal('show');
		$("#"+id).removeClass("fade");
		$("#"+id).addClass('modalmsg');
		$(document).on('click','.modalmsg',function() {
			$("#"+id).modal('show');
			return false;
		});

	};
	this.alert=function(){
		if(arguments.length<2){
			return;
		}
		var title = arguments[0] ? arguments[0] : '提示';
		var content = arguments[1] ? arguments[1] : '';
		var type = arguments[2] ? arguments[2] : 'info';
		var method = arguments[3] ? arguments[3] : '';
		var width = arguments[4] ? arguments[4] : '300px';
		if(method&&jQuery.isFunction(method)){
			this.method_me=method;
		}else{
			this.method_me=function(){};
		}//&&jQuery.isFunction(method)
		var id=this.init(type,width);
		$("#"+id).find(".modal-header").find(".modal-title").html(title);
		$("#"+id).find(".modal-body").find(".mes-con").html(content);
		var footer_str="";
		if(type=='info'||type=='error'){
			footer_str="<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" onclick=\"javascript:$.strapmes.method_me();\">确定</button>";
		}
		$("#"+id).find(".modal-footer").html(footer_str);
		$("#"+id).modal("show");
		$("#"+id).on('hidden.bs.modal', function (e) {
			$("#"+id).remove();
		})
	};
	this.confirm=function(){
		if(arguments.length<3){
			return;
		}
		var title = arguments[0] ? arguments[0] : '提示';
		var content = arguments[1] ? arguments[1] : '';
		var type = 'info';
		var method = arguments[2] ? arguments[2] : '';
		var width = arguments[3] ? arguments[3] : '300px';
		if(method&&jQuery.isFunction(method)){
			this.method_me=method;
		}else{
			this.method_me=null;
		}
		var id=this.init(type,width);
		$("#"+id).find(".modal-header").find(".modal-title").html(title);
		$("#"+id).find(".modal-body").find(".mes-con").html(content);
		var footer_str="<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" onclick=\"$.strapmes.method_me(true)\">确定</button>"+
		"<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\" onclick=\"$.strapmes.method_me(false)\">取消</button>";
		$("#"+id).find(".modal-footer").html(footer_str);
		$("#"+id).modal("show");
		
		$("#"+id).on('hidden.bs.modal', function (e) {
			$("#"+id).remove();
		})
	};
};
function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}