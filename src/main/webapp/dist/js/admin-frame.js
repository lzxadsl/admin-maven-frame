/**
 * 系统公用JS
 * @author 李智贤
 * @data 2015-09-10
 */
$(function(){
	$('input[type="text"]').attr('autocomplete','off');
});
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
			layer.open({
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
		this.each(function () {
			var _this = $(this);
			if(args != false){
				options['tiptype'] = function(msg,o,cssctl){
					//msg：提示信息;
					//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
					//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
					
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
					}	
				};
			}
			_this.Validform(options);
		});
	};
})(jQuery);

/*-----------------------------表单提交插件begin----------------------------------*/
(function(){
	$.fn.submitForm = function(options){
		
	};
})(jQuery);
/*-----------------------------表单提交插件end------------------------------------*/

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
