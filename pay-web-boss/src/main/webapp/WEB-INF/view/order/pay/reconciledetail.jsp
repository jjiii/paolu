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
	<script type="text/javascript" src="${ctx}assets/js/view/order/pay/reconciledetail.js"></script>
</head>
<body>
	<!-- Content area -->
	<div class="content">
		<div class="panel panel-flat">
			<div class="panel-body">
				<table class="table table-hover table-bordered">
				    <thead>
				        <tr>
				            <th colspan="4">支付订单基本信息</th>
				        </tr>
				    </thead>
				    <tbody>
				     	<tr>
                           <td class="text-agr" width="25%" align="right">枫车支付流水号：</td>
                           <td class="text-agl" width="25%">${data.orderNo}</td>
                           <td class="text-agr" width="25%" align="right">支付币种及金额（元）：</td>
                           <td class="text-agl" width="25%">￥&nbsp;&nbsp;${data.amount}</td>
                       </tr>
                       <tr>
                           <td class="text-agr" width="25%" align="right">商户订单号：</td>
                           <td class="text-agl" width="25%">${data.merchantOrderNo}</td>
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
                           		<c:if test="${data.status=='pay_wait'}">待支付</c:if>
                           		<c:if test="${data.status=='success'}">成功</c:if>
                           		<c:if test="${data.status=='finish'}">结束(不能退款)</c:if>
                           		<c:if test="${data.status=='close'}">关闭(超时/未收到通知)</c:if>                             
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
				
				<c:if test="${mistakes.size()>0}">
					<table class="table table-hover table-bordered">
					    <thead>
					        <tr align="center">
					            <th colspan="4">对账差错信息</th>
					        </tr>
					    </thead>
					    <tbody>
						    <c:forEach items="${mistakes}" var="item">
						    	<s:hasPermission name="order:pay:settle">
							    	<c:if test="${item.handleStatus!='handled'}">
								    	<tr>
								    		<td colspan="4" align="right">
								    			<a class="btn btn-default btn-xs" href="javascript:;" onclick="settle('${item.id}')">
								                 	确认平帐
								                 </a>
								    		</td>
								    	</tr>
							    	</c:if>
						    	</s:hasPermission>
						    	<tr>
		                           <td class="text-agr" width="25%" align="right">差错类型：</td>
		                           <td class="text-agl" width="25%">
										<c:if test="${item.mistakeType=='channel_miss'}">渠道漏单</c:if>
		                           		<c:if test="${item.mistakeType=='local_miss'}">本地漏单</c:if>
		                           		<c:if test="${item.mistakeType=='local_status_less'}">本地短款，状态不符</c:if>
		                           		<c:if test="${item.mistakeType=='local_status_more'}">本地长款,状态不符</c:if>
		                           		<c:if test="${item.mistakeType=='local_cash_less'}">本地短款，金额不符</c:if>
		                           		<c:if test="${item.mistakeType=='local_cash_more'}">本地长款,金额不符</c:if>			                           
		                           </td>
		                           <td class="text-agr" width="25%" align="right">差错处理状态：</td>
		                           <td class="text-agl" width="25%">
										<c:if test="${item.handleStatus=='handled'}">已处理</c:if>
		                           		<c:if test="${item.handleStatus=='nohandle'}">未处理</c:if>		                           
		                           </td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">差错处理方式：</td>
		                           <td class="text-agl" width="25%">
		                           		<c:if test="${item.handleWay=='local'}">以本地系统为准</c:if>
		                           		<c:if test="${item.handleWay=='channel'}">以支付渠道为准</c:if>			                           
		                           </td>
		                           <td class="text-agr" width="25%" align="right">差错处理人员：</td>
		                           <td class="text-agl" width="25%">${item.handleUser}</td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">差错处理时间：</td>
		                           <td class="text-agl" width="25%"><fmt:formatDate value="${item.handleTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                           <td class="text-agr" width="25%" align="right">商户应通知状态：</td>
		                           <td class="text-agl" width="25%">
		                           		<c:if test="${item.notifyStatus=='wait_send'}">待发送</c:if>
		                           		<c:if test="${item.notifyStatus=='send_fail'}">发送失败</c:if>
		                           		<c:if test="${item.notifyStatus=='send_success'}">发送成功</c:if>
		                           		<c:if test="${item.notifyStatus=='confirm'}">已确认处理</c:if>			                           
		                           </td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">差错处理备注：</td>
		                           <td class="text-agl" width="25%" colspan="3">${item.handleRemark}</td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付订单号：</td>
		                           <td class="text-agl" width="25%">${item.payOrderNo}</td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付订单号：</td>
		                           <td class="text-agl" width="25%">${item.channelOrderNo}</td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付流水号：</td>
		                           <td class="text-agl" width="25%">${item.payTradeNo}</td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付流水号：</td>
		                           <td class="text-agl" width="25%">${item.channelTradeNo}</td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付订单状态：</td>
		                           <td class="text-agl" width="25%">
		                           		<c:if test="${item.payTradeStatus=='pay_wait'}">待支付</c:if>
		                           		<c:if test="${item.payTradeStatus=='success'}">成功</c:if>
		                           		<c:if test="${item.payTradeStatus=='finish'}">结束(不能退款)</c:if>
		                           		<c:if test="${item.payTradeStatus=='close'}">关闭(超时/未收到通知)</c:if>		                           
		                           </td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付订单状态：</td>
		                           <td class="text-agl" width="25%">
		                           		<c:if test="${item.channelTradeStatus=='pay_wait'}">待支付</c:if>
		                           		<c:if test="${item.channelTradeStatus=='success'}">成功</c:if>
		                           		<c:if test="${item.channelTradeStatus=='finish'}">结束(不能退款)</c:if>
		                           		<c:if test="${item.channelTradeStatus=='close'}">关闭(超时/未收到通知)</c:if>		                           
		                           </td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付订单金额：</td>
		                           <td class="text-agl" width="25%">${item.payTradeAmount}</td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付订单金额：</td>
		                           <td class="text-agl" width="25%">${item.channelTradeAmount}</td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付订单生成时间：</td>
		                           <td class="text-agl" width="25%"><fmt:formatDate value="${item.payOrderTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付时间：</td>
		                           <td class="text-agl" width="25%"><fmt:formatDate value="${item.channelTradeOrderTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                       </tr>
		                       <tr>
		                           <td class="text-agr" width="25%" align="right">枫车支付成功时间：</td>
		                           <td class="text-agl" width="25%"><fmt:formatDate value="${item.paySuccessTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                           <td class="text-agr" width="25%" align="right">第三方支付支付成功时间：</td>
		                           <td class="text-agl" width="25%"><fmt:formatDate value="${item.channelTradeSuccessTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
		                       </tr>
		                       <tr>
		                           <td colspan="4"></td>
		                       </tr>
						    </c:forEach>
					    </tbody>
					</table>
				</c:if>
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
