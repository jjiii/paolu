<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>业务对账明细</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<script src="${ctx}assets/js/view/common/baselist.js"></script>
	<script src="${ctx}assets/js/view/billitem/list.js"></script>
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
					<span id="nav-second-menu">业务对账明细</span>
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
					<a href="javascript:history.back();"> 对账批次管理</a>
				</li>
				<li>
					<a href=""> 业务对账明细</a>
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
				<h5 class="panel-title">对账明细列表</h5>
				<div class="heading-elements">
					<ul class="icons-list">
                		<li><a data-action="collapse"></a></li>
                		<li><a data-action="reload" onclick="location.reload();"></a></li>
                	</ul>
               	</div>
			</div>
		
			<div class="panel-body">
				<form id="search-form" class="form-inline">
					<input type="hidden" id="batchNo" name="batchNo" value="${batchNo}" />
					<input type="hidden" id="billType" name="billType" value="${billType}" />
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
					            <th>支付渠道</th>
					            <th>支付流水号</th>
					            <th>支付订单号</th>
					            <th>支付时间</th>
					        </tr>
					    </thead>
					     <tbody id="data-body">
					     </tbody>		
					</table>
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
		<tr><td colspan="6" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{d.data[i].batchNo}}</td>
		    <td>{{=d.data[i].billDate||''}}</td>
		    <td>
				{{# if(d.data[i].payChannel=='weixin'){ }}
					微信
				{{# }else if(d.data[i].payChannel=='unionpay'){ }}
					银联
				{{# }else if(d.data[i].payChannel=='alipay'){ }}
					支付宝
				{{# } }}
			</td>
		    <td>{{d.data[i].payTradeNo}}</td>
		    <td>{{d.data[i].payOrderNo}}</td>
		    <td>{{d.data[i].paySuccessTime}}</td>
		</tr>
	{{# } }}
</script>

</body>
</html>
