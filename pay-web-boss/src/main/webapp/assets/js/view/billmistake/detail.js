var openIndex;
var html;

$(function(){
	$('table').on('click','a',function(){
		var id = $(this).data('id');
		$('#mistakeId').val(id);
		html = $('#modal_form_reset').html();
		$('#modal_form_reset').html('');
		openIndex = layer.open({
			  type: 1,
			  title: '差错处理',
			  skin: 'layui-layer-rim', //加上边框
			  area: ['600px', '350px'], //宽高
			  content: html,
			  end:function(){
				  $('#modal_form_reset').html(html);
			  }
		});
	});
	
	$('body').on('click','#doSettle',function(){
		var handleRemark = $('#handleRemark').val();
		if(handleRemark){
			var params = $('#data-form').serialize();
			var loadIndex = layer.load(0, {
				shade: [0.3,'#fff'] //0.3透明度的白色背景
			});
			$.baseAjax(rootPath+"/order/refund/dosettle",params,true,'POST','JSON',
					function(data){
						if(data.errcode==300){	// 登录超时
							parent.layer.alert("登录超时，请重新登录",{icon: 2},function(){
								location.reload();
							});
							return;
						}
						if(data){
							layer.alert('操作成功',{icon:1,closeBtn: 0}, function(index){
								    layer.close(loadIndex);
								    layer.close(index);
								    layer.close(openIndex);
								    $('#modal_form_reset').html(html);
								});
							// 删除确认平账按钮
							$('a[data-id='+$('#mistakeId').val()+']').parents('tr').remove();
						}else{
							layer.alert('操作失败',{icon:2,closeBtn: 0});
						}
						layer.close(loadIndex);
			},
			function(XMLHttpRequest){
				layer.close(loadIndex);
			}
			);
		}else{
			layer.tips('请填写备注信息', '#handleRemark', {
				  tips: 1
			});
		}
	});
});

