<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}/assets/js/view/role/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="editRole">
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>角色名称:</label>
					<div class="col-lg-10">
						<input type="hidden" name="id" value="${role.id}">
						<input type="text" name="roleName" class="form-control" placeholder="角色名称" value="${role.roleName}" required maxlength="20">
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>角色编码:</label>
					<div class="col-lg-10">
						<input type="text" name="roleCode" class="form-control" placeholder="角色编码" value="${role.roleCode}" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right">角色描述:</label>
					<div class="col-lg-10">
						<textarea rows="3" name="remark" class="form-control" placeholder="角色描述">${role.remark}</textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-lg-10">
						 <fieldset>
						    <legend>关联的用户</legend>
							 <ol class="list-inline">
							 	<c:forEach items="${operators}" var="operator" varStatus="status">
								 	<li>${status.count}.${operator.realName}</li>
							 	</c:forEach>
							 </ol>
						  </fieldset>
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
