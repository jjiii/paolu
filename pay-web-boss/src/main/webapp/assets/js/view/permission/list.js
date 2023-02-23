//var current = 1;
//var size = 15;

$(function(){
	list('/permission/list');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size=15;
		list('/permission/list');
	});
	
	$('#menus').on('change',function(){
		var pid = $(this).find('option:selected').data('id');
		var cid = "";
		if(pid){
			var secondMenus = $(this).find('option[data-pid='+pid+']');
			if(secondMenus.length>0){
				secondMenus.each(function(){
					cid += this.value+",";
				});
				cid = cid.substr(0,cid.lastIndexOf(','));
			}
		}else{
			cid = $(this).val();
		}
		$('#menuId').val(cid);
	});
	
	delPermission();
	
});


/**
 * 加添权限界面
 * @param url
 * @param params
 */
function addPermissionUI(){
	var url = rootPath+'/permission/addUI';
	openModal(url,'添加权限');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editPermissionUI(permissionId){
	var data = {'permissionId':permissionId};
	var url = rootPath+'/permission/editUI?'+$.param(data);
	openModal(url,'编辑权限');
}

/**
 * 删除权限
 * @param roleId
 */
function delPermission(){
	delRecord('/permission/delete','permissionId');
}
