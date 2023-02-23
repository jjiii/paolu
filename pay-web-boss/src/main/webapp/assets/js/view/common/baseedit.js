/**
 * 保存或更新操作共用js
 * 保存或编辑页须共用一套jsp模板才可引用
 */
var listPage = parent;	//父级页面
var indexPage = parent.parent;  //顶级页面index

/**
 * 新增或编辑通用
 * @param url
 */
function addOrEdit(url){
	var params = $('#data-form').serialize();
	// loading效果
	var loadIndex = layer.load(0, {shade: [0.3,'#fff']});
	$.baseAjax(rootPath+url,params,true,'post','json',function(data){
		if(data.errcode==300){	// 登录超时
			parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
				location.reload();
			});
			return;
		}
		if(data.errcode==0){
			indexPage.alertModal(data.errmsg,1,function(){
				// 标识表单已修改成功
				$(listPage.document).find('#issave').val(1);
			});
		}else{
			indexPage.msgModal(data.errmsg,2);
		}
		layer.close(loadIndex);
	},function(){
		layer.close(loadIndex);
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