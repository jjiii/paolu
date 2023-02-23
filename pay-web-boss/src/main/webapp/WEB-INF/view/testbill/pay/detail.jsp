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
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}/assets/js/view/testbill/pay/detail.js"></script>
</head>
<body>
	<!-- Content area -->
	<div class="content">
		<div class="panel panel-flat">
			<div class="panel-body">
				<form id="data-form" class="form-horizontal">
					<input type="hidden" name="id" value="${data.id}">
					<div id="searchDiv" class="well well-sm text-right" style="margin-bottom:10px;">
						<button type="submit" class="btn btn-primary"><i class="icon-floppy-disk"></i> 保存</button>
					</div>
					<table class="table table-hover table-bordered">
					    <thead>
					        <tr>
					            <th colspan="4">支付订单基本信息</th>
					        </tr>
					    </thead>
					    <tbody>
					     	<tr>
	                           <td class="text-agr" width="25%" align="right">枫车支付流水号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="orderNo" value="${data.orderNo}" required></td>
	                           <td class="text-agr" width="25%" align="right">支付币种及金额（元）：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="number" name="amount" value="${data.amount}" required></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">商户订单号：</td>
	                           <td class="text-agl" width="25%"><input class="form-control" type="text" name="merchantOrderNo" value="${data.merchantOrderNo}" required></td>
	                           <td class="text-agr" width="25%" align="right">商品名称：</td>
	                           <td class="text-agl" width="25%">${data.productName}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${data.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                           <td class="text-agr" width="25%" align="right">支付完成时间：</td>
	                           <td class="text-agl" width="25%"><fmt:formatDate value="${data.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">商户名称及ID：</td>
	                           <td class="text-agl" width="25%">${data.merchantName}&nbsp;&nbsp;${data.merchantCode}</td>
	                           <td class="text-agr" width="25%" align="right">应用名称及ID：</td>
	                           <td class="text-agl" width="25%">${data.merchantAppName}&nbsp;&nbsp;${data.merchantAppCode}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付接口：</td>
	                           <td class="text-agl" width="25%">
	                           		<c:if test="${data.channel=='weixin'}">微信</c:if>
	                           		<c:if test="${data.channel=='alipay'}">支付宝</c:if>
	                           		<c:if test="${data.channel=='unionpay'}">银联</c:if>                           
	                           </td>
	                           <td class="text-agr" width="25%" align="right">第三方支付流水号：</td>
	                           <td class="text-agl" width="25%">${data.tradeNo}</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">支付状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<select class="form-control" name="status">
	                           			<option value="pay_wait" ${ data.status=='pay_wait' ?'selected':''} >待支付</option>
	                           			<option value="success" ${ data.status=='success' ?'selected':''} >成功</option>
	                           			<option value="finish" ${ data.status=='finish' ?'selected':''} >结束(不能退款)</option>
	                           			<option value="close" ${ data.status=='close' ?'selected':''} >关闭(超时/未收到通知)</option>
	                           		</select>
	                           </td>
	                           <td class="text-agr" width="25%" align="right">支付状态原因：</td>
	                           <td class="text-agl" width="25%">
	                           	    <c:choose>
		                           		<c:when test="${data.status=='success'}">${data.successReason}</c:when>
		                           		<c:when test="${data.status=='close'}">${data.closeReason}</c:when>
		                           	</c:choose>                             
	                           </td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">对账状态：</td>
	                           <td class="text-agl" width="25%">
	                           		<c:if test="${data.billStatus=='wait'}">待对账</c:if>
	                           		<c:if test="${data.billStatus=='normal'}">正常</c:if>
	                           		<c:if test="${data.billStatus=='doubt'}">存疑</c:if>
	                           		<c:if test="${data.billStatus=='mistake'}">差错</c:if>
	                           		<c:if test="${data.billStatus=='balance'}">平账</c:if>  
								</td>
	                           <td class="text-agr" width="25%" align="right">对账批次：</td>
	                           <td class="text-agl" width="25%">暂无</td>
	                       </tr>
	                       <tr>
	                           <td class="text-agr" width="25%" align="right">第三方支付支付人ID：</td>
	                           <td class="text-agl" width="25%">${data.buyerId}</td>
	                           <td class="text-agr" width="25%" align="right">第三方支付商户ID：</td>
	                           <td class="text-agl" width="25%">${data.channelMerchantId}</td>
	                       </tr>
					    </tbody>
					</table>
				</form>
			</div>
			
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
	
</body>
</html>
