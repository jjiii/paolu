<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<s:notAuthenticated>
	<script>
		var url = parent.location.href;
		if(url.search('/login')<=0){
			parent.location.href="${ctx}login"
		}
	</script>
</s:notAuthenticated>
<s:authenticated>
	<c:redirect url="${ctx}/"/>
</s:authenticated>
<head>
	<%@ include file="inc/common/meta.jsp" %>
	<title>用户登录</title>
	<%@ include file="inc/common/global.jsp" %>

	<!-- Core JS files -->
	<script type="text/javascript" src="assets/js/plugins/loaders/pace.min.js"></script>
	<script type="text/javascript" src="assets/js/plugins/loaders/blockui.min.js"></script>
	<!-- /core JS files -->

	<!-- Theme JS files -->
	<script type="text/javascript" src="assets/js/plugins/forms/styling/uniform.min.js"></script>

	<script type="text/javascript" src="assets/js/core/app.js"></script>
	<script type="text/javascript" src="assets/js/pages/login.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript">
		$(function(){
			$("#login_form").validate({
				messages: {
				  	username: "用户名或密码不能为空",
				  	password: "用户名或密码不能为空"
				},groups:{
					username:"username password"
				},
				errorPlacement:function(error,element) {
					$('#msg').empty();
					error.insertAfter("#msg");
				}
			});
		});
	</script>
</head>

<body class="bg-slate-800">

	<!-- Page container -->
	<div class="page-container login-container">

		<!-- Page content -->
		<div class="page-content">

			<!-- Main content -->
			<div class="content-wrapper">

				<!-- Content area -->
				<div class="content">

					<!-- Advanced login -->
					<form id="login_form" action="login" method="post">
						<div class="panel panel-body login-form">
							<div class="text-center">
								<div class="icon-object border-warning-400 text-warning-400"><i class="icon-people"></i></div>
								<h5 class="content-group-lg">用户登录 <small class="display-block">输入您的用户名和密码</small></h5>
							</div>

							<div class="form-group has-feedback has-feedback-left">
								<input type="text" name="username" class="form-control" placeholder="用户名" autocomplete="off" required />
								<div class="form-control-feedback">
									<i class="icon-user text-muted"></i>
								</div>
							</div>

							<div class="form-group has-feedback has-feedback-left">
								<input type="password" name="password" class="form-control" placeholder="密码" autocomplete="off" required />
								<div class="form-control-feedback">
									<i class="icon-lock2 text-muted"></i>
								</div>
							</div>
							
							<div class="form-group login-options">
								<div class="row">
									<div class="col-sm-12"><span id="msg" class="text-danger">${msg}</span></div>
									<input type="hidden" name="rememberMe" value="false"/>
									<!-- <div class="col-sm-6">
										<label class="checkbox-inline">
											<input type="checkbox" class="styled" checked="checked">
											记住我
										</label>
									</div> -->

									<!-- <div class="col-sm-6 text-right">
										<a href="login_password_recover.html">忘记密码?</a>
									</div> -->
								</div>
							</div>

							<div class="form-group">
								<button type="submit" class="btn bg-blue btn-block">登录<i class="icon-circle-right2 position-right"></i></button>
							</div>
						</div>
					</form>
					<!-- /advanced login -->


					<!-- Footer -->
					<div class="footer text-white" style="background: none;">
						枫车电商 版权所有  &copy; 2016-2020 <a href="#" class="text-white">枫车支付系统 </a> v1.0
					</div>
					<!-- /footer -->

				</div>
				<!-- /content area -->

			</div>
			<!-- /main content -->

		</div>
		<!-- /page content -->

	</div>
	<!-- /page container -->

</body>
</html>
