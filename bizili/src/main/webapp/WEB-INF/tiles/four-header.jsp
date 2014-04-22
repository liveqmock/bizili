<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/taglib.inc" %>
<%@ include file="../inc/constants.inc" %>
	<script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.min.js"></script>

	<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap.min.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap-theme.min.css" media="all" />
<style type="text/css">
/* input { */
/* 	border:1px #ccc solid; */
/* } */
</style>
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
<div id="header">
	<div class="up">
		<p class="welcome">
			<c:if test="${sessionScope.security_context_user.name ne null}">您好，${sessionScope.security_context_user.name}，欢迎您！&nbsp;<a
					href="<c:url value="/j_spring_security_logout"/>" target="_self">退出</a>
			</c:if>
			<c:if test="${sessionScope.security_context_user.name eq null}">您好，欢迎您！&nbsp;请<a
					href="<c:url value="/user/userLogin.htm"/>" target="_self">登录</a>
			</c:if>
		</p>
		<ul class="topmenu">
			<li>我的平台</li>
			<li class="separation">|</li>
			<li>购物车0件</li>
			<li class="separation">|</li>
			<li>客服中心</li>
			<li class="separation">|</li>
			<li><span>快捷导航</span></li>
			<li class="separation">|</li>
			<li>我要买</li>
			<li class="separation">|</li>
			<li>我要卖</li>
		</ul>
	</div>
	<h1 class="epMcCenterTitle">
		<span></span>
	</h1>
</div>
