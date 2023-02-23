<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/operator/edit.js"></script>
</head>
<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal">
				<div class="form-group">
					<label class="col-xs-2 control-label text-right">账号:</label>
					<div class="col-xs-6">
						<p class="form-control-static">${operator.loginName}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right">姓名:</label>
					<div class="col-xs-6">
						<p class="form-control-static">${operator.realName}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right">手机号:</label>
					<div class="col-xs-6">
						<p class="form-control-static">${operator.mobileNo}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right">描述:</label>
					<div class="col-xs-6">
						<p class="form-control-static">${operator.remark}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right">状态:</label>
					<div class="col-xs-6">
						<p class="form-control-static">
							<c:if test="${operator.status=='UNACTIVE'}">禁用</c:if>
							<c:if test="${operator.status=='ACTIVE'}">启用</c:if>
						</p>
					</div>
				</div>
				<div class="text-right">
					<button type="button" class="btn btn-default" onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
				</div>				
			</form>
		</div>
	</div>
	<!-- /simple panel -->
</body>
</html>
