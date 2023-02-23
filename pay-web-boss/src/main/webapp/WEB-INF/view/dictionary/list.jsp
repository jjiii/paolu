<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>数据字典管理</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/baselist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/dictionary/list.js"></script>
</head>

<body>

	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">权限管理</span> - 
					<span id="nav-second-menu">数据字典管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}/main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 权限管理</a>
				</li>
				<li>
					<a href=""> 数据字典管理</a>
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
				<h5 class="panel-title">数据字典列表</h5>
				<div class="heading-elements">
					<ul class="icons-list">
                		<li><a data-action="collapse"></a></li>
                		<li><a data-action="reload" onclick="location.reload();"></a></li>
                	</ul>
               	</div>
			</div>
		
			<div class="panel-body">
				<form id="search-form" class="form-inline">
					<div id="searchDiv" class="well well-sm" style="margin-bottom:10px;">
						<div class="form-group">
							<label>字典名称:</label>
							<input type="text" name="name" class="form-control" placeholder="字典名称">
						</div>
						<div class="form-group">
							<label>字典编码:</label>
							<input type="text" name="code" class="form-control" placeholder="字典编码">
						</div>
						<div class="form-group">
							<label>所属字典:</label>
							<select name="parent" class="form-control">
								<option value="">请选择</option>
								<c:forEach items="${dictionarys}" var="dict">
									<option value="${dict.id}">${dict.name}</option>
								</c:forEach>
							</select>
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
						<s:hasPermission name="pms:dictionary:add">
							<button type="button" class="btn btn-default" onclick="addDict()"><i class=" icon-add-to-list position-left"></i>添加字典</button>
						</s:hasPermission>
					</div>
				
					<table class="table table-hover table-bordered" id="table">
					    <thead>
					        <tr>
					            <th>字典名称</th>
					            <th>字典编号</th>
					            <th>序号</th>
					            <th>描述</th>
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
		<tr><td colspan="5" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{d.data[i].name}}</td>
		    <td>{{d.data[i].code}}</td>
		    <td>{{d.data[i].orderby}}</td>
		    <td>{{d.data[i].description}}</td>
		    <td>
				<s:hasPermission name="pms:dictionary:edit">
		        <button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="editDict('{{d.data[i].id}}')">
		        	<i class="icon-pencil5 position-left"></i>
		        	修改
		        </button>
				</s:hasPermission>
				<s:hasPermission name="pms:dictionary:edit">
		        <button type="button" class="btn btn-default btn-xs del" href="javascript:;" data-id="{{d.data[i].id}}">
		        	<i class="icon-bin position-left"></i>
		        	删除
		        </button>
				</s:hasPermission>
		    </td>
		</tr>
	{{# } }}
	</script>
</body>
</html>
