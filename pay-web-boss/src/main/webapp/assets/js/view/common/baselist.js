/**
 * 列表页共用查询方法
 * 列表页须共用一套jsp模板才可引用
 */
var current = 1,size = 15;

/**
 * 查询列表记录
 * @param url
 */
function list(url){
	var index = parent.layer.load(2, {shade: [0.1,'#fff']});
	var params = {'current':current,'size':size};
	$.extend(params,$('#search-form').serializeObject());
	$.ajax({
		url:rootPath+url,
		type:'GET',
		data:params,
		dataType:'JSON',
		success:function(data){
			if(data.errcode==300){	// 登录超时
				parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
					location.reload();
				});
				return;
			}
			$('#pages').text(data.pages);
			$('#total').text(data.total);
			// 渲染页面
			var gettpl = $('#tabledata').html();
			laytpl(gettpl).render(data, function(html){
			   $('#data-body').html(html);
			});
			pageList(url,data);
			parent.layer.close(index);
		}
	});
}

/**
 * 处理分页查询
 * @param data
 */
function pageList(url,data){
	//显示分页
	laypage.dir=false;
    laypage({
      cont: 'pagebar', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
      pages: data.pages, //通过后台拿到的总页数
      curr: current, //当前页
      skip: true, //是否开启跳页
      skin: '#AF0000',
      groups: 5, //连续显示分页数
      jump: function(obj, first){ //触发分页后的回调
        if(!first){ // 点击跳页触发函数自身，并传递当前页：obj.curr
        	current = obj.curr;
        	list(url);
        }
      }
    });
}

/**
 * 
 * @param url
 * @param jsonData
 */
function delRecord(url,paramName){
	$('#data-body').on('click','.del',function(){
		var param = (paramName+'='+$(this).data('id'));
		console.info(param);
		var target = $(this);
		parent.askModal('确定要删除吗？',function(){
			if(param){
				$.ajax({
					url:rootPath+url,
					type:'GET',
					data:param,
					dataType:'JSON',
					success:function(data){
						if(data.errcode==300){	// 登录超时
							parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
								location.reload();
							});
							return;
						}
						if(data.errcode==0){
						parent.layer.alert(data.errmsg,{icon: 1},function(index){
							target.parents('tr').remove();
							parent.layer.close(index);
						});
						}else{
							parent.layer.alert(data.errmsg,{icon: 2},function(index){
								parent.layer.close(index);
							});
						}
					}
				});
			}
		})
	})
}