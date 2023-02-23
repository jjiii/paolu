<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>查看对账存疑详情</title>
	<%@ include file="../inc/common/global.jsp" %>
	
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
</head>
<body>
	<!-- Content area -->
	<div class="content">
		<div class="panel panel-flat">
			<div class="panel-body">
			
				<table class="table table-hover table-bordered">
				    <thead>
				        <tr>
				            <th colspan="4">对账存疑详情</th>
				        </tr>
				    </thead>
				    <tbody>
				     	<tr>
                           <td class="text-agr" width="25%" align="right">账单日期：</td>
                           <td class="text-agl" width="25%"><fmt:formatDate value="${billBizDoubt.billDate}" pattern="yyyy-MM-dd"/></td>
                           <td class="text-agr" width="25%" align="right">支付渠道：</td>
                           <td class="text-agl" width="25%">
                           		<c:if test="${billBizDoubt.payChannel=='weixin'}">微信</c:if>
                           		<c:if test="${billBizDoubt.payChannel=='unionpay'}">银联</c:if>
                           		<c:if test="${billBizDoubt.payChannel=='alipay'}">支付宝</c:if>
                           </td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">商户应用编号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.merchantAppCode}</td>
                           <td class="text-agr" width="25%" align="right">商户应用名称：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.merchantAppName}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">商户支付订单号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.payMerchOrderNo}</td>
                           <td class="text-agr" width="25%" align="right">枫车支付订单号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.payOrderNo}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">枫车支付流水号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.payTradeNo}</td>
                           <td class="text-agr" width="25%" align="right">枫车支付订单金额：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.payTradeAmount}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">枫车支付订单下单时间：</td>
                           <td class="text-agl" width="25%"><fmt:formatDate value="${billBizDoubt.payOrderTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                           <td class="text-agr" width="25%" align="right">枫车支付成功时间：</td>
                           <td class="text-agl" width="25%"><fmt:formatDate value="${billBizDoubt.paySuccessTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">商户退款订单号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.refundMerchOrderNo}</td>
                           <td class="text-agr" width="25%" align="right">枫车退款订单号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.refundOrderNo}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">枫车退款流水号：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.refundTradeNo}</td>
                           <td class="text-agr" width="25%" align="right">枫车退款订单金额：</td>
                           <td class="text-agl" width="25%">${billBizDoubt.refundTradeAmount}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">枫车退款订单申请时间：</td>
                           <td class="text-agl" width="25%"><fmt:formatDate value="${billBizDoubt.refundApplyTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                           <td class="text-agr" width="25%" align="right">枫车退款成功时间：</td>
                           <td class="text-agl" width="25%"><fmt:formatDate value="${billBizDoubt.refundSuccessTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                       </tr>
				    </tbody>
				</table>
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->

</body>
</html>
