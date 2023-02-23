<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	<link href="${ctx}/assets/css/plugin/ztree/zTreeStyle.css" rel="stylesheet">
	<script src="${ctx}/assets/js/plugins/ztree/jquery.ztree.core.min.js"></script>
	<script src="${ctx}/assets/js/plugins/ztree/jquery.ztree.excheck.min.js"></script>
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}/assets/js/view/permission/edit.js"></script>
	<script>
		$(function(){
			initMenuTree();
		});
	</script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="editPermission">
				<div class="form-group">
					<label class="col-md-2 control-label text-right">
						<input type="hidden" id="menuId" name="menuId" value="${permission.menuId}"/>
						<div id="treemenu" class="ztree" style="border-right:1px solid #ddd"></div>
					</label>
					<div class="col-md-10">
						<div class="form-group">
							<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>权限名称:</label>
							<div class="col-lg-10">
								<input type="hidden" name="id" value="${permission.id}">
								<input type="text" name="permissionName" class="form-control" placeholder="权限名称" value="${permission.permissionName}" required maxlength="20">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>权限标识:</label>
							<div class="col-lg-10">
								<input type="text" name="permission" class="form-control" placeholder="权限标识" value="${permission.permission}" readonly="readonly">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label text-right">权限描述:</label>
							<div class="col-lg-10">
								<textarea rows="3" name="remark" class="form-control" placeholder="权限描述">${permission.remark}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-lg-10">
								 <fieldset>
								    <legend>关联的角色</legend>
									 <ol class="list-inline">
									 	<c:forEach items="${roles}" var="role" varStatus="status">
										 	<li>${status.count}.${role.roleName}</li>
									 	</c:forEach>
									 </ol>
								  </fieldset>
							</div>
						</div>						
					</div>
				</div>
	
				<div class="text-right">
					<button type="submit" class="btn btn-primary">保存<i class="icon-floppy-disk position-right"></i></button>
					<button type="button" class="btn btn-default" onclick="closePage()">退出<i class="icon-exit2 position-right"></i></button>
				</div>			
			</form>
		</div>
	</div>
	<!-- /simple panel -->
</body>
</html>
