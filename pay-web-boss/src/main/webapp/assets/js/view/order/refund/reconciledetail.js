var openIndex;
var html;

$(function(){
	
});

/**
 * 确定平帐
 * @param id	差错记录ID
 */
function settle(id){
	$('#mistakeId').val(id);
	html = $('#modal_form_reset').html();
	$('#modal_form_reset').html('');
	openIndex = layer.open({
		  type: 1,
		  title: '差错处理',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['600px', '350px'], //宽高
		  content: html
		});
}

/**
 * 执行平帐
 */
function doSettle(){
	var handleRemark = $('#handleRemark').val();
	if(handleRemark){
		var params = $('#data-form').serialize();
		var loadIndex = layer.load(0, {
			shade: [0.3,'#fff'] //0.3透明度的白色背景
		});
		$.baseAjax(rootPath+"/order/refund/dosettle",params,true,'POST','JSON',
				function(data){
			if(data){
				layer.alert('操作成功',{closeBtn: 0}, function(index){
					    layer.close(loadIndex);
					    layer.close(index);
					    layer.close(openIndex);
					    $('#modal_form_reset').html(html);
					    list();
					});
			}else{
				layer.alert('操作失败');
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
}
