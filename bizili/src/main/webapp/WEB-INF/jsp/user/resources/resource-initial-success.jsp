<%@ page language="java" pageEncoding="UTF-8"%>
<!-- relative the file position, parent level,parent level,parent level, and equals inc level -->
<%@ include file="../../../inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>      
    <title></title>

    <script type="text/javascript">
    $(document).ready(function(){
    	$('#queryButton').click(function(){
    		$("#queryForm").submit();
    	});
    });
    </script>
  </head> 
  <body>
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
						<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td class="twof">资源名</td>
								<td class="eigf"><input type="text" name="resourceName" class="tf" /></td>
								<td class="twof">资源类型</td>
								<td class="twef">
								<select id="resourceType" name="resourceType" style="width:140px;">
										<option value="">--请选择--</option>
										<option value="action">Action</option>
										<option value="url">URL</option>
										<option value="method">Method</option>
								</select>
								</td>
								<td class="twof">资源URL</td>
								<td class="twef"><input type="text" name="resourceUrl" style="width:160px;"/></td>
								<td class="twof">权限ID</td>
								<td class="twef"><input type="text" name="authId"/></td>
							</tr>
							<tr>
								<td class="twof">使用中</td>
								<td class="twef">
								<select id="enable" name="enable" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="true">是</option>
										<option value="false">否</option>
								</select>
								</td>
								<td class="twof">默认URL</td>
								<td class="twef">
								<select id="defaults" name="defaults" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="1">是</option>
										<option value="0">否</option>
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
                <td class="fouf">序号</td>
                <td class="fivf">权限ID</td>
                <td class="fivf">资源名</td>
                <td class="fouf">资源类型</td>
                <td class="fivf">资源URL</td>
                <td class="fivf">使用中</td>
                <td class="sixf">默认URL</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              <c:forEach items="${listResult}" var="authResource" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="twof"><input type="checkbox" /></td>
                <td class="fouf">${authResource.id }</td>
                <td class="sixf">${authResource.authId }</td>
                <td class="fivf">${authResource.resourceName }</td>
                <td class="fouf">${authResource.resourceType }</td>
                <td class="fivf">${authResource.resourceUrl }</td>
                <td class="sixf">${authResource.enable }</td>
                <td class="fouf"><c:if test="${authResource.defaults eq 1}">是</c:if><c:if test="${authResource.defaults eq 0}">否</c:if></td>
                <td class="fivf"><input type="button" class="pageCutSmallBtnDel" id="input4" value="删除" /></td>
              </tr>
              </c:forEach>
            </table>
					<div>
						<input type="checkbox" class="pageCutSmallCheckbox" />
							<label for="#"> 全选 </label> <input type="button"
							class="pageCutSmallButton" id="" value="发布到店铺" />
					</div>
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
</body>
</html>
