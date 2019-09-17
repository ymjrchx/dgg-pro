/*
 * Author：xtt
 * 根据ztree封装的下拉搜索
 * 2017.10.16
 */
//修改树形样式
var closeArr='';

function bodyClose(e) {
	var className=closeArr.substr(1,closeArr.length);
	var case1=$(e.target).parents(".tree-select").length,
		case2=$(e.target).parents(".tree-select-warp").length,
		case3="#"+$(e.target).attr("id");
	// 判断当前点击的父级是否包含以下class，包含则不关闭侧滑层
//	console.log(closeArr)
	if(case1==0&&case2==0&&case3!=closeArr) {
		$(closeArr).parent().removeClass('down');
		if(className!=''&&className!=null){
			$('.'+className).hide();
		}
	}else{
		return false
	}
}
function fontCssTree(treeId, treeNode) {
	if(treeNode.level==0){
		$("#"+treeNode.tId+"_span").css("color","#000");
	}
	return treeNode.level == 0 ? {color:"#999","font-size":"14px"} : {color:"#666","font-size":"12px"};
};
;(function(){
	var treeSelect = function(ele,opt){
		this.$element=ele,
		defaults = {
			check:false,
			url:"../static/json/tree.json",
			contentType: "application/json",
			idKey: "id",
			pIdKey: "pId",
			rootPId: 0,
			form:false,
			ajaxDataFilter:'',//格式化数据
			idName:null,
			code:'id',//存下拉树code或id
			parentIsCheck:true,
			search:false,
			changeFun:'',
			onAsyncSuccess:'',
            beforeExpand:'',
            autoParam:{},
            chkboxType: { "Y" : "ps", "N" : "ps" }
		}
		this.options=$.extend(defaults,opt);
	};
	treeSelect.prototype = {
		start:function() {
			var treeOut='tree-select';
			var zTreeObj;
			var ele = $(this.$element).selector;
			var isCheck = this.options.check;
			var form = this.options.form;
			var idName = this.options.idName;
			var treeId=$(this.$element).attr("id");
			var code=this.options.code;
			var parentIsCheck=this.options.parentIsCheck;
			var search=this.options.search;
			var changeFun=this.options.changeFun;
			var beforeExpand=this.options.beforeExpand;
			var onAsyncSuccess=this.options.onAsyncSuccess;
			function beforeClick(treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var check = (treeNode && !treeNode.isParent);
				if (!check&&!parentIsCheck){
                    zTree.expandNode(treeNode);
                    return check;
				}
			}
			/*ztree 配置参数 start*/
			var setting = {
				async: {
					enable: true,
					type: "get",
					url:this.options.url,
					otherParam:this.options.otherParam,
					contentType:this.options.contentType,
                    autoParam:this.options.autoParam,
					dataFilter: this.options.ajaxDataFilter
				},
				check: {
					enable: this.options.check,
                    chkboxType: this.options.chkboxType
				},
				view: {
					showIcon: false,
					showLine: false,
					fontCss : fontCssTree
				},
				data: {
					simpleData: {
						enable: true,
						idKey: this.options.idKey,
						pIdKey: this.options.pIdKey,
						rootPId: this.options.rootPId
					},
					key:{
						url:null
					}
				},
				callback: {
					onClick: treeOpt,
					beforeClick: beforeClick,
                    beforeExpand: this.options.beforeExpand,
					//加载完成，回选ztree，打开ztree
					onAsyncSuccess:function(event, treeId, treeNode, msg) {
                        var treeCheck = $.fn.zTree.getZTreeObj(treeId);
                        var checkId=$(ele).attr('data-id');
                        var inputStr=''
                        if(onAsyncSuccess!=''&&onAsyncSuccess!=null){
	                        onAsyncSuccess(event, treeId, treeNode, msg);
						}
                        if(checkId!=undefined&&checkId!="") {
                            checkId = checkId.split(",");
                            for(var i=0;i<checkId.length;i++) {
                                var node1 = treeCheck.getNodeByParam(code, checkId[i], null);
                                if(node1!=null&&node1!='') {
                                    inputStr+=','+node1.name;
                                    treeCheck.checkNode(node1, true);
                                    treeCheck.selectNode(node1,true);//指定选中ID的节点
                                    treeCheck.expandNode(node1, true, false);//指定选中ID节点展开
								}
                            }
                            inputStr=inputStr.substr(1,inputStr.length)
                            $(ele).val(inputStr)
                        }

						if(msg=='[]'||msg==null) {
							$("#"+treeId+"_warp").text('无数据');
						}
						// console.log(msg)
					}
				}
			};
			/*ztree 配置参数 end*/
			/*下拉框 html 标签拼接 start*/
			var treeHtm='<div class="'+treeOut+' '+treeId+'"><div id="'+treeId+'_warp" class="ztree my-tree"></div>';
			//多选---添加按钮---判断
			if(isCheck) {
				treeHtm+='<div class="tree-btn">'+
						 '<a href="javascript:void(0)" class="layui-btn layui-btn-normal layui-btn-mini" id="get_'+treeId+'">确定</a>'+
						 '<a href="javascript:void(0)" class="layui-btn layui-btn-primary layui-btn-mini" id="reset_'+treeId+'">关闭</a>'+
						 '</div>'
			}
			treeHtm+='</div>';
			if(form) {
				var name = $(ele).attr("name");
				var dataId = $(ele).attr("data-id");
				if(dataId==undefined) {
					dataId='';
				}
				var inputId = '<input type="hidden" name="'+idName+'" value='+dataId+'>';
				$(this.$element).next('input[name="'+idName+'"]').remove();
				$(this.$element).after(inputId)
			}
			/*下拉框 html 标签拼接 end*/
			$('body').append(treeHtm);
			/*ztree 初始化 start*/
			$(document).ready(function(){
				$("body").attr("onclick","bodyClose(event);");
//				closeArr.push(ele)
			  zTreeObj = $.fn.zTree.init($("#"+treeId+"_warp"), setting);
			  var zTree1 =$.fn.zTree.getZTreeObj(treeId+"_warp");
				//console.log(zTree1)
				var nodes = zTree1.getNodes();
	  			zTree1.checkNode(nodes[0], false, false); 
	  			return bodyClose
			});
			/*ztree 初始化 end*/
			/*点击 展开 下拉 start*/
			$(this.$element).click(function(){
				var _this = $(this);
				closeArr=ele;
				/*下拉定位css start*/
				var treeLay = {
					width:_this.outerWidth()+'px',
					top:_this.outerHeight()+_this.offset().top+'px',
					left:_this.offset().left+'px',
					/*display:"none"*/
				}
				/*下拉定位css end*/
				$('.'+treeOut).css(treeLay);
				treeDown(_this,treeId);
			});
			/*点击 展开 下拉 end*/
			//多选，点击---确定---按钮
			$('#get_'+treeId).click(function(){
				var treeCheck = $.fn.zTree.getZTreeObj(treeId+"_warp");
				var nodes = treeCheck.getCheckedNodes(true);
				var valStr = "";
				var dataId = [];
				$(ele).data("param",nodes);
				$.each(nodes, function(i,vals) {
					valStr+=vals.name+'；';
					dataId.push(vals[code])
				});
				$(ele).attr("data-id",dataId)
				$(ele).val(valStr);
				if(form){
					$(ele).next('[type="hidden"]').val(dataId);
				}
				treeUp($(ele),treeOut);
			});
			//多选，点击---关闭--- 按钮
			$('#reset_'+treeId).click(function(){
				treeUp($(ele),treeOut);
			})
			$(this.$element).focus(function(){
				var _this=$(this);
				document.onkeydown = function(event){
//					keyevent(event,_this,treeOut)
				};
			});
			/*点击节点方法&&单选时 start*/
			function treeOpt (event, treeId, treeNode) {
				if(!isCheck){
					$(ele).data("param",treeNode);
					$(ele).val(treeNode.name);
					$(ele).attr("data-id",treeNode[code]);
					if(form){
						$(ele).next('[type="hidden"]').val(treeNode[code]);
					}
					if(changeFun!=''&&changeFun!=null){
                        changeFun(event, treeId, treeNode);
					}
					treeUp($(ele),treeOut)
				}else {
					zTreeObj.checkNode(treeNode, !treeNode.checked, true);
				}
			}
			/*点击节点方法&&单选时 end*/
			/*下拉展开调用方法 start*/
			function treeDown(obj,ele) {
				$('.tree-select').hide();
				var isOpen=obj.parent().hasClass('down');
				if(isOpen&&!search) {
					obj.parent().removeClass('down');
					$('.'+ele).hide();
				}else {
					obj.parent().addClass('down');
					$('.'+ele).show();
					
				}
			}
			/*下拉展开调用方法 end*/
			/*下拉关闭调用方法 start*/
			function treeUp(obj,ele) {
				obj.parent().removeClass('down');
				$('.'+ele).hide();
			}
			/*下拉关闭调用方法 end*/
			/* 搜索功能 start*/
			if(search) {
				document.getElementById(treeId).addEventListener("input", searchFun, false);
			}
			function searchFun(){  
			    var treeObj = $.fn.zTree.getZTreeObj(treeId+"_warp");  
			    var keywords=$(ele).val();  
			    var nodes = treeObj.getNodesByParamFuzzy("name", keywords, null);
			    console.log(nodes)
		    	$("#"+treeId+"_warp").show();  
		    	$("#"+treeId+"_warp").parent().children('p').remove();
			    if (nodes.length>0) {
			        treeObj.selectNode(nodes[0]);  
			  	    $(ele).focus();
			    }  else {
			    	$("#"+treeId+"_warp").parent().append('<p style="padding:5px; text-align:center">暂无数据</p>')
			    	$("#"+treeId+"_warp").hide()
			    }
			}
			/* 搜索功能 end*/
		},
		//清空val
		resetVal:function(){
			$(this.$element).val('');
			$(this.$element).next().val('');
			$(this.$element).attr("data-id",null);
			$(this.$element).data("param","");
		},
		getJson:function() {
			var treeId=$(this.$element).attr("id");
			var treeCheck = $.fn.zTree.getZTreeObj(treeId+"_warp");
			var data = $(this.$element).attr("data-id");
			var dataArr=new Array();
			if(data!=undefined&&data!='') {
				data = data.split(",");
				for(var i=0;i<data.length;i++) {
					var node1 = treeCheck.getNodeByParam("id", data[i], null);
					dataArr.push(node1);
				}
			}
			return dataArr;
		},
		getAllJson:function(){
			var treeId=$(this.$element).attr("id");
			var treeObj = $.fn.zTree.getZTreeObj(treeId+"_warp");
			var nodes = treeObj.getNodes();
		}
	};
	$.fn.selectTree = function(options) {
		var treeSelects = new treeSelect(this,options);
		if(options=='reset') {
			return treeSelects.resetVal();
		}else if(options=='getJson') {
			return treeSelects.getJson();
		}else if(options=='getAllJson') {
			return treeSelects.getAllJson();
		}
		else {
			return treeSelects.start();
		}
	}
})();
//function keyevent(event,obj,ele){
//	if(event.keyCode==13) {
//		treeDown(obj,ele)
//	}
//} 
