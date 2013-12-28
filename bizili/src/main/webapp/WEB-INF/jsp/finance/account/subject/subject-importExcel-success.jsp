<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#sureImport').click(function(){
			var uploadFile = $('#uploadFile').val();
			if ($.trim(uploadFile) == '') {
				aAlert('请选择文件。');
				return false;
			}
			var index = uploadFile.lastIndexOf('.');
			var ext = (uploadFile.substring(index)).toLowerCase();
			if (ext != '.xls') {
				aAlert('导入文件格式不正确，请使用Excel文件。');
				return false;
			}
			var uploadForm = $('#uploadForm');
			uploadForm.submit();
		});
	});
</script>
<div id="epMcContent">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">批量上传</h3>
	<div class="tab">
			<ul class="tabMenu none bordFont floatUl normalFont">
				<li class="first"><a href="<c:url value="/account/subject-initial.htm"/>">科目列表</a></li>
				<li><a href="<c:url value="/account/subject-input.htm?init=true"/>">新增会计科目</a></li>
				<li><a href="<c:url value="/account/subject-importExcel.htm?init=true"/>" class="current">导入会计科目</a></li>
			</ul>
	<form id="uploadForm" enctype="multipart/form-data" action="subject-importExcel.htm" method="post">
	<input type="hidden" name="tokenName" value="${token_name}">
	<div id="tabcontent">
		<h3>提示：</h3>
		<p style="padding-left:10px;">导入科目前，请下载我们提供的Excel模板，并按照规范填写。<a href="<c:url value='/download/subject/subject.xls'/>">下载Excel科目模板</a></p>
		<p style="padding-left:10px;"> <input type="file" name="uploadFile" id="uploadFile" style="border:1px #ccc solid;height:22px;"> <input type="button" id="sureImport" class="imSure" value="确认导入" /></p>
	</div>
	</form>
	</div>
	<div class="heiTenpx"></div>
	</div>
</div>
