var listPage = parent;	//父级页面
var indexPage = parent.parent;  //顶级页面index

$(function(){
	$("#data-form").validate({
		submitHandler: function(form) {
			eval($(form).data('method'))();
		}
	});
});
/**
 * 添加菜单
 * @returns {Boolean}
 */
function addMenu(){
	addOrEdit('/menu/add')
}


/**
 * 更新菜单
 * @param menuId
 */
function editMenu(){
	addOrEdit('/menu/edit')
}
