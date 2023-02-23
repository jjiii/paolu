<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/ztree/zTreeStyle.css" rel="stylesheet">
	<script src="${ctx}/assets/js/plugins/ztree/jquery.ztree.core.min.js"></script>
	<script src="${ctx}/assets/js/plugins/ztree/jquery.ztree.excheck.min.js"></script>
	<script src="${ctx}/assets/js/view/role/asign.js"></script>
	<script>
		$(function(){
			initPermisTree();
			$('#permhtm').html($('#allpermis').html());
			changeCheck();
		});
	</script>
	<style>
		#permhtm li{ width:25%}
	</style>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal">
				<div class="form-group">
					<label class="col-lg-3 control-label text-right">
						<input type="hidden" id="roleId" name="roleId" value="${roleId}">
						<div id="treemenu" class="ztree" style="border-right:1px solid #ddd"></div>
					</label>
					<div class="col-lg-9">
						<h6 class="page-header"><div class="checkbox"><label><input id="all" type="checkbox" /></label>权限列表</div></h6>
						<ul id="permhtm" class="list-inline"><li>请先选择左边菜单</li></ul>
					</div>
				</div>
				
				<div class="text-right">
					<button type="button" class="btn btn-primary" onclick="asignPermission()">分配<i class="icon-floppy-disk position-right"></i></button>
					<button type="button" class="btn btn-default" onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
				</div>			
			</form>
		</div>
	</div>
	<!-- /simple panel -->
	
	<div class="hidden" id="allpermis">
		<c:forEach items="${bossPermissions}" var="permission" >
			<li>
				<div class="checkbox">
					<label>
						<c:if test="${permission.ischecked}">
							<input type="hidden" name="permisIds" value="${permission.id}"/>
						</c:if>
						<input type="checkbox" name="permissionId" value="${permission.id}" <c:if test="${permission.ischecked}">checked="checked"</c:if> />${permission.permissionName}
					</label>
				</div>
			</li>
		</c:forEach>
	</div>
</body>
</html>
