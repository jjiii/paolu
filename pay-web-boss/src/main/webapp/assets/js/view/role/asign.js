var listPage = parent;	//父级页面
var indexPage = parent.parent;  //顶级页面index
/**
 * 分配菜单
 */
function asignMenu(){
	var params = {'roleId':$('#roleId').val()};
	var menuIds = "";
	var treeObj =  $.fn.zTree.getZTreeObj('treemenu');
	var nodes = treeObj.getCheckedNodes(true);
	for(var n in nodes){
		menuIds += nodes[n].id+',';
	}
	if(menuIds){
		// 分配菜单
		menuIds = menuIds.substr(0,menuIds.length-1);
		params.menuIds = menuIds;
		// 分配菜单
		$.ajax({
			url:rootPath+'/role/assignMenu',
			type:'POST',
			data:params,
			dataType:'JSON',
			success:function(data){
				if(data.errcode==0){
					indexPage.alertModal(data.errmsg,1);
				}else{
					indexPage.msgModal(data.errmsg,2);
				}
			}
		});
	}
}

/**
 * 分配权限
 */
function asignPermission(){
	var params = $('#data-form').serialize();
	if(params){
		// 分配权限
		$.ajax({
			url:rootPath+'/role/assignPermis',
			type:'POST',
			data:params,
			dataType:'JSON',
			success:function(data){
				if(data.errcode==0){
					indexPage.alertModal(data.errmsg,1,function(){
						location.reload();
					});
				}else{
					indexPage.alertModal(data.errmsg,2);
				}
			}
		});
	}
}

/**
 * 菜单树
 */
function initMenuTree(){
	var setting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true,
				pIdKey: "parentId"
			}
		}
	};
		
	var params = {'roleId':$('#roleId').val()};
	// 载入菜单数据
	$.ajax({
		url:rootPath+'/menu/listdata',
		data:params,
		type:'GET',
		dataType:'JSON',
		success:function(data){
			var menus = data.menus;
			formatTree(menus);
			$.fn.zTree.init($("#treemenu"), setting, menus);
			var treeObj = $.fn.zTree.getZTreeObj("treemenu");
			var nodes = treeObj.getNodes();
			for (var i=0, l=nodes.length; i < l; i++) {
				var node = nodes[i];
				if(node.isParent){
					for(var n in node.children){
						treeObj.checkNode(node.children[n], node.children[n].ischecked, true);
					}
				}else{
					treeObj.checkNode(node, node.ischecked, true);
				}
			}
		}
	});
}

/**
 * 权限菜单树
 */
function initPermisTree(){
	var setting = {
		check : {
			enable : false
		},
		data : {
			simpleData : {
				enable : true,
				pIdKey: "parentId"
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				var permhtm = "";
				if(treeNode.parentId){
					/*var permissions = treeNode.permissions;
					var permhtm = '';
					for(var i in permissions){
						var permission = permissions[i];
						permhtm += '<li>'+
									'<div class="checkbox"><label>'+
									'<input type="hidden" name="permisIds" value="'+permission.id+'"/>'+
									'<input type="checkbox" name="permissionId" value="'+permission.id+'"'+(permission.ischecked && 'checked="checked"')+'/>'+permission.permissionName+
									'</label></div></li>'
					}*/
					permhtm = createNodeHtml(treeNode);
					permhtm ? $('#permhtm').html(permhtm):$('#permhtm').html('该菜单暂无相应权限');
				}else if( treeNode.getIndex()!= 0){
					permhtm = "";
					var childNodes = treeNode.children;
					for(var i in treeNode.children){
						var childTreeNode = childNodes[i];
						permhtm += createNodeHtml(childTreeNode);
					}
					permhtm ? $('#permhtm').html(permhtm):$('#permhtm').html('该菜单暂无相应权限');
				}
				changeCheck();
			}
		}
	};
		
	var params = {'roleId':$('#roleId').val()};
	// 载入菜单数据
	$.ajax({
		url:rootPath+'/menu/listwithpermis',
		data:params,
		type:'GET',
		dataType:'JSON',
		success:function(data){
			var menus = data.menus;
			formatTree(menus);
			$.fn.zTree.init($("#treemenu"), setting, data.menus);
			var treeObj = $.fn.zTree.getZTreeObj("treemenu");
			// 创建根节点
			var newNodes = [ {
				name : "全部权限",
				icon : rootPath + '/assets/images/forder-open.png',
				iconOpen : rootPath + '/assets/images/forder-open.png',
				iconClose : rootPath + '/assets/images/forder-close.png',
				click : "$('#permhtm').html($('#allpermis').html())"
			} ];
			newNodes = treeObj.addNodes(null,0,newNodes);
			// 设置菜单为展开状态
			var nodes = treeObj.getNodes();
			var isOpen = nodes[0].open;
		}
	});
	
	// 全选框状态
	$('#all').click(function(){
		var that = $(this);
		$('#permhtm :checkbox').each(function(){
			$(this).prop('checked',that.prop('checked'));
		});
	});
	
	// 复选框单选状态
	$('#permhtm').on('click',':checkbox',function(){
		changeCheck();
	})
			
}

/**
 * 根据点击的树节点生成权限列表
 * @param treeNode
 */
function createNodeHtml(treeNode){
	var permissions = treeNode.permissions;
	var permhtm = '';
	for(var i in permissions){
		var permission = permissions[i];
		permhtm += '<li>'+
					'<div class="checkbox"><label>'+
					(permission.ischecked?'<input type="hidden" name="permisIds" value="'+permission.id+'"/>':'')+
					'<input type="checkbox" name="permissionId" value="'+permission.id+'"'+(permission.ischecked && 'checked="checked"')+'/>'+permission.permissionName+
					'</label></div></li>'
	}
	
	return permhtm;
}

/**
 * 改变树状样式
 * @param treeDatas
 */
function formatTree(treeDatas){
	for(var i in treeDatas){
		if(treeDatas[i].parentId==0){
			treeDatas[i].open=true;
			treeDatas[i].icon = rootPath+'/assets/images/forder-close.png';
			treeDatas[i].iconOpen = rootPath+'/assets/images/forder-open.png';
			treeDatas[i].iconClose = rootPath+'/assets/images/forder-close.png';
		}else{
			treeDatas[i].icon = rootPath+'/assets/images/sub-task.png';
		}
	}
}

/**
 * 改变复选框选择状态
 */
function changeCheck(){
	var checkedlen = $('#permhtm :checked').length;
	var checkboxlen = $('#permhtm :checkbox').length;
	(checkboxlen>0 && checkedlen==checkboxlen) ? $('#all').prop('checked',true):$('#all').prop('checked',false);
}

/**
 * 关闭当前弹层
 */
function closePage(){
	//先得到当前iframe层的索引
	var index = parent.layer.getFrameIndex(window.name); 
	//再执行关闭   
	parent.layer.close(index); 
}