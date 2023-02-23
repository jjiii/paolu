<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>

	<%@ include file="../inc/common/global.jsp" %>
	
	<!-- Coustomer JS files -->
	<link rel="stylesheet" href="${ctx}assets/css/treetable/jquery.treetable.css">
	<link rel="stylesheet" href="${ctx}assets/css/treetable/jquery.treetable.theme.default.css">

	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/ui/nicescroll.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/layout_fixed_custom.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/view/common/baselist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/menu/list.js"></script>
	<script>
		$(function(){
			$.getScript('${ctx}assets/js/plugins/treetable/jquery.treetable.js',function(){
				$("#table").treetable({ expandable: true });
			});
			
			//监听加载状态改变  
			document.onreadystatechange = completeLoading;  
			var index = parent.layer.load(2, {shade: [0.1,'#fff']});  
			//加载状态为complete时移除loading效果  
			function completeLoading() {
			    if (document.readyState == 'complete') {  
			        parent.layer.close(index);
			    }  
			}  
		});
		
	</script>
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
				<span id="nav-second-menu">菜单管理</span>
			</h4>
		</div>
	</div>

	<div class="breadcrumb-line">
		<ul class="breadcrumb" id="breadcrumb">
			<li>
				<a href="${ctx}/main"><i class="icon-home2 position-left"></i> 首页</a>
			</li>
			<li>
				<a href=""> 权限管理</a>
			</li>
			<li>
				<a href=""> 菜单管理</a>
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
			<h5 class="panel-title">菜单列表</h5>
			<div class="heading-elements">
				<ul class="icons-list">
               		<li><a data-action="collapse"></a></li>
               		<li><a data-action="reload" onclick="location.reload();"></a></li>
               		<!-- <li><a data-action="close"></a></li> -->
               	</ul>
            </div>
		</div>
	
		<div class="panel-body">
			<s:hasPermission name="pms:menu:add">
				<button class="btn btn-default btn-xs" onclick="addMenuUI('${ctx}menu/addUI')"><i class=" icon-add-to-list position-left"></i>添加菜单</button>
			</s:hasPermission>
			<table class="table table-hover" id="table">
			    <thead>
			        <tr>
			            <th>名称</th>
			            <th>序号</th>
			            <th>状态</th>
			            <th>URL路径</th>
			            <th>操作</th>
			        </tr>
			    </thead>
			     <tbody id="data-body">
			        <c:forEach items="${menus}" var="menu">
			        	<c:if test="${menu.parentId==0}">
			        		<tr data-tt-id='${menu.id}'>
				                <td id='p${menu.id}'>${menu.name}</td>
				                <td>${menu.number}</td>
				                <td>
				                	<c:if test="${menu.status=='ACTIVE'}">显示</c:if>
				                	<c:if test="${menu.status=='UNACTIVE'}">隐藏</c:if>
				                </td>
				                <td>${menu.url}</td>
				                <td>
				                	<s:hasPermission name="pms:menu:add">
				                	<c:if test="${menu.parentId==0}">
				                        <a class="btn btn-default btn-xs" href="javascript:;" onclick="addMenuUI('${ctx}menu/addUI','${menu.id}','${menu.name}')">
				                        	<i class=" icon-add-to-list position-left"></i>
				                        	添加子节点
				                        </a>
				                	</c:if>
				                	</s:hasPermission>
				                	<s:hasPermission name="pms:menu:edit">
			                        <a class="btn btn-default btn-xs" href="javascript:;" onclick="editMenuUI('${menu.id}')">
			                        	<i class="icon-pencil5 position-left"></i>
			                        	修改
			                        </a>
			                        </s:hasPermission>
			                        <s:hasPermission name="pms:menu:delete">
			                        <a class="btn btn-default btn-xs del" href="javascript:;" data-id="${menu.id}">
			                        	<i class="icon-bin position-left"></i>
			                        	删除
			                        </a>
			                        </s:hasPermission>
				                </td>
			            	</tr>
			            	<c:forEach items="${menus}" var="child">
			            		<c:if test="${child.parentId==menu.id}">
			        				<tr data-tt-id='${child.id}' data-tt-parent-id='${child.parentId}'>
						                <td>${child.name}</td>
						                <td>${child.number}</td>
						                <td>
						                	<c:if test="${child.status=='ACTIVE'}">显示</c:if>
				                			<c:if test="${child.status=='UNACTIVE'}">隐藏</c:if>
										</td>
						                <td>${child.url}</td>
						                <td>
						                	<s:hasPermission name="pms:menu:edit">
					                        <a class="btn btn-default btn-xs" href="javascript:;" onclick="editMenuUI('${child.id}')">
					                        	<i class="icon-pencil5 position-left"></i>
					                        	修改
					                        </a>
					                        </s:hasPermission>
					                        <s:hasPermission name="pms:menu:delete">
					                        <a class="btn btn-default btn-xs del" href="javascript:;" data-id="${child.id}">
					                        	<i class="icon-bin position-left"></i>
					                        	删除
					                        </a>
					                        </s:hasPermission>
						                </td>
				            		</tr>			            		
			            		</c:if>
			            	</c:forEach>
			        	</c:if>
			        </c:forEach>		     	
			     </tbody>		
			</table>
		</div>
	</div>
	<!-- /simple panel -->
	
</div>
<!-- /content area -->		


</body>

</html>
