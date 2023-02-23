<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common/taglib.jsp" %>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="../inc/common/meta.jsp" %>
	<title>枫车支付系统</title>
	<%@ include file="../inc/common/global.jsp" %>
	
	<!-- Theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/ui/nicescroll.min.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/layout_fixed_custom.js"></script>
	<!-- /theme JS files -->
	<script type="text/javascript" src="${ctx}assets/js/plugins/laypage/laypage.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/common/baselist.js"></script>
	<script type="text/javascript" src="${ctx}assets/js/view/operator/list.js"></script>
</head>
<body>
	<!-- Page header -->
	<div class="page-header">
		<input type="hidden" id="issave" value="0"/>
		<div class="page-header-content">
			<div class="page-title">
				<h4>
					<!-- <i class="icon-arrow-left52 position-left"></i> -->
					<span class="text-semibold" id="nav-fist-menu">权限管理</span> - 
					<span id="nav-second-menu">操作员管理</span>
				</h4>
			</div>
		</div>

		<div class="breadcrumb-line">
			<ul class="breadcrumb" id="breadcrumb">
				<li>
					<a href="${ctx}main"><i class="icon-home2 position-left"></i> 首页</a>
				</li>
				<li>
					<a href=""> 权限管理</a>
				</li>
				<li>
					<a href=""> 操作员管理</a>
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
				<h5 class="panel-title">操作员列表</h5>
				<div class="heading-elements">
					<ul class="icons-list">
                		<li><a data-action="collapse"></a></li>
                		<li><a data-action="reload" onclick="location.reload();"></a></li>
                		<!-- <li><a data-action="close"></a></li> -->
                	</ul>
               	</div>
			</div>
		
			<div class="panel-body">
				<form id="search-form" class="form-inline">
					<div id="searchDiv" class="well well-sm" style="margin-bottom:10px;">
						<div class="form-group">
							<label>账号:</label>
							<input type="text" name="loginName" class="form-control" placeholder="账号">
						</div>
						<div class="form-group">
							<label>姓名:</label>
							<input type="text" name="realName" class="form-control" placeholder="姓名">
						</div>
						<div class="form-group">
							<label>手机号:</label>
							<input type="text" name="mobileNo" class="form-control" placeholder="手机号">
						</div>
						<div class="form-group">
							<label>状态:</label>
							<select name="status" class="form-control">
							  <option value="">请选择</option>
							  <option value="ACTIVE">启用</option>
							  <option value="UNACTIVE">禁用</option>
							</select>
						</div>
						<button type="button" id="search" class="btn btn-default">搜索</button>
						<s:hasPermission name="pms:operator:add">
							<button type="button" class="btn btn-default" onclick="addOperatorUI()"><i class=" icon-add-to-list position-left"></i>添加操作员</button>
						</s:hasPermission>
					</div>	
					<table class="table table-hover" id="table">
					    <thead>
					        <tr>
					            <th>序号</th>
					            <th>账号</th>
					            <th>姓名</th>
					            <th>手机号</th>
					            <th>状态</th>
					            <th>备注</th>
					            <th>创建时间</th>
					            <th>创建人</th>
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
	
<!-- data-render -->
<script id="tabledata" type="text/html">
	{{# if(d.data.length==0){ }}
		<tr><td colspan="9" class="text-center">暂无记录</td></tr>
	{{# } }}
	{{# for(var i = 0, len = d.data.length; i < len; i++){ }}
		<tr>
		    <td>{{i+1}}</td>
		    <td>{{d.data[i].loginName}}</td>
		    <td>{{d.data[i].realName}}</td>
		    <td>{{d.data[i].mobileNo}}</td>
		    <td>
				{{# if(d.data[i].status=='ACTIVE'){ }}
					启用
				{{# }else{ }}
					禁用
				{{# } }}
			</td>
		    <td>{{d.data[i].remark}}</td>
		    <td>{{d.data[i].createTime}}</td>
		    <td>{{d.data[i].creater}}</td>
		    <td>
				<s:hasPermission name="pms:operator:edit">
				 {{# if(d.data[i].type==1){ }}
                 <a class="btn btn-default btn-xs" href="javascript:;" onclick="editOperatorUI('{{d.data[i].id}}')">
                 	修改
                 </a>
				 {{# } }}
				 </s:hasPermission>
				 <s:hasPermission name="pms:operator:resetpwd">
                 <a class="btn btn-default btn-xs" href="javascript:;" onclick="resetPwd('{{d.data[i].id}}')">
                 	重置密码
                 </a>
				 </s:hasPermission>
				 <s:hasPermission name="pms:operator:changestatus">
				 {{# if(d.data[i].type==1){ }}
                 <a class="btn btn-default btn-xs" data-status="{{d.data[i].status}}" href="javascript:;" onclick="freezeOperator('{{d.data[i].id}}')">
					{{# if(d.data[i].status=='ACTIVE'){ }}
						禁用
					{{# }else{ }}
						启用
					{{# } }}
                 </a>
				 {{# } }}
                 </s:hasPermission>
				 <s:hasPermission name="pms:operator:delete">
				 {{# if(d.data[i].type==1){ }}
                 <a class="btn btn-default btn-xs del" href="javascript:;" data-id="{{d.data[i].id}}">
                 	删除
                 </a>
				 {{# } }}
                 </s:hasPermission>
		    </td>
		</tr>
	{{# } }}
</script>
<!-- /data-render -->
</body>
</html>
