<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>商户应用管理</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<script src="${ctx}assets/js/view/common/baselist.js"></script>
	<script src="${ctx}/assets/js/view/merchant/list.js"></script>
</head>

<body>

	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">商户管理</span> - 
					<span id="nav-second-menu">商户应用管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}/main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 商户管理</a>
				</li>
				<li>
					<a href=""> 商户应用管理</a>
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
				<h5 class="panel-title">商户应用列表</h5>
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
					<div id="searchDiv" class="well well-sm text-right mg-bottom10">
						<s:hasPermission name="mct:mrechant:add">
							<button type="button" class="btn btn-default text-right" onclick="addMerchant()">新增商户</button>
						</s:hasPermission>
					</div>
				
					<table class="table table-hover table-bordered" id="table">
					    <thead>
					        <tr>
					            <th>序号</th>
					            <th>商户号</th>
					            <th>商户名称</th>
					            <th>创建时间</th>
					            <th>备注</th>
					            <th>状态</th>
					            <th>操作</th>
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
		    <td>{{i+1}}</td>
		    <td>{{d.data[i].merchantCode}}</td>
		    <td>{{d.data[i].merchantName}}</td>
		    <td>{{d.data[i].createTime}}</td>
		    <td>{{d.data[i].remark}}</td>
		    <td>
				{{# if(d.data[i].status==1){ }}开启中{{# }else{ }}关闭中{{# } }}
			</td>
		    <td>
				<s:hasPermission name="mct:mrechant:view">
		        <button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="editMerchant('{{d.data[i].id}}')">
		        	查看
		        </button>
				</s:hasPermission>
				<s:hasPermission name="mct:mrechant:close">
		        <button type="button" class="btn btn-default btn-xs" data-status='{{d.data[i].status}}' href="javascript:;" onclick="changeStatus({{d.data[i].id}})">
		        	{{# if(d.data[i].status==1){ }}
						关闭
		        	{{# }else{ }}
						开启
		        	{{# } }}
		        </button>
				</s:hasPermission>
		    </td>
		</tr>
	{{# } }}
</script>

</body>
</html>
