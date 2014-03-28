<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>资源管理</title>
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
    	$('#queryButton').click(function(){
    		$('#query').val('true');
    		$("#queryForm").submit();
    	});
    });
    function deleteRes(id) {
    	$.dialog.confirm('你确定要删除这条资源吗？', function(){
			$.ajax({
	            type:"post",
	            dataType:"text",
	            url: '${ctx}/users/resources-delete.htm?resourceId=' + id,
	            success: function(msg){
	            	$('#tr'+id).remove();
	            },
	            error: function (msg) {
	                alert(msg.responseText);
	            }
	        });
    	}, function(){
    	    $.dialog.tips('您放弃删除。');
    	});
	}
    
  	//修改实体
    function editRes(id) {
   	 window.location.href = 'resources-input.htm?init=true&resourceId='+id;
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
			<h3 class="bordFont bigFont">资源管理</h3>
			<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/users/resources-initial.htm"/>" class="current">资源列表</a>
					</li>
					<li><a href="<c:url value="/users/resources-input.htm?init=true"/>">新增资源</a></li>
				</ul>
				<div class="">
					<form action="resources-initial.htm" id="queryForm" name="queryForm" method="post">
					<input type="hidden" name="query" id="query" value="false"/>
						<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td class="twof">资源名</td>
								<td class="eigf"><input type="text" name="resourceName" class="tf" /></td>
								<td class="twof">资源类型</td>
								<td class="twef">
								<select id="resourceType" name="resourceType" style="width:140px;">
										<option value="">--请选择--</option>
										<option value="url">URL</option>
										<option value="action">Action</option>
										<option value="method">Method</option>
								</select>
								</td>
								<td class="twof">资源URL</td>
								<td class="twef"><input type="text" name="resourceUrl" style="width:160px;"/></td>
								<td class="twof">菜单模块</td>
								<td class="twef">
								<select id="moduleId" name="moduleId" style="width:120px;">
									<option value="">--请选择--</option>
									<c:forEach items="${list}" var="module">
										<option value="${module.moduleId}" <c:if test="${module.moduleId == resources.moduleId}">selected="selected"</c:if> >${module.moduleName}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td class="twof">是否启用</td>
								<td class="twef">
								<select id="enabled" name="enabled" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="1">是</option>
										<option value="0">否</option>
								</select>
								</td>
								<td class="twof">默认URL</td>
								<td class="twef">
								<select id="defaults" name="defaults" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="true">是</option>
										<option value="false">否</option>
								</select>
								</td>
								<td class="twof">排序</td><td class="fotf"><select style="width:90px;" name="page.orderBy"><option value="resourceUrl">资源URL</option><option value="resourceType">资源类型</option></select>-<select style="width:70px;" name="page.ascDesc"><option value="asc">升序</option><option value="desc">降序</option></select></td>
								<td class="twof"></td>
								<td class="twef">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
									<input type="reset" value="清除" class="cancellation" />
								</td>
							</tr>
						</table>
					
					<table class="tableSteel">
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="twof"></td>
                <td class="fouf">资源名</td>
                <td class="fivf">资源类型</td>
                <td class="fivf">资源URL</td>
                <td class="fouf">资源描述</td>
                <td class="fivf">是否可用</td>
                <td class="fivf">默认URL</td>
                <td class="fivf">菜单模块</td>
                <td class="sixf">菜单中显示</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              <c:forEach items="${listResult}" var="resource" varStatus="st">
              <tr id="tr${resource.resourceId}" style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="twof"></td>
                <td class="fouf">${resource.resourceName}</td>
                <td class="sixf">${resource.resourceType }</td>
                <td class="fivf">${resource.resourceUrl }</td>
                <td class="fouf">${resource.resourceDesc }</td>
                <td class="fivf"><c:if test="${resource.enabled eq 1}">是</c:if><c:if test="${resource.enabled eq 0}">否</c:if></td>
                <td class="sixf"><c:if test="${resource.defaults == true}">是</c:if><c:if test="${resource.defaults == false}">否</c:if></td>
                <td class="sixf">
                <c:forEach items="${list}" var="module">
					<c:if test="${resource.moduleId eq module.moduleId}">${module.moduleName}</c:if>
				</c:forEach>
                </td>
                <td class="fouf"><c:if test="${resource.showInMenu == true}">是</c:if><c:if test="${resource.showInMenu == false}">否</c:if></td>
                <td class="fivf"><img src="../images/btn_edit.gif" style="margin-left:4px;margin-top:4px;cursor:pointer;" onclick="javascript:editRes('${resource.resourceId}')" title="修改"/>
                <img src="../images/tu12.gif" style="margin-left:4px;margin-top:4px;cursor:pointer;" onclick="javascript:deleteRes('${resource.resourceId}')" title="删除"/></td>
              </tr>
              </c:forEach>
            </table>
					<div id="page">
						<p>
						<vte:pages hasForm="true" beanName="page" formName="queryForm"/>
						</p>
					</div>
					</form>
				</div>


			</div>
			<div class="clear"></div>
			<div class="heiTenpx"></div>
		</div>
		<div class="heiTenpx"></div>
	</div>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="/WEB-INF/tiles/four-footer.jsp" />
	</div>
</div>

</body>
</html>
