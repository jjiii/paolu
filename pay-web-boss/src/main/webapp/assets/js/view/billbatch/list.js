
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
	list('/billbatch/listdata');
	// 搜所
	$('#search').on('click',function(){
		current = 1;size = 15;
		list('/billbatch/listdata');
	});;
	
});



/**
 * 重启对账批次
 * @param batchNo
 */
function recheckBatch(batchNo){
	var data = {"batchNo":batchNo};
	$.ajax({
		url:rootPath+'/billbatch/recheck',
		type:'get',
		data:data,
		dataType:'JSON',
		success:function(data){
			if(data.errcode==300){	// 登录超时
				parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
					location.reload();
				});
				return;
			}
			if(data.result){
				parent.layer.alert("重启成功",{icon: 1},function(index){
					parent.document.getElementById('iframepage').contentWindow.location.reload(true);
					parent.layer.close(index);
				});
			}else{
				parent.layer.alert("重启失败",{icon: 2},function(index){
					parent.layer.close(index);
				});
			}
		}
	});
}

/**
 * 查看支付或退款订单
 * @param batchNo
 * @param billType
 */
function viewBillItem(batchNo,billType){
	location.href=rootPath+'/billitem/list?batchNo='+batchNo+"&billType="+billType;
}
