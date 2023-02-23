$(function(){
	$("#data-form").validate({
		submitHandler: function(form) {
			addOrEdit('/testbill/pay/edit');
		}
	});
});