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
	<script src="${ctx}/assets/js/view/dictionary/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="editDict">
				<div class="form-group">
					<label class="col-md-2 control-label text-right">所属字典:</label>
					<div class="col-md-10">
						<select name="parent" class="form-control">
							<option value="0">请选择</option>
							<c:forEach items="${dictionarys}" var="dict">
								<option value="${dict.id}" <c:if test="${dictionary.parent==dict.id}">selected</c:if>>${dict.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>			
				<div class="form-group">
					<div class="form-group">
						<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>字典名称:</label>
						<div class="col-lg-10">
							<input type="hidden" name="id" value="${dictionary.id}">
							<input type="text" name="name" class="form-control" placeholder="字典名称" value="${dictionary.name}" required maxlength="20">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label text-right"><span class="text-warning"> * </span>字典编码:</label>
						<div class="col-lg-10">
							<input type="text" name="code" class="form-control" placeholder="字典编码" value="${dictionary.code}" maxlength="10">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label text-right"><span class="text-warning"> * </span>字典顺序:</label>
						<div class="col-md-10">
							<input type="number" name="orderby" class="form-control" placeholder="字典顺序" value="${dictionary.orderby}" number required maxlength="3">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label text-right">字典描述:</label>
						<div class="col-lg-10">
							<textarea rows="3" name="description" class="form-control" placeholder="字典描述">${dictionary.description}</textarea>
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
