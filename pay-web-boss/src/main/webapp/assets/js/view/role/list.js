
$(function(){
	list('/role/list');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size = 15;
		// 保存查询参数
		list('/role/list');
	});
	
	delRole();
});

/**
 * 加添角色界面
 * @param url
 * @param params
 */
function addRoleUI(){
	var url = rootPath+'/role/addUI';
	openModal(url,'添加角色');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editRoleUI(roleId){
	var data = {'roleId':roleId};
	var url = rootPath+'/role/editUI/edit?'+$.param(data);
	openModal(url,'编辑角色');
}

/**
 * 删除角色
 * @param roleId
 */
function delRole(roleId){
	delRecord('/role/delete','roleId');
}

/**
 * 分配菜单界面
 * @param roleId
 */
function assignMenuUI(roleId){
	var url = rootPath+'/role/assignMenuUI?roleId='+roleId;
	openModal(url,'分配菜单');
}

/**
 * 分配权限界面
 * @param roleId
 */
function assignPermissionUI(roleId){
	var url = rootPath+'/role/assignPermisUI?roleId='+ roleId;
	openModal(url,'分配角色');
}
