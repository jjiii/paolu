$(function(){
	var start = {
		  elem: '#start',
		  format: 'YYYY-MM-DD',
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
		  format: 'YYYY-MM-DD',
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

$(function(){
	list('/billdoubt/listdata');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size = 15;
		list('/billdoubt/listdata');
	});;
	
});

function detail(id){
	openModal(rootPath+'/billdoubt/detail?id='+id,"对账存疑详情");
}
