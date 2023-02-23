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
			<form id="data-form" class="form-horizontal" data-method="addPermission">
				<div class="form-group">
					<label class="col-md-3 control-label text-right">
						<input type="hidden" id="menuId" name="menuId"/>
						<div id="treemenu" class="ztree" style="border-right:1px solid #ddd"></div>
					</label>
					<div class="col-md-9">
						<div class="form-group">
							<label class="col-md-2 control-label text-right"><span class="text-warning"> * </span>权限名称:</label>
							<div class="col-md-10">
								<input type="text" name="permissionName" class="form-control" placeholder="权限名称" required maxlength="20">
								<p class="help-block">建议格式：父菜单名称- 子菜单名称-功能，如：权限管理-菜单管理-新增</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label text-right"><span class="text-warning"> * </span>权限标识:</label>
							<div class="col-md-10">
								<input type="text" name="permission" class="form-control" placeholder="权限标识" required maxlength="40">
								<p class="help-block">建议格式：模块缩写-controller路径-方法路径，如：pms:menu:add，该字段定义后不可修改</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label text-right">权限描述:</label>
							<div class="col-md-10">
								<textarea rows="3" name="remark" class="form-control" placeholder="权限描述"></textarea>
								<p class="help-block">注意：添加权限时，请先从左边选择一个<code>子菜单</code>，以确定该权限归属于哪个菜单下的权限</p>
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
