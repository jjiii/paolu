$(function(){
	$("#data-form").validate({
		submitHandler: function(form) {
			addOrEdit('/testbill/refund/edit');
		}
	});
});
