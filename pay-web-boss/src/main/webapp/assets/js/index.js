
/**
 * 载入页面
 * @param url
 * @param menu
 * @param secondMenu
 * @param clickli
 */
function loadpage(url,clickli){
	var event = window.event || arguments.callee.caller.arguments[0];
	if(event){
		var target = event.target||event.srcElement;
		if(target.tagName){
			// 移除未点击菜单的样式
			$('#sidebar').find('ul').find('ul>li').removeClass('active');
			// 点击的菜单追加样式
			$(target).parent('li').addClass('active');
		}
	}
	// 加载数据
	loadView('#html-data',url,clickli);
}

/**
 * 载入右边视图
 * @param node
 * @param url
 * @param clickli
 */
function loadView(node,url,clickli){
	if(url){
		clickli&&(url+='?clickli='+clickli);
		$('#iframepage').attr('src',rootPath+"/"+url);
	}else{
		layer.msg("没有有效的访问地址");
	}
}

//询问弹框
function askModal(msg,suc,fail){
	layer.confirm(msg, {
		  btn: ['确定','取消'], //按钮
		  icon:3,
		  title:'请确认'
		}, function(index){
			suc&&suc();
			layer.close(index);
		}
	);
}

/**
 * 提示信息
 * @param msg
 * @param icon
 */
function msgModal(msg,icon,func){
	layer.msg(msg, {icon: icon||1,time: 1000},function(){
		func&&func();
	});
}

/**
 * 弹框提示
 * @param msg
 * @param icon
 * @param func
 */
function alertModal(msg,icon,func){
	layer.alert(msg, {icon: icon||1},function(index){
		func&&func();
		layer.close(index);
	});
}

/**
 * 查看操作员
 * @param operatorId
 */
function userInfo(operatorId){
	var data = {'operatorId':operatorId};
	var url = rootPath+'/operator/editview/view?'+$.param(data);
	layer.open({type:2,title:'我的资料',content:url,area:['600px','400px'],maxmin:false});
}

/**
 * 账号设置
 * @param operatorId
 */
function userSetting(operatorId){
	var data = {'operatorId':operatorId};
	var url = rootPath+'/operator/editview/editself?'+$.param(data);
	openModal(url,'帐号设置')
}

//弹出层
function openModal(url,title){
	var index = layer.open({
		type: 2,
		title:title,
		content: url,
		area: ['500px','345px'],
		maxmin: false,
		success:function(layero){
		}
	});
}
