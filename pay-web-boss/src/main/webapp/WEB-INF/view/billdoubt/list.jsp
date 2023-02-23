<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>对账存疑管理</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<script src="${ctx}assets/js/view/common/baselist.js"></script>
	<script src="${ctx}assets/js/view/billdoubt/list.js"></script>
</head>

<body>

	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">对账管理</span> - 
					<span id="nav-second-menu">对账存疑管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}/main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 对账管理</a>
				</li>
				<li>
					<a href=""> 对账存疑管理</a>
				</li>

			</ul>
		</div>
		
	</div>
	<!-- /page header -->
	
	<!-- Content area -->
	<div class="content">
	
		<!-- Simple panel -->
		<div class="panel panel-flat">
			<div class="panel-heading">
				<h5 class="panel-title">对账存疑列表</h5>
				<div class="heading-elements">
					<ul class="icons-list">
                		<li><a data-action="collapse"></a></li>
                		<li><a data-action="reload" onclick="location.reload();"></a></li>
                		<!-- <li><a data-action="close"></a></li> -->
                	</ul>
               	</div>
			</div>
		
			<div class="panel-body">
				<form id="search-form" class="form-inline">
					<div id="searchDiv" class="well well-sm" style="margin-bottom:10px;">
					
						<div class="form-group">
							<select name="orderNoKey" class="form-control">
								<option value="payOrderNo">支付订单号</option>
								<option value=refundOrderNo>退款订单号</option>
							</select>
							<input type="text" class="form-control" name="orderNo" />
						</div>					
						<div class="form-group">
							<label>账单日期:</label>
							<input type="text" name="startDate" class="form-control laydate-icon" id="start" />
							<p class="form-control-static">至</p>
							<input type="text" name="endDate" class="form-control laydate-icon" id="end" />
						</div>
						<!-- <div class="form-group">
							<label>批次号:</label>
							<input type="text" name="batchNo" class="form-control" />
						</div> -->
						<div class="form-group">
							<label>对账类型:</label>
							<select name="billType" class="form-control">
								<option value="">全部</option>
								<option value="pay">支付对账</option>
								<option value="refund">退款对账</option>
							</select>
						</div>
						<div class="form-group">
							<label>支付渠道:</label>
							<select name="payChannel" class="form-control">
								<option value="">全部</option>
								<option value="weixin">微信</option>
								<option value="alipay">支付宝</option>
								<option value="unionpay">银联</option>
							</select>
						</div>
						<%-- <div class="form-group">
							<label>商户应用:</label>
							<select name="merchantAppCode" class="form-control">
								<option value="">全部</option>
								<c:forEach items="${merchantApps}" var="merchantApp">
									<option value="${merchantApp.merchantAppCode}">${merchantApp.merchantAppName}</option>
								</c:forEach>
							</select>
						</div> --%>
						<!-- <div class="form-group">
							<label>商户支付订单号:</label>
							<input type="text" name="payMerchOrderNo" class="form-control" />
						</div>
						<div class="form-group">
							<label>商户退款订单号:</label>
							<input type="text" name="refundMerchOrderNo" class="form-control" />
						</div> 
						<div class="form-group">
							<label>枫车支付订单号:</label>
							<input type="text" name="payOrderNo" class="form-control" />
						</div>
						<div class="form-group">
							<label>枫车退款订单号:</label>
							<input type="text" name="refundOrderNo" class="form-control" />
						</div> -->
						
						<!-- <div class="form-group">
							<label>枫车支付流水号:</label>
							<input type="text" name="payTradeNo" class="form-control" />
						</div>
						<div class="form-group">
							<label>枫车退款流水号:</label>
							<input type="text" name="refundTradeNo" class="form-control" />
						</div> -->
						<button type="button" id="search" class="btn btn-default">搜索</button>
					</div>
				
					<div style="overflow:auto;">
						<table class="table table-hover table-bordered" id="table">
						    <thead>
						        <tr>
						            <th>账单日期</th>
						            <th>支付渠道</th>
						            <th>对账类型</th>
						            <!-- <th>商户应用编号</th>
						            <th>商户应用名称</th> 
						            <th>商户支付订单号</th>-->
						            <th>支付订单号</th>
						            <!-- <th>枫车支付流水号</th> -->
						            <th>支付订单金额</th>
						            <!-- <th>枫车支付订单下单时间</th>
						            <th>枫车支付成功时间</th>
						            <th>商户退款订单号</th> -->
						            <th>退款订单号</th>
						            <!-- <th>枫车退款流水号</th> -->
						            <th>退款订单金额</th>
						            <th>操作</th>
						            <!-- <th>枫车退款订单申请时间</th>
						            <th>枫车退款成功时间</th> -->
						        </tr>
						    </thead>
						     <tbody id="data-body">
						     </tbody>		
						</table>
					</div>
					<div class="pagebar-container">
						<span class="hidden">共 <i id="pages"></i> 页， <i id="total"></i> 条记录</span>
						<div id="pagebar" class="text-right"></div>
					</div>
				</form>
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->

<script id="tabledata" type="text/html">
	{{# if(d.data.length==0){ }}
		<tr><td colspan="8" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>
				{{d.data[i].billDate}}
			</td>
		    <td>
				{{# if(d.data[i].payChannel=='weixin'){ }}
					微信
				{{# }else if(d.data[i].payChannel=='unionpay'){ }}
					银联
				{{# }else if(d.data[i].payChannel=='alipay'){ }}
					支付宝
				{{# } }}
			</td>
		    <td>
				{{# if(d.data[i].billType=='pay'){ }}
					支付对账
				{{# }else if(d.data[i].billType=='refund'){ }}
					退款对账
				{{# } }}
			</td>

		    <td>{{=d.data[i].payOrderNo||''}}</td>
		    <td>{{=formatNumber(d.data[i].payTradeAmount||'0',2)}}</td>

		    <td>{{=d.data[i].refundOrderNo||''}}</td>
		    <td>{{=formatNumber(d.data[i].refundTradeAmount||'0',2)}}</td>
		    <td>
				<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="detail({{d.data[i].id}})">
					<i class="icon-stack-text position-left"></i>
					查看详情
				</button>
			</td>
		</tr>
	{{# } }}
</script>

</body>
</html>
