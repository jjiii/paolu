<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../../inc/common/global.jsp" %>
	
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		table thead tr th{text-align: center;}
	</style>
	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/ui/nicescroll.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/layout_fixed_custom.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}/assets/js/view/testbill/refund/detail.js"></script>
</head>
<body>
	<!-- Content area -->
	<div class="content">
		<div class="panel panel-flat">
			<div class="panel-body">
				<form id="data-form" class="form-horizontal">
					<div id="searchDiv" class="well well-sm text-right" style="margin-bottom:10px;">
						<button type="submit" class="btn btn-primary"><i class="icon-floppy-disk"></i> 保存</button>
					</div>			
					<input type="hidden" name="refundOrder.id" value="${refundOrder.id}">
					<input type="hidden" name="payPaymentOrder.id" value="${paymentOrder.id}">
					<table class="table table-hover table-bordered">
					    <thead>
					        <tr>
					            <th colspan="4">退款订单基本信息</th>
					        </tr>
					    </thead>
					    <tbody>
					     	<tr>
	                           <td class="text-agr" width="25%" align="right">枫车退款流水号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="refundOrder.orderNo" value="${refundOrder.orderNo}" required></td>
	                           <td class="text-agr" width="25%" align="right">商户退款订单号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="refundOrder.merchantOrderNo" value="${refundOrder.merchantOrderNo}" required></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">第三方支付实际退款人ID：</td>
	                           <td class="text-agl" width="25%">${refundOrder.buyerId}</td>
	                           <td class="text-agr" width="25%" align="right">退款原因：</td>
	                           <td class="text-agl" width="25%">${refundOrder.refundReason}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">退款时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${refundOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                           <td class="text-agr" width="25%" align="right">退款完成时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${refundOrder.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">退款币种及金额（元）：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="number" name="refundAmount" value="${refundOrder.refundAmount}" required></td>
	                           <td class="text-agr" width="25%" align="right">第三方退款流水号：</td>
	                           <td class="text-agl" width="25%">${refundOrder.tradeNo}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">退款状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<select class="form-control" name="refundStatus">
	                           			<option value="pay_wait" ${ refundOrder.refundStatus=='application' ?'selected':''} >待支付</option>
	                           			<option value="success" ${ refundOrder.refundStatus=='success' ?'selected':''} >成功</option>
	                           			<option value="finish" ${ refundOrder.refundStatus=='faile' ?'selected':''} >结束(不能退款)</option>
	                           		</select>	                           		                          
	                           </td>
	                           <td class="text-agr" width="25%" align="right">退款状态原因：</td>
	                           <td class="text-agl" width="25%">
		                           	<c:choose>
		                           		<c:when test="${refundOrder.refundStatus=='success'}">${refundOrder.refundSuccessReason}</c:when>
		                           		<c:when test="${refundOrder.refundStatus=='fail'}">${refundOrder.refundFaileReason}</c:when>
		                           	</c:choose>
	                           </td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">对账状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<c:if test="${refundOrder.billStatus=='wait'}">待对账</c:if>
	                           		<c:if test="${refundOrder.billStatus=='normal'}">正常</c:if>
	                           		<c:if test="${refundOrder.billStatus=='doubt'}">存疑</c:if>
	                           		<c:if test="${refundOrder.billStatus=='mistake'}">差错</c:if>
	                           		<c:if test="${refundOrder.billStatus=='balance'}">平账</c:if>                           
	                           </td>
	                           <td class="text-agr" width="25%" align="right">对账批次：</td>
	                           <td class="text-agl" width="25%">暂无</td>
	                       </tr>
					    </tbody>
					</table>
					
					<table class="table table-hover table-bordered">
					    <thead>
					        <tr>
					            <th colspan="4">原支付订单基本信息</th>
					        </tr>
					    </thead>
					    <tbody>
					     	<tr>
	                           <td class="text-agr" width="25%" align="right">枫车支付流水号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="paymentOrder.orderNo" value="${paymentOrder.orderNo}" required></td>
	                           <td class="text-agr" width="25%" align="right">支付币种及金额（元）：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="number" name="paymentOrder.amount" value="${paymentOrder.amount}" required></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">商户订单号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="paymentOrder.merchantOrderNo" value="${paymentOrder.merchantOrderNo}" required></td>
	                           <td class="text-agr" width="25%" align="right">商品名称：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.productName}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${paymentOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                           <td class="text-agr" width="25%" align="right">支付完成时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${paymentOrder.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">商户名称及ID：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.merchantName}&nbsp;&nbsp;${paymentOrder.merchantCode}</td>
	                           <td class="text-agr" width="25%" align="right">应用名称及ID：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.merchantAppName}&nbsp;&nbsp;${paymentOrder.merchantAppCode}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付接口：</td>
	                           <td class="text-agl" width="25%">
	                           		<c:if test="${paymentOrder.channel=='weixin'}">微信</c:if>
	                           		<c:if test="${paymentOrder.channel=='alipay'}">支付宝</c:if>
	                           		<c:if test="${paymentOrder.channel=='unionpay'}">银联</c:if>
	                           	</td>
	                           <td class="text-agr" width="25%" align="right">第三方支付流水号：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.tradeNo}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<select class="form-control" name="payPaymentOrder.status">
	                           			<option value="pay_wait" ${ data.status=='pay_wait' ?'selected':''} >待支付</option>
	                           			<option value="success" ${ data.status=='success' ?'selected':''} >成功</option>
	                           			<option value="finish" ${ data.status=='finish' ?'selected':''} >结束(不能退款)</option>
	                           			<option value="close" ${ data.status=='close' ?'selected':''} >关闭(超时/未收到通知)</option>
	                           		</select>
	                           </td>
	                           <td class="text-agr" width="25%" align="right">支付状态原因：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.remark}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">对账状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<c:if test="${paymentOrder.billStatus=='wait'}">待对账</c:if>
	                           		<c:if test="${paymentOrder.billStatus=='normal'}">正常</c:if>
	                           		<c:if test="${paymentOrder.billStatus=='doubt'}">存疑</c:if>
	                           		<c:if test="${paymentOrder.billStatus=='mistake'}">差错</c:if>
	                           		<c:if test="${paymentOrder.billStatus=='balance'}">平账</c:if>
	                           	</td>
	                           <td class="text-agr" width="25%" align="right">对账批次：</td>
	                           <td class="text-agl" width="25%">暂无</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">第三方支付支付人ID：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.buyerId}</td>
	                           <td class="text-agr" width="25%" align="right">第三方支付商户ID：</td>
	                           <td class="text-agl" width="25%">${paymentOrder.channelMerchantId}</td>
	                       </tr>
					    </tbody>
					</table>
				</form>		
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
	
	<!-- Inline form modal -->
	<div id="modal_form_reset" class="modal fade">
		<div class="panel panel-flat clear-mg-bottom">
			<div class="panel-body">
				<form id="data-form" action="#" class="form-horizontal data-form">
					<input type="hidden" id="mistakeId" name="mistakeId">
					<div class="form-group">
						<label class="col-sm-2 control-label">处理方式: </label>
						<label class="radio-inline">
						  <input type="radio" id="handleType1" name="handleType" value="channel" checked="checked"> 渠道为准(将修改原支付信息)
						</label>
						<label class="radio-inline">
						  <input type="radio" id="handleType2" name="handleType" value="local"> 枫车支付为准
						</label>
					</div>
					
					<div class="form-group">
						<label for="handleRemark" class="col-sm-2 control-label">处理备注: </label>
						<textarea id="handleRemark" name="handleRemark" class="form-control col-sm-10" rows="3" style="width: 80%;" maxlength="30"></textarea>
					</div>
	
					<div class="modal-footer text-right">
						<button type="button" class="btn btn-primary"  data-dismiss="modal" onclick="doSettle();">提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- /inline form modal -->
</body>
</html>
