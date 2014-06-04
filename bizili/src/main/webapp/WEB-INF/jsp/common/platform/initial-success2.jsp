<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>我的平台</title>
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
			<div class="epMcCenter floatLeft">
	<div class="company">
	<img alt="###" src="../images/t1.jpg" class="floatLeft" />
		<div>
		<dl class="floatRight">
			<dt class="bigFont">杭州利东软件科技有限公司</dt>
			<dd >上次登录：2011年02月22日 09:45</dd>
			<dt>卖家信用：<img src="../images/tu1.gif" /><img src="../images/tu1.gif" alt=""/><img src="../images/tu1.gif" /></dt>
			<dt>买家信用：<img src="../images/tu1.gif" /><img src="../images/tu1.gif" /><img src="../images/tu1.gif" /></dt>
			<dt class="none">会员等级：<a class="bordFont blue" href="#">注册交易会员</a>&nbsp;&nbsp;&nbsp; <a href="#" class="why blue">如何升级?</a></dt>
		</dl>
		</div>
	</div><!--[if !IE]>end .company<![endif]-->
	<div class="quickEntrance">
		<h3 class="bigFont bordFont">快捷入口</h3>
		<table>
			<tr>
				<td ><a href="#">我要支付</a></td>
				<td ><a href="#">我要提款</a></td>
				<td ><a href="#">我的仓库</a></td>
				<td ><a href="#">超市挂牌</a></td>
				<td ><a href="#">商城挂牌</a></td>
			</tr>
		</table>
	</div><!--[if !IE]>end .company<![endif]-->
	<div class="operationReminder " style="margin-bottom:10px;">
	<h3 class=" bigFont bordFont">操作提醒</h3>
	<ul>
		<li><h4>我是卖家</h4>
			<ul>
				<li>待审核资源<span>（0）</span></li><li>议价留言<span>（0）</span></li><li><a href="#" target="_top">已生成合同<span>（2）</span></a></li><li><a href="#" target="_top">确认过户<span>（1）</span></a></li><li><a href="#" target="_top">确认实提量<span>（1）</span></a></li><li><a href="#" target="_top">开票确认<span>（1）</span></a></li><li><a href="#" target="_top">等待评价<span>（1）</span></a></li>
			</ul>
		</li>
		<li><h4>我是买家</h4>
			<ul>
				<li><a href="#" target="_top">议价留言<span>（2）</span></a></li><li><a href="#" target="_top">已生成合同<span>（2）</span></a></li><li>等待支付<span>（0）</span></li><li>确认过户<span>（0）</span></li><li>打印提单<span>（0）</span></li><li><a href="#" target="_top">确认实提量<span>（1）</span></a></li><li><a href="#" target="_top">到票确认<span>（1）</span></a></li><li><a href="#" target="_top">等待评价<span>（1）</span></a></li>
			</ul>
		</li>
	</ul>
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
