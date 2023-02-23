<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../inc/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>商户详情</title>
	<%@ include file="../inc/common/global.jsp" %>
	<script src="${ctx}assets/js/plugins/layer/layer.js"></script>
	<script src="${ctx}assets/js/view/common/base.ajax.js"></script>
	<script src="${ctx}assets/js/view/common/baseedit.js"></script>
	<script src="${ctx}assets/js/view/merchant/edit.js"></script>
</head>

<body>

	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<a href="javascript:history.back();"><i class="icon-arrow-left52 position-left"></i></a>
					<span class="text-semibold" id="nav-fist-menu">商户管理</span> - 
					<span id="nav-second-menu">商户应用管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}/main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href="${ctx}merchant/list"> 商户应用管理</a>
				</li>
				<li>
					<a href=""> 商户详情</a>
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
				    <label class="col-sm-2 control-label">创建时间:</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" id="merchantCode" placeholder="创建时间" value="<fmt:formatDate value='${merchant.createTime}' pattern='yyyy-MM-dd'/>" readonly>
				    </div>
				  </div>
				  <div class="form-group">
				    <label  class="col-sm-2 control-label">创建人:</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" id="merchantCode" placeholder="创建人" value="${merchant.creater}" readonly>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="merchantCode" class="col-sm-2 control-label">商户编号:</label>
				    <div class="col-sm-10">
				      <input type="hidden" name="id" value="${merchant.id}" />
				      <input type="text" class="form-control" id="merchantCode" placeholder="商户编号" value="${merchant.merchantCode}" readonly>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="merchantName" class="col-sm-2 control-label">商户名称:</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" id="merchantName" placeholder="商户名称" value="${merchant.merchantName}" readonly>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="email" class="col-sm-2 control-label">邮箱:</label>
				    <div class="col-sm-10">
				      <input type="email" name="email" class="form-control" id="email" placeholder="邮箱" value="${merchant.email}" required>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="remark" class="col-sm-2 control-label">备注:</label>
				    <div class="col-sm-10">
				      <textarea rows="3" cols="5" name="remark" class="form-control" id="remark"  placeholder="备注">${merchant.remark}</textarea>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">状态:</label>
				    <div class="col-sm-10">
				      	<label class="radio-inline"><input type="radio" name="status" value="1" <c:if test="${merchant.status==1}">checked</c:if> /> 开启</label>
				      	<label class="radio-inline"><input type="radio" name="status" value="0" <c:if test="${merchant.status==0}">checked</c:if> /> 关闭</label>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10 text-right">
				      <button type="submit" class="btn btn-primary">提交</button>
				    </div>
				  </div>
				</form>
				
				<!-- merchant_app_list -->
				<s:hasPermission name="mct:app:list">
				<div>
					<input type="hidden" id="issave" value="0" />
					<form id="search-form" class="form-inline">
						<div id="searchDiv" class="well well-sm text-right mg-bottom10">
							<s:hasPermission name="mct:app:add">
								<button type="button" class="btn btn-default text-right" onclick="addMerchantApp('${merchant.merchantCode}','${merchant.merchantName}')">新增应用</button>
							</s:hasPermission>
						</div>
						
						<table class="table table-hover table-bordered" id="table">
						    <thead>
						        <tr>
						            <th>序号</th>
						            <th>应用编号</th>
						            <th>应用名称</th>
						            <!-- <th>类型</th> -->
						            <th>创建时间</th>
						            <th>状态</th>
						            <th>操作</th>
						        </tr>
						    </thead>
						    <tbody id="data-body">
						     	<c:forEach items="${merchantApps}" var="merchantApp" varStatus="status">
							       <tr>
							            <td>${status.count}</td>
							            <td>${merchantApp.merchantAppCode}</td>
							            <td>${merchantApp.merchantAppName}</td>
							            <!-- <td>app</td> -->
							            <td><fmt:formatDate value="${merchantApp.createTime}" pattern="yyyy-MM-dd"/></td>
							            <td>
							            	<c:if test="${merchantApp.status==0}">关闭中</c:if>
							            	<c:if test="${merchantApp.status==1}">开启中</c:if>
							            </td>
							            <td class="td-btns">
							            	<s:hasPermission name="mct:app:view">
											<button type="button" class="btn btn-default btn-xs" href="javascript:;" onclick="editMerchantApp(${merchantApp.id})">
												查看
											</button>
											</s:hasPermission>
											
											<s:hasPermission name="mct:app:close">				            
											<button type="button" data-status="${merchantApp.status}" class="btn btn-default btn-xs" href="javascript:;" onclick="changeMerchantAPPStatus(${merchantApp.id})">
												<c:if test="${merchantApp.status==1}">关闭</c:if>
							            		<c:if test="${merchantApp.status==0}">开启</c:if>
											</button>
											</s:hasPermission>					            
							            </td>
							        </tr>					     
						     	</c:forEach>
						     	<tr id="norecord" class="hidden"><td colspan="7" class="text-center">暂无记录</td></tr>
						     </tbody>		
						</table>
						<div id="pagebar" class="text-right mg-top10"></div>
					</form>
				</div>
				</s:hasPermission>
				<!-- /merchant_app_list -->	
				
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
</body>
</html>
