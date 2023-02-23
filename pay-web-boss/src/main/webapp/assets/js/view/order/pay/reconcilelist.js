var current = 1;
var size = 15;

$(function(){
	list();
	// 搜索
	$('#search').on('click',function(){
		current = 1;
		var params = $('#search-form').serializeObject();
		// 保存查询参数
		list(params);
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

function list(queryString){
	var index = parent.layer.load(2, {shade: [0.1,'#fff']});
	var params = {'current':current,'size':size};
	$.extend(params,queryString);
	$.ajax({
		url:rootPath+'/order/pay/reconcilelistdata',
		type:'GET',
		data:params,
		dataType:'JSON',
		cache:false,
		success:function(data){
			// 渲染页面
			var gettpl = $('#tabledata').html();
			laytpl(gettpl).render(data, function(html){
			   $('#data-body').html(html);
			});
			pageList(data);
			parent.layer.close(index);
		}
	});
}

function pageList(data){
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
        	list();
        }
      }
    });
}

/**
 * 展示信息页面
 * @param id
 */
function view(id){
	var data = {'id':id};
	var url = rootPath+'/order/pay/reconciledetail?'+$.param(data);
	var title = '支付订单详情';
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