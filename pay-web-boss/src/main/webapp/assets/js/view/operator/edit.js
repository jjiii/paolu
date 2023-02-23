
$(function(){
	
	//检测手机号是否正确  
	$.validator.addMethod("isMobile", function(value, element) {  
	    var length = value.length;  
	    var regPhone = /^1\d{10}$/;  
	    return this.optional(element) || ( length == 11 && regPhone.test( value ) );    
	}, "请正确填写您的手机号码"); 
	
	$("#data-form").validate({
		rules:{  
			mobileNo:{  
	            required:true,  
	            maxlength:11,
	            isMobile:true
	        }
	    },  
		submitHandler: function(form) { 
			eval($(form).data('method'))();
		}
	});
});

/**
 * 添加操作员
 */
function addOperator(){
	addOrEdit('/operator/add')
}

/**
 * 更新操作员
 * @param 更新操作员
 */
function editOperator(){
	addOrEdit('/operator/edit')
}

/**
 * 当前用户修改个人信息
 * @param 当前用户修改个人信息
 */
function editSelf(){
	addOrEdit('/operator/editselfdata')
}
