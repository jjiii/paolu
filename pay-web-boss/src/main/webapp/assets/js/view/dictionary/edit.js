
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
function addDict(){
	addOrEdit('/dictionary/adddata');
}

/**
 * 更新权限
 * @param 更新权限
 */
function editDict(){
	addOrEdit('/dictionary/editdata');
}

