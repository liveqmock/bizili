<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc" %>
<%@ include file="/WEB-INF/inc/constants.inc" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>欢迎</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="../js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js"></script>

	<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" media="all" />
	<link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css" media="all" />
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
			$.dialog.alert(msg,function(){});
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

  </head> 
  <body>

  <div class="container">
  <nav class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">微特</a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse">
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">链接</a></li>
      <li><a href="#">衣服</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">下拉菜单 <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
          <li class="divider"></li>
          <li><a href="#">One more separated link</a></li>
        </ul>
      </li>
    </ul>
    <form class="navbar-form navbar-left" role="search">
      <div class="form-group">
        <input type="text" class="form-control" placeholder="Search">
      </div>
      <button type="submit" class="btn btn-default">搜索</button>
    </form>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">Link</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
        </ul>
      </li>
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>
  </div>
  <div id="container">
  
  <div class="row">
  <div class="col-md-6"></div>
  <div class="col-md-3">
<!--   <form class="form-horizontal" role="form"> -->
<!--   <div class="form-group"> -->
<!--     <label for="inputEmail3" class="col-sm-2 control-label">Email</label> -->
<!--     <div class="col-sm-10"> -->
<!--       <input type="email" class="form-control" id="inputEmail3" placeholder="Email"> -->
<!--     </div> -->
<!--   </div> -->
<!--   <div class="form-group"> -->
<!--     <label for="inputPassword3" class="col-sm-2 control-label">Password</label> -->
<!--     <div class="col-sm-10"> -->
<!--       <input type="password" class="form-control" id="inputPassword3" placeholder="Password"> -->
<!--     </div> -->
<!--   </div> -->
<!--   <div class="form-group"> -->
<!--     <div class="col-sm-offset-2 col-sm-10"> -->
<!--       <div class="checkbox"> -->
<!--         <label> -->
<!--           <input type="checkbox"> Remember me -->
<!--         </label> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
<!--   <div class="form-group"> -->
<!--     <div class="col-sm-offset-2 col-sm-10"> -->
<!--       <button type="submit" class="btn btn-default">Sign in</button> -->
<!--     </div> -->
<!--   </div> -->
<!-- </form> -->
	<h3 class="text-center">平台登录</h3>   
        <form role="form" class="form-horizontal" id="login_form" name="login_form" action="${ctx}/j_spring_security_check" method="post">
        <input type="hidden" id="msg" value="<c:if test="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION ne null}">您输入的用户名或密码错误。</c:if>">
  <div class="form-group">
    <label for="username">用户名：</label>
    <input type="email" class="form-control" id="username" name="j_username" value="yinlei_nb" placeholder="邮箱或用户名">
  </div>
  <div class="form-group">
    <label for="password">用户名：</label>
    <input type="password" class="form-control" id="password" name="j_password" value="123456" placeholder="密码">
  </div>
<div class="form-group text-center">
  <input type="button" class="btn btn-primary" onclick="javascript:userLogin();" value="登录"/>
  <input name="reset" class="btn btn-default" value="取消" id="reset" type="reset"/>
</div>
</form>
        

  </div>
 <div class="col-md-3"></div>
 </div>
 </div>
 <div class="container">
 <div class="row">
 <ul class="list-inline text-center">
  <li>Copyright © 2012 www.vteba.com.</li>
  <li>浙ICP备gaga</li>
  <li>增值电信业务经营许可证编号：浙A1-g20120130</li>
</ul>
 </div>
 
 <div class="row">
 <ul class="list-inline text-center">
  <li>联系我们</li>
  <li>版权所有</li>
  <li>招贤纳士</li>
  <li>联系合作</li>
  <li>帮助中心</li>
  <li>友情链接</li>
</ul>
  
</div>
 </div>
  
  </body>
  </html>

