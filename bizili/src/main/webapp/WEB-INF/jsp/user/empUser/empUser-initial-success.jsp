<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
<script type="text/javascript" src="<c:url value='/dwr/interface/EmpUserBean.js'></c:url>"></script>
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
    	
    	//查询
    	$('#queryButton').click(function(){
    		var queryForm = $('#queryForm');
    		queryForm.attr('action','empUser-initial.htm');
    		$('#ajaxForm').val('2');//设为2，普通查询
    		queryForm.submit();
    	});
    	
    	//ajax异步提交
    	$('#queryForm').ajaxForm({
			beforeSubmit:function(){
				var ajax = $('#ajaxForm').val();
				if ($.trim(ajax) == '1') {//批量删除
					if(!isChecked("ids")){
						aAlert("至少选中一条数据。");
						return false;
					}
					var ret = confirm('您确定删除要全部删除吗？');
					if (!ret) {
						return false;
					}
				}//other
				
			},
			success:function(data) {
				var ajax = $('#ajaxForm').val();
				if(data==1){
					if ($.trim(ajax) == '1') {//批量删除
						$("input:checked").attr("checked",false);
		    			var arrayIds = $('#deletedIds').val().split(',');
		    			for (var i=0; i < (arrayIds.length -1); i++) {
		    				$('#tr'+arrayIds[i]).remove();
		    			}
					}
					
				}else{
					if ($.trim(ajax) == '1') {
						aAlert(data);
					}
				}
			} 
		});
    	//批量删除
    	$('#deleteAll').click(function(){
    		var form = $('#queryForm');
    		form.attr('action','empUser-delete.htm');
    		$('#ajaxForm').val('1');//设为1，批量删除
    		form.submit();
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
    });
 
 //修改实体
 function editUser(id) {
	 window.location.href = 'empUser-input.htm?init=true&model.userId='+id;
 }
 
 //单条删除实体
 function deleteUser(userId) {
	 EmpUserBean.deleteUser(userId,function(data){
		 if (data == 'success') {
			 $('#tr'+userId).remove();//在页面上删除那一行
			 aAlert('删除成功。');
		 }
	 });
 }
</script>

<div id="epMcContent" style="margin-bottom:10px;">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">用户管理</h3>
	<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/users/empUser-initial.htm"/>" class="current">用户列表</a></li>
					<li><a href="<c:url value="/users/empUser-roles.htm?init=true"/>">我的角色</a></li>
					<li><a href="<c:url value="/users/empUser-input.htm?init=true"/>">新增用户</a></li>
				</ul>
				<form action="empUser-initial.htm" id="queryForm" name="queryForm" method="post">
				<input type="hidden" id="listSize" value="${listResult.size()}">
				<input type="hidden" id="deletedIds" value=""><!-- 要被批量删除的实体id -->
				<input type="hidden" id="ajaxForm" value="">
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
                <td class="fouf">用户名</td>
                <td class="fivf">Email</td>
                <td class="fivf">部门代码</td>
                <td class="fivf">部门名称</td>
                <td class="fouf">账号</td>
                <td class="fivf">可用</td>
                <td class="sixf">创建时间</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              
              <c:forEach items="${listResult}" var="empUser" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}" id="tr${empUser.userId}">
                <td class="twof"><input type="checkbox" name="ids" value="${empUser.userId}" id="check${st.count}"/></td>
                <td class="fouf">${empUser.name}</td>
                <td class="sixf">${empUser.email}</td>
                <td class="fivf">${empUser.deptId}</td>
                <td class="sixf">${empUser.deptName}</td>
                <td class="fouf">${empUser.userAccount}</td>
                <td class="fivf">${empUser.enable}</td>
                <td class="fouf"><fmt:formatDate value="${empUser.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td class="fivf"><input type="button" onclick="javascript:editUser('${empUser.userId}')" title="修改" class="tableSteelBtnEdit" />&nbsp;<input type="button" onclick="javascript:deleteUser('${empUser.userId}')" title="删除" class="tableSteelBtnDel" /></td>
              </tr>
              </c:forEach>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="checkbox" id="allCheckSelected" />
          <label for="allCheckSelected" style="position:relative ;bottom:5px;">全选</label>
          <!-- <input type="button" class="pageCutSmallBtnCheckout" id="input2" value="确定导入" /> -->
          <input type="button" class="pageCutSmallBtnDel" id="deleteAll" value="删除" />
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

