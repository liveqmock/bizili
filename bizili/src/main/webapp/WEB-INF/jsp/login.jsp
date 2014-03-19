<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc" %>
<%@ include file="/WEB-INF/inc/constants.inc" %>

<script type="text/javascript" src="../js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.alerts.js"></script>
<script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="../js/commonUtils.js"></script>
<script type="text/javascript" src="../js/numberFormat.js"></script>

<link rel="stylesheet" type="text/css" href="../css/reset.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../css/global.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../css/epMCLayout.css" media="all" />		
<link rel="stylesheet" type="text/css" href="../css/font.css"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>欢迎</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<style type="text/css">
	input {
	border:1px #ccc solid;
	height:20px;
	}
	</style>
	<script language="javascript">
	function changeValidateCode(obj) {
     //获取当前的时间作为参数，防止缓存
     var timenow = new Date().getTime();  
     //每次请求需要一个不同的参数，否则可能会返回同样的验证码  
     //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了  
     obj.src="randomNumber.htm?d="+timenow; 
  	}
	function userLogin(){
		var form = document.forms['login_form'];
		form.submit();
	}
	$(document).keypress(function(event) {
	    switch (event.keyCode) {
	        case 13:
	        	var forms = $('#login_form');
	        	forms.submit();
	    }
	});
	$(document).ready(function(){
		var msg = $('#msg').val();
		if ($.trim(msg) != '') {
			aAlert(msg);
		}
		
		$('#sureImport').click(function(){
			var uploadForm = $('#uploadForm');
			
			uploadForm.submit();
		});
		
		$('#loginTest').click(function(){
			$.dialog({
				title:'我是新标题',
				min:true,
				max:true,
				lock:true});
		});
	});
	</script>
	<!-- 
	<script language="javascript" for="document" event="onkeydown">
		//将用户的enter转化为tab键,但在 button,submit,reset,textarea上除外
		if (event.keyCode == 13 && event.srcElement.type != 'button'
				&& event.srcElement.type != 'submit'
				&& event.srcElement.type != 'reset'
				&& event.srcElement.type != 'textarea'
				&& event.srcElement.type != '') {
			event.keyCode = 9;
		}
	</script> -->
  </head> 
  <body>
  <div id="container">
  <div id="header">
			<div class="up">
				<p class="welcome">
					您好，欢迎您的到来！
					<a href="<c:url value="/j_spring_security_logout"/>" target="_blank">退出</a>
					<a href="<c:url value="/user/userLogin.htm"/>" target="_blank">登录</a>
				</p>
				<ul class="topmenu">
					<li>
						我的会员中心
					</li>
					<li class="separation">|</li>
					<li>
						购物车0件
					</li>
					<li class="separation">|</li>
					<li>
						客服中心
					</li>
					<li class="separation">|</li>
					<li>
						<span>快捷导航</span>
					</li>
					<li class="separation">|</li>
					<li>
						我要买
					</li>
					<li class="separation">|</li>
					<li>
						我要卖
					</li>
				</ul>
			</div>
			
			<h1 class="epMcCenterTitle"><span>企业会员中心</span></h1>
		</div>  
	<div id="content">
        <div id="right" style="float:right;">
        <h2 style="float:left;margin-left:150px;margin-bottom:10px;">平台登录</h2>   
        <form id="login_form" name="login_form" action="${SERVLET_PATH}/j_spring_security_check" method="post">
        
        <input type="hidden" id="msg" value="<c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION ne null}">您输入的用户名或密码错误。</c:if>">
            <table width="500" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th>用户名称</th><!-- ${sessionScope['SPRING_SECURITY_LAST_USERNAME']} -->
                    <td><input id="username" maxlength="64" style="width:170px;" name="j_username" type="text" value="yinlei_nb" />
					</td>
                </tr>
                <tr style="height:20px;">
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <th>用户密码</th>
                    <td><input id="password" maxlength="64" value="123456" style="width:170px" name="j_password" type="password" />&nbsp;&nbsp;</td>
                </tr>
                <!-- 
                <tr>
                	<th>验证码</th>
                	<td><input type="text" maxlength="6" style="width:170px" id="authCode" name="j_authcode">&nbsp;&nbsp;<img src="randomNumber.htm" onclick="changeValidateCode(this)" alt="看不清楚？点击刷新"></img></td>
                </tr> -->
                <!-- 
                <tr>
                	<td></td>
                    <td><a class="icon arrow" href="${SERVLET_PATH}/jsp/forgotpassword.jsp">忘记密码？</a>&nbsp;<input id="remember" name="_spring_security_remember_me" value="true" type="checkbox" class="field" />两周之内记住我！</td>
                </tr>
                 -->
                <tr style="height:20px;">
                    <td colspan="2"></td>
                </tr>
                <tr style="margin-top:20px;">
                	<td></td>
                    <td><input name="login" class="pageCutSmallButton" value="登 录" id="login" onclick="javascript:userLogin();" type="button"/>&nbsp;&nbsp;<input name="reset" class="cancellation" value="重置" id="reset" type="reset"/></td>
                </tr>
            </table>
            <div>
            	
        	</div>         
   		</form>
   		
<!--    		<form id="uploadForm" method="post" enctype="multipart/form-data" action="subject-importExcel2.htm"> -->
<!-- 	<div id="tabcontent"> -->
<!-- 		<h3>提示：</h3> -->
<!-- 		<p>导入科目前，请下载我们提供的Excel模板，并按照规范填写。<a href="#">下载Excel表格模板</a></p> -->
<!-- 		<p> <input type="file" name="uploadFile"/> -->
<!-- 		<input type="button" id="sureImport" class="imSure" value="确认导入" /></p> -->
<!-- 	</div> -->
<!-- 	</form> -->
    </div>        
</div>
  </div>
 <div id="footer"> 
  <table style="width:1000px;">
				<thead>
					<tr>
						<th>关于我们</th>
						<td>|</td>
						<th>新手指南</th>
						<td>|</td>
						<th>评价体系</th>
						<td>|</td>
						<th>订购支付</th>
						<td>|</td>
						<th>服务条款</th>
						<td>|</td>
						<th>网站地图</th>
						<td>|</td>
						<th>帮助中心</th>
						<td>|</td>
						<th>友情链接</th>
						<td>|</td>
						<th>诚聘英才</th>
						<td>|</td>
						<th>意见反馈</th>
						<td>|</td>
						<th>广告业务</th>
					</tr>
				</thead>
			</table>
			<ul class="copyright">
				<li>
					Copyright © 2012 www.skmbw.com.
				</li>
				<li>
					浙ICP备96963
				</li>
				<li>
					增值电信业务经营许可证编号：浙A1-20120130
				</li>
			</ul>
	</div>
  </body>
  </html>

