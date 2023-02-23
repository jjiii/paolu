<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>商户应用新增</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}assets/js/view/merchantapp/edit.js"></script>
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="addMerchantAppData">
			  <input type="hidden" name="merchantCode" value="${merchantCode}" />
			  <input type="hidden" name="merchantName" value="${merchantName}" />
			  <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">应用名称:</label>
			    <div class="col-sm-10">
			      <input type="text" name="merchantAppName" class="form-control" id="merchantAppName" placeholder="应用名称">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="merchantAppCode" class="col-sm-2 control-label">应用编号:</label>
			    <div class="col-sm-10">
			      <input type="text" name="merchantAppCode" class="form-control" id="merchantAppCode" value="${merchantAppCode}" placeholder="应用编号" readonly>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="priKey" class="col-sm-2 control-label">应用接入私钥:</label>
			    <div class="col-sm-10">
			      <textarea rows="5" name="priKey" class="form-control" readonly="readonly" placeholder="应用接入私钥">${privateKey}</textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="pubKey" class="col-sm-2 control-label">应用接入公钥:</label>
			    <div class="col-sm-10">
			      <textarea rows="5" name="pubKey" class="form-control" required placeholder="应用接入公钥"></textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="pubKey" class="col-sm-2 control-label">商户公钥:</label>
			    <div class="col-sm-10">
			      <textarea rows="5" name="mctPubKey" class="form-control" readonly="readonly" placeholder="商户公钥">${publicKey}</textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="pubKey" class="col-sm-2 control-label">对账通知地址:</label>
			    <div class="col-sm-10">
			      <input type="text" name="downloadNotifyUrl" class="form-control" id="downloadNotifyUrl" placeholder="对账通知地址">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="pubKey" class="col-sm-2 control-label">欢账错误通知地址:</label>
			    <div class="col-sm-10">
			      <input type="text" name="handleMistakeNotifyUrl" class="form-control" id="handleMistakeNotifyUrl" placeholder="欢账错误通知地址">
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
