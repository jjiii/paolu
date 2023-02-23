<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../../inc/common/global.jsp" %>
	
	<link href="${ctx}/assets/css/plugin/laypage/laypage.css" rel="stylesheet" type="text/css">
	<!-- Theme JS files -->
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/baselist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/uploaders/fileinput.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/testbill/billfile/billfile.js"></script>
</head>
<body>
	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">测试对账</span> - 
					<span id="nav-second-menu">修改第三方对账</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 测试对账</a>
				</li>
				<li>
					<a href=""> 修改第三方对账</a>
				</li>
			</ul>
		</div>
		
	</div>
	<!-- /page header -->

	<!-- Content area -->
	<div class="content">
		
		<!-- Simple panel -->
		<div class="panel panel-default">
			<form id="form-data" class="form-horizontal" enctype="multipart/form-data">
				<div class="panel-heading">
					<button type="submit" class="btn btn-primary pull-right" style="margin-top:-5px;z-index: 999">提 交</button>
					<h5 class="panel-title text-center"><span>上传对账文件</span></h5>
				</div>
			
				<div class="panel-body">
					<div class="form-group">
	  					<label for="channel" class="col-sm-1 control-label">渠道</label>
					    <div class="col-sm-3">
					    	<select id="channel" class="form-control" name="channel">
					    		<option value="alipay">支付宝</option>
					    		<option value="weixin">微信</option>
					    		<option value="unionpay">银联</option>
					    	</select>
					    </div>
					</div>				
					<div class="form-group">
	  					<label for="mchId" class="col-sm-1 control-label">商户ID</label>
					    <div class="col-sm-3">
					      <input type="text" name="channelMerchantId" class="form-control" id="mchId" placeholder="渠道对应mchId"/>
					    </div>
					</div>				
					<div class="form-group">
	  					<label for="appId" class="col-sm-1 control-label">应用ID</label>
					    <div class="col-sm-3">
					      <input type="text" name="channelAppId" class="form-control" id="appId" placeholder="渠道对应appId"/>
					    </div>
					</div>				
					<div class="form-group">
	  					<label for="billdate" class="col-sm-1 control-label">对账日期</label>
					    <div class="col-sm-3">
					      <input type="text" name="billDate" class="form-control laydate-icon" id="billdate" placeholder="请选择对账日期"/>
					    </div>
					</div>				
					<div class="form-group">
	  					<label for="alipay" class="col-sm-1 control-label">上传文件</label>
					    <div class="col-sm-3">
					      <input type="file" name="billfile" class="form-control file-input" id="alipay" value="上传对账文件" />
					    </div>
					</div>
				</div>
		    </form>		
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
	
</body>
</html>
