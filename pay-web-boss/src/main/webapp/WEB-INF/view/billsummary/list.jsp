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
	<script src="${ctx}assets/js/view/billsummary/list.js"></script>
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
					<span id="nav-second-menu">对账汇总管理</span>
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
					<a href=""> 对账汇总管理</a>
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
				<h5 class="panel-title">对账汇总列表</h5>
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
						<button type="button" id="search" class="btn btn-default">搜索</button>
					</div>
				
					<table class="table table-hover table-bordered" id="table">
					    <thead>
					        <tr>
					            <th>账单日期</th>
					            <th>对账批次总数</th>
					            <th>对几批次成功个数</th>
					            <th>商户应用总数</th>
					            <th>生成账单成功总数</th>
					            <th>下载通知成功总数</th>
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
		<tr><td colspan="7" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{=d.data[i].billDate||''}}</td>
		    <td>{{d.data[i].batchCount}}</td>
		    <td>{{d.data[i].batchRunSuccessCount}}</td>
		    <td>{{d.data[i].merchantAppCount}}</td>
		    <td>{{d.data[i].billMakeSuccessCount}}</td>
		    <td>{{d.data[i].downloadNotifySuccessCount}}</td>
		</tr>
	{{# } }}
</script>

</body>
</html>
