<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../inc/taglib.inc"%>
<html>
<head>
<title>用户管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$('#queryButton').click(function(){
		var queryForm = $('#queryForm');
		queryForm.submit();
	});
});
</script>
</head>
<body>
<div id="epMcContent" style="margin-bottom:10px;">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">用户管理</h3>
	<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/users/empUser-initial.htm"/>">用户列表</a></li>
					<li><a class="current" href="<c:url value="/users/empUser-roles.htm?init=true"/>">我的角色</a></li>
					<li><a href="<c:url value="/users/empUser-input.htm?init=true"/>">新增用户</a></li>
				</ul>
				<form action="empUser-roles.htm" id="queryForm" name="queryForm" method="post">
		<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td class="twof">用户名</td>
								<td class="eigf"><input type="text" name="name" class="tf" /></td>
								<td class="twof">资源类型</td>
								<td class="twef">
								<select id="resourceType" name="resourceType" style="width:140px;">
										<option value="">--请选择--</option>
										<option value="action">Action</option>
										<option value="url">URL</option>
										<option value="method">Method</option>
								</select>
								</td>
								<td class="twof">账号</td>
								<td class="twef"><input type="text" name="userAccount" style="width:160px;"/></td>
								<td class="twof">Email</td>
								<td class="twef"><input type="text" name="email"/></td>
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
								<td class="twof">排序</td><td class="fotf"><select style="width:90px;" name="page.orderBy"><option value="name">用户名</option><option value="email">Email</option><option value="userAccount">账号</option></select>-<select style="width:70px;" name="page.ascDesc"><option value="asc">升序</option><option value="desc">降序</option></select></td>
								<td class="twof"></td>
								<td class="twef">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
									<input type="reset" value="清除" class="cancellation" />
								</td>
							</tr>
						</table>
	
			<table class="tableSteel">
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="twof">序号</td>
                <td class="fouf">角色名</td>
                <td class="fivf">描述</td>
                <td class="fivf">可用</td>
                <td class="fouf">用户</td>
                <td class="sixf">创建时间</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              
              <c:forEach items="${list}" var="role" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="twof"><input type="checkbox" /></td>
                <td class="fouf">${role.roleName}</td>
                <td class="sixf">${role.roleDesc}</td>
                <td class="fouf"><c:if test="${role.enabled eq 1}">是</c:if><c:if test="${role.enabled ne 1}">否</c:if></td>
                <td class="fivf">${userName}</td>
                <td class="fouf"><fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td class="fivf"><input type="button" class="pageCutSmallBtnDel" id="input4" value="删除" /></td>
              </tr>
              </c:forEach>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="checkbox" id="pageCutSmallCheckbox1" />
          <label for="pageCutSmallCheckbox1" style="position:relative ;bottom:5px;">全选</label>
          <input type="button" class="pageCutSmallBtnCheckout" id="input2" value="确定导入" />
          <input type="button" class="pageCutSmallBtnDel" id="input23" value="删除" />
		  </p>
		  <div id="page">
			<p>
				<vte:pages hasForm="true" beanName="page" formName="queryForm"/>
			</p>
		  </div>
		  </form>
	</div>
	</div>
	</div>
</body>
</html>
