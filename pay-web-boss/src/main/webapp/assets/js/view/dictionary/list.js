
$(function(){
	list('/dictionary/listdata');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size=15;
		list('/dictionary/listdata');
	});
	
	delDict();
	
});


/**
 * 加添权限界面
 * @param url
 * @param params
 */
function addDict(){
	var url = rootPath+'/dictionary/add';
	openModal(url,'添加字典');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editDict(dictionaryId){
	var data = {'dictionaryId':dictionaryId};
	var url = rootPath+'/dictionary/edit?'+$.param(data);
	openModal(url,'编辑字典');
}

/**
 * 删除权限
 * @param roleId
 */
function delDict(){
	delRecord('/dictionary/delete','dictionaryId');
}
