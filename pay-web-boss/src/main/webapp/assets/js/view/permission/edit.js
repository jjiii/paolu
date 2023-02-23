//var listPage = parent;	//父级页面
//var indexPage = parent.parent;  //顶级页面index

$(function(){
	$("#data-form").validate({
		submitHandler: function(form) {
			eval($(form).data('method'))();
		}
	});
});

/**
 * 添加权限
 */
function addPermission(){
	if(!$('#menuId').val()){
		indexPage.alertModal('请先选择一个菜单',2);
		return;
	}
	
	addOrEdit('/permission/add');
}

/**
 * 更新权限
 * @param 更新权限
 */
function editPermission(){
	addOrEdit('/permission/edit');
}

/**
 * 新增或编辑通用
 * @param url
 */
/*function addOrEdit(url){
	var params = $('#data-form').serialize();
	$.baseAjax(url,params,true,'post','json',function(data){
		if(data.errcode==0){
			indexPage.alertModal(data.errmsg,1,function(){
				// 标识表单已修改成功
				$(listPage.document).find('#issave').val(1);
			});
		}else{
			indexPage.msgModal(data.errmsg,2);
		}
	});
}*/

/**
 * 菜单树
 */
function initMenuTree(){
	var setting = {
		check : {
			enable : false,
			chkStyle: "radio"
		},
		data : {
			simpleData : {
				enable : true,
				pIdKey: "parentId"
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode){
				// 如果点击的是二级菜单
				if(treeNode.parentId){
					// 取二级菜单id
					$('#menuId').val(treeNode.id);
				}
			}
		}
	};
		
	// 载入菜单数据
	$.ajax({
		url:rootPath+'/menu/listdata',
		type:'GET',
		dataType:'JSON',
		success:function(data){
			var menus = data.menus;
			for(var i in data.menus){
				if(menus[i].parentId==0){
					menus[i].open=true;
					menus[i].icon = rootPath+'/assets/images/forder-close.png';
					menus[i].iconOpen = rootPath+'/assets/images/forder-open.png';
					menus[i].iconClose = rootPath+'/assets/images/forder-close.png';
				}else{
					menus[i].icon = rootPath+'/assets/images/sub-task.png';
				}
			}
			var treeObj = $.fn.zTree.init($("#treemenu"), setting, menus);
			var sNodes = treeObj.getSelectedNodes();
			if (sNodes.length > 0) {
				var isOpen = sNodes[0].open;
			}
		}
	});
}

/**
 * 关闭当前弹层
 */
/*function closePage(){
	//先得到当前iframe层的索引
	var index = parent.layer.getFrameIndex(window.name); 
	//再执行关闭   
	parent.layer.close(index); 
}*/