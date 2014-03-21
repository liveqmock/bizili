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
span.star{
	padding-left:10px;
	color:red;
}
</style>
<%@ include file="/WEB-INF/inc/taglib.inc" %>
<%@ include file="/WEB-INF/inc/constants.inc" %>
<%@ include file="/WEB-INF/inc/script.inc" %>
<%@ include file="/WEB-INF/inc/style.inc" %>
<script type="text/javascript">
    $(document).ready(function(){
    	$('#saveResBtn').click(function(){
    		var saveForm = $('#tabContent');
    		saveForm.submit();
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
			<div id="epMcContent">
	<div class="epMcCtContent">
	<h3 class="titleBig bigFont bordFont">资源管理</h3>
	<form id="tabContent" action="resources-input.htm" method="post">
				<div class="tab">
					<ul class="tabMenu none bordFont floatUl normalFont">
						<li class="first"><a href="<c:url value="/users/resources-initial.htm"/>">资源列表</a>
						</li>
						<li><a href="<c:url value="/users/resources-input.htm?init=true"/>"  class="current">新增资源</a></li>
					</ul>
				</div>
				<div id="tabContent1">
		
<!-- 		<table class="tableSteel"> -->
<!-- 			<tr class="title"> -->
<!-- 			<td class="twof">序号</td> -->
<!-- 			<td class="fouf">资源名<span class="red">*</span></td> -->
<!-- 			<td class="sixf">资源类型<span class="red">*</span></td> -->
<!-- 			<td class="fouf">资源URL<span class="red">*</span></td> -->
<!-- 			<td class="eigf">资源描述<span class="red">*</span></td> -->
<!-- 			<td class="fouf">是否可用<span class="red">*</span></td> -->
<!-- 			<td class="fouf">默认URL<span class="red">*</span></td> -->
<!-- 			<td class="eigf" style="border-right:1px #09f solid;">菜单中显示<span class="red">*</span></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 			<td class="twof"></td> -->
<!-- 			<td><input type="text" name="resourcesList[0].resourceName" class="twf" /></td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[0].resourceType"> -->
<!-- 					<option value="action">Action</option> -->
<!-- 					<option value="url">URL</option> -->
<!-- 					<option value="method">Method</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td><input type="text" name="resourcesList[0].resourceUrl" class="twf230" /></td> -->
<!-- 			<td><input type="text" name="resourcesList[0].resourceDesc" class="twf230" /></td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[0].enabled"> -->
<!-- 					<option value="1">是</option> -->
<!-- 					<option value="0">否</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[0].defaults"> -->
<!-- 					<option value="0">否</option> -->
<!-- 					<option value="1">是</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td > -->
<!-- 				<select name="resourcesList[0].showInMenu"> -->
<!-- 					<option value="0">否</option> -->
<!-- 					<option value="1">是</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 			<td class="twof"></td> -->
<!-- 			<td><input type="text" name="resourcesList[1].resourceName" class="twf" /></td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[1].resourceType"> -->
<!-- 					<option value="action">Action</option> -->
<!-- 					<option value="url">URL</option> -->
<!-- 					<option value="method">Method</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td><input type="text" name="resourcesList[1].resourceUrl" class="twf230" /></td> -->
<!-- 			<td><input type="text" name="resourcesList[1].resourceDesc" class="twf230" /></td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[1].enabled"> -->
<!-- 					<option value="1">是</option> -->
<!-- 					<option value="0">否</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td> -->
<!-- 				<select name="resourcesList[1].defaults"> -->
<!-- 					<option value="0">否</option> -->
<!-- 					<option value="1">是</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			<td > -->
<!-- 				<select name="resourcesList[1].showInMenu"> -->
<!-- 					<option value="0">否</option> -->
<!-- 					<option value="1">是</option> -->
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td colspan="8" class="floRight"><input type="button" class="newLine bordFont" title="暂不可用" value="新增一行" /></td> -->
<!-- 			</tr>  -->
<!-- 		  </table> -->
		  
		  
		  <table class="tableSteel">
			<tr>
			<td class="twof"></td>
			<td class="fivf">资源名</td>
			<td><input type="text" name="resourceName" class="twf190" /><span class="star">*</span></td>
			
			<td class="fivf">资源描述</td>
			<td><input type="text" name="resourceDesc" class="twf190" /><span class="star">*</span></td>
			<td class="twof"></td>
			</tr>
			<tr>
			<td class="twof"></td>
			<td class="fivf">资源URL</td>
			<td><input type="text" name="resourceUrl" class="twf190" /><span class="star">*</span></td>
			
			<td class="fivf">默认URL</td>
			<td>
				<select name="defaults">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			<span class="star">*</span></td>
			<td class="twof"></td>
			</tr>
			<tr>
			<td class="twof"></td>
			<td class="fivf">是否启用</td>
			<td>
					<select name="enabled">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
					<span class="star">*</span>
			</td>
			
			<td class="fivf">菜单中显示</td>
			<td>
					<select name="showInMenu">
						<option value="0">否</option>
						<option value="1">是</option>
					</select><span class="star">*</span></td>
			<td class="twof"></td>
			</tr>
			<tr>
			<td class="twof"></td>
			<td class="fivf">资源类型</td>
			<td class="tenf" colspan="3">
				<select name="resourceType">
					<option value="action">Action</option>
					<option value="url">URL</option>
					<option value="method">Method</option>
				</select>
			<span class="star">*</span></td>
			<td class="twof"></td>
			</tr>
		  </table>
		  
			<div>
				<input type="button" class="pageCutSmallBtnSub" id="saveResBtn" value="保存" />
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