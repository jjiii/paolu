/**
 * 展示信息页面
 * @param operatorId
 */
function showOperator(operatorId){
	var data = {'operatorId':operatorId};
	var url = 'operator/editUI/view?'+$.param(data);
	//$('#html-data').load(url);
	$('#modal_remote').modal({remote:url});
}