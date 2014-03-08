<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>会计科目列表</title>
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
<script type="text/javascript" src="<c:url value='/dwr/interface/RolesBean.js'></c:url>"></script>
    <script type="text/javascript">
    
    $(document).ready(function(){
    	$('#allCheckSelected').click(function(){
    		var listSize = parseInt($('#listSize').val());
    		var all = $('#allCheckSelected');
    		if (all.attr('checked') == 'checked') {
    			for (var i=1; i <= listSize ; i++) {
        			$('#check'+i).attr("checked",true);
        		}
    		} else {
    			for (var i=1; i <= listSize ; i++) {
        			$('#check'+i).attr("checked",false);
        		}
    		}
    		
    	});
      	//是否选中
    	isChecked = function(chekedElementName){
    		var flag = false;
    		var element = document.getElementsByName(chekedElementName);
    		var tempIds = "";
    		if (element.length > 0) {
    			for (var i = 0; i < element.length; i++) {
    				if(element[i].checked){
    					flag = true;
    					tempIds += element[i].value + ',';//记录下被删除对象的id，以备后续在页面上删除
    				}
    			}
    		}
    		$('#deletedIds').val(tempIds);
    		return flag;
    	};
    	$('#queryButton').click(function(){
    		$("#queryForm").submit();
    	});
    });
    
    //查询角色的权限
    function queryAuth(roleId){
    	window.showModalDialog('<c:url value="/users/roles-roleAuthList.htm?roleId='+roleId+'"/>','', "dialogWidth=810px;dialogHeight=600px;status=no;help=no;scrollbars=no;dialogLeft:360px;dialogTop:140px");
	}
    
    //删除单笔数据
    function deleteRole(roleId){
    	var result = confirm('您确定要删除角色？');
    	if (result) {
    		RolesBean.deleteRole(roleId,function(data){
        		if (data == 'inused') {
        			aAlert('角色使用中，无法删除！');
        		} else if(data == 'success') {
        			$('#tr'+ roleId).remove();
        			aAlert('角色删除成功！');
        		}
        	});
    	}
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
			<h3 class="bordFont bigFont">角色管理</h3>
			<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/users/roles-initial.htm"/>" class="current">角色列表</a>
					</li>
					<li><a href="<c:url value="/users/roles-input.htm?init=true"/>">新增角色</a></li>
				</ul>
				<div class="">
					<form action="roles-initial.htm" id="queryForm" name="queryForm" method="post">
					<input type="hidden" id="listSize" value="${listResult.size()}">
					<input type="hidden" id="deletedIds" value="">
						<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td class="twof">&nbsp;&nbsp;角色名</td>
								<td class="twef">
								<input type="text" name="roleName" class="tf" />
								</td>
								<td class="twof">说明</td>
								<td class="twef">
								<input type="text" name="roleDesc" class="tf" />
								</td>
								<td class="twof">使用中</td>
								<td class="twef">
								<select id="defaults" name="defaults" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="1">是</option>
										<option value="0">否</option>
								</select>
								</td>
								<td class="twof">排序</td><td class="fotf"><select style="width:90px;" name="page.orderBy"><option value="roleName">角色名</option><option value="roleDesc">描述</option></select>-<select style="width:70px;" name="page.ascDesc"><option value="asc">升序</option><option value="desc">降序</option></select></td>
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
                <td class="fivf">角色名</td>
                <td class="fivf">描述</td>
                
                <td class="fivf">使用中</td>
                <td class="sixf">优先级</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              <c:forEach items="${listResult}" var="role" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}" id="tr${role.roleId}">
                <td class="twof"><input type="checkbox" name="ids" value="${role.roleId}" id="check${st.count}"/></td>
                <td class="fouf">${role.roleId }</td>
                <td class="sixf">${role.roleName }</td>
                <td class="fivf">${role.roleDesc }</td>
                
                <td class="sixf">${role.enabled }</td>
                <td class="fouf">${role.priority }</td>
                <td class="fivf"><input type="button" class="pageCutSmallBtnDel" title="权限" style="font-weight:100;margin-left:0px;width:27px;" onclick="javascript:queryAuth('${role.roleId}');" value="权限" />&nbsp;<input type="button" onclick="javascript:editUser('${role.roleId}')" title="修改" class="tableSteelBtnEdit" />&nbsp;<input type="button" onclick="javascript:deleteRole('${role.roleId}')" title="删除" class="tableSteelBtnDel" /></td>
              </tr>
              </c:forEach>
            </table>
					<div>
						<input type="checkbox" id="allCheckSelected" class="pageCutSmallCheckbox" />
							<label for="allCheckSelected">全选 </label> <input type="button" class="cancellation" value="删除" />
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
		</div>
	</div>
	<div id="footer">
		<jsp:include page="/WEB-INF/tiles/four-footer.jsp" />
	</div>
</div>

</body>
</html>
