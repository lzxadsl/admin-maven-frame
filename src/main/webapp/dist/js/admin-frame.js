/**
 * 系统公用JS
 * @author 李智贤
 * @data 2015-09-10
 */

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
			return Sys
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
