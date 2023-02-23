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
			initMenuTree();
		});
	</script>
</head>
<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal">
				<input type="hidden" id="roleId" name="roleId" value="${roleId}">
				<div id="treemenu" class="ztree"></div>
				<div class="text-right">
					<button type="button" class="btn btn-primary" onclick="asignMenu()">分配<i class="icon-floppy-disk position-right"></i></button>
					<button type="button" class="btn btn-default" onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
				</div>			
			</form>
		</div>
	</div>
	<!-- /simple panel -->
</body>
</html>
