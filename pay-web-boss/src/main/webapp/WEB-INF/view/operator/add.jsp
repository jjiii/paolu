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
	<script src="${ctx}assets/js/view/operator/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="addOperator">
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>账号:</label>
					<div class="col-lg-10">
						<input type="text" name="loginName" class="form-control" placeholder="账号" required maxlength="20">
						<p class="help-block">注意：该字段定义后不可修改</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>密码:</label>
					<div class="col-lg-10">
						<input type="password" name="loginPwd" class="form-control" placeholder="密码" required maxlength="20">
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>姓名:</label>
					<div class="col-lg-10">
						<input type="text" name="realName" class="form-control" placeholder="姓名" required maxlength="20">
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>手机号:</label>
					<div class="col-lg-10">
						<input type="text" name="mobileNo" class="form-control" placeholder="手机号" required maxlength="11" >
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> </span>角色:</label>
					<div class="col-lg-10">
						<c:forEach items="${roles}" var="role">
							<label class="checkbox-inline">
							  <input type="checkbox" name="roleId" value="${role.id}"> ${role.roleName}
							</label>
						</c:forEach>
					</div>
				</div>			
				<div class="form-group">
					<label class="col-lg-2 control-label text-right"><span class="text-warning"> </span>描述:</label>
					<div class="col-lg-10">
						<textarea rows="3" name="remark" class="form-control" placeholder="备注信息"></textarea>
					</div>
				</div>			
				<div class="form-group">
					<label class="col-lg-2 control-label text-right">状态:</label>
					<div class="col-lg-10">
						<label class="radio-inline">
							<input type="radio" class="styled" name="status" checked="checked" value="ACTIVE">
							启用
						</label>
	
						<label class="radio-inline">
							<input type="radio" class="styled" name="status" value="UNACTIVE">
							禁用
						</label>
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