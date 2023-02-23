<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>商户详情</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script src="${ctx}/assets/js/plugins/layer/layer.js"></script>
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}/assets/js/view/merchantapp/edit.js"></script>
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
					<a href=""> 应用详情</a>
				</li>

			</ul>
		</div>
		
	</div>
	<!-- /page header -->
	
	<!-- Content area -->
	<div class="content mg-bottom10">
		<!-- Simple panel -->
		<div class="panel panel-flat clear-mg-bottom">
			<div class="panel-body">
				<form id="data-form" class="form-horizontal" data-method="editMerchantData">
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">创建时间:</label>
				    <div class="col-sm-10">
				      <input type="hidden" name="id" value="${merchantApp.id}">
				      <input type="text" class="form-control" id="createTime" value="<fmt:formatDate value='${merchantApp.createTime}' pattern='yyyy-MM-dd'/>" readonly="readonly">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">应用名称:</label>
				    <div class="col-sm-10">
				      <input type="text" name="merchantAppName" class="form-control" id="merchantAppName" value="${merchantApp.merchantAppName}" placeholder="应用名称" readonly="readonly">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="merchantAppCode" class="col-sm-2 control-label">应用编号:</label>
				    <div class="col-sm-10">
				      <input type="text" name="merchantAppCode" class="form-control" id="merchantAppCode" value="${merchantApp.merchantAppCode}" placeholder="应用编号" readonly="readonly">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="priKey" class="col-sm-2 control-label">应用接入私钥:</label>
				    <div class="col-sm-10">
				      <textarea rows="5" class="form-control" readonly="readonly" placeholder="应用接入私钥">${merchantApp.priKey}</textarea>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="pubKey" class="col-sm-2 control-label">应用接入公钥:</label>
				    <div class="col-sm-10">
				      <textarea rows="5" name="pubKey" class="form-control" required placeholder="应用接入公钥">${merchantApp.pubKey}</textarea>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="pubKey" class="col-sm-2 control-label">商户公钥:</label>
				    <div class="col-sm-10">
				      <textarea rows="5" class="form-control" readonly="readonly" placeholder="商户公钥">${merchantApp.mctPubKey}</textarea>
				    </div>
				  </div>				  
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">状态:</label>
				    <div class="col-sm-10">
				      	<label class="radio-inline"><input type="radio" name="status" value="1" <c:if test="${merchantApp.status==1}">checked</c:if> /> 开启</label>
				      	<label class="radio-inline"><input type="radio" name="status" value="0" <c:if test="${merchantApp.status==0}">checked</c:if> /> 关闭</label>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="pubKey" class="col-sm-2 control-label">对账通知地址:</label>
				    <div class="col-sm-10">
				      <input type="text" name="downloadNotifyUrl" class="form-control" id="downloadNotifyUrl" value="${merchantApp.downloadNotifyUrl}" placeholder="对账通知地址">
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="pubKey" class="col-sm-2 control-label">欢账错误通知地址:</label>
				    <div class="col-sm-10">
				      <input type="text" name="handleMistakeNotifyUrl" class="form-control" id="handleMistakeNotifyUrl" value="${merchantApp.handleMistakeNotifyUrl}" placeholder="欢账错误通知地址">
				    </div>
				  </div>				  
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10 text-right">
				      <button type="submit" class="btn btn-primary">提交</button>
				    </div>
				  </div>
				</form>	
			</div>
			
			<div class="panel-body">
				<s:hasPermission name="mct:config:list">
				<form id="search-form" class="form-inline">
					<div id="searchDiv" class="well well-sm text-right mg-bottom10">
						<s:hasPermission name="mct:config:add">
							<button type="button" class="btn btn-default text-right" onclick="addMerchantAppConfig('${merchantApp.merchantCode}','${merchantApp.merchantName}','${merchantApp.merchantAppCode}','${merchantApp.merchantAppName}')">新增支付接口</button>
						</s:hasPermission>
					</div>
					
					<table class="table table-hover table-bordered" id="table">
					    <thead>
					        <tr>
					            <th>序号</th>
					            <th>接口名称</th>
					            <th>创建时间</th>
					            <th>状态</th>
					            <th>操作</th>
					        </tr>
					    </thead>
					     <tbody id="data-body">
					     	<c:forEach items="${merchantAppConfigs}" var="merchantAppConfig" varStatus="status">
						       <tr>
						            <td>${status.count}</td>
						            <td>
						            	<c:if test="${merchantAppConfig.channel=='alipay'}">支付宝</c:if>
						            	<c:if test="${merchantAppConfig.channel=='weixin'}">微信</c:if>
						            	<c:if test="${merchantAppConfig.channel=='unionpay'}">银联</c:if>
						            </td>
						            <td><fmt:formatDate value="${merchantAppConfig.createTime}" pattern="yyyy-MM-dd"/></td>
						            <td>
						            	<c:if test="${merchantAppConfig.status==0}">关闭中</c:if>
						            	<c:if test="${merchantAppConfig.status==1}">开启中</c:if>
						            </td>
						            <td class="td-btns">
						            	<s:hasPermission name="mct:config:view">
										<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="editMerchantConfigApp(${merchantAppConfig.id})">
											查看
										</button>
										</s:hasPermission>
										<s:hasPermission name="mct:config:close">				            
										<button type="button" data-status="${merchantAppConfig.status}" class="btn btn-default btn-xs" href="javascript:;" onclick="changeMerchantAPPStatus(${merchantAppConfig.id})">
											<c:if test="${merchantAppConfig.status==1}">关闭</c:if>
						            		<c:if test="${merchantAppConfig.status==0}">开启</c:if>
										</button>
										</s:hasPermission>					            
						            </td>
						        </tr>
					     	</c:forEach>
					        <tr id="norecord" class="hidden"><td colspan="5" class="text-center">暂无记录</td></tr>					     
					     </tbody>		
					</table>
					<div id="pagebar" class="text-right mg-top10"></div>
				</form>
				</s:hasPermission>
			</div>
						
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
</body>
</html>
