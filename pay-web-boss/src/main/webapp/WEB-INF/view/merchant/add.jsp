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
	<script src="${ctx}/assets/js/view/merchant/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="addMerchantData">
			  <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">商户编号:</label>
			    <div class="col-sm-10">
			      <input type="text" name="merchantCode" class="form-control" id="merchantCode" value="${merchantCode}" placeholder="商户编号" readonly>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">商户名称:</label>
			    <div class="col-sm-10">
			      <input type="text" name="merchantName" class="form-control" id="merchantName" placeholder="商户名称" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">邮箱:</label>
			    <div class="col-sm-10">
			      <input type="email" name="email" class="form-control" id="email" placeholder="邮箱" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">备注:</label>
			    <div class="col-sm-10">
			      <textarea rows="3" cols="5" name="remark" class="form-control" id="remark"  placeholder="备注"></textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10 text-right">
			      <button type="submit" class="btn btn-primary">提交</button>
			    </div>
			  </div>
			</form>	
		</div>
	</div>
	<!-- /simple panel -->
</body>
</html>
