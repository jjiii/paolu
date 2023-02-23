<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>修改个人信息</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/operator/edit.js"></script>

</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="editSelf">
				<div class="form-group">
					<label class="col-xs-3 control-label text-right">账号:</label>
					<div class="col-xs-7">
						<input type="hidden" name="id" value="${operator.id}">
						<p class="form-control-static">${operator.loginName}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label text-right">姓名:</label>
					<div class="col-xs-7">
						<p class="form-control-static">${operator.realName}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label text-right">密码:</label>
					<div class="col-xs-7">
						<input type="password" name="loginPwd" class="form-control" placeholder="密码" maxlength="20">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label text-right"><span class="text-warning"> * </span>手机号:</label>
					<div class="col-xs-7">
						<input type="text" name="mobileNo" class="form-control" placeholder="手机号" value="${operator.mobileNo}" required maxlength="11">
					</div>
				</div>			

				<div class="text-right">
					<button type="submit" class="btn btn-primary">更新<i class="icon-floppy-disk position-right"></i></button>
					<button type="button" class="btn btn-default" onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
				</div>			
			</form>
		</div>
	</div>
	<!-- /simple panel -->
</body>
</html>
