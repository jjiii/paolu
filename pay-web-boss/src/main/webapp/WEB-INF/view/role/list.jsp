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
	<script type="text/javascript" src="${ctx}assets/js/view/role/list.js"></script>
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
					<span id="nav-second-menu">角色管理</span>
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
					<a href=""> 角色管理</a>
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
				<h5 class="panel-title">角色列表</h5>
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
							<label>角色名称:</label>
							<input type="text" name="roleName" class="form-control" placeholder="角色名称">
						</div>
						<div class="form-group">
							<label>角色编码:</label>
							<input type="text" name="roleCode" class="form-control" placeholder="角色编码">
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
						<s:hasPermission name="pms:role:add">
							<button type="button" class="btn btn-default" onclick="addRoleUI()"><i class=" icon-add-to-list position-left"></i>添加角色</button>
						</s:hasPermission>
					</div>	
					<table class="table table-hover" id="table">
					    <thead>
					        <tr>
					            <th>序号</th>
					            <th>角色名称</th>
					            <th>角色编码</th>
					            <th>描述</th>
					            <th>创建人</th>
					            <th>创建时间</th>
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
</body>
<!-- data-render -->
<script id="tabledata" type="text/html">
	{{# if(d.data.length==0){ }}
		<tr><td colspan="7" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{i+1}}</td>
		    <td>{{d.data[i].roleName}}</td>
		    <td>{{d.data[i].roleCode}}</td>
		    <td>{{d.data[i].remark}}</td>
		    <td>{{d.data[i].creater}}</td>
		    <td>{{d.data[i].createTime}}</td>
		    <td>
				 <s:hasPermission name="pms:role:assignmenu">
 				 {{# if(d.data[i].roleCode!='admin'){ }}
                 <a class="btn btn-default btn-xs" href="javascript:;" onclick="assignMenuUI('{{d.data[i].id}}')">
	                  <i class="icon-pencil5 position-left"></i>
	                                                              分配菜单
	             </a>
				 {{# } }}
				 </s:hasPermission>
				 <s:hasPermission name="pms:role:assignpermission">
				 {{# if(d.data[i].roleCode!='admin'){ }}
	             <a class="btn btn-default btn-xs" href="javascript:;" onclick="assignPermissionUI('{{d.data[i].id}}')">
	                  <i class="icon-pencil5 position-left"></i>
	                                                              分配权限
	             </a>
				 {{# } }}
				 </s:hasPermission>
				 <s:hasPermission name="pms:role:edit">
	             <a class="btn btn-default btn-xs" href="javascript:;" onclick="editRoleUI('{{d.data[i].id}}')">
	                  <i class="icon-pencil5 position-left"></i>
	                                                               修改
	             </a>
				</s:hasPermission>
				<s:hasPermission name="pms:role:delete">
				{{# if(d.data[i].roleCode!='admin'){ }}
	             <a class="btn btn-default btn-xs del" href="javascript:;" data-id="{{d.data[i].id}}">
	                  <i class="icon-bin position-left"></i>
	                                                               删除
	             </a>
				{{# } }}
				</s:hasPermission>
		    </td>
		</tr>
	{{# } }}
</script>
<!-- /data-render -->
