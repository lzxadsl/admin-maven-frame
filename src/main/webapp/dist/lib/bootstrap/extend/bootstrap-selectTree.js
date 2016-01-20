/**
 * bootstrap 下拉树形菜单插件，居于jquery.ztree-3.5
 * @author lizx <851697971@qq.com>
 * @version 1.0
 * @date 2015-10-20
 */
(function($){
	 'use strict';//开启严格模式
	 /**
	  * 调用入口:$('#order_status1').bootstrapSelectTree({});
	  * options 为对象时是属性设置参数，为字符串时，是调用具体方法，
	  * agrs 为调用方法时的参数
	  */
	 $.fn.bootstrapSelectTree = function(option,agrs){
		 var value = null;
		 this.each(function () {
             var $this = $(this),
             data = $this.data('bootstrap.selectTree'),
             options = $.extend({},$.fn.bootstrapSelectTree.defaults,$this.data(),
                    typeof option === 'object' && option);
             
             if (typeof option === 'string') {
            	 if (!data) {
                     return;
                 }
            	 value = data[option](agrs);
            	 if (option === 'destroy') {
                     $this.removeData('bootstrap.selectTree');
                 }
             }
             if (!data) {
            	 //为元素添加data属性
                 $this.data('bootstrap.selectTree', (data = new BootstrapSelectTree($this,options)));
             }
        });
		 
		return typeof value === 'undefined' || value == null ? this : value;
	 };
	 
	 /**
	 * 定义默认配置信息
	 * data：格式{data:[{title:null,result:{}},{title:null,result:{}}]}
	 * url和data参数只有一个生效，url优先
	 * 如果有option选项，则它的优先级低于data
	 */
	$.fn.bootstrapSelectTree.defaults = {
			url    : null, //请求路径
			params : {},   //请求参数
			paramsType : '',//参数默认是以表单形势传递，为json时是以json格式传递到后台
			data   : [],   //数据[{key:value},{key:value}]
	        method : 'get',//请求方法
	        editable   : true,//是否可编辑
	        separator  : ',',//多选时返回值的分割符
	        multiple : false,//多选
	        downBorder : false,//下拉按钮是否带边框
	        noCheckParent:true,//默认多选时父节点不让选择
	        cls:'',//自定义样式,多个样式用逗号隔开 class1,class2
	        setting : {},//属性菜单基本配置信息
	        formatter:function(rec){},//格式化节点	
	        onSelect : function(val,rec){},
	        unSelect : function(val,rec){},//反选
	        onBeforeLoad: function(param){},//param 请求参数
			onLoadSuccess: function(data){},//data加载成功后返回的数据
			onLoadError: function(){},
			onExpand:function(event, treeId, treeNode){},//展开事件
			onCollapse:function(event, treeId, treeNode){},//折叠事件
			filter : false//选项过滤
	};
	
	var BootstrapSelectTree = function (el,options) {
        this.options = options;//配置项
        this.$el = el;//文本框
        this.$el_ = this.$el.clone();
        this.timeoutId_ = 0;
        //键盘上功能键键值数组
        this.functionalKeyArray = [9,20,13,16,17,18,91,92,93,45,36,33,34,35,37,39,112,113,114,115,116,117,118,119,120,121,122,123,144,19,145,40,38,27];
        this.$contentDownList = null;//下拉框
        this.options.downListIdPrefix = '_bootstrap_combox_zTree_il_';//下拉框id前缀
        this.lastSelText = [];//保存最后一次选择的内容
        this.options.downListCls = 'zTree-downlist';//下拉框样式，目前此样式为空，预留
        this.options.selItemCls = 'zTree-item-selected',//被选择节点样式,目前犹豫没有引用样式文件，所以此样式只是个空的
        this.options.selItemColor = '#ffe48d';//被选中节点背景色，暂时使用这个
        this.selText = [];//保存被选中的文本
        this.contentUlId = null;//下拉树id
        this.zTreeObj = null;
        this.init();
    };
    
    //初始化控件
    BootstrapSelectTree.prototype.init = function(){
    	this.createSelect();//创建文本选择框
    	this.initServer();//数据加载，并创建下拉节点
    	this.initEvent();//添加事件
    };
    //向服务器请求数据
    BootstrapSelectTree.prototype.initServer = function(){
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
    /**
     * 创建选择框
	 */
    BootstrapSelectTree.prototype.createSelect = function(){
    	var $this = this;
    	var $input = $this.$el;
    	var options = $this.options;
    	//添加样式
    	$input.addClass('form-control');
    	$input.addClass(options.cls.replace(/,/g,' '));
    	//获取元素宽、高
    	var width = $input.outerWidth() - 2;
    	var height = $input.outerHeight();
    	var browserV = getBrowserMsg();
    	if(browserV.ie != undefined && parseFloat(getBrowserMsg().ie) > 8){
    		height = $input.height();
    	}
    	var name = $input.attr('name')?$input.attr('name'):'';//name属性
    	$input.removeAttr('name');//删除name属性，最终把name属性移到隐藏域上
    	$input.wrap('<span style="height:'+height+'px;display:block;overflow:hidden;width:'+width+'px;"></span>');
    	$input.css('padding-left','17px');
    	//$input.css('display','inline-block');//20151012 去掉该属性
    	$input.css('float','left');//20151012 增加该属性
    	$input.attr('autocomplete','off');
    	$input.prop('type','text');
    	
    	if(!$input.attr('placeholder')){
    		$input.attr('placeholder','请选择');
    	}
    	
    	if(options.emptyText != undefined && options.emptyText != null){
    		$input.val(options.emptyText);
    	}
    	var astyle = 'float:right;position:relative;display:inline-block;'
    				+'width:20px;height:'+(height)+'px;border-radius:4px;border:solid 1px #ccc;'
    				+'border-left:0;border-bottom-left-radius: 0; border-top-left-radius: 0;';
    	
    	$input.css('border-bottom-right-radius',0);
    	$input.css('border-top-right-radius',0);
		$input.css('width',(width-20)+'px');
    	//20151012 修改a高度-1，改变属性position 为 relative，增加float
    	if($this.options.downBorder){
    		astyle += 'background-color:#eee;';
    	}
    	else{
        	$input.css('border-right',0);
    	}
    	$('<a href="javascript:;" style="'+astyle+'">'
			+'<span style="margin: 14px 4.5px 13px;display: inline-block;'
				+'width: 0px;height: 0px;border-top: 5px solid #555;'
				+'border-right: 5px solid transparent;border-left: 5px solid transparent">'
			+'</span>'
		+'</a>').insertAfter($input);
    	$('<input type="hidden" name="'+name+'" value="'+$input.val()+'">').insertAfter($input);
    	
    };

    /**
     * 事件初始化
     */
    BootstrapSelectTree.prototype.initEvent = function(){
    	var $this = this;
    	var $input = $this.$el;
    	
    	if($this.options.disabled){
    		$input.attr('readonly',true);
    		return;
    	}
    	//a按钮点击事件
    	$input.parent().find('a').unbind('click').click(function(){
    		if(!$this.$contentDownList.is(":visible")){
    			$this.showDownList();
    		}
    		else{
    			$this.hideDownList();
    		}
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
    		$this.showDownList();
		});
    	//文本框失去焦点
    	$input.unbind('blur').blur(function(){
		});
    	
    	//添加按键事件
    	$input.unbind('keyup').keyup(function(event){
    		//$this.keyUp(event);
    	});
    	//添加按键事件
    	$input.unbind('keydown').keydown(function(event){
    		//$this.keyDown(event);
    	});
    };
    /**
     * 创建下拉框
	 */
    BootstrapSelectTree.prototype.createDownList = function(data){
    	var $this = this;
    	if(data){
    		$this.options['data'] = data;
    	}
    	var width = $this.$el.outerWidth();
    	var height = $this.$el.outerHeight();
    	var style = 'display: none;'
    		+'background-color: #FFFFFF;'
    		+'border: 1px solid '+($this.options.downBorder?'#ccc;':'#66afe9;')
    		+'position: absolute;'//relative absolute
    		+'z-index: 10000;'
    		+'float:left;'
    		//+'top:0;'
    		+'width:'+(width+20)+'px;'
    		+'margin-top:'+height+'px;'//20151012 增加该属性
    		+'max-height: 220px;'
    		+'overflow-x: hidden;'
    		+'overflow-y: auto;';
    	var _id = null;
    	for(var i = 0; i < 999; i++){
    		_id = $this.options.downListIdPrefix + i;
    		if($('#'+_id) && $('#'+_id).length < 1 ){
    			$this.contentDownId = _id;
    			break;
    		}
    	}
    	_id = $this.$el.attr('id')?$this.$el.attr('id')+'_zTree':_id;
    	$this.contentUlId = _id;
    	$('<div style="'+style+'" ><ul id="'+_id+'" class="ztree"></ul></div>').appendTo($this.$el.parent());
    	var $div = $this.$el.parent().find('div');
    	
		//鼠标悬停时选中当前行
    	$div.delegate('a', 'mouseover', function() {//鼠标经过，设置背景色
			if(!$(this).hasClass($this.options.selItemCls)){//已被选择的除外
				$(this).css('background','none repeat scroll 0 0 #0080FF');
				$(this).css('color','#FFFFFF');
			}
			
		}).delegate('a', 'mouseout', function() {//鼠标移出，清除背景色
			if(!$(this).hasClass($this.options.selItemCls)){//已被选择的除外
				$(this).css('background','');
				$(this).css('color','#000000');
			}
		}).delegate('a', 'click', function(){//单击选择
			//$this.select($(this));
		});
		$this.$contentDownList = $div;
		$this.initZTree();
    };
    /**
     * 初始化下拉树
     */
    BootstrapSelectTree.prototype.initZTree = function(){
    	var $this = this;
    	var zNodes = $this.options.data;
    	var setting = {
			check: {
				enable: $this.options.multiple
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
			}
		};
    	var callback = {
			onExpand:function(event, treeId, treeNode){
				if($this.$contentDownList.attr('style').indexOf('float') != -1){
					$this.$contentDownList.css('float','');
				}
				else{
					$this.$contentDownList.css('float','left');
				}
				$this.options.onExpand.call(event, treeId, treeNode);
			},
			onCollapse:function(event, treeId, treeNode){
				if($this.$contentDownList.attr('style').indexOf('float') != -1){
					$this.$contentDownList.css('float','');
				}
				else{
					$this.$contentDownList.css('float','left');
				}
				$this.options.onCollapse.call(event, treeId, treeNode);
			}
		};
    	if($this.options.multiple){
    		callback['onCheck'] = function (e,treeId,treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId),
				nodes = zTree.getCheckedNodes(true),
				vals = [],
				ids = [];
				nodes.sort(function compare(a,b){return a.id-b.id;});
				for (var i=0, l = nodes.length; i<l; i++) {
					vals.push(nodes[i].name);
					ids.push(nodes[i].id);
				}
				$this.$el.parent().find('input[type="text"]').val(vals.join($this.options.separator));
				$this.$el.parent().find('input[type="hidden"]').val(ids.join($this.options.separator));
				//$this.hideDownList();
				$this.options.onSelect.call(this,ids.join(','),treeNode);
			};
    	}
    	else{
    		callback['onClick'] = function (e,treeId,treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId),
				nodes = zTree.getSelectedNodes(),
				vals = [],
				ids = [];
				nodes.sort(function compare(a,b){return a.id-b.id;});
				for (var i=0, l = nodes.length; i<l; i++) {
					vals.push(nodes[i].name);
					ids.push(nodes[i].id);
				}
				$this.$el.parent().find('input[type="text"]').val(vals.join($this.options.separator));
				$this.$el.parent().find('input[type="hidden"]').val(ids.join($this.options.separator));
				$this.hideDownList();
				$this.options.onSelect.call(this,ids.join(','),treeNode);
			};
    	}
    	setting['callback'] = callback;
    	$this.setting = $.extend({},setting,$this.options.setting);
    	$.fn.zTree.init($('#'+$this.contentUlId),$this.setting,zNodes);
    	$this.zTreeObj = $.fn.zTree.getZTreeObj($this.contentUlId);
		$this.setSelect();
    };
    
    BootstrapSelectTree.prototype.getZTreeObj = function(){
    	return $this.zTreeObj;
    };
    
    /**
     * 设置选中
     */
    BootstrapSelectTree.prototype.setSelect = function(_nodes){
    	var $this = this;
    	var value = $this.$el.parent().find('input[type="hidden"]').val();
		var nodes = _nodes || $this.zTreeObj.getNodes();
		var values = value.replace(/\[/g,'').replace(/\]/g,'').split($this.options.separator);
		$.each(nodes,function(i,node){
			if(node.isParent){
				//node.nocheck = true;
				$this.zTreeObj.expandNode(node,true,true,true);
			}
			if($.inArray(String(node.id),values) != -1){
				$this.selText.push(node.name);
    			if($this.options.multiple){//多选
    				node.checked = $this.options.noCheckParent;
    			}else{
    				$('#'+node.tId+'_a').addClass('curSelectedNode');
    				$this.zTreeObj.selectNode(node);
    			}
    			$this.$el.parent().find('input[type="text"]').val($this.selText.join($this.options.separator));
    		}
    		else{
    			if($this.options.multiple){//多选
    				node.checked = false;
    			}else{
    				$this.zTreeObj.cancelSelectedNode(node);//清除原来选中
    			}
    		}
    		
    		if(node.children && node.children.length > 0){
    			$this.setSelect(node.children);
    		}
    		$this.zTreeObj.updateNode(node);
    	});
    };
    
    /**
     * 设置选中，主要提供给外部调用
     */
    BootstrapSelectTree.prototype.select = function(value){
    	this.$el.parent().find('input[type="hidden"]').val(value);
    	this.selText = [];
    	if(this.$contentDownList){
    		this.setSelect();
    	}
    	else{
    		var int = setInterval(function(){
				if(this.$contentDownList){
					clearInterval(int);
					this.setSelect();
				}
			},1000);
    	}
    };
    
    /**
     * 获取选中值
     */
    BootstrapSelectTree.prototype.getValue = function(){
    	return this.$el.parent().find('input[type="hidden"]').val();
    };
    
    /**
     * 选择文本框keyup事件
     */
    BootstrapSelectTree.prototype.keyUp = function(event){
    	var $this = this;
    	var functionalKeyArray = $this.functionalKeyArray;
    	var $contentDownList = $this.$contentDownList;
		$contentDownList.show();
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
				//为空时显示所以节点,并移除选择属性
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
					}
					else{
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
	BootstrapSelectTree.prototype.keyDown = function(){
		var $this = this;
    	var $contentDownList = $this.$contentDownList;
    	//输入框keydown事件
		switch (event.keyCode){
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
					$contentDownList.scrollTop($nextSibling[0].offsetTop - $contentDownList.height() + $nextSibling.height() );
					
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
					$contentDownList.scrollTop($prevSibling[0].offsetTop - $prevSibling.height() + $prevSibling.height());
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
    BootstrapSelectTree.prototype.reload = function(){
    	var $this = this;
    	$this.$el.parent().find('input[type="text"]').val('');
		$this.$el.parent().find('input[type="hidden"]').val('');
    	$this.getZTreeObj().destroy();
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
        			$.fn.zTree.init($('#'+$this.contentUlId),$this.setting,$this.options['data']);
    			},
    			error: function(xhr, textStatus, errorThrown){
    				$this.options.onLoadError.call(this);
    				throw 'ajax 数据加载失败';
    		    }
        	});
    	}
    	else{
    		$.fn.zTree.init($('#'+$this.contentUlId),$this.setting,$this.options['data']);
    	}
    };
    
    /**
     * 隐藏下拉列表
     */
    BootstrapSelectTree.prototype.hideDownList = function(){
		var $contentDownList = this.$contentDownList;
		if($contentDownList.css('display') != 'none'){
			var items = $contentDownList.find('a');
			items.css('background','');//清除背景色
			//items.css('color','#000000');//恢复字体颜色
			this.$el.parent().find('a').css('box-shadow','');
			this.$el.parent().find('a').css('border-color','#ccc');
			this.$el.css('box-shadow','');
			this.$el.css('border-color','');
			$contentDownList.hide();
			$(document).unbind('mousedown',function(event){
				var $target = $(event.target);
				if(!($target.parents().andSelf().is($contentDownList)) && !($target.parents().andSelf().is($this.$el.parent()))){
					$this.hideDownList();
				};
			});
		}		
	};
	/**
     * 显示下拉列表
     * @param showMatch 为true时，显示模糊匹配，默认显示所以节点
     */
	BootstrapSelectTree.prototype.showDownList = function(showMatch){
		var $this = this;
		var $contentDownList = $this.$contentDownList; 
		//为选中的添加背景色
		$contentDownList.find('.'+$this.options.selItemCls).css({'background':'none repeat scroll 0 0 '+$this.options.selItemColor});
		//下拉框显示时设置span获取焦点表框样式
		if(!$this.options.downBorder){
			$this.$el.parent().find('a').css('box-shadow','rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset, rgba(102, 175, 233, 0.6) 0px 0px 8px');
			$this.$el.parent().find('a').css('border-color','#66afe9');
			$this.$el.css('box-shadow','rgba(0, 0, 0, 0.0745098) 0px 1px 1px inset, rgba(102, 175, 233, 0.6) 0px 0px 8px');
			$this.$el.css('border-color','#66afe9');
		}
		$contentDownList.show();	
		$(document).bind('mousedown',function(event){
			var $target = $(event.target);
			if(!($target.parents().andSelf().is($contentDownList)) && !($target.parents().andSelf().is($this.$el.parent()))){
				$this.hideDownList();
			};
		});
	};

	/**
	 * 清空下拉节点
	 */
	BootstrapSelectTree.prototype.clear = function(){
		
	};
	
	/**
     * 获取光标在字符串中的位置
     */
	BootstrapSelectTree.prototype.getCursorIndex = function(){
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