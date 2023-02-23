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
						<img src="${ctx}/assets/images/image.png" alt="">
						<span>${BossOperator.realName}</span>
						<i class="caret"></i>
					</a>

					<ul class="dropdown-menu dropdown-menu-right">
						<li><a href="#"><i class="icon-user-plus"></i> 我的资料</a></li>
						<li class="divider"></li>
						<li><a href="#"><i class="icon-cog5"></i> 帐号设置</a></li>
						<li><a href="${ctx}/logout"><i class="icon-switch2"></i> 退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
