<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/baselist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/permission/list.js"></script>
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
					<span id="nav-second-menu">权限管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 权限管理</a>
				</li>
				<li>
					<a href=""> 权限管理</a>
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
				<h5 class="panel-title">权限列表</h5>
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
							<label>权限名称:</label>
							<input type="text" name="permissionName" class="form-control" placeholder="权限名称">
						</div>
						<div class="form-group">
							<label>权限标识:</label>
							<input type="text" name="permission" class="form-control" placeholder="权限标识">
						</div>
						<div class="form-group">
							<label>所属菜单:</label>
							<input type="hidden" id="menuId" name="menuId" />
							<select id="menus" class="form-control">
								<option value="">请选择</option>
								<c:forEach items="${menus}" var="menu">
									<c:if test="${menu.parentId==0}">
										<option data-id="${menu.id}" value="${menu.id}">${menu.name}</option>
									</c:if>
									<c:forEach items="${menus}" var="secondMenu">
										<c:if test="${secondMenu.parentId eq menu.id}">
											<option data-pid="${menu.id}" value="${secondMenu.id}">&nbsp;&nbsp;&nbsp;&nbsp;|--${secondMenu.name}</option>
										</c:if>
									</c:forEach>
								</c:forEach>
							</select>
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
						<s:hasPermission name="pms:permission:add">
							<button type="button" class="btn btn-default" onclick="addPermissionUI()"><i class=" icon-add-to-list position-left"></i>添加权限</button>
						</s:hasPermission>
					</div>
				
					<table class="table table-hover" id="table">
					    <thead>
					        <tr>
					            <th>序号</th>
					            <th>权限名称</th>
					            <th>权限标识</th>
					            <th>描述</th>
					            <th>创建时间</th>
					            <th>创建人</th>
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
		<tr><td colspan="7" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{i+1}}</td>
		    <td>{{d.data[i].permissionName}}</td>
		    <td>{{d.data[i].permission}}</td>
		    <td>{{d.data[i].remark}}</td>
		    <td>{{d.data[i].createTime}}</td>
		    <td>{{d.data[i].creater}}</td>
		    <td>
				<s:hasPermission name="pms:permission:edit">
		        <button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="editPermissionUI('{{d.data[i].id}}')">
		        	<i class="icon-pencil5 position-left"></i>
		        	修改
		        </button>
				</s:hasPermission>
				<s:hasPermission name="pms:permission:delete">
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
