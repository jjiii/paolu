$(function(){
	delMenu();
});
/**
 * 删除菜单
 * @param menuId
 */
function delMenu(menuId){
	delRecord('/menu/delete','menuId');
}

/**
 * 加添菜单界面
 * @param url
 * @param params
 */
function addMenuUI(url,menuid,menuname){
	menuid&&(url+= '?id='+menuid+"&name="+menuname);
	openModal(url,'添加菜单');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editMenuUI(menuId){
	var event = window.event || arguments.callee.caller.arguments[0];
	var srcEle = $(event.target||event.srcElement);
	var parentId = srcEle.parents('tr').data('tt-parent-id');
	var data = {'menuId':menuId};
	if(parentId){
		var parentName = $.trim($('#p'+parentId).text());
		data.parentName = parentName;
	}
	var url = rootPath+'/menu/editUI?'+$.param(data);
	//$('#html-data').load(url);
	//$('#modal_remote').modal({remote:url});
	openModal(url,'编辑菜单');
}

