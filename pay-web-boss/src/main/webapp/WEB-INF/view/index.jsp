<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="inc/common/meta.jsp" %>
	<title>枫车支付系统</title>

	<%@ include file="inc/common/global.jsp" %>

	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}/assets/js/plugins/ui/nicescroll.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/layout_fixed_custom.js"></script>
	<!-- /theme JS files -->
	
	<!-- Coustomer JS files -->
	<script type="text/javascript" src="${ctx}/assets/js/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/index.js"></script>

	<!-- /Coustomer JS files -->
	<script type="text/javascript"> 
			
		/*$(function(){
			var reurl = '${reurl}';
			if(reurl){
				var uri = reurl.substr(reurl.lastIndexOf('/')+1,reurl.length);
				if(uri){
					var clickli = getQueryString('clickli',uri);
					if(clickli){
						clickli = $('#'+clickli);
						clickli.addClass('active')
						// 一级菜单的LI标签
						menuLi = clickli.parents('li');
						// 二级菜单的UL标签
						secondMenuUl = clickli.parents('ul');
						secondMenuUl.show();
						menuLi.addClass('active');
						loadpage(reurl,clickli);
					}
				}
				// 清除session
				$.get('${ctx}/clearhistoryurl');
			}
		});*/
		
	</script>

</head>

<body class="navbar-top">

	<!-- Main navbar -->
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-header">
			<a class="navbar-brand" href="${ctx}">枫车支付系统<!-- <img src="assets/images/logo_light.png" alt=""> --></a>

			<ul class="nav navbar-nav pull-right visible-xs-block">
				<li><a data-toggle="collapse" data-target="#navbar-mobile"><i class="icon-tree5"></i></a></li>
				<li><a class="sidebar-mobile-main-toggle"><i class="icon-paragraph-justify3"></i></a></li>
			</ul>
		</div>

		<div class="navbar-collapse collapse" id="navbar-mobile">
			<ul class="nav navbar-nav">
				<li><a class="sidebar-control sidebar-main-toggle hidden-xs"><i class="icon-paragraph-justify3"></i></a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown dropdown-user">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<!-- <img src="assets/images/image.png" alt=""> -->
						<span>${BossOperator.realName}</span>
						<i class="caret"></i>
					</a>

					<ul class="dropdown-menu dropdown-menu-right">
						<li><a href="javascript:;" onclick="userInfo(${BossOperator.id})"><i class="icon-user-plus"></i> 我的资料</a></li>
						<li class="divider"></li>
						<li><a href="javascript:;" onclick="userSetting(${BossOperator.id})"><i class="icon-cog5"></i> 帐号设置</a></li>
						<li><a href="${ctx}/logout"><i class="icon-switch2"></i> 退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- /main navbar -->


	<!-- Page container -->
	<div class="page-container">

		<!-- Page content -->
		<div class="page-content">

			<!-- Main sidebar -->
			<div class="sidebar sidebar-main sidebar-fixed">
				<div class="sidebar-content">

					<!-- User menu -->
					<div class="sidebar-user">
						<div class="category-content">
							<div class="media">
								<a href="#" class="media-left"><img src="assets/images/image.png" class="img-circle img-sm" alt=""></a>
								<div class="media-body" style="vertical-align:middle">
									<span class="media-heading text-semibold">${BossOperator.realName}</span>
								</div>

								<div class="media-right media-middle">
									<ul class="icons-list">
										<li>
											<a href="javascript:;" onclick="userSetting(${BossOperator.id})"><i class="icon-cog3"></i></a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<!-- /user menu -->


					<!-- Main navigation -->
					<div id="sidebar" class="sidebar-category sidebar-category-visible">
						<div class="category-content no-padding">
							<ul class="navigation navigation-main navigation-accordion">

								<!-- Main -->
								<li class="navigation-header"><span>菜单管理</span> <i class="icon-menu" title="Main pages"></i></li>
								<li><a href="${ctx}"><i class="icon-home4"></i> <span>系统首页</span></a></li>
								
								<c:forEach items="${menus}" var="menu">
									<li>
										<c:if test="${menu.parentId eq 0}">
											<a href="javascript:;"><i class="${menu.icon}"></i> <span>${menu.name}</span></a>
										</c:if>
										<ul>
											<c:forEach items="${menus}" var="secondMenu">
												<c:if test="${secondMenu.parentId eq menu.id}">
													<li id="c${secondMenu.id}"><a href="javascript:;" onclick="loadpage('${secondMenu.url}','c${secondMenu.id}')">${secondMenu.name}</a></li>
												</c:if>
											</c:forEach>
										</ul>
									</li>
								</c:forEach>
								<!-- /main -->

							</ul>
						</div>
					</div>
					<!-- /main navigation -->

				</div>
			</div>
			<!-- /main sidebar -->
			<!-- Main content -->
			<div class="content-wrapper wrapper-height" style="height:100%;">
				<div style="_position:relative;width:100%;height:100%;">
					<iframe src="${ctx}/main" id="iframepage" name="iframepage" class="main-iframe" frameborder="0" scrolling="yes" marginheight="0" marginwidth="0"></iframe>
				</div>
				<!-- Footer -->
				<%@ include file="inc/common/footer.jsp" %>
				<!-- /footer -->
			</div>
			<!-- /Main content -->
	</div>
	<!-- /page container -->
<!-- remote modal -->
<%@ include file="inc/common/modal.jsp" %>
<!-- /remote modal -->
</body>
</html>
