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
	<script type="text/javascript" src="${ctx}assets/js/plugins/ui/nicescroll.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/layout_fixed_custom.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/order/pay/reconcilelist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/plugins/laydate/laydate.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/base.ajax.js"></script>
</head>
<body>
	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">订单管理</span> - 
					<span id="nav-second-menu">支付订单管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 订单管理</a>
				</li>
				<li>
					<a href=""> 支付订单管理</a>
				</li>
			</ul>
		</div>
		
	</div>
	<!-- /page header -->

	<!-- Content area -->
	<div class="content">
		
		<!-- Simple panel -->
		<div class="panel panel-flat">
			<div class="panel-heading">
				<h5 class="panel-title">支付订单列表</h5>
				<div class="heading-elements">
					<ul class="icons-list">
		            </ul>
		        </div>
			</div>
		
			<div class="panel-body">
				<form id="search-form" class="form-inline">
					<div id="searchDiv" class="well well-sm" style="margin-bottom:10px;">
						<div class="form-group">
							<select name="searchKey" class="form-control">
							  <option value="order_no">枫车支付流水号</option>
							  <option value="trade_no">第三方支付流水号</option>
							  <option value="merchant_order_no">商户订单号</option>
							  <option value="merchant_name">商户名</option>
							  <option value="merchant_app_name">应用名</option>
							  <option value="batch_no">对账批次号</option>
							</select>
							<input type="text" name="searchValue" class="form-control">
						</div>
						<div class="form-group">
							<label>订单来源:</label>
							<select name="merchant_app_code" class="form-control">
								<option value="">全部</option>
								<c:forEach items="${merchantApps}" var="merchantApp">
									<option value="${merchantApp.merchantAppCode}">${merchantApp.merchantAppName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label>支付时间:</label>
							<input type="text" name="startDate" class="form-control laydate-icon" id="start" />
							<p class="form-control-static">至</p>
							<input type="text" name="endDate" class="form-control laydate-icon" id="end" />
						</div>
						<div class="form-group">
							<label>第三方支付:</label>
							<select name="channel" class="form-control">
							  <option value="">全部</option>
							  <option value="alipay">支付宝</option>
							  <option value="weixin">微信</option>
							  <option value="union">银联</option>
							</select>
						</div>
						<div class="form-group">
							<label>对账状态:</label>
							<select name="bill_status" class="form-control">
							  <option value="wait">待对账</option>
							  <option value="normal">正常</option>
							  <option value="doubt">存疑</option>
							  <option value="mistake">差错</option>
							  <option value="balance">平帐</option>
							</select>
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
					</div>	
					<table class="table table-hover table-bordered" id="table">
						<colgroup>
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
						</colgroup>
					    <thead>
					        <tr>
					            <th>枫车支付流水号</th>
					            <th>第三方支付流水号</th>
					            <th>商户订单号</th>
					            <th>支付时间</th>
					            <th>币种及金额（元）</th>
					            <th>商户名称及ID</th>
					            <th>应用名称及ID</th>
					            <th>第三方支付类型及ID</th>
					            <th>支付状态</th>
					            <th>对账状态及对账批次号</th>
					            <th>操作</th>
					        </tr>
					    </thead>
					     <tbody id="data-body">
					     </tbody>
					</table>
				    <div class="pagebar-container">
						<span class="hidden">共 <i id="pages"></i> 页， <i id="total"></i> 条记录</span>
						<div id="pagebar" class="text-right"></div>
					</div>
			    </form>		
			</div>
		</div>
		<!-- /simple panel -->
	</div>
	<!-- /Content area -->
	
	<!-- Inline form modal -->
	<div id="modal_form_reset" class="modal fade">
		<div class="panel panel-flat clear-mg-bottom">
			<div class="panel-body">
				<form id="data-form" action="#" class="form-horizontal data-form">
					<input type="hidden" id="payOrderId" name="payOrderId">
					<div class="form-group">
						<label class="col-sm-2 control-label">处理方式: </label>
						<label class="radio-inline">
						  <input type="radio" id="inlineCheckbox1" name="handleWay" value="channel" checked="checked"> 渠道为准(将修改原支付信息)
						</label>
						<label class="radio-inline">
						  <input type="radio" id="inlineCheckbox2" name="handleWay" value="local"> 枫车支付为准
						</label>
					</div>
					
					<div class="form-group">
						<label for="handleRemark" class="col-sm-2 control-label">处理备注: </label>
						<textarea id="handleRemark" name="handleRemark" class="form-control col-sm-10" rows="3" style="width: 80%;" maxlength="30"></textarea>
					</div>
	
					<div class="modal-footer text-right">
						<button type="button" class="btn btn-primary"  data-dismiss="modal" onclick="doSettle();">提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- /inline form modal -->

<!-- data-render -->
<script id="tabledata" type="text/html">
	{{# if(d.data.length==0){ }}
		<tr><td colspan="11" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{d.data[i].orderNo}}</td>
		    <td>{{=d.data[i].tradeNo||''}}</td>
		    <td>{{=d.data[i].merchantOrderNo||''}}</td>
		    <td>{{d.data[i].orderTime}}</td>
		    <td>{{d.data[i].amount}}</td>
		    <td>{{d.data[i].merchantName}}<br/>{{d.data[i].merchantCode}}</td>
		    <td>{{d.data[i].merchantAppName}}<br/>{{d.data[i].merchantAppCode}}</td>
		    <td>
				{{# if(d.data[i].channel=='weixin'){ }}
					微信
				{{# }else if(d.data[i].channel=='unionpay'){ }}
					银联
				{{# }else if(d.data[i].channel=='alipay'){ }}
					支付宝
				{{# } }}
				 <br /> 
				{{# if(d.data[i].payWay=='scanpay'){ }}
					扫码支付
				{{# }else if(d.data[i].payWay=='h5pay'){ }}
					H5支付
				{{# }else if(d.data[i].payWay=='f2f_pay'){ }}
					条码支付
				{{# } }}
			</td>
		    <td>
				{{# if(d.data[i].status=='pay_wait'){ }}
					待支付
				{{# }else if(d.data[i].status=='success'){ }}
					成功
				{{# }else if(d.data[i].status=='finish'){ }}
					结束(不能退款)
				{{# }else if(d.data[i].status=='close'){ }}
					关闭(超时/未收到通知)
				{{# } }}
			</td>
			<td>
				{{# if(d.data[i].billStatus=='wait'){ }}
					待对账
				{{# }else if(d.data[i].billStatus=='normal'){ }}
					正常
				{{# }else if(d.data[i].billStatus=='doubt'){ }}
					存疑
				{{# }else if(d.data[i].billStatus=='mistake'){ }}
					差错
				{{# }else if(d.data[i].billStatus=='balance'){ }}
					平账
				{{# } }}
				<br/>对账批次号暂无
			</td>
		    <td>
				 <s:hasPermission name="order:pay:view">
                 <a class="btn btn-default btn-xs" href="javascript:;" onclick="view('{{d.data[i].id}}')">
                 	查看详情
                 </a>
				 </s:hasPermission>
		    </td>
		</tr>
	{{# } }}
</script>
<!-- /data-render -->
</body>
</html>
