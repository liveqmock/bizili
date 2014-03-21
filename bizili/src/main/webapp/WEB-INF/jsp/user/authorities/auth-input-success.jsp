<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>权限管理</title>
<style type="text/css">
img {border-width: 0px 0px 0px 0px}
#middel{
	width:1340px;
}
#left{
	float:left;
}

#content{
	width:1200px;
}
#footer{
	width:1340px;
}
</style>
<%@ include file="/WEB-INF/inc/taglib.inc" %>
<%@ include file="/WEB-INF/inc/constants.inc" %>
<%@ include file="/WEB-INF/inc/script.inc" %>
<%@ include file="/WEB-INF/inc/style.inc" %>
<style type="text/css">
table.singleIncreasesResources{
	width:1235px;
	height:;
	/*border-top:1px #ccc solid;*/
	border-bottom:1px #ccc solid;
	font-weight:600;
}
table.singleIncreasesResources thead tr{
	line-height:30px;
	height:30px;
	border-bottom:1px #ccc dashed;
}
table.singleIncreasesResources thead tr td{
	padding-left:16px;
}
table.singleIncreasesResources tbody tr{
	height:40px;
	line-height:40px;
	border-bottom:1px #ccc dashed;
}
td.twoFont{
	width:105px;
	text-align:center;
	font-weight:600;
	color:#666;
}
td.star{
	width:65px;
	text-align:left;
	color:red;
}
td.widt{
	width:215px;
	font-weight:600;
	color:#666;
}
input{
	border:1px #ccc solid;
}
input.widt{
	width:210px;
}
input.szd{
	width:98px;
}
input.gsr{
	width:98px;
}
input.inputRadio{
	width:16px;
	height:16px;
	line-height:24px;
	margin-right:10px;
	margin-left:10px;
}
input.note{
	width:580px;
}
span.star{
	padding-left:10px;
	color:red;
}
input.determination{
	margin:10px 20px 20px 500px;
	border:0;
}
input.cancellation{
	margin:10px 20px 20px 0;
	border:0;
}

input.iptBuySteelAdd{
	width:13px;
	height:13px;
	margin-left:5px;
	background:transparent url('../images/tb2.gif') no-repeat scroll left top;
}
</style>
    <script type="text/javascript">
	function saveAuth(){
		var module = $('#moduleId').val();
		if ($.trim(module) == '') {
			aAlert('请您选择权限所属的模组。');
			return false;
		}
		var enabled = $('#enabled').val();
		if ($.trim(enabled) == '') {
			aAlert('请您选择是否默认URL。');
			return false;
		}
		var targetForm = $('#frm');
		targetForm.submit();
	}
	$(document).ready(function(){
		$('#addResource').click(function(){
			$.dialog({
				title:'权限资源查询',
				content:'url:<c:url value="/users/resources-list.htm"/>',
				width:820,
				height:620,
				min:true,
				max:true,
				esc:true,
				lock:true
			});
			//var returnValue = window.showModalDialog('<c:url value="/users/resources-list.htm"/>','', "dialogWidth=820px;dialogHeight=600px;status=no;help=no;scrollbars=no;dialogLeft:360px;dialogTop:140px");
// 			var ids = "";
// 			var names = "";
// 			var rets = returnValue.split(';');
// 			for(var i =0; i<rets.length-1; i++){
// 				var temp = rets[i].split('#');
// 				ids += temp[0]+",";
// 				names += temp[1]+",";
// 			}
// 			$('#urls').val(names);
// 			$('#urls').attr('readOnly',true);
// 			$('#resIds').val(ids);
		});
		
	});
	</script>
</head>
<body>
<div id="container">
	<div id="header">
		<jsp:include page="/WEB-INF/tiles/four-header.jsp" />
	</div>
	<div id="middel">
		<div id="left">
			<jsp:include page="/WEB-INF/tiles/four-left.jsp" />
		</div>
		<div id="content">
			<div id="epMcContent" class="epMcCtContent" style="margin-top:10px;margin-bottom:10px;">
	<h3 class="bordFont bigFont">权限管理</h3>
	<form name="frm" id="frm" action="authorities-input.htm" method="post">
			<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/users/authorities-initial.htm"/>">权限列表</a></li>
					<li><a href="<c:url value="/users/authorities-input.htm?init=true"/>" class="current">新增权限</a></li>
				</ul>
			</div>
			<table class="singleIncreasesResources">
	<tbody>
		<tr>
		<td class="twoFont">权限名 </td><td class="widt"><input class="widt" name="authName" type="text"/><span class="star">*</span></td><td class="star"></td>
		<td class="twoFont">权限描述</td><td class="widt"><input class="widt" name="authDesc" type="text" /><span class="star">*</span></td><td class="star"></td>
		</tr>
		<tr>
		<td class="twoFont">默认URL</td><td class="widt">
		<select id="enabled" name="enabled" style="width:120px;">
			<option value="">--请选择--</option>
			<option value="1">是</option>
			<option value="0">否</option>
		</select>
		<span class="star">*</span></td><td class="star"></td>
		<td class="twoFont">归属</td><td class="widt"><input class="widt" name="userId" type="text" />（属于哪个用户）</td><td class="star"></td>
		
		</tr>
		<tr>
		<td class="twoFont">模组</td><td class="widt">
		<select id="moduleId" name="moduleId" style="width:120px;">
			<option value="">--请选择--</option>
			<c:forEach items="${list}" var="module">
			<option value="${module.moduleId}">${module.moduleName}</option>
			</c:forEach>
		</select>
		<span class="star">*</span>（属于哪个菜单）</td><td class="star"></td>
		<td class="twoFont">动作</td><td class="widt"><input class="widt" name="action" type="text" />（该权限的默认URL）</td><td class="star"></td>
		</tr>
		<tr>
		<td class="twoFont">资源</td><td class="widt" colspan="5"><input type="hidden" name="resIds" id="resIds" value=""/><input type="text" name="urls" id="urls" value="" class="note" /><img id="addResource" src="../images/tb2.gif" style="margin-left:6px;cursor:pointer;"></img>（新增请输入，已有的请点击+选择）</td>
		</tr>
<!-- 		<tr> -->
<!-- 		<td class="twoFont">备注</td><td class="widt" colspan="5"><input type="text" name="description" class="widt" /></td> -->
<!-- 		</tr> -->
	</tbody>
	
	</table>
	<input type="button" class="determination" value="保存" onclick="saveAuth();"/>
	<input type="reset" class="cancellation" id="" value="取消" />
	</form>
</div>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="/WEB-INF/tiles/four-footer.jsp" />
	</div>
</div>

</body>
</html>
