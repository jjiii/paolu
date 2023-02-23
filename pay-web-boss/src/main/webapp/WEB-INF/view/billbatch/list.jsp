<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>对账批次管理</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<script src="${ctx}assets/js/view/common/baselist.js"></script>
	<script src="${ctx}assets/js/view/billbatch/list.js"></script>
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
					<span id="nav-second-menu">对账批次管理</span>
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
					<a href=""> 对账批次管理</a>
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
				<h5 class="panel-title">对账批次列表</h5>
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
							<label>账单日期:</label>
							<input type="text" name="startDate" class="form-control laydate-icon" id="start" />
							<p class="form-control-static">至</p>
							<input type="text" name="endDate" class="form-control laydate-icon" id="end" />
						</div>
						<div class="form-group">
							<label>第三方支付:</label>
							<select name="payChannel" class="form-control">
								<option value="">全部</option>
								<option value="alipay">支付宝</option>
								<option value="weixin">微信</option>
								<option value="unionpay">银联</option>
							</select>
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
					</div>
				
					<table class="table table-hover table-bordered" id="table">
					    <thead>
					        <tr>
					            <th>对账批次</th>
					            <th>账单日期</th>
					            <th>对账结束时间</th>
					            <th>第三方支付及ID</th>
					            <th class="th-width6">支付订单笔数(枫车/第三方支付)</th>
					            <th class="th-width6">支付订单金额(枫车/第三方支付)</th>
					            <th class="th-width6">退款订单笔数(枫车/第三方支付)</th>
					            <th class="th-width6">退款订单金额(枫车/第三方支付)</th>
					            <th class="th-width5">差数总数</th>
					            <th class="th-width5">差错待处理数</th>
					            <th>操作</th>
					        </tr>
					    </thead>
					     <tbody id="data-body">
					     </tbody>		
					</table>
					<div id="pagebar" class="text-right"></div>
				</form>
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->

<script id="tabledata" type="text/html">
	{{# if(d.data.length==0){ }}
		<tr><td colspan="11" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{d.data[i].batchNo}}</td>
		    <td>{{=d.data[i].billDate||''}}</td>
		    <td>{{=d.data[i].endTime||''}}</td>
		    <td>
				{{# if(d.data[i].payChannel=='weixin'){ }}
					微信
				{{# }else if(d.data[i].payChannel=='unionpay'){ }}
					银联
				{{# }else if(d.data[i].payChannel=='alipay'){ }}
					支付宝
				{{# } }}
				/ {{d.data[i].channelAppId||''}}
			</td>
		    <td>{{d.data[i].tradeCount}}/{{d.data[i].channelTradeCount}}</td>
		    <td>{{d.data[i].tradeAmount}}/{{d.data[i].channelTradeAmount}}</td>
		    <td>{{d.data[i].refundCount}}/{{d.data[i].channelRefundCount}}</td>
		    <td>{{d.data[i].refundAmount}}/{{d.data[i].channelRefundAmount}}</td>
		    <td>{{d.data[i].mistakeCount}}</td>
		    <td>{{d.data[i].mistakeUnhandleCount}}</td>
		    <td class="td-btns">
				<s:hasPermission name="order:pay:list">
				<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="viewBillItem('{{d.data[i].batchNo}}','pay')">
					<i class="icon-stack-text position-left"></i>
					查看支付订单
				</button>
				</s:hasPermission>
				<s:hasPermission name="order:refund:list">	            
				<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="viewBillItem('{{d.data[i].batchNo}}','refund')">
					<i class="icon-stack-text position-left"></i>
					查看退款订单
				</button>
				</s:hasPermission>
				<s:hasPermission name="bill:batch:recheck">
				{{# if(d.data[i].handleStatus=='fail'){ }}
					<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="recheckBatch('{{d.data[i].batchNo}}')">
						<i class="icon-redo position-left"></i>
						重新启动
					</button>
				{{# } }}
				</s:hasPermission>
		    </td>
		</tr>
	{{# } }}
</script>

</body>
</html>
