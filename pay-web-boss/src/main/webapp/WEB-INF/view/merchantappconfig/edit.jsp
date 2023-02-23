<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>查看支付接口</title>
	<%@ include file="../inc/common/global.jsp" %>
	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/uploaders/fileinput.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}/assets/js/view/merchantappconfig/edit.js"></script>
	<script>
		$(function(){
			initChannel();
			initFileInput();
		});
	</script>
</head>

<body>

	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<a href="javascript:history.back()"><i class="icon-arrow-left52 position-left"></i></a>
					<span class="text-semibold" id="nav-fist-menu">商户管理</span> - 
					<span id="nav-second-menu">商户应用管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href="${ctx}merchant/list"> 商户应用管理</a>
				</li>
				<li>
					<a href="javascript:history.back()"> 商户详情</a>
				</li>
				<li>
					<a href="javascript:history.back()"> 应用详情</a>
				</li>
				<li>
					<a href=""> 支付接口详情</a>
				</li>

			</ul>
		</div>
		
	</div>
	<!-- /page header -->
	<!-- Content area -->
	<div class="content">
		
		<!-- Simple panel -->
		<div class="panel panel-flat clear-mg-bottom">
			<div class="panel-body">
				<form id="data-form" class="form-horizontal" data-method="editMerchantAppConfigData">
				  <input type="hidden" name="id" value="${merchantAppConfig.id}" />
				  <input type="hidden" name="channel" value="${merchantAppConfig.channel}"/>
				  <input type="hidden" name="merchantCode" value="${merchantAppConfig.merchantCode}"/>
				  <input type="hidden" name="merchantAppCode" value="${merchantAppConfig.merchantAppCode}"/>
				  <div class="form-group">
				    <label for="createTime" class="col-sm-2 control-label">创建时间:</label>
				    <div class="col-sm-10">
				      	<input type="text" class="form-control" id="createTime" value="<fmt:formatDate value='${merchantAppConfig.createTime}' pattern='yyyy-MM-dd'/>" readonly>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="channel" class="col-sm-2 control-label">支付接口类型:</label>
				    <div class="col-sm-10">
				      <select id="channel" class="form-control" disabled="disabled">
				      	<option value="alipay" <c:if test="${merchantAppConfig.channel=='alipay'}">selected</c:if> >支付宝</option>
				      	<option value="weixin" <c:if test="${merchantAppConfig.channel=='weixin'}">selected</c:if>>微信</option>
				      	<option value="unionpay" <c:if test="${merchantAppConfig.channel=='unionpay'}">selected</c:if>>银联</option>
				      </select>
				    </div>
				  </div>
				  <div id="activ-input">
				  	  <div class="form-group">
						    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
						    <div class="col-sm-10">
						      <select name="payWay" id="payWay" class="form-control">
						      	<option value="web" <c:if test="${merchantAppConfig.payWay=='web'}">selected</c:if> >web</option>
						      	<option value="app" <c:if test="${merchantAppConfig.payWay=='app'}">selected</c:if> >app</option>
						      </select>
						    </div>
					  </div>
					  <div class="form-group">
					    <label for="channelAppId" class="col-sm-2 control-label">支付宝ID:</label>
					    <div class="col-sm-10">
					      <input type="text" name="channelAppId" class="form-control" id="channelAppId" value="${merchantAppConfig.channelAppId}" placeholder="支付宝ID" required>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="pubKey" class="col-sm-2 control-label">支付宝公钥:</label>
					    <div class="col-sm-10">
					      <input type="text" name="pubKey" class="form-control" id="pubKey" value="${merchantAppConfig.pubKey}" placeholder="支付宝公钥" required>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="priKey" class="col-sm-2 control-label">商户应用私钥:</label>
					    <div class="col-sm-10">
					      <input type="text" name="priKey" class="form-control" id="priKey" value="${merchantAppConfig.priKey}" placeholder="商户应用私钥" required>
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
	</div>
	
	<div class="hidden">
	  <!-- alipay -->
	  <div id="alipay">
	  
	  	  <div class="form-group">
			    <label for="payWay" class="col-sm-2 control-label">支付方式:</label>
			    <div class="col-sm-10">
			      <select name="payWay" id="payWay" class="form-control">
			      	<option value="web" <c:if test="${merchantAppConfig.payWay=='web'}">selected</c:if> >web</option>
			      	<option value="app" <c:if test="${merchantAppConfig.payWay=='app'}">selected</c:if> >app</option>
			      </select>
			    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="channelAppId" class="col-sm-2 control-label">支付宝ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelAppId" class="form-control" id="channelAppId" value="${merchantAppConfig.channelAppId}" placeholder="支付宝ID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="pubKey" class="col-sm-2 control-label">支付宝公钥:</label>
		    <div class="col-sm-10">
		      <input type="text" name="pubKey" class="form-control" id="pubKey" value="${merchantAppConfig.pubKey}" placeholder="支付宝公钥" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="priKey" class="col-sm-2 control-label">商户应用私钥:</label>
		    <div class="col-sm-10">
		      <input type="text" name="priKey" class="form-control" id="priKey" value="${merchantAppConfig.priKey}" placeholder="商户应用私钥" required>
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
			      	<option value="web" <c:if test="${merchantAppConfig.payWay=='web'}">selected</c:if> >web</option>
			      	<option value="app" <c:if test="${merchantAppConfig.payWay=='app'}">selected</c:if> >app</option>
			      </select>
			    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="channelMerchantId" class="col-sm-2 control-label">微信appID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelAppId" class="form-control" id="channelAppId" value="${merchantAppConfig.channelAppId}" placeholder="appID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="subMchId" class="col-sm-2 control-label">微信商户ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelMerchantId" class="form-control"  id="channelMerchantId" value="${merchantAppConfig.channelMerchantId}" placeholder="mchID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPath" class="col-sm-2 control-label">证书路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certFile" class="form-control file-input" id="certPath" placeholder="证书路径">
		      <p class="help-block">证书路径：${merchantAppConfig.certPath}</p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">证书秘匙:</label>
		    <div class="col-sm-10">
		      <input type="text" name="certPwd" class="form-control" id="certPwd" value="${merchantAppConfig.certPwd}" placeholder="证书秘匙" required>
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
			      	<option value="web" <c:if test="${merchantAppConfig.payWay=='web'}">selected</c:if> >web</option>
			      	<option value="app" <c:if test="${merchantAppConfig.payWay=='app'}">selected</c:if> >app</option>
			      </select>
			    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="channelMerchantId" class="col-sm-2 control-label">银联ID:</label>
		    <div class="col-sm-10">
		      <input type="text" name="channelMerchantId" class="form-control" id="channelMerchantId" value="${merchantAppConfig.channelMerchantId}" placeholder="银联ID" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPath" class="col-sm-2 control-label">证书路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certValidateFile" class="form-control file-input" id="certValidate" value="${merchantAppConfig.certValidate}" placeholder="证书路径">
		      <p class="help-block">证书路径：${merchantAppConfig.certValidate}</p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPwd" class="col-sm-2 control-label">证书秘匙:</label>
		    <div class="col-sm-10">
		      <input type="text" name="certPwd" class="form-control" id="certPwd" value="${merchantAppConfig.certPwd}" placeholder="证书秘匙" required>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certPath" class="col-sm-2 control-label">签名证书:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certFile" class="form-control file-input" id="certPath" value="${merchantAppConfig.certPath}" placeholder="证书路径">
		      <p class="help-block">证书路径：${merchantAppConfig.certPath}</p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="certEncPath" class="col-sm-2 control-label">敏感信息路径:</label>
		    <div class="col-sm-10">
		      <input type="file" name="certEncFile" class="form-control file-input" id="certEncPath" value="${merchantAppConfig.certEncPath}" placeholder="敏感信息路径">
		      <p class="help-block">证书路径：${merchantAppConfig.certEncPath}</p>
		    </div>
		  </div>
	  </div>
	  <!-- /union -->
	</div>
</body>
</html>
