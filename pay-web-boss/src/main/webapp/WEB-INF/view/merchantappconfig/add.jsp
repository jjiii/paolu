<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>添加支付接口</title>
	<%@ include file="../inc/common/global.jsp" %>

	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/uploaders/fileinput.min.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>	
	<script src="${ctx}/assets/js/view/merchantappconfig/edit.js"></script>

	
</head>

<body>
	<!-- Simple panel -->
	<div class="panel panel-flat clear-mg-bottom">
		<div class="panel-body">
			<form id="data-form" class="form-horizontal" data-method="addMerchantAppConfigData" enctype="multipart/form-data">
			  <input type="hidden" name="merchantCode" value="${merchantCode}" />
			  <input type="hidden" name="merchantName" value="${merchantName}" />
			  <input type="hidden" name="merchantAppCode" value="${merchantAppCode}" />
			  <input type="hidden" name="merchantAppName" value="${merchantAppName}" />
			  <div class="form-group">
			    <label for="channel" class="col-sm-2 control-label">支付接口类型:</label>
			    <div class="col-sm-10">
			      <select name="channel" id="channel" class="form-control">
			      	<option value="alipay">支付宝</option>
			      	<option value="weixin">微信</option>
			      	<option value="unionpay">银联</option>
			      </select>
			    </div>
			  </div>
			  <div id="activ-input">
				  <div class="form-group">
				    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
				    <div class="col-sm-10">
				      <select name="payWay" id="payWay" class="form-control">
				      	<option value="web">web</option>
				      	<option value="app">app</option>
				      </select>
				    </div>
			  	  </div>
				  <div class="form-group">
				    <label for="channelAppId" class="col-sm-2 control-label">支付宝ID:</label>
				    <div class="col-sm-10">
				      <input type="text" name="channelAppId" class="form-control" id="channelAppId" placeholder="支付宝ID" required>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="pubKey" class="col-sm-2 control-label">支付宝公钥:</label>
				    <div class="col-sm-10">
				      <input type="text" name="pubKey" class="form-control" id="pubKey" placeholder="支付宝公钥" required>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="priKey" class="col-sm-2 control-label">商户应用私钥:</label>
				    <div class="col-sm-10">
				      <input type="text" name="priKey" class="form-control" id="priKey" placeholder="商户应用私钥" required>
				    </div>
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
	
	<div style="position:absolute;top:-1000px;">
	  <!-- alipay -->
	  <div id="alipay">
	  		
	  	  <div class="form-group">
			    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
			    <div class="col-sm-10">
			      <select name="payWay" id="payWay" class="form-control">
			      	<option value="web">web</option>
			      	<option value="app">app</option>
			      </select>
			    </div>
		  </div>
			  
		  <div class="form-group">
		    <label for="channelAppId" class="col-sm-2 control-label">支付宝ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelAppId" class="form-control" id="channelAppId" placeholder="支付宝ID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="pubKey" class="col-sm-2 control-label">支付宝公钥:</label>
		    <div class="col-sm-10">
		      <input type="text" name="pubKey" class="form-control" id="pubKey" placeholder="支付宝公钥" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="priKey" class="col-sm-2 control-label">商户应用私钥:</label>
		    <div class="col-sm-10">
		      <input type="text" name="priKey" class="form-control" id="priKey" placeholder="商户应用私钥" required>
		    </div>
		  </div>
	  </div>
	  <!-- /alipay -->
	  
	  <!-- weixin -->
	  <div id="weixin">
	   	  <div class="form-group">
			    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
			    <div class="col-sm-10">
			      <select name="payWay" id="payWay" class="form-control">
			      	<option value="web">web</option>
			      	<option value="app">app</option>
			      </select>
			    </div>
		  </div>
		  <div class="form-group">
		    <label for="channelMerchantId" class="col-sm-2 control-label">微信appID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelAppId" class="form-control" id="channelAppId"  placeholder="appID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subMchId" class="col-sm-2 control-label">微信商户ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelMerchantId" class="form-control" id="channelMerchantId" value="${merchantAppConfig.channelMerchantId}" placeholder="mchID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPath" class="col-sm-2 control-label">证书路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certFile" class="form-control" id="certPath" placeholder="未选择证书">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">证书秘匙:</label>
		    <div class="col-sm-10">
		      <input type="text" name="certPwd" class="form-control" id="certPwd" placeholder="证书秘匙" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">js密钥(h5端需要):</label>
		    <div class="col-sm-10">
		      <input type="text" name="appSecret" class="form-control" id="appSecret" value="${merchantAppConfig.appSecret}" placeholder="appSecret" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">父类ID(微信app支付需要):</label>
		    <div class="col-sm-10">
		      <input type="text" name="partnerId" class="form-control" id="partnerId" value="${merchantAppConfig.partnerId}" placeholder="partnerId" required>
		    </div>
		  </div>
	  </div>
	  <!-- /weixin -->
	  
	  <!-- union -->
	  <div id="unionpay">
	  		
	  	<div class="form-group">
			    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
			    <div class="col-sm-10">
			      <select name="payWay" id="payWay" class="form-control">
			      	<option value="web">web</option>
			      	<option value="app">app</option>
			      </select>
			    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="channelMerchantId" class="col-sm-2 control-label">银联ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelMerchantId" class="form-control" id="channelMerchantId" placeholder="银联ID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPath" class="col-sm-2 control-label">证书路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certValidateFile" class="form-control" id="certValidateFile" placeholder="未选择证书">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">证书秘匙:</label>
		    <div class="col-sm-10">
		      <input type="text" name="certPwd" class="form-control" id="certPwd" placeholder="证书秘匙" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">签名证书:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certFile" class="form-control" id="certPath" placeholder="未选择签名证书">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certEncPath" class="col-sm-2 control-label">敏感信息路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certEncFile" class="form-control" id="certEncPath" placeholder="未选择文件">
		    </div>
		  </div>
	  </div>
	  <!-- /union -->
	</div>
</body>
</html>
