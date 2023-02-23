var listPage = parent;	//父级页面
var indexPage = parent.parent;  //顶级页面index

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
function addMerchantAppData(){
	addOrEdit(rootPath+'/merchantapp/adddata');
}

/**
 * 更新商户
 * 
 */
function editMerchantData(){
	addOrEdit(rootPath+'/merchantapp/editdata');
}

/**
 * 新增或编辑通用
 * @param url
 */
function addOrEdit(url){
	var loadIndex = indexPage.layer.load(0, {shade: [0.3,'#fff']});
	var params = $('#data-form').serialize();
	$.baseAjax(url,params,true,'post','json',function(data){
		if(data.errcode==0){
			indexPage.alertModal(data.errmsg,1,function(){
				// 标识表单已修改成功
				$(listPage.document).find('#issave').val(1);
			});
		}else{
			indexPage.msgModal(data.errmsg,2);
		}
		indexPage.layer.close(loadIndex);
	},function(){
		indexPage.layer.close(loadIndex);
	});
}

/**
 * 加添商户支付接口
 * @param url
 * @param params
 */
function addMerchantAppConfig(merchantCode,merchantName,merchantAppCode,merchantAppName){
	var params = {'merchantCode':merchantCode,'merchantName':merchantName,'merchantAppCode':merchantAppCode,'merchantAppName':merchantAppName};
	var url = rootPath+'/merchantappconfig/add?'+$.param(params);
	openModal(url,'新增支付接口');
}

/**
 * 查看商户支付接口
 * @param merchantAppId
 */
function editMerchantConfigApp(merchantAppConfigId){
	var data = {'merchantConfigAppId':merchantAppConfigId};
	var url = rootPath+'/merchantappconfig/edit?'+$.param(data);
	//openModal(url,'查看支付接口详情');
	location.href=url;
}

/**
 * 开启/关闭商户支付接口
 * @param merchantAppConfigId
 */
function changeMerchantAPPStatus(merchantAppConfigId){
	var event = window.event || arguments.callee.caller.arguments[0];
	var target = $(event.target||event.srcElement);
	var changeStatus = (target.data('status')==1?0:1);
	var data = {'id':merchantAppConfigId,'status':changeStatus};
	var url = rootPath+'/merchantappconfig/editdata';
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

/**
 * 关闭当前弹层
 */
function closePage(){
	//先得到当前iframe层的索引
	var index = parent.layer.getFrameIndex(window.name); 
	//再执行关闭   
	parent.layer.close(index); 
}

