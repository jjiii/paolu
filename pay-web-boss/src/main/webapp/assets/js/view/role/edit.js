
$(function(){
	$("#data-form").validate({
		submitHandler: function(form) { 
			eval($(form).data('method'))();
		}
	});
});

/**
 * 添加角色
 */
function addRole(){
	addOrEdit('/role/add');
}

/**
 * 更新角色
 * @param 更新角色
 */
function editRole(){
	addOrEdit('/role/edit');
}

