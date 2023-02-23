<div class="sidebar sidebar-main sidebar-fixed">
	<div class="sidebar-content">

		<!-- User menu -->
		<div class="sidebar-user">
			<div class="category-content">
				<div class="media">
					<a href="#" class="media-left"><img src="${ctx}/assets/images/image.png" class="img-circle img-sm" alt=""></a>
					<div class="media-body" style="vertical-align:middle">
						<span class="media-heading text-semibold">${BossOperator.realName}</span>
					</div>

					<div class="media-right media-middle">
						<ul class="icons-list">
							<li>
								<a href="#"><i class="icon-cog3"></i></a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- /user menu -->


		<!-- Main navigation -->
		<div class="sidebar-category sidebar-category-visible">
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
										<%-- <li><a href="${ctx}${secondMenu.url}" onclick="loadpage('${ctx}${secondMenu.url}','${menu.name}','${secondMenu.name}')">${secondMenu.name}</a></li> --%>
										<li><a href="${ctx}${secondMenu.url}">${secondMenu.name}</a></li>
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