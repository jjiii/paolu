
$(function(){
	$("#data-form").validate({
		submitHandler: function(form) {
			eval($(form).data('method'))();
		}
	});
	
	checkRecord();
});

/**
 * 处理没记录的情况
 */
function checkRecord(){
	if($('#norecord')){
		var record = $('#data-body').find('tr').length;
		record <=1 && $('#norecord').removeClass('hidden');
	}
}

/**
 * 新增商户
 */
function addMerchantData(){
	addOrEdit('/merchant/adddata');
}

/**
 * 更新商户
 * 
 */
function editMerchantData(){
	addOrEdit('/merchant/editdata');
}

/**
 * 加添商户应用界面
 * @param url
 * @param params
 */
function addMerchantApp(merchantCode,merchantName){
	var data = {'merchantCode':merchantCode,'merchantName':merchantName};
	var url = rootPath+'/merchantapp/add?'+$.param(data);
	openModal(url,'添加商户应用');
}

/**
 * 查看商户应用
 * @param merchantAppId
 */
function editMerchantApp(merchantAppId){
	var data = {'merchantAppId':merchantAppId};
	var url = rootPath+'/merchantapp/edit?'+$.param(data);
	//openModal(url,'查看商户');
	location.href=url;
}

/**
 * 开启/关闭商户应用
 * @param merchantAppId
 */
function changeMerchantAPPStatus(merchantAppId){
	var event = window.event || arguments.callee.caller.arguments[0];
	var target = $(event.target||event.srcElement);
	var changeStatus = (target.data('status')==1?0:1);
	var data = {'id':merchantAppId,'status':changeStatus};
	var url = rootPath+'/merchantapp/editdata';
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
