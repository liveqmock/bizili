<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<style type="text/css">
#container .epMcCtInnerTop h3{
	line-height:30px;
	padding-right:16px;
}
#container #epMcContent .operatorManage h3.floatRight{
	padding-right:260px;
}

input.iptBuySteelReducing{
	width:16px;
	height:16px;
	margin-left:10px;
	margin-right:5px;
	background:transparent url('../images/tb1.gif') no-repeat scroll left top;
}
input.iptBuySteelAdd{
	width:16px;
	height:16px;
	margin-left:5px;
	background:transparent url('../images/tb2.gif') no-repeat scroll left top;
}
input.inputSubmit{
	display:block;
	width:85px;
	height:24px;
	clear:both;
	margin:10px auto 50px;
	background:transparent url('../images/tb3.gif') no-repeat scroll left bottom ;
}
select#operatorNum{
	width:150px;
	font-size:12px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('saveRole').click(function(){
		var queryForm = $('#queryForm');
		queryForm.submit();
	});
});
</script>
</head>
<body>
<div id="epMcContent">

	<div class="epMcCtContent">
	<h3 class="bigFont bordFont blac">角色管理</h3>
		<div class="tab">
		<ul class="tabMenu none bordFont floatUl normalFont">
			<li class="first"><a href="<c:url value="/users/roles-initial.htm"/>">角色列表</a></li>
			<li><a class="current" href="<c:url value="/users/roles-input.htm?init=true"/>">新增角色</a></li>
		</ul>
	<div id="tabContent1">
	<form action="roles-input.htm" method="post" name="queryForm" id="queryForm">
		<table class="bugSteel first" >
		<tr>
			<td class="fivf floCenter">仓库：</td><td class="eigf"><input type="text" class="ef" /></td><td class="fivf floCenter">产地：</td><td class="eigf"><input type="text" class="ef"/></td><td class="fivf floCenter">排序：</td><td class="fotf"><select style="width:90px;" ><option>请选择</option></select>-<select style="width:64px;" ><option>升序</option></select></td>
			<td class="sixf"><input type="button" class="determination" value="查询" /></td>
			<td class="sixf"><input type="reset" class="cancellation" value="重置" /></td>
		</tr>
		</table>
		<table class="tableSteel">
			<tr class="title">
			<td class="twof">序号</td>
			<td class="fouf">角色名<span class="red">*</span></td>
			<td class="sixf">角色描述<span class="red">*</span></td>
			<td class="fouf">可用<span class="red">*</span></td>
			<td class="fouf">优先级<span class="red">*</span></td>
			<td class="eigf" style="border-right:1px #09f solid;">操作</td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[0].roleName" class="twf190" /></td><td><input type="text" name="roleList[0].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[0].enabled" class="tf" /></td><td><input type="text" name="roleList[0].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[1].roleName" class="twf190" /></td><td><input type="text" name="roleList[1].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[1].enabled" class="tf" /></td><td><input type="text" name="roleList[1].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[2].roleName" class="twf190" /></td><td><input type="text" name="roleList[2].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[2].enabled" class="tf" /></td><td><input type="text" name="roleList[2].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[3].roleName" class="twf190" /></td><td><input type="text" name="roleList[3].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[3].enabled" class="tf" /></td><td><input type="text" name="roleList[3].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[4].roleName" class="twf190" /></td><td><input type="text" name="roleList[4].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[4].enabled" class="tf" /></td><td><input type="text" name="roleList[4].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[5].roleName" class="twf190" /></td><td><input type="text" name="roleList[5].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[5].enabled" class="tf" /></td><td><input type="text" name="roleList[5].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[6].roleName" class="twf190" /></td><td><input type="text" name="roleList[6].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[6].enabled" class="tf" /></td><td><input type="text" name="roleList[6].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="roleList[7].roleName" class="twf190" /></td><td><input type="text" name="roleList[7].roleDesc" class="twf190" /></td><td><input type="text" name="roleList[7].enabled" class="tf" /></td><td><input type="text" name="roleList[7].priority" class="twf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
				<td colspan="8" class="floRight"><input type="button" class="newLine bordFont" value="新增一行" /></td>
			</tr> 
		  </table>
			<div>
				<input type="checkbox" class="pageCutSmallCheckbox" /><label for="#">全选</label><input type="submit" class="pageCutSmallBtnSub" id="saveRole" value="保存" /><input type="button" class="pageCutSmallBtnDel" id="" value="删除" />
			</div>
			</form>
		</div>
	</div>
	</div>
</div>
</body>
</html>