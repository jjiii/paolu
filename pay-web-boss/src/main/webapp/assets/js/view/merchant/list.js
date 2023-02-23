
$(function(){
	list('/merchant/listdata');
	
	// 搜所
	$('#search').on('click',function(){
		current = 1;size = 15;
		list('/merchant/listdata');
	});;
	
	$("#data-form").validate({
		submitHandler: function(form) {
			addMerchantData();
		}
	});
});

/**
 * 加添商户界面
 * @param url
 * @param params
 */
function addMerchant(){
	var url = rootPath+'/merchant/add';
	openModal(url,'添加商户');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editMerchant(merchantId){
	var data = {'merchantId':merchantId};
	var url = rootPath+'/merchant/edit?'+$.param(data);
	//openModal(url,'查看商户');
	location.href=url;
}

/**
 * 开启/关闭商家
 * @param merchantId
 * @param status
 */
function changeStatus(merchantId){
	var event = window.event || arguments.callee.caller.arguments[0];
	var target = $(event.target||event.srcElement);
	var changeStatus = (target.data('status')==1?0:1);
	var data = {'id':merchantId,'status':changeStatus};
	var url = rootPath+'/merchant/editdata';
	$.ajax({
		url:url,
		type:'GET',
		data:data,
		dataType:'json',
		success:function(result){
			if(result.errcode==300){	// 登录超时
				parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
					location.reload();
				});
				return;
			}
			if(result.errcode==0){
				parent.alertModal(result.errmsg,1,function(){
					if(changeStatus==1){
						target.data('status',1);
						target.parent('td').prev().text('开启中');
						target.text('关闭');
					}else{
						target.data('status',0);
						target.parent('td').prev().text('关闭中');
						target.text('开启');
					}
				});
			}else{
				parent.msgModal(result.errmsg,2);
			}
		}
	});
}
