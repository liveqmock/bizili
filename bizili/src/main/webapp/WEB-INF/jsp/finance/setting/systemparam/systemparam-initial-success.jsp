<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#queryButton').click(function(){
    		var queryForm = $('#queryForm');
    		queryForm.submit();
    	});
    });
    </script>
<style type="text/css">
table.tableInfor{
	width:1140px;
	margin:10px auto;
}
table.tableInfor tr{
	
	border-bottom:1px #ccc dashed;
	line-height:40px;
}
table.tableInfor tr td{
	color:#666;
}
table.tableInfor tr td img{
	margin-left:10px;
	vertical-align:middle;
}
table.tableInfor tr td.fourFont{
	width:124px;
	text-align:center;
	font-weight:600;
}
input.determination{
	display:block;
	margin-left:40%;
	margin-bottom:40px;
}
</style>
<script language="javascript" type="text/javascript" src="../js/dateSelect.js"></script>
<div id="epMcContent">
	<div class="epMcCtContent">
		<h3 class="titleBig bigFont bordFont">参数设置</h3>
		<p id="tipsYellow">为保障您的信息安全，部分信息不能在页面里修改，如要修改请拨打客服热线<span class="bordFont evenBigFont">400-666-6699</span></p>

	<table class="tableInfor">
		<tr>
			<td class="fourFont">公司名称：</td><td>上海华凌钢铁有限公司<a href="#"><img src="../images/tu7.gif" alt="" /></a><a href="#"><img alt="" src="../images/tu8.gif" /></a><a href="#"><img alt="" src="../images/tu9.gif" /></a></td>
		</tr>
		<tr>
			<td class="fourFont" style="height:80px;">公司LOGO：</td><td>从电脑中选择你喜欢的照片：你可以上传JPG、JPEG、GIF、PNG或BMP文件<br /><input type="text" style="border:1px #ccc solid;width:120px;position:relative;top:5px\0;"/><input type="button" class="browsing" value="浏览"/><input type="button" class="uploadLogo" value="上传logo" /></td>
		</tr>
		<tr>
			<td class="fourFont">所属地区：</td><td><select><option>所属省份</option></select><select style="margin-left:20px;"><option>所在城市</option></select></td>
		</tr>
		<tr>
			<td class="fourFont">负 责 人：</td><td>李丽</td>
		</tr>
		<tr>
			<td class="fourFont">电　　话：</td><td> 0541-45124100</td>
		</tr>
		<tr>
			<td class="fourFont">手机号码：</td><td>139485124100</td>
		</tr>
		<tr>
			<td class="fourFont">邮　　箱：</td><td>zh@sina.com</td>
		</tr>
		<tr>
			<td class="fourFont">传　　真：</td><td>0541-54125400</td>
		</tr>
		<tr>
			<td class="fourFont">会员地址：</td><td>上海市大连路1200号12楼</td>
		</tr>
		<tr>
			<td class="fourFont">邮　　编：</td><td>210054</td>
		</tr>
		<tr>
			<td class="fourFont">入场日期：</td><td>2011年2月1日</td>
		</tr>
		<tr>
			<td class="fourFont">到期日期：</td><td>2012年2月1日</td>
		</tr>
		<tr>
			<td class="fourFont">经营品种：</td><td></td>
		</tr>
		<tr>
			<td class="fourFont">会员等级：</td><td>交易会员</td>
		</tr>
	</table>
	<input type="submit" class="determination" id="" value="修改"/>
	</div>