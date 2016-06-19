/**
 * bootstrap 搜索框插件
 * @author lizx 
 * @version 1.0
 * @date 2015-09-30
 */
(function($){
	/*
	 * 调用例子，页面中放一个input，然后在JS中初始化，没制定width默认是100%
	 * <input id="search" style="width:350px;"/>
	 $(function(){
    	$('#search').search_tip({
    		url:'${ctx}/lizx/ajaxSearchList.html',
    		textField:'resourceName',
    		onSelect : function(keyword,rec){
    			console.log(keyword);
    		},
    		onSearchClick:function(keyword,rec){
    			console.log('v:'+keyword);
    		}
    	});
     });*/
	 /**
	  * 调用入口:$('#search').search_tip({});
	  * options 为对象时是属性设置参数，为字符串时，是调用具体方法，
	  * agrs 为调用方法时的参数
	  */
	 $.fn.search_tip = function(option,agrs){
		 var value = null;
		 this.each(function () {
             var $this = $(this),
             data = $this.data('bootstrap.select'),
             options = $.extend({},$.fn.search_tip.defaults,$this.data(),
                    typeof option === 'object' && option);
             
             if (typeof option === 'string') {
            	 if (!data) {
                     return;
                 }
            	 value = $.fn.search_tip.methods[option](this,data.options,agrs);
            	 //value = data[option](agrs);
            	 if (option === 'destroy') {
                     $this.removeData('bootstrap.select');
                 }
             }
             if (!data) {
            	 //为元素添加data属性
            	//判断标签类型,如果是select标签则转换成input标签
            	 if($this[0].tagName != 'INPUT'){
            		var data = [];
            		$this.find('option').each(function(){
            			var obj = {};
            			obj[options.valueField] = $(this).val();
            			obj[options.textField] = $(this).text();
            			data.push(obj);
            		});
            		if(!options.data || options.data.length == 0){
            			options['data'] = data;
            		}
                 	var inputEl = $('<input name=""  placeholder="请选择"/>');
                 	inputEl.insertAfter($this);
                 	var newEl = $this.next();
                 	newEl.attr('id',$this.attr('id'));
                 	newEl.attr('name',$this.attr('name'));
                 	newEl.attr('value',$this.attr('value'));
                 	newEl.attr('style',$this.attr('style'));
                 	$this.remove();
                 	$this = $(newEl);
                 }
                 $this.data('bootstrap.select', (data = new BootstrapSelect($this,options)));
             }
        });
		 
		return typeof value === 'undefined' || value == null ? this : value;
	 };
	 
	 /**
	 * 默认参数配置信息
	 * data：格式{data:[{title:null,result:{}},{title:null,result:{}}]}
	 * url和data参数只有一个生效，url优先
	 * 如果有option选项，则它的优先级低于data
	 */
	$.fn.search_tip.defaults = {
			url    : null, //请求路径
			params : {},   //请求参数
			paramsType : '',//参数默认是以表单形势传递，为json时是以json格式传递到后台
			data   : [],   //数据[{key:value},{key:value}]
	        method : 'get',//请求方法
	        textField  : 'text',//显示文本字段
	        valueField : '',//隐藏文本字段
	        emptyText  : null,//（暂时无用）空选项文本，该属性为null或undefined时不创建空选项，默认不创建
	        emptyValue : '',//（暂时无用）空选项值
	        separator  : ',',//多选时返回值的分割符
	        editable   : true,//是否可编辑
	        multiple : false,//多选开关
	        disabled : false,//禁用
	        sildUp : false,//为true时当下拉框顶到底部时会向上弹出
	        //downBorder : false,//下拉按钮是否带边框
	        filterRemote:{
	        	field:'keyword'//过滤的参数字段名称
	        },//是否开启远程过滤,false时关闭该功能
	        initLoad:false,//初始化页面时立即加载（一般是加载历史搜索）
	        cls:'',//自定义样式,多个样式用逗号隔开 class1,class2
	        formatter:function(rec){},//格式化节点	
	        onSelect : function(val,rec){},//选中回调
	        unSelect : function(val,rec){},//反选回调
	        onBeforeLoad: function(param){},//param 请求参数
			onLoadSuccess: function(data){},//data加载成功后返回的数据
			onLoadError: function(){},
			onSearchClick:function(val,rec){},//点击查询按钮回调
			filter : false//选项过滤
	};
	
	/**
	 * 控件方法属性
	 */
	$.fn.search_tip.methods = {
			/**
			 * 获取下拉框选中值,多选的时候返回的是按指定分割符分割的字符串
			 */
			getValue:function(target,options){
				return $(target).parent().find('input[type="hidden"]').val();
			},
			/**
			 * 获取下拉框选中文本,多选的时候返回的是按指定分割符分割的字符串
			 */
			getText:function(target,options,args){
				return $(target).parent().find('input[type="text"]').val();
			},
			/**
			 * 设置选中，如果是多选 value 格式：2，3，4
			 */
			select:function(target,options,values){
				if($(target).data('bootstrap.select').$contentDownList){
					setValues($(target).data('bootstrap.select'),values);
				}
				else{
					var int = setInterval(function(){
						if($(target).data('bootstrap.select').$contentDownList){
							clearInterval(int);
							setValues($(target).data('bootstrap.select'),values);
						}
					},1000);
				}
			},
			/**
			 * 获取所有下拉节点的数据集，返回值是数组类型
			 */
			getData:function(target,options){
				return options.data;
			},
			/**
			 * 重新加载，可重定向url和params
			 */
			reload:function(target,options,args){
				args = args || {};
				if(args.url){
					options['url'] = args.url;
				}
				if(args.params){
					options['params'] = args.params;
				}
				if(args.emptyText != null){
					options['emptyText'] = args.emptyText;
				}
				if(args.emptyValue != null){
					options['emptyValue'] = args.emptyValue;
				}
				$(target).data('bootstrap.select').reload();
			},
			/**
			 * 刷新
			 */
			refresh:function(target){
				$(target).data('bootstrap.select').reload();
			},
			/**
			 * 清空节点
			 */
			clear:function(target){
				$(target).data('bootstrap.select').clear();
			}
			
	};
	
	var BootstrapSelect = function (el,options) {
		options.valueField?'':options.valueField = options.textField;
        this.options = options;//配置项
        this.$el = el;//文本框
        this.$el_ = this.$el.clone();
        this.timeoutId_ = 0;
        this.icoUrl = 'dist/images/icon_search.png';//搜索图标路径
        this.clearicoUrl = 'dist/images/clear.png';//清除图标路径
        //键盘上功能键键值数组
        this.functionalKeyArray = [9,20,13,16,17,18,91,92,93,45,36,33,34,35,37,39,112,113,114,115,116,117,118,119,120,121,122,123,144,19,145,40,38,27];
        this.$contentDownList = null;//下拉框
        this.options.downListIdPrefix = '_bootstrap_combox_il_';//下拉框id前缀
        this.lastSelText = [];//保存最后一次选择的内容
        this.lastKeyWord = null;//最后一次输入的内容
        this.options.downListCls = 'combox-downlist';//下拉框样式，目前此样式为空，预留
        this.options.selItemCls = 'combox-item-selected',//被选择节点样式,目前犹豫没有引用样式文件，所以此样式只是个空的
        this.options.selItemColor = '#ffe48d';//被选中节点背景色，暂时使用这个
        this.contentDownId = null;//下拉框id
        this.init();
    };

    //初始化控件
    BootstrapSelect.prototype.init = function(){
    	this.createSelect();//创建文本选择框
    	this.initServer();//数据加载，并创建下拉节点
    	this.initEvent();//添加事件
    };
    //向服务器请求数据
    BootstrapSelect.prototype.initServer = function(){
    	var $this = this;
    	if($this.options.url && $this.options.initLoad){
    		$this.options.onBeforeLoad.call(this,$this.options.params);
        	$.ajax({
        		url:$this.options.url,
        		type:$this.options.method,
        		data:$this.options.paramsType == 'json' ? JSON.stringify($this.options.params) : $this.options.params,
        		dataType:'json',
        		success: function(jsonLst) {
        			$this.options.onLoadSuccess.call(this,jsonLst);
        			$this.createDownList(jsonLst);
    			},
    			error: function(xhr, textStatus, errorThrown){
    				$this.options.onLoadError.call(this);
    				$this.createDownList([]);
    				throw 'ajax 数据加载失败';
    		    }
        	});
    	}
    	else if(!$this.options.data || typeof $this.options.data != 'object'){
    		throw 'data 数据类型有误，必须为数组';
    	}
    	else{
    		$this.createDownList();
    	}
    };
    BootstrapSelect.prototype.initStyle = function(){
    	var result = {};
    	var inputStyle = 'display: block;width:100%;height:36px;'
		  +'padding: 6px 12px 6px 10px;float:left;font-size: 14px;'
		  +'line-height: 1.42857143;color: #555;'
		  +'background-color: #fff;'
		  +'background-image: none;'
		  +'border: 1px solid #ccc;'
		  +'border-radius: 0;'
		  +'outline:none;'
		  //+'border-bottom-left-radius:4px !important;'
		  //+'border-top-left-radius:4px !important;'
		  +'-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);'
		  +'box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);';
    	  +'-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;'
    	  +'-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;'
    	  +' transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;';
    	result['input'] = inputStyle;
    	return result;
    };
    /**
     * 创建选择框
	 */
    BootstrapSelect.prototype.createSelect = function(){
    	var $this = this;
    	var $input = $this.$el;
    	var options = $this.options;
    	var style = $this.initStyle();
    	//添加样式
    	$input.attr('style',style.input+$input.attr('style'));
    	//$input.addClass('search-input');
    	$input.addClass(options.cls.replace(/,/g,' '));
    	//获取元素宽、高
    	var width = $input.outerWidth() - 1;
    	var height = $input.outerHeight();
    	var browserV = getBrowserMsg();
    	if(browserV.ie != undefined && parseFloat(getBrowserMsg().ie) > 8){
    		height = $input.height();
    	}
    	if(browserV.ie != undefined && options.emptyText == null){
    		options.emptyText = '--请选择--';
    	}
    	var aWidth = 45;//a标签宽度
    	var cleanBtnWidth = 20;//打叉图标宽度
    	var name = $input.attr('name')?$input.attr('name'):'';//name属性
    	$input.removeAttr('name');//删除name属性，最终把name属性移到隐藏域上
    	$input.wrap('<span style="height:'+height+'px;display:block;overflow:hidden;width:'+(width)+'px;"></span>');
    	$input.attr('autocomplete','off');
    	$input.prop('type','text');
    	if(!$input.attr('placeholder')){
    		$input.attr('placeholder','请输入搜索内容');
    	}
    	
    	if(options.emptyText != undefined && options.emptyText != null){
    		$input.val(options.emptyText);
    	}
    	
		$input.css('width',(width-aWidth-cleanBtnWidth-aWidth)+'px');
		//20151012 修改a高度-1，改变属性position 为 relative，增加float
		$input.css('border-right',0);
		//下拉按钮按钮
		var leftastyle = 'float:left;position:relative;display:inline-block;'
			+'width:'+aWidth+'px;height:'+(height)+'px;border:solid 1px #ccc;border-right:0;';
		$('<a href="javascript:;" id="searchDownBtn" style="'+leftastyle+'">'
				+'<span style="margin: 14px 4.5px 13px 17px;display: inline-block;'
					+'width: 0px;height: 0px;border-top: 5px solid #555;'
					+'border-right: 5px solid transparent;border-left: 5px solid transparent">'
				+'</span>'
			+'</a>').insertBefore($input);
		//搜索按钮
		var astyle = 'position:relative;display:inline-block;'
			+'width:'+aWidth+'px;height:'+(height)+'px;border:solid 0px #ccc;'
			+'border-left:0; ';
		astyle += 'background:#3385ff url('+$this.icoUrl+') no-repeat center;';
		$('<a href="javascript:;" id="searchSubmitBtn" style="'+astyle+'"></a>').insertAfter($input);
		//打叉图标宽度
		var cleanastyle = 'float:left;position:relative;display:inline-block;'
			+'width:'+cleanBtnWidth+'px;height:'+(height)+'px;border-top:solid 1px #ccc;border-bottom:solid 1px #ccc;';
		$('<a href="javascript:;" id="searchCleanBtn" style="'+cleanastyle+'"></a>').insertAfter($input);
		
    	$('<input type="hidden" name="'+name+'" value="'+options.emptyValue+'">').insertAfter($input);
    };

    /**
     * 事件初始化
     */
    BootstrapSelect.prototype.initEvent = function(){
    	var $this = this;
    	var $input = $this.$el;
    	
    	if($this.options.disabled){
    		$input.attr('readonly',true);
    		return;
    	}

    	//下拉按钮
    	$('#searchDownBtn').unbind('click').click(function(){
    		if(!$this.$contentDownList.is(":visible")){
    			$this.showDownList();
    		}else{
    			$this.hideDownList();
    		}
    	});
    	//搜索按钮
    	$('#searchSubmitBtn').unbind('click').click(function(){
    		$this.options.onSearchClick.call(this,$input.val(),$this.options.data);
    	});
    	//清除按钮
    	$('#searchCleanBtn').unbind('click').click(function(){
    		$this.$el.val('');
    		$(this).css('background','none');
    		$this.$el.nextAll('input').val('');
    	});
		//文本框点击事件,
    	$input.unbind('click').click(function(){
    		$this.showDownList();
    		if(!$this.options.editable){
    			$(this).blur();
        	}
    	});
    	//文本框得到焦点
    	$input.unbind('focus').focus(function(){
    		$(this).css('box-shadow','rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset');
    		$this.showDownList();
		});
    	//文本框失去焦点
    	$input.unbind('blur').blur(function(){
    		//$this.$el.css('border-color','#ccc');
		});
    	
    	//添加按键事件
    	$input.unbind('keyup').keyup(function(event){
    		if($this.options.filterRemote){
    			$this.filterRemote(event);
    		}else{
    			$this.keyUp(event);
    		}
    	});
    	//添加按键事件
    	$input.unbind('keydown').keydown(function(event){
    		$this.keyDown(event);
    	});
    };
    /**
     * 创建下拉列表
	 */
    BootstrapSelect.prototype.createDownList = function(data){
    	var $this = this;
    	if(data){
    		$this.options['data'] = data;
    	}
    	//var width = $this.$el.outerWidth();
    	var height = $this.$el.outerHeight();
    	var spanWidth = $this.$el.parent().outerWidth();//整个下拉框宽度
    	var style = 'display: none;'
    		+'background-color: #FFFFFF;'
    		//+'border: 1px solid '+($this.options.downBorder?'#ccc;':'#66afe9;')
    		+'border: 1px solid #ccc;'
    		+'border-top:0;'
    		+'position: absolute;'//relative absolute
    		+'z-index: 10000;'
    		+'float:left;'
    		+'width:'+(spanWidth)+'px;'
    		+'margin-top:'+height+'px;'//20151012 增加该属性
    		+'max-height: 220px;'
    		+'overflow-x: auto;'
    		+'overflow-y: auto;';

    	$('<div style="'+style+'"></div>').appendTo($this.$el.parent());
    	var $div = $this.$el.parent().find('div');

		//鼠标悬停时选中当前行
    	$div.delegate('div', 'mouseover', function() {//鼠标经过，设置背景色
			if(!$(this).hasClass($this.options.selItemCls)){//已被选择的除外
				$(this).css('background','none repeat scroll 0 0 #0080FF');
				$(this).css('color','#FFFFFF');
			}
			
		}).delegate('div', 'mouseout', function() {//鼠标移出，清除背景色
			if(!$(this).hasClass($this.options.selItemCls)){//已被选择的除外
				$(this).css('background','');
				$(this).css('color','#000000');
			}
		}).delegate('div', 'click', function(){//单击选择
			$this.select($(this));
		});
		$this.$contentDownList = $div;
		makeContAndShow($this);
    };
    
    /**
     * 远程过滤
     */
    BootstrapSelect.prototype.filterRemote = function(event){
    	var $this = this;
    	var functionalKeyArray = $this.functionalKeyArray;
		//输入框keyup事件
		var k = event.keyCode;
		var ctrl = event.ctrlKey;
		var isFunctionalKey = false;//按下的键是否是功能键
		for(var i=0;i<functionalKeyArray.length;i++){
			if(k == functionalKeyArray[i]){
				isFunctionalKey = true;
				break;
			}
		}
		var $contentDownList = $this.$contentDownList;

		//k键值不是功能键或是ctrl+c、ctrl+x时才触发自动补全功能
		if(!isFunctionalKey && (!ctrl || (ctrl && k == 67) || (ctrl && k == 88)) ){
			var keyword_ = $.trim($this.$el.val());
			var keywords = (keyword_ ? keyword_ : '').split($this.options.separator);
			//获取当前光标所在位置
			var cursorIndex = $this.getCursorIndex();
			if(typeof $this.options.filterRemote == 'object' && $this.options.filterRemote.field){
				$this.options.params[$this.options.filterRemote.field] = keywords[cursorIndex];
			}else{
				$this.options.params[$this.options.textField] = keywords[cursorIndex];
			}
			//两次输入的内容不一致才重新加载
			if($this.lastKeyWord != keyword_){
				$this.reload();
			}
			//重新设置隐藏值
			$this.$el.parent().find('input[type=hidden]').val('');
			$contentDownList.css('float','');
			if(keyword_){//控制隐藏或显示清除按钮
				$('#searchCleanBtn').css('background','url('+$this.clearicoUrl+') no-repeat center');
			}else{
				$('#searchCleanBtn').css('background','none');
			}
			$this.lastKeyWord = keyword_;
		}
		
		//回车键
		if(k == 13){
			if($this.$contentDownList.css('display') != 'none'){
				$this.hideDownList();
				event.keyCode = 9;
			}
			$this.options.onSearchClick.call(this,$this.$el.val(),$this.options.data);
		}
    };
    
    /**
     * 选择文本框keyup事件
     */
    BootstrapSelect.prototype.keyUp = function(event){
    	var $this = this;
    	var functionalKeyArray = $this.functionalKeyArray;
    	var $contentDownList = $this.$contentDownList;
		if($contentDownList.children().length > 0){//有下拉节点，才显示
			$contentDownList.show();
		}
		//输入框keyup事件
		var k = event.keyCode;
		var ctrl = event.ctrlKey;
		var isFunctionalKey = false;//按下的键是否是功能键
		for(var i=0;i<functionalKeyArray.length;i++){
			if(k == functionalKeyArray[i]){
				isFunctionalKey = true;
				break;
			}
		}
		//k键值不是功能键或是ctrl+c、ctrl+x时才触发自动补全功能
		if(!isFunctionalKey && (!ctrl || (ctrl && k == 67) || (ctrl && k == 88)) ){
			var keyword_ = $.trim($this.$el.val());
			if(keyword_ == null || keyword_ == ''){
				//为空时显示所有节点,并移除选择属性
				$contentDownList.find('div').css('display','block')
											.css('background','')
											.css('color','#000000')
											.removeClass($this.options.selItemCls);
				$this.$el.parent().find('input[type=hidden]').val('');
				return;
			}
			var keywords = (keyword_ ? keyword_ : '').split($this.options.separator);
			//获取当前光标所在位置
			var cursorIndex = $this.getCursorIndex();
			var vals = [];//按删除键删除时，用于存放新值
			var removeVal = null;//被移除的值
			//有定义过滤器
			if($this.options.filter && typeof $this.options.filter == 'function'){
				if($contentDownList.parent().find('input[type="hidden"]').val()){
					vals = $contentDownList.parent().find('input[type="hidden"]').val().split($this.options.separator);
					if(keywords.length == vals.length){
						removeVal = vals[cursorIndex];
						vals.splice(cursorIndex,1);
					}
				}
				$contentDownList.find('div').each(function(){
					var filter = $this.options.filter.call(this,keywords[cursorIndex],$(this).data('jsonData'));
					if(filter == true){//有匹配到，显示
						$(this).css('display','block');
					}else{
						//隐藏
						$(this).css('display','none');
					}
					
					if(removeVal && removeVal == $(this).data('jsonData')[$this.options.valueField]){
						$(this).removeClass($this.options.selItemCls);
						$(this).css('background','');
					}
				});
				//重新设置隐藏值
				$this.$el.parent().find('input[type=hidden]').val(vals.join($this.options.separator));
				$contentDownList.css('float','');
				return;
			}
			//获取选择项跟输入框中的值进行匹配
			$contentDownList.find('.'+$this.options.selItemCls).each(function(){
				if($.inArray($(this).html(),keywords) == -1){
					//取消选择
					$(this).removeClass($this.options.selItemCls);
					$(this).css('background','');
					//$(this).css('color','#000000');
				}
				else{
					vals.push($(this).data('jsonData')[$this.options.valueField]);
				}
			});
			//重新设置隐藏值
			$this.$el.parent().find('input[type=hidden]').val(vals.join($this.options.separator));
			//下拉框显示模糊匹配到的项
			$contentDownList.find('div:not(:contains("'+keywords[cursorIndex]+'"))').css('display','none');
			$contentDownList.find('div:contains("'+keywords[cursorIndex]+'")').css('display','block');
			$contentDownList.css('float','');
		}
		//回车键
		if(k == 13){
			if($this.$contentDownList.css('display') != 'none'){
				$this.hideDownList();
				event.keyCode = 9;
			}
		}
    };
    
    /**
     * 选择文本框keydown事件
     */
	BootstrapSelect.prototype.keyDown = function(event){
		var $this = this;
    	var $contentDownList = $this.$contentDownList;
    	//输入框keydown事件
		switch (event.keyCode) {
			case 40://向下键
				if($contentDownList.css('display') == 'none')return;
				var $nextSibling = $contentDownList.find('.'+$this.options.selItemCls);
				if($nextSibling.length <= 0){//没有选中行时，选中第一行
					$nextSibling = $contentDownList.find("div:first");
				}else{
					$nextSibling = $($nextSibling[$nextSibling.length-1]).next();
				}
				if($nextSibling.length > 0){//有下一行时（不是最后一行）
					$this.select($nextSibling,true);
					//div滚动到选中的行,jquery-1.6.1 $nextSiblingTr.offset().top 有bug，数值有问题
					$contentDownList.scrollTop($nextSibling[0].offsetTop - $contentDownList.height() + $nextSibling.height());
					
				}else{
					$this.$el.val($this.lastSelText.join($this.options.separator));//输入框显示用户原始输入的值
				}
				break;
				
			case 38://向上键
				if($contentDownList.css('display') == 'none')return;
				var $prevSibling = $contentDownList.find('.'+$this.options.selItemCls);
				if($prevSibling.length <= 0){//没有选中行时，选中最后一行行
					$prevSibling = $contentDownList.find('div:last');
				}else{
					$prevSibling = $($prevSibling[0]).prev();
				}
				if($prevSibling.length > 0){//有上一行时（不是第一行）			
					$this.select($prevSibling,true);
					//div滚动到选中的行,jquery-1.6.1 $$previousSiblingTr.offset().top 有bug，数值有问题
					$contentDownList.scrollTop($prevSibling[0].offsetTop - $prevSibling.height() + $prevSibling.height()-200);
				}else{
					$this.$el.val($this.lastSelText.join($this.options.separator));//输入框显示用户原始输入的值
				}
				break;
			case 27://ESC键隐藏下拉框
				$this.hideDownList();
			break;
			case 13://回车
				if($this.$contentDownList.css('display') != 'none'){
					$this.hideDownList();
				}
			break;
		}
    };
    
    /**
     * 重新加载
     */
    BootstrapSelect.prototype.reload = function(){
    	var $this = this;
    	if($this.options.url){
    		$this.options.onBeforeLoad.call(this,$this.options.params);
        	$.ajax({
        		url:$this.options.url,
        		type:$this.options.method,
        		data:$this.options.paramsType == 'json' ? JSON.stringify($this.options.params) : $this.options.params,
        		dataType:'json',
        		success: function(jsonLst) {
        			$this.options.onLoadSuccess.call(this,jsonLst);
        			//创建option节点
        			$this.options['data'] = jsonLst;
        			makeContAndShow($this,true);
    			},
    			error: function(xhr, textStatus, errorThrown){
    				$this.options.onLoadError.call(this);
    				throw 'ajax 数据加载失败';
    		    }
        	});
    	}
    	else{
    		//创建option节点
	    	makeContAndShow($this,true);
    	}
    };
    
    /**
     * 隐藏下拉列表
     */
    BootstrapSelect.prototype.hideDownList = function(){
		var $contentDownList = this.$contentDownList;
		if($contentDownList.css('display') != 'none'){
			var items = $contentDownList.find('div');
			items.css('background','');//清除背景色
			//items.css('color','#000000');//恢复字体颜色
			this.$el.parent().find('a').css('box-shadow','');
			this.$el.parent().find('a').css('border-color','#ccc');
			this.$el.css('box-shadow','');
			//this.$el.css('border-color','');
			$contentDownList.hide();
			//$(document).unbind('mousedown');
			$(document).unbind('mousedown',function(event){
				var $target = $(event.target);
				if(!($target.parents().andSelf().is($div)) && !($target.parents().andSelf().is($this.$el.parent()))){
					$this.hideDownList();
				};
			});
		}		
	};
	/**
     * 显示下拉列表
     * @param showMatch 为true时，显示模糊匹配，默认显示所有节点
     */
	BootstrapSelect.prototype.showDownList = function(showMatch){
		var $this = this;
		var $contentDownList = $this.$contentDownList; 
		showMatch = showMatch || false;
		if(!showMatch){
			$contentDownList.find('div').css('display','block');
		}
		else{
			var keyword_ = $.trim($this.$el.val());
			if(keyword_ == null || keyword_ == ''){
				return;
			}	
			var keywords = (keyword_ ? keyword_ : '').split($this.options.separator);
			$contentDownList.find('div:not(:contains("'+keywords[(keywords.length-1)]+'"))').css('display','none');
			$contentDownList.find('div:contains("'+keywords[(keywords.length-1)]+'")').css('display','block');
		}
		//为选中的添加背景色
		$contentDownList.find('.'+$this.options.selItemCls).css({'background':'none repeat scroll 0 0 '+$this.options.selItemColor},{'color':'#FFFFFF'});
		//下拉框显示时设置span获取焦点表框样式
		/*if(!$this.options.downBorder){
			//$this.$el.parent().find('a').css('box-shadow','rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset, rgba(102, 175, 233, 0.6) 0px 0px 8px');
			//$this.$el.parent().find('a').css('border-color','#66afe9');
			//$this.$el.css('box-shadow','rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset, rgba(102, 175, 233, 0.6) 0px 0px 8px');
			//$this.$el.css('border-color','#66afe9');
		}*/

		$contentDownList.css('float','left');
		/**2015-11-02 lizx当下拉框距离底部距离较小时，向上弹窗*/
		var cdTop = $contentDownList.parent().offset().top - $(document).scrollTop();
		var winHeight = $(document.body).height();
		var cdHeight = $contentDownList.height();
		if((cdTop + cdHeight +10) >= winHeight && $this.options.sildUp){
			$contentDownList.css('margin-top',(-cdHeight + 2)+'px');
		}else{
			var height = $this.$el.outerHeight();
			$contentDownList.css('margin-top',(-5)+'px');
		}
		if($contentDownList.children().length > 0){//有下拉节点，才显示
			$contentDownList.show();
		}
		$(document).bind('mousedown',function(event){
			var $target = $(event.target);
			if(!($target.parents().andSelf().is($contentDownList)) && !($target.parents().andSelf().is($this.$el.parent()))){
				$this.hideDownList();
			};
		});
	};

	/**
     * 选择时触发
     * @param selItem 被选择的节点
     * @param keyEvent 是否是按上下键触发的选择
	 */
	BootstrapSelect.prototype.select = function(selItem,keyEvent){
		keyEvent = keyEvent || false;
		var $this = this;
		var options = $this.options;
		var selRecord = [];//被选择项数据
		var vals = [];//值
		var text = [];//文本
		var $selItem = selItem;
		var unSelect = false;//是否反选
		if($this.options.multiple){//多选
			if($selItem.hasClass($this.options.selItemCls)){//选择时如果该项已被选择，则做反选动作
				$selItem.removeClass($this.options.selItemCls);
				$selItem.css('background','');
				unSelect = true;
			}
			else{
				if($this.options.emptyText != null){//有设置空节点
					if($selItem.html() == $this.options.emptyText){//空节点被选择
						$this.$contentDownList.find('div').siblings().removeClass($this.options.selItemCls); 
						$this.$contentDownList.find('div').siblings().css('background','');
						$this.hideDownList();
					}else{
						//其他节点被选择时，设置空节点不被选择
						$this.$contentDownList.find('div:contains("'+$this.options.emptyText+'")').removeClass($this.options.selItemCls);
						$this.$contentDownList.find('div:contains("'+$this.options.emptyText+'")').css('background','');
					}
				}
				//设置被选择节点的样式
				$selItem.addClass($this.options.selItemCls);
				$selItem.css({'background':'none repeat scroll 0 0 '+$this.options.selItemColor},{'color':'#FFFFFF'});
			}
		}
		else{
			$this.$contentDownList.find('div:not(this)').removeClass($this.options.selItemCls); 
			$this.$contentDownList.find('div:not(this)').css('background',''); 
			$selItem.addClass($this.options.selItemCls);
			$selItem.css({'background':'none repeat scroll 0 0 '+$this.options.selItemColor},{'color':'#FFFFFF'});
			if(!keyEvent){
				$this.hideDownList();
			}
		}
		
		this.$contentDownList.find('.'+options.selItemCls).each(function(i,v){
			$(this).css('color','#000000');//被选择的项字体设置成黑色
			selRecord.push($(this).data('jsonData'));
			vals.push($(this).data('jsonData')[options.valueField]);
			text.push($(this).data('jsonData')[options.textField]);
		});
		$this.$el.parent().find('input[type="hidden"]').val(vals.join(options.separator));
		$this.$el.parent().find('input[type="text"]').val(text.join(options.separator));
		$this.lastSelText = text;
		
		if(unSelect){
			options.unSelect.call(this,$selItem.data('jsonData')[$this.options.valueField],$selItem.data('jsonData'));
		}
		else{
			options.onSelect.call(this,$selItem.data('jsonData')[$this.options.valueField],$selItem.data('jsonData'));
		}
		//$this.$el.css('border-color','#ccc');
	};
	/**
	 * 清空下拉节点
	 */
	BootstrapSelect.prototype.clear = function(){
		var $this = this;
		var itemsHtml = '';
		if($this.options.emptyText != undefined && $this.options.emptyText != null){
			var nullItem = {};
	    	nullItem[$this.options.textField] = $this.options.emptyText;
	    	nullItem[$this.options.valueField] = '';
			itemsHtml = '<div style="padding-left:10px;width:100%;height:20px;">' + $this.options.emptyText + '</div>'; 
			//每行tr绑定数据，返回给回调函数
			$this.$contentDownList.find('div').each(function(index,val){
				$(this).data('jsonData',nullItem);
			});
		}
		$this.$el.parent().find('input').val('');
		$this.$contentDownList.html(itemsHtml);
	};
	
	/**
	 * 根据值获取文本
	 */
	BootstrapSelect.prototype.getTextForVal = function(value){
		var $this = this;
		var retVal = '';
		$.each($this.options.data,function(i,data){
			if(data[$this.options.textField] == value){
				retVal = data[$this.options.valueField];
				return false;
			}
		});
		return retVal;
	};
	/**
     * 获取光标在字符串中的位置
     */
	BootstrapSelect.prototype.getCursorIndex = function(){
		var $this = this;
		var index = 0;
    	var textObj = $this.$el.get(0);
    	if(!textObj.value){
    		return index;
    	}
    	
        if(document.all && textObj.createTextRange && textObj.caretPos){
            var caretPos = textObj.caretPos;
            index = caretPos.text.split($this.options.separator).length -1;
        }
        else if(textObj.setSelectionRange){
            var rangeStart=textObj.selectionStart;
            //var rangeEnd=textObj.selectionEnd;
            var tempStr = textObj.value.substring(0,rangeStart);
            index = tempStr.split($this.options.separator).length -1;
        }
        return index;
	};
	
	/**
	 * 设置选中
	 * @param target 下拉框列表
	 * @param options 配置项
	 * @param value 值
	 */
	function setValues(bootstrapSelect,value){
		var $this = bootstrapSelect;
		var options = $this.options;
		var $input = $this.$el;
		//如果没有传入value，则根据value属性来赋值
		var values = value == undefined || value == null ?'':value;
		var inputText = [];//存放被选中的文本
		$input.parent().find('input[type="hidden"]').val(values);//隐藏域设置值
		$this.$contentDownList.find('div').each(function(index){
			var item = $(this);//节点
			var itemVal = item.data('jsonData')[options.valueField];//节点值
			var itemText = item.data('jsonData')[options.textField];//节点文本
			item.removeClass(options.selItemCls);//清除原来选择
			//设置选中
			$.each((values+'').split(options.separator),function(i,val){
    			if(val == itemVal){
    				item.addClass(options.selItemCls);
    				inputText.push(itemText);
    				return false;//break
    			}
    		});
		});
		$this.lastSelText = inputText;
		$input.val(inputText.join(options.separator));//文本框设置文本
	}
    
	
	/**
	 * 创建下拉框选项
	 * @param inputDownList 
	 * @param isReload
	 */
    function makeContAndShow(inputDownList,isReload){
    	var nullItem = {};
    	nullItem[inputDownList.options.textField] = inputDownList.options.emptyText;
    	nullItem[inputDownList.options.valueField] = '';
    	var data_ = inputDownList.options.data;
    	if(inputDownList.options.emptyText != undefined && inputDownList.options.emptyText != null){
    		data_.splice(0,0,nullItem); 
    	}
    	var options = inputDownList.options;
    	var $input = inputDownList.$el;
    	
    	var $this = inputDownList.$contentDownList;
    	if(data_ == null || typeof data_ != 'object'){
    		data_ = [];
    	}
    	//设置下拉框位置
		var itemsHtml = '';
		$.each(data_,function(i,obj){
			options.formatter.call(this,obj);
			itemsHtml += '<div style="padding-left:10px;width:100%;height:25px;line-height:25px;">' + obj[options.textField] + '</div>';
		});
		$this.html(itemsHtml);
    	
		//每行tr绑定数据，返回给回调函数
		$this.find('div').each(function(index,val){
			$(this).data('jsonData',data_[index]);
		});

		if(isReload){
			if(!options.filterRemote){
				//重新加载时设置空选项被选择
				setValues(inputDownList,'');
			}else{
				if($this.children().length > 0){//有下拉节点，才显示
					$this.show();
				}
			}
		}
		else{
			//第一次加载时根据value属性设置选中
			setValues(inputDownList,$input.attr('value'));
		}
	}	
    
    function getBrowserMsg(){
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
    }
})(jQuery);