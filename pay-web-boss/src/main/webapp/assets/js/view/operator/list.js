
$(function(){
	list('/operator/list');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size = 15;
		// 保存查询参数
		list('/operator/list');
	});
	
	delOperator();
});

/**
 * 加添菜单界面
 * @param url
 * @param params
 */
function addOperatorUI(){
	var url = rootPath+'/operator/addUI';
	openModal(url,'添加操作员');
}

/**
 * 跳转到编辑页面
 * @param menuId
 */
function editOperatorUI(operatorId){
	var data = {'operatorId':operatorId};
	var url = rootPath+'/operator/editUI/edit?'+$.param(data);
	openModal(url,'修改操作员');
}

/**
 * 展示信息页面
 * @param operatorId
 */
function showOperator(operatorId){
	var data = {'operatorId':operatorId};
	var url = rootPath+'/operator/editUI/view?'+$.param(data);
	openModal(url,'查看操作员');
}

/**
 * 重置密码
 * @param operatorId
 */
function resetPwd(operatorId){
	var data = {'operatorId':operatorId};
	var url = rootPath+'/operator/reset';
	layer.prompt({title:'输入新密码'},function(value, index, elem){
		if(value){
			data.loginPwd=value;
			$.getJSON(url,data,function(data){
				if(data.errcode==300){	// 登录超时
					parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
						location.reload();
					});
					return;
				}
				layer.msg(data.errmsg);
			});
		}
		layer.close(index);
	});
}


/**
 * 冻结账号
 * @param operatorId
 */
function freezeOperator(operatorId){
	var event = window.event || arguments.callee.caller.arguments[0];
	var target = $(event.target||event.srcElement);
	var status = target.data('status');
	var data = {'operatorId':operatorId,'isfreeze':status!='UNACTIVE'};
	var url = rootPath+'/operator/freeze';
	$.getJSON(url,data,function(data){
		if(data.errcode==300){	// 登录超时
			parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
				location.reload();
			});
			return;
		}
		var newstauts = (status!='UNACTIVE'?'UNACTIVE':'ACTIVE');
		var btntext = (status!='UNACTIVE'?'启用':'禁用');
		var tdtext = (status=='UNACTIVE'?'启用':'禁用');
		target.data('status',newstauts);
		target.text(btntext);
		target.parent('td').siblings('td').eq(4).text(tdtext);
		if(data.errcode==0){
			layer.alert(data.errmsg,{icon:1});
		}else{
			layer.alert(data.errmsg,{icon:2});
		}
	});
}

/**
 * 解冻账号
 * @param operatorId
 */
function unFreezeOperator(operatorId){
	var data = {'operatorId':operatorId,'isfreeze':false};
	var url = rootPath+'/operator/freeze';
	$.getJSON(url,data,function(data){
		layer.msg(data.errmsg);
	});
}

/**
 * 删除操作员
 */
function delOperator(operatorId){
	delRecord('/operator/delete','operatorId');
}
