
$(function(){
	list('/testbill/refund/listdata');
	// 搜索
	$('#search').on('click',function(){
		current = 1;
		list('/testbill/refund/listdata');
	});
	
	var start = {
			  elem: '#start',
			  format: 'YYYY-MM-DD hh:mm:ss',
			  //min: laydate.now(), //设定最小日期为当前日期
			  max: '2099-06-16 23:59:59', //最大日期
			  istime: true,
			  istoday: false,
			  choose: function(datas){
			     end.min = datas; //开始日选好后，重置结束日的最小日期
			     end.start = datas //将结束日的初始值设定为开始日
			  }
		};
	var end = {
		  elem: '#end',
		  format: 'YYYY-MM-DD hh:mm:ss',
		  min: laydate.now(),
		  max: '2099-06-16 23:59:59',
		  istime: true,
		  istoday: false,
		  choose: function(datas){
		    start.max = datas; //结束日选好后，重置开始日的最大日期
		  }
	};
	laydate(start);
	laydate(end);
});


/**
 * 展示信息页面
 * @param id
 */
function view(id){
	var data = {'id':id};
	var url = rootPath+'/testbill/refund/detail?'+$.param(data);
	var title = '退款订单详情';
	openModal(url,title);
}

//弹出全屏层
function openModal(url,title){
	var index = layer.open({
		type: 2,
		title:title,
		content: url,
		area: ['320px', '195px'],
		maxmin: false,
		end:function(){
			$('#issave').val()==1 && location.reload();
		},
		success:function(){
			//var body = layer.getChildFrame('body', index);
		}
	});
	layer.full(index);
}