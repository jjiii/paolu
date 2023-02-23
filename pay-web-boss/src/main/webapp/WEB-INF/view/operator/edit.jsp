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
	<script src="${ctx}assets/js/view/operator/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
	
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="editOperator">
				<div class="form-group">
					<label class="col-xs-2 control-label text-right"><span class="text-warning"> * </span>账号:</label>
					<div class="col-xs-8">
						<input type="hidden" name="id" value="${operator.id}">
						<input type="hidden" name="type" value="${operator.type}">
						<input type="text" name="loginName" class="form-control" placeholder="账号" value="${operator.loginName}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right"><span class="text-warning"> * </span>姓名:</label>
					<div class="col-xs-8">
						<input type="text" name="realName" class="form-control" placeholder="姓名" value="${operator.realName}" required maxlength="20">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right"><span class="text-warning"> * </span>手机号:</label>
					<div class="col-xs-8">
						<input type="text" name="mobileNo" class="form-control" placeholder="手机号" value="${operator.mobileNo}" required maxlength="11">
					</div>
				</div>
				<s:hasRole name="admin">
					<c:if test="${operator.id != BossOperator.id}">
					<div class="form-group">
						<label class="col-xs-2 control-label text-right"><span class="text-warning"> </span>角色:</label>
						<div class="col-xs-8">
							<c:forEach items="${roles}" var="role">
								<label class="checkbox-inline">
								  <input type="checkbox" name="roleId" value="${role.id}" <c:if test="${role.isChecked==1}">checked</c:if>> ${role.roleName}
								</label>
							</c:forEach>
						</div>
					</div>
					</c:if>		
				</s:hasRole>
				<div class="form-group">
					<label class="col-xs-2 control-label text-right"><span class="text-warning"> </span>描述:</label>
					<div class="col-xs-8">
						<textarea rows="3" name="remark" class="form-control" placeholder="备注信息">${operator.remark}</textarea>
					</div>
				</div>
				
				<c:if test="${operator.id != BossOperator.id}">
					<div class="form-group">
						<label class="col-xs-2 control-label text-right">状态:</label>
						<div class="col-xs-8">
							<label class="radio-inline">
								<input type="radio" class="styled" name="status" <c:if test="${operator.status=='ACTIVE'}">checked="checked"</c:if> value="ACTIVE">
								启用
							</label>
		
							<label class="radio-inline">
								<input type="radio" class="styled" name="status" <c:if test="${operator.status=='UNACTIVE'}">checked="checked"</c:if> value="UNACTIVE">
								禁用
							</label>
						</div>
					</div>
				</c:if>
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
