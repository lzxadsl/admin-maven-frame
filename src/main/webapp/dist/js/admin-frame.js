/**
 * 系统公用JS
 * @author 李智贤
 * @data 2015-09-10
 */
$(function(){
	$('input[type="text"]').attr('autocomplete','off');
});
var BASE_LIB_PATH = 'dist/lib/';//JS包根路径
(function($){
	$.extend({
		getBrowserMsg:function(){
			/**
			 * 获取浏览器类型
			 */
			var Sys = {};
			var ua = navigator.userAgent.toLowerCase();
			var s;
			(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
			(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
			(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
			(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
			(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
			return Sys;
		},
		setCookie:function(name, value){//设置cookies 
		    //设置名称为name,值为value的Cookie
		    var expdate = new Date();   //初始化时间
		    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);//时间
		    document.cookie = name+'='+value+';expires='+expdate.toGMTString()+';path=/';
		   //即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
		},
		getCookie:function(name){//读取cookies  
		    var arr,reg=new RegExp('(^| )'+name+'=([^;]*)(;|$)');
		    if(arr=document.cookie.match(reg)){
		    	return unescape(arr[2]); 
		    }
		    else{
		    	return null; 
		    }
		},
		delCookie:function(name){ 
		    var exp = new Date(); 
		    exp.setTime(exp.getTime() - 10000);//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间 
		    document.cookie = name + "=''; expires=" + exp.toGMTString(); 
		},
		/**
		 * 序列化表单
		 * @param form 表单对象
		 * @param returnString 返回的数据类型，默认是json对象,为true时返回的是一个json字符串
		 */
		serializeForm:function(form,returnString){//根据表单对象获取表单数据 ,返回类型为json对象
			var serializeObj = {}; 
			if(!(form instanceof jQuery)){
				form = $(form);
			}
			var array = form.serializeArray(); 
			$(array).each(function(){ 
				if(serializeObj[this.name]){ 
					if($.isArray(serializeObj[this.name])){ 
						serializeObj[this.name].push(this.value); 
					}else{ 
						serializeObj[this.name]=[serializeObj[this.name],this.value]; 
					} 
				}else{ 
					serializeObj[this.name]=this.value; 
				} 
			}); 
			//返回json字符串
			if(returnString){
				return JSON.stringify(serializeObj);
			}
			return serializeObj; 
		},
		/*弹出层*/
		/*
			参数解释：
			title	标题
			url		请求的url
			id		需要操作的数据id
			w		弹出层宽度（缺省调默认值）
			h		弹出层高度（缺省调默认值）
		*/
		layer_show:function(title,url,w,h){
			if (title == null || title == '') {
				title=false;
			};
			if (url == null || url == '') {
				url="404.jsp";
			};
			if (w == null || w == '') {
				w=800;
			};
			if (h == null || h == '') {
				h=($(window).height() - 50);
			};
			return layer.open({
				type: 2,
				area: [w+'px', h +'px'],
				fix: false, //不固定
				maxmin: true,
				shade:0.4,
				title: title,
				content: url
			});
		},
		/*关闭弹出框口*/
		layer_close:function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		}
	});
	
})(jQuery);

/**
 * 自定义表单验证提示效果，让提示信息滑入显示,
 * 依赖于Validform5+以上版本
 * Validform API:http://validform.rjboy.cn/document.html
 * */
(function($){
	/**
	 * @param config 使用跟Validform的配置即可
	 * @param args 为false时，该功能不起作用
	 * */
	$.fn.customValidform = function(options,args){
		//tiptype可用的值有：1、2、3、4和function函数，默认tiptype为1。 3、4是5.2.1版本新增
		options = options || {tiptype:2};
		var result = {};
		var _this = $(this);
		if(args != false){
			options['tiptype'] = function(msg,o,cssctl){
				//msg：提示信息;
				//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
				//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
				//var isPass = true;//是否验证通过
				if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
					var objtip = o.obj.parent().find(".Validform_checktip");
					if(objtip.length==0){
						var html = '<div class="Validform_info">'
										+'<span class="Validform_checktip Validform_wrong" style="margin-left:0;">'+msg+'</span>'
										+'<span class="Validform_dec">'
											+'<s class="dec1">◆</s>'
											+'<s class="dec2">◆</s>'
										+'</span>'
									+'</div>';
						o.obj.parent().append(html);
						objtip = o.obj.parent().find(".Validform_checktip");
					}
					cssctl(objtip,o.type);
					var infoObj=o.obj.parent().find(".Validform_info");
					if(o.type==2){
						infoObj.fadeOut(200);
					}else{
						//isPass = false;
						if(infoObj.is(":visible")){return;}
						var left=o.obj.offset().left;
							//top=o.obj.offset().top;
						var top = o.obj.outerHeight(),
							left = o.obj.outerWidth();
				    	var browserV = $.getBrowserMsg();
				    	if(browserV.ie != undefined && parseFloat(browserV.ie) > 8){
				    		top = o.obj.height();
				    	}
						infoObj.css({
							left:left/2,
							top:-top
						}).show().animate({
							//top:top-5	
						},200);
					}
					//options['isPass'] = isPass;
				}	
			};
		}
		result = _this.Validform(options);
		return result;
	};
})(jQuery);

/*-----------------------------表单提交插件begin----------------------------------*/
/*依赖layout做弹出提示*/
(function(){
	$.fn.ajaxSubmitForm = function(options){
		var $this = this;
		if(!($this instanceof jQuery)){$this = $($this);}
		if($this.attr('action'))options['url'] = $this.attr('action');
		options = $.extend({},$.fn.ajaxSubmitForm.defaults,
                typeof options === 'object' && options);
    	if(options.url){
    		var formData = {};
    		//提交参数类型为json字符串
    		if(options.paramType.toUpperCase() == 'STRING'){
    			formData[options.paramField] = $.serializeForm($this,true);
    		}else{//提交参数类型为json对象
    			formData = $.serializeForm($this);
    		}
    		
    		if(options.confirm){
    			layer.open({
				    content: options.confirmMsg,
				    btn: options.btn,
				    shadeClose: false,
				    title:options.title,
				    icon:3,
				    yes: function(index, layero){
				    	layer.close(index);
				    	submitServer(options,formData);
				    },cancel: function(index){
				    	options.onCancel.call(this,index);
				    }
    			});
    		}else{
    			submitServer(options,formData);
    		}
        	
    	}else{
    		layer.alert('URL 无效', {icon: 5});
    	}
	};
	//提交请求
	function submitServer(options,formData){
		var tipMsg = layer.msg(options.waitMsg, {icon: 16,shade:[0.1,'#000'],time:0});
		options.onBeforeSubmit.call(this,formData);
		$.ajax({
    		url:options.url,
    		type:options.method,
    		data: formData,
    		dataType:'json',
    		success: function(jsonLst) {
    			options.onSubmitSuccess.call(this,jsonLst);
    			layer.close(tipMsg);
			},
			error: function(xhr, textStatus, errorThrown){
				options.onSubmitError.call(this,xhr, textStatus, errorThrown);
				layer.close(tipMsg);
		    }
    	});
	}
	//默认配额制项
	$.fn.ajaxSubmitForm.defaults = {
			url:'',
			method:'get',
			paramType:'object',//提交的参数类型，默认是json对象，如果是string，则提交的数据是json字符串
			paramField:'params',//提交数据为json字符串时，提交到后台的参数名
			confirm:true,//提交前是否弹出提示，默认是
			confirmMsg:'确定提交保存？',
			waitMsg:'正在保存，请稍等...',
			btn: ['确认', '取消'],
			title:'提示',
			onBeforeSubmit:function(data){},//表单数据
			onSubmitSuccess:function(ret){},//ret是提交成功后的返回值
			onSubmitError:function(xhr, textStatus, errorThrown){},
			onCancel:function(index){}//取消保存操作
	};
})(jQuery);
/*-----------------------------表单提交插件end------------------------------------*/

/**
 * 查询条件，自动获取、重置
 * @author lizhixian
 * @version 1.0
 * @create_date 2015-09-23
 */
(function($){
	$.fn.extend({
		/**
		 * 输出序列化表单查询属性和值{key1:value1,key2:value2}
		 */
		getSearchParams:function(params){
			var id = $(this).attr('id');
			params = params || {};
			$('#'+id+' [name]').each(function(index, element) {
				var name = element.name.substring(element.name);
				var value = $(element).val();
				if(value){
					params[name] = value;
				}
		    });	
			return params;
		},
		/**
		 * 重置查询条件,$('#id').reSet(false)
		 * @param includeHidden 为false时，不重置隐藏域，反之则重置
		 */
		reSet:function(includeHidden){
			return this.each(function() {
		        $('input,select,textarea', this).clearInputs(includeHidden);   //this表示设置上下文环境，有多个表单时只作用调用的表单
		    });
		},
		clearInputs:function(includeHidden) {
		    var re = /^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i; // 'hidden' is not in this list
		    return this.each(function() {
		        var t = this.type, tag = this.tagName.toLowerCase();
		        if (re.test(t) || tag == 'textarea') {
		            this.value = '';
		        }
		        else if (t == 'checkbox' || t == 'radio') {
		            this.checked = false;
		        }
		        else if (tag == 'select') {
		        	//重置后根据defaultIndex属性来设置重置后的选项
		            this.selectedIndex = $(this).attr('defaultIndex')?$(this).attr('defaultIndex'):-1;
		        } 
		        else if (t == "file") {
		            if (/MSIE/.test(navigator.userAgent)) {
		                 $(this).replaceWith($(this).clone(true));
		            } else {
		                 $(this).val('');
		            }
		       }
		        else if (includeHidden) {
		            if ((includeHidden === true && /hidden/.test(t)) ||
		                 (typeof includeHidden == 'string' && $(this).is(includeHidden)) ) {
		                this.value = '';
		            }
		        }
		    });
		}
	});
})(jQuery);
/*------------------------uploadify文件上传，扩展插件---------------------------*/
(function($){
	$.fn.uploadFile = function(option){
		var options = $.extend({},$.fn.uploadFile.defaults,
                typeof option === 'object' && option);
		
		/*if($('script[src*="jquery.uploadify"]').length == 0){
			//jQuery.getScript(BASE_LIB_PATH+'uploadify/jquery.uploadify.min.js');
			//$("<script type='text/javascript' src='"+BASE_LIB_PATH+"uploadify/jquery.uploadify.min.js'></script>").appendTo('head');
		}*/
		var html = '<div style="display:none;padding:10px 10px 0px 10px;width:400px;" class="container" id="layeruploadify">'
				   		+'<div class="form-group">'
				   			+'<span for="name">'+options.headText+'</span>'
				   		+'</div>'
				   		+'<div class="form-group">'
				   			+'<input type="file" name="uploadify" id="uploadify" />'
						+'</div>'
						+'<div style="height:150px;overflow:auto;">'
						+'<div id="uploadifyFileQueue"></div>'
						+'</div>'
						+'<div class="form-group">'
							+'<button type="button" id="uploadifySubmit" class="btn btn-info"><span class="glyphicon glyphicon-cloud-upload"></span> 上 传</button> '
							+'<button type="button" id="uploadifyCancel" class="btn btn-info"><span class="glyphicon glyphicon-remove-circle"></span> 取 消</button>'
						+'</div>'
				   +'</div>';
		if($('#layeruploadify').length == 0){
			$(html).appendTo(this);
		}
		var uploadwin = layer.open({
	  		    type: 1,
	  		    title:options.winTitle,
	  		    area: [options.winWidth+'px', options.winHeight+'px'], //宽高
	  		    content:$('#layeruploadify')
	  	});
		//初始化上传控件
		options['onUploadSuccess'] = function(file, data, response){
			var queueSize = $('#uploadify').data('uploadify').queueData.queueLength;
			options.onSuccess.call(this,file, data, response,queueSize);
		};
		var uploadify = $('#uploadify').uploadify(options);
		//上传
		$('#uploadifySubmit').unbind('click').click(function(){
			if($('#uploadify').data('uploadify').queueData.queueLength == 0){
				layer.alert('请选择文件！', {icon: 2});
			}
			$('#uploadify').uploadify('upload');
	  	});
		//取消
		$('#uploadifyCancel').unbind('click').click(function(){
			$('#uploadify').uploadify('cancel','*');
			$('#uploadify').uploadify('destroy');
			layer.close(uploadwin);
		});
		return uploadify;
	};
	/*var buttonText = '<button type="button" class="btn btn-info">'
						+'<span class="glyphicon glyphicon-folder-open"></span> 选择文件'
					+'</button>';*/
	//默认配置
	$.fn.uploadFile.defaults = {
		winWidth:420,
		winHeight:340,
		winTitle:'文件上传',
		swf: 'dist/lib/uploadify/uploadify.swf', 
		cancelImg: 'dist/lib/uploadify/uploadify-cancel.png',
		uploader: '',
		queueID: 'uploadifyFileQueue',
		fileSizeLimit:'10MB',
		fileTypeDesc:'请选择文件',
		buttonText:'选择文件', 
		fileTypeExts:'*.*;',
		auto: false,
		queueSizeLimit:5,
		onSelect: function (fileObj){
            if(fileObj.size == 0){
                alert(runicode("不能上传一个大小为 0 字节的文件！"));
                return false;
            }
        }, 
		onSuccess:function(file, data, response,queueSize){},
		onUploadError:function(){}
	};
})(jQuery);
/*-----------------------uploadify文件上传，扩展插件end-------------------------*/
/*placeholder兼容性处理*/
(function(window, document, $) {
	var isInputSupported = 'placeholder' in document.createElement('input');
	var isTextareaSupported = 'placeholder' in document.createElement('textarea');
	var prototype = $.fn;
	var valHooks = $.valHooks;
	var propHooks = $.propHooks;
	var hooks;
	var placeholder;

	if (isInputSupported && isTextareaSupported) {
		placeholder = prototype.placeholder = function() {
			return this;
		};
		placeholder.input = placeholder.textarea = true;
	} else {
		placeholder = prototype.placeholder = function() {
			var $this = this;
			$this
				.filter((isInputSupported ? 'textarea' : ':input') + '[placeholder]')
				.not('.placeholder')
				.bind({
					'focus.placeholder': clearPlaceholder,
					'blur.placeholder': setPlaceholder
				})
				.data('placeholder-enabled', true)
				.trigger('blur.placeholder');
			return $this;
		};
		placeholder.input = isInputSupported;
		placeholder.textarea = isTextareaSupported;
		hooks = {
			'get': function(element) {
				var $element = $(element);
				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value;
				}
				return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '' : element.value;
			},
			'set': function(element, value) {
				var $element = $(element);
				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value = value;
				}
				if (!$element.data('placeholder-enabled')) {
					return element.value = value;
				}
				if (value == '') {
					element.value = value;
					if (element != safeActiveElement()) {
						setPlaceholder.call(element);
					}
				} else if ($element.hasClass('placeholder')) {
					clearPlaceholder.call(element, true, value) || (element.value = value);
				} else {
					element.value = value;
				}
				return $element;
			}
		};

		if (!isInputSupported) {
			valHooks.input = hooks;
			propHooks.value = hooks;
		}
		if (!isTextareaSupported) {
			valHooks.textarea = hooks;
			propHooks.value = hooks;
		}

		$(function() {
			$(document).delegate('form', 'submit.placeholder', function() {
				var $inputs = $('.placeholder', this).each(clearPlaceholder);
				setTimeout(function() {
					$inputs.each(setPlaceholder);
				}, 10);
			});
		});

		$(window).bind('beforeunload.placeholder', function() {
			$('.placeholder').each(function() {
				this.value = '';
			});
		});
	}

	function args(elem) {
		var newAttrs = {};
		var rinlinejQuery = /^jQuery\d+$/;
		$.each(elem.attributes, function(i, attr) {
			if (attr.specified && !rinlinejQuery.test(attr.name)) {
				newAttrs[attr.name] = attr.value;
			}
		});
		return newAttrs;
	}

	function clearPlaceholder(event, value) {
		var input = this;
		var $input = $(input);
		if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
			if ($input.data('placeholder-password')) {
				$input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
				if (event === true) {
					return $input[0].value = value;
				}
				$input.focus();
			} else {
				input.value = '';
				$input.removeClass('placeholder');
				input == safeActiveElement() && input.select();
			}
		}
	}

	function setPlaceholder() {
		var $replacement;
		var input = this;
		var $input = $(input);
		var id = this.id;
		if (input.value == '') {
			if (input.type == 'password') {
				if (!$input.data('placeholder-textinput')) {
					try {
						$replacement = $input.clone().prop('type','text');
					} catch(e) {
						$replacement = $('<input>').prop($.extend(args(this), { 'type': 'text' }));
					}
					$replacement
						.removeAttr('name')
						.data({
							'placeholder-password': $input,
							'placeholder-id': id
						})
						.bind('focus.placeholder', clearPlaceholder);
					$input
						.data({
							'placeholder-textinput': $replacement,
							'placeholder-id': id
						})
						.before($replacement);
				}
				$input = $input.removeAttr('id').hide().prev().attr('id', id).show();
			}
			$input.addClass('placeholder');
			$input[0].value = $input.attr('placeholder');
		} else {
			$input.removeClass('placeholder');
		}
	}
	function safeActiveElement() {
		try {
			return document.activeElement;
		} catch (exception) {}
	}
}(this, document, jQuery));

/*------------------日期公共方法--------------------*/
(function($){
	var dateUtil = {
		/**
		 * 获取指定月的最后一天
		 * @param strData 日期 字符串类型
		 * @return 日期字符串
		 */
		getCurrentMonthLast:function (strData){
			 var date=new Date(strData.replace(/-/g,"/"));
			 var currentMonth=date.getMonth();
			 var nextMonth=++currentMonth;
			 var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
			 var oneDay=1000*60*60*24;
			 var newDate = new Date(nextMonthFirstDay-oneDay);
			 var year = newDate.getFullYear();
			 var month = newDate.getMonth()+1;
			 var day = newDate.getDate();
			 return  year + '-' + (month < 10 ? '0'+month : month) +'-'+ (day < 10 ? '0'+day : day); 
		},

		/**
		 * 获取时间段
		 * @param date 指定日期,格式：2015-01-01
		 * @param n 不进行赋值则获取当前日期，格式 'm:-2',表示获取指定日期到向前2个月的时间段,d 获取日时间段、y 获取年时间段
		 * @return 字符串数组
		 */
		getDateSection:function(date,n){
		    var arr = date.split('-');
		    n = n || 'd:+0';
		    //转换成日期对象
			var dateObj = new Date(parseInt(arr[0], 10),parseInt(arr[1], 10) - 1, parseInt(arr[2], 10));
			var nType = n.split(':')[0];
		    var num = parseInt(n.split(':')[1],10);
		    var dateSection = [];//时间段数组
		    var absNum = Math.abs(num);//取绝对值
		    switch(nType){
		    	case 'd' :
		    	    if(num < 0){//直接设置成num天前日期
		    	    	dateObj.setDate(dateObj.getDate() - absNum);
		    	    }
		    		for(var i = 0; i < absNum; i++){
		    			dateObj.setDate(dateObj.getDate() + 1);
		    			var month = dateObj.getMonth()+1;
		    			dateSection.push(dateObj.getFullYear() + '-' + (month < 10 ? '0'+month : month) + '-' + (dateObj.getDate() < 10 ? '0'+dateObj.getDate() : dateObj.getDate()));
		    		}
		    	break;
		    	case 'm' : 
		    		dateObj.setDate(1);//因为每个月最后一天不一样，所以统一设置成一号来进行计算
		    		if(num < 0){//直接设置成num月前日期
		    			dateObj.setMonth(dateObj.getMonth() - absNum);
		    		}
		    		for(var i = 0; i < absNum; i++){
		    			dateObj.setMonth(dateObj.getMonth() + 1);
		    			var month = dateObj.getMonth()+1;
		    			dateSection.push(dateObj.getFullYear() + '-' + (month < 10 ? '0'+month : month));
		    		}
		    	break;
		    	case 'y' :
		    	    if(num < 0){//直接设置成num年前日期
		    			dateObj.setFullYear(dateObj.getFullYear() - absNum);
		    		} 
		    		var year = dateObj.getFullYear()+1;
		    		for(var i = 0; i < absNum; i++){
		    			dateSection.push(''+year++);//转成字符串
		    		}
		    	break;
		    	default :'';
		    }
		    return dateSection;
		},

		/**
		 * 获取当前日期 
		 * @param isMonth true返回年月 false 返回年月日
		 * @param n 不进行赋值则获取当前日期，格式 'm:+/-2',表示当前日期+/-2个月,d +/-天 、 m +/-月 、 y +/-年
		 * @return 日期字符串
		 */
		getCurrentDate:function(isMonth,n){
		    var mydate = new Date();
		    n = n || 'd:+0';
		    var nType = n.split(':')[0];
		    var num = parseInt(n.split(':')[1],10);
		    switch(nType){
		    	case 'd' : mydate.setDate(mydate.getDate() + num); break;
		    	case 'm' : mydate.setMonth(mydate.getMonth() + num); break;
		    	case 'y' : mydate.setFullYear(mydate.getFullYear() + num); break;
		    	default :'';
		    }
			var year = mydate.getFullYear();
			var month = mydate.getMonth()+1;
			var day = mydate.getDate();
			var retDate = '';
			if(isMonth){
				retDate = year + '-' + (month < 10 ? "0"+month : month);
			}
			else{
				retDate = year + '-' + (month < 10 ? "0"+month : month) + '-' + (day < 10 ? '0'+day : day);
			}
			return retDate;
		},

		/**
		 * 获取指定日期的
		 * @param isMonth true返回年月 false 返回年月日
		 * @param date 指定日期
		 * @param n 不进行赋值则获取当前日期，格式 'm:+/-2',表示当前日期+/-2个月,d +/-天 、 m +/-月 、 y +/-年
		 * @return 日期字符串
		 */
		getPointDate:function(isMonth,date,n){
		    var mydate = new Date(date);
		    n = n || 'd:+0';
		    var nType = n.split(':')[0];
		    var num = parseInt(n.split(':')[1],10);
		    switch(nType){
		    	case 'd' : mydate.setDate(mydate.getDate() + num); break;
		    	case 'm' : mydate.setMonth(mydate.getMonth() + num); break;
		    	case 'y' : mydate.setFullYear(mydate.getFullYear() + num); break;
		    	default :'';
		    }
			var year = mydate.getFullYear();
			var month = mydate.getMonth()+1;
			var day = mydate.getDate();
			var retDate = '';
			if(isMonth){
				retDate = year + '-' + (month < 10 ? "0"+month : month);
			}
			else{
				retDate = year + '-' + (month < 10 ? "0"+month : month) + '-' + (day < 10 ? '0'+day : day);
			}
			return retDate;
		}
	};
	$.extend({
		dateUtil:dateUtil
	});
})(jQuery);
/*-----日期格式化-----*/
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1,
        // month
        "d+": this.getDate(),
        // day
        "h+": this.getHours(),
        // hour
        "m+": this.getMinutes(),
        // minute
        "s+": this.getSeconds(),
        // second
        "q+": Math.floor((this.getMonth() + 3) / 3),
        // quarter
        "S": this.getMilliseconds()
        // millisecond
    };
    if (/(y+)/.test(format) || /(Y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/*-----------------------------------日期公共方法end-------------------------------------*/
/**防止按删除键时返回上个页面*/
document.onkeydown = backSpaceDown;
function backSpaceDown(e){
	if(((event.keyCode == 8) && ((event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password")||
	event.srcElement.readOnly == true))||((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82))) || (event.keyCode == 116)) {
		event.keyCode = 0;
		event.returnValue = false;
	}
	return true;
}