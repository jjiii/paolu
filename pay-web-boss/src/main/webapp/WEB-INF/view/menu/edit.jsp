<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/menu/edit.js"></script>
</head>

<body>
<!-- Simple panel -->
<div class="panel panel-flat clear-mg-bottom">

	<div class="panel-body">
		<form  id="data-form" class="form-horizontal" data-method="editMenu">
			<div class="form-group">
				<label class="col-lg-2 control-label text-right">上级菜单:</label>
				<div class="col-lg-10">
					<input type="hidden" name="parentId" value="${menu.parentId}">
					<input type="text" class="form-control" placeholder="上级菜单" readonly="readonly" value="${parentName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>菜单名称:</label>
				<div class="col-lg-10">
					<input type="hidden" name="id" value="${menu.id}">
					<input type="text" name="name" class="form-control" placeholder="菜单名称" value="${menu.name}" required maxlength="10">
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label text-right">菜单序号:</label>
				<div class="col-lg-10">
					<input type="text" name="number" class="form-control" placeholder="菜单序号" value="${menu.number}" maxlength="6">
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label text-right">菜单URL:</label>
				<div class="col-lg-10">
					<input type="text" name="url" class="form-control" placeholder="菜单URL" value="${menu.url}" maxlength="100">
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label text-right">是否显示:</label>
				<div class="col-lg-10">
					<label class="radio-inline">
						<input type="radio" class="styled" name="status" <c:if test="${menu.status=='ACTIVE'}">checked="checked"</c:if> value="ACTIVE">
						显示
					</label>

					<label class="radio-inline">
						<input type="radio" class="styled" name="status" <c:if test="${menu.status=='UNACTIVE'}">checked="checked"</c:if> value="UNACTIVE">
						隐藏
					</label>
				</div>
			</div>			
			<div class="form-group">
				<label class="col-lg-2 control-label text-right">菜单图标:</label>
				<div class="col-lg-10">
					<input type="text" name="icon" class="form-control" placeholder="菜单图标" value="${menu.icon}" maxlength="50">
				</div>
			</div>
			<div class="text-right">
				<button type="submit" class="btn btn-primary">保存<i class="icon-floppy-disk position-right"></i></button>
				<button type="button" class="btn btn-default"  onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
			</div>			
		</form>
	</div>
</div>
<!-- /simple panel -->
</body>
</html>
