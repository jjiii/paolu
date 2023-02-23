<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="inc/common/global.jsp" %>
</head>

<body>
	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">支付系统</span> - 
					<span id="nav-second-menu"> 首页</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
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
				<h5 class="panel-title">欢迎使用枫车支付系统---图片图片</h5>
			</div>
		
			<div class="panel-body">
				
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
</body>
</html>
