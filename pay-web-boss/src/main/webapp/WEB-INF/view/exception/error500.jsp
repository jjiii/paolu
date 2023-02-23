<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>

	<%@ include file="../inc/common/global.jsp" %>

</head>
<body>

	<!-- Error wrapper -->
	<div class="container-fluid text-center">
		<h1 class="error-title">500</h1>
		<h3 class="text-semibold content-group text-warning">发生错误：${errmap['errmsg']}</h3>
	
		<div class="row">
			<div class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3">
				<form action="#" class="main-search">
	
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-4">
							<a href="javascript:;" class="btn btn-primary btn-block content-group" onclick="parent.layer.closeAll()||history.back()"><i class="icon-circle-left2 position-left"></i> 返回</a>
						</div>
						<div class="col-sm-4"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- /error wrapper -->
</body>
</html>