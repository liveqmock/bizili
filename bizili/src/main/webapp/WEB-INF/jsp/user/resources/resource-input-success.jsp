<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>新增资源</title>
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
<script type="text/javascript">
    $(document).ready(function(){
    	$('#allCheckSelected').click(function(){
    		var listSize = parseInt($('#listSize').val());
    		var all = $('#allCheckSelected');
    		if (all.attr('checked') == 'checked') {
    			for (var i=1; i <= listSize ; i++) {
        			$('#check'+i).attr("checked",true);
        		}
    		} else {
    			for (var i=1; i <= listSize ; i++) {
        			$('#check'+i).attr("checked",false);
        		}
    		}
    		
    	});
    	
    	$('#queryButton').click(function(){
    		var subjectCode = $('#subjectCode').val();
    		if ($.trim(subjectCode) != '' && !isInteger(subjectCode)) {
    			aAlert('科目代码填写错误。');
    			return false;
    		}
    		var subjectName = $('#subjectName').val();
    		if (isValidChar(subjectName)) {
    			aAlert('科目名含有非法字符。');
    			return false;
    		}
    		var queryForm = $('#queryForm');
    		queryForm.submit();
    	});
    });
    function resetForm() {
    	$('#subjectCode').val('');
    	$('#subjectName').val('');
    	$('#aidAccount').val('');
    	$('#majorCate').val('');
    	$('#state').val('');
    	$('#balanceDirection').val('');
    	$('#orderBy').val('subjectCode');
    	$('#ascDesc').val('asc');
    }
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
			<div id="epMcContent">
	<div class="epMcCtContent">
	<h3 class="titleBig bigFont bordFont">资源管理</h3>
	<form id="tabContent" action="resources-input.htm" method="post">
				<div class="tab">
					<ul class="tabMenu none bordFont floatUl normalFont">
						<li class="first"><a href="<c:url value="/users/resources-initial.htm"/>">资源列表</a>
						</li>
						<li><a href="<c:url value="/users/resources-input.htm"/>"  class="current">新增资源</a></li>
					</ul>
				</div>
				<div id="tabContent1">
		<table class="bugSteel first" >
		<tr class="first" >
			<td class="fivf floCenter" >品名：</td>
            <td class="tenf" ><input type="text" class="ef" /></td>
            <td class="fivf floCenter">材质：</td>
            <td class="fotf"> <select class="4f" style="width:160px;"><option class="4f"></option></select></td>
            <td class="fivf floCenter">规格：</td>
            <td class="twef"><input type="text" class="twf" /></td>
		</tr>
		<tr>
			<td class="fivf floCenter">仓库：</td><td class="eigf"><input type="text" class="ef" /></td><td class="fivf floCenter">产地：</td><td class="eigf"><input type="text" class="ef"/></td><td class="fivf floCenter">排序：</td><td class="fotf"><select style="width:90px;" ><option>请选择</option></select>-<select style="width:64px;" ><option>升序</option></select></td>
			<td class="sixf"><input type="button" class="determination" value="查询" /></td>
			<td class="sixf"><input type="reset" class="cancellation" value="重置" /></td>
		</tr>
		</table>
		<table class="tableSteel">
			<tr class="title">
			<td class="twof">序号</td>
			<td class="fouf">权限ID<span class="red">*</span></td>
			<td class="sixf">资源名<span class="red">*</span></td>
			<td class="fouf">资源类型<span class="red">*</span></td>
			<td class="eigf">资源URL<span class="red">*</span></td>
			<td class="fouf">可用<span class="red">*</span></td>
			<td class="fouf">默认URL<span class="red">*</span></td>
			<td class="eigf" style="border-right:1px #09f solid;">操作</td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="authList[0].authId" class="twf" /></td><td><input type="text" name="authList[0].resourceName" class="twf" /></td><td><input type="text" name="authList[0].resourceType" class="tf" /></td><td><input type="text" name="authList[0].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[0].enable" class="tf" /></td><td><input type="text" name="authList[0].defaults" class="tf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td><input type="text" name="authList[1].authId" class="twf" /></td><td><input type="text" name="authList[1].resourceName" class="twf" /></td><td><input type="text" name="authList[1].resourceType" class="tf" /></td><td><input type="text" name="authList[1].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[1].enable" class="tf" /></td><td><input type="text" name="authList[1].defaults" class="tf" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[2].authId" class="twf" /></td><td><input type="text" name="authList[2].resourceName" class="twf" /></td><td><input type="text" name="authList[2].resourceType" class="fif" /></td><td><input type="text" name="authList[2].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[2].enable" class="fof" /></td><td><input type="text" name="authList[2].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[3].authId" class="twf" /></td><td><input type="text" name="authList[3].resourceName" class="twf" /></td><td><input type="text" name="authList[3].resourceType" class="fif" /></td><td><input type="text" name="authList[3].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[3].enable" class="fof" /></td><td><input type="text" name="authList[3].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[4].authId" class="twf" /></td><td><input type="text" name="authList[4].resourceName" class="twf" /></td><td><input type="text" name="authList[4].resourceType" class="fif" /></td><td><input type="text" name="authList[4].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[4].enable" class="fof" /></td><td><input type="text" name="authList[4].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[5].authId" class="twf" /></td><td><input type="text" name="authList[5].resourceName" class="twf" /></td><td><input type="text" name="authList[5].resourceType" class="fif" /></td><td><input type="text" name="authList[5].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[5].enable" class="fof" /></td><td><input type="text" name="authList[5].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[6].authId" class="twf" /></td><td><input type="text" name="authList[6].resourceName" class="twf" /></td><td><input type="text" name="authList[6].resourceType" class="fif" /></td><td><input type="text" name="authList[6].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[6].enable" class="fof" /></td><td><input type="text" name="authList[6].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[7].authId" class="twf" /></td><td><input type="text" name="authList[7].resourceName" class="twf" /></td><td><input type="text" name="authList[7].resourceType" class="fif" /></td><td><input type="text" name="authList[7].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[7].enable" class="fof" /></td><td><input type="text" name="authList[7].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[8].authId" class="twf" /></td><td><input type="text" name="authList[8].resourceName" class="twf" /></td><td><input type="text" name="authList[8].resourceType" class="fif" /></td><td><input type="text" name="authList[8].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[8].enable" class="fof" /></td><td><input type="text" name="authList[8].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<td class="twof"><input type="checkbox" value="#" /></td>
				<td style="width: 82px"><input type="text" name="authList[9].authId" class="twf" /></td><td><input type="text" name="authList[9].resourceName" class="twf" /></td><td><input type="text" name="authList[9].resourceType" class="fif" /></td><td><input type="text" name="authList[9].resourceUrl" class="twf190" /></td><td><input type="text" name="authList[9].enable" class="fof" /></td><td><input type="text" name="authList[9].defaults" class="fof" /></td><td class="tenf"><input type="button" class="tableSteelBtnDel" /><input type="button" value="复制" class="tableSteelBtnCopy" /></td>
			</tr>
			<tr>
				<td colspan="8" class="floRight"><input type="button" class="newLine bordFont" value="新增一行" /></td>
			</tr> 
		  </table>
			<div>
				<input type="checkbox" class="pageCutSmallCheckbox" /><label for="#">全选</label><input type="submit" class="pageCutSmallBtnSub" id="" value="保存" /><input type="button" class="pageCutSmallBtnDel" id="" value="删除" />
			</div>
		</div>
		
	</form>	
	</div>
	</div>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="/WEB-INF/tiles/four-footer.jsp" />
	</div>
</div>

</body>
</html>