<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>      
    <title>会计科目</title>
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
    	
    	$('#queryButton').click(function(){
    		var subjectCode = $('#subjectCode').val();
    		if ($.trim(subjectCode) != '' && !isInteger(subjectCode)) {
    			aAlert('科目代码填写错误。');
    			return false;
    		}
    		var subjectName = $('#subjectName').val();
    		if (isValidChar(subjectName)) {
    			aAlert('科目名含有非法字符。');
    			return false;
    		}
    		var queryForm = $('#queryForm');
    		queryForm.submit();
    	});
    });
    function resetForm() {
    	$('#subjectCode').val('');
    	$('#subjectName').val('');
    	$('#aidAccount').val('');
    	$('#majorCate').val('');
    	$('#state').val('');
    	$('#balanceDirection').val('');
    	$('#orderBy').val('subjectCode');
    	$('#ascDesc').val('asc');
    }
    </script>
    </head>
  <body>
<div id="epMcContent" style="margin-bottom:10px;">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">会计科目</h3>
	<div class="tab">
				<ul class="tabMenu none bordFont floatUl normalFont">
					<li class="first"><a href="<c:url value="/account/subject-initial.htm"/>" class="current">科目列表</a></li>
					<li><a href="<c:url value="/account/subject-input.htm?init=true"/>">新增会计科目</a></li>
					<li><a href="<c:url value="/account/subject-importExcel.htm?init=true"/>">导入会计科目</a></li>
				</ul>
		<form action="subject-initial.htm" id="queryForm" name="queryForm" method="post">
		<input type="hidden" id="listSize" value="${listResult.size()}">
		<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td class="twof">科目代码</td>
								<td class="eigf"><input type="text" id="subjectCode" name="subjectCode" value="${subjectCode}" class="tf" /></td>
								<td class="twof">科目大类</td>
								<td class="twef">
								<select id="majorCate" name="majorCate" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="资产" <c:if test="${majorCate eq '资产'}">selected="selected"</c:if>>资产</option>
										<option value="负债" <c:if test="${majorCate eq '负债'}">selected="selected"</c:if>>负债</option>
										<option value="共同" <c:if test="${majorCate eq '共同'}">selected="selected"</c:if>>共同</option>
										<option value="权益" <c:if test="${majorCate eq '权益'}">selected="selected"</c:if>>权益</option>
										<option value="成本" <c:if test="${majorCate eq '成本'}">selected="selected"</c:if>>成本</option>
										<option value="损益" <c:if test="${majorCate eq '损益'}">selected="selected"</c:if>>损益</option>
								</select>
								</td>
								<td class="twof">科目名</td>
								<td class="twef"><input type="text" id="subjectName" name="subjectName" value="${subjectName}" style="width:160px;"/></td>
								<td class="twof">辅助核算</td>
								<td class="twef">
							<select id="aidAccount" name="aidAccount"
								style="width: 100px;">
									<option value="">--请选择--</option>
									<option value="供应商往来" <c:if test="${aidAccount eq '供应商往来'}">selected="selected"</c:if>>供应商往来</option>
									<option value="客户往来" <c:if test="${aidAccount eq '客户往来'}">selected="selected"</c:if>>客户往来</option>
									<option value="个人往来" <c:if test="${aidAccount eq '个人往来'}">selected="selected"</c:if>>个人往来</option>
									<option value="项目核算" <c:if test="${aidAccount eq '项目核算'}">selected="selected"</c:if>>项目核算</option>
									<option value="部门核算" <c:if test="${aidAccount eq '部门核算'}">selected="selected"</c:if>>部门核算</option>
							</select></td>
							</tr>
							<tr>
								<td class="twof">状态</td>
								<td class="twef">
								<select id="state" name="state" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="使用中" <c:if test="${state eq '使用中'}">selected="selected"</c:if>>使用中</option>
										<option value="未使用" <c:if test="${state eq '未使用'}">selected="selected"</c:if>>未使用</option>
								</select>
								</td>
								<td class="twof">余额方向</td>
								<td class="twef">
								<select id="balanceDirection" name="balanceDirection" style="width:120px;">
										<option value="">--请选择--</option>
										<option value="借" <c:if test="${balanceDirection eq '借'}">selected="selected"</c:if>>借</option>
										<option value="贷" <c:if test="${balanceDirection eq '贷'}">selected="selected"</c:if>>贷</option>
								</select>
								</td>
								<td class="twof">排序</td><td class="fotf">
								<select style="width:90px;" id="orderBy" name="page.orderBy">
								<option value="subjectCode" <c:if test="${page.orderBy eq 'subjectCode'}">selected="selected"</c:if>>科目代码</option>
								<option value="majorCate" <c:if test="${page.orderBy eq 'majorCate'}">selected="selected"</c:if>>科目大类</option>
								<option value="subjectType" <c:if test="${page.orderBy eq 'subjectType'}">selected="selected"</c:if>>科目类别</option></select>-<select style="width:70px;" id="ascDesc" name="page.ascDesc">
								<option value="asc" <c:if test="${page.ascDesc eq 'asc'}">selected="selected"</c:if>>升序</option>
								<option value="desc" <c:if test="${page.ascDesc eq 'desc'}">selected="selected"</c:if>>降序</option></select></td>
								<td class="twof"></td>
								<td class="twef">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
									<input type="button" onclick="javascript:resetForm();" value="清除" class="cancellation" />
								</td>
							</tr>
						</table>
			<table class="tableSteel">
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="twof">序号</td>
                <td class="fouf">科目代码</td>
                <td class="fivf">科目名</td>
                <td class="fivf">科目类型</td>
                <td class="fouf">余额方向</td>
                <td class="fivf">外币核算</td>
                <td class="fivf">辅助核算</td>
                <td class="sixf">期末调汇</td>
                <td class="fouf">状态</td>
                <td class="fouf">大类</td>
                <td class="fouf">级别</td>
                <td class="fouf">帐页格式</td>
                <td class="fivf" style="border-right:1px #09f solid">操作</td>
              </tr>
              
              <c:forEach items="${listResult}" var="subject" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="twof"><input type="checkbox" id="check${st.count}" value="${subject.id}"/></td>
                <td class="fouf">${subject.subjectCode}</td>
                <td class="sixf">${subject.subjectName}</td>
                <td class="fivf">${subject.subjectType}</td>
                <td class="fouf">${subject.balanceDirection}</td>
                <td class="fivf">${subject.foreignCurrencyAccount}</td>
                <td class="sixf">${subject.aidAccount}</td>
                <td class="fouf">${subject.adjustExrate}</td>
                <td class="fouf">${subject.state}</td>
                <td class="fouf">${subject.majorCate}</td>
                <td class="fouf">${subject.level}</td>
                <td class="fouf"><c:if test="${subject.ledgerFormat == 1}">金额式</c:if><c:if test="${subject.ledgerFormat == 2}">数量金额式</c:if></td>
                <td class="fivf"><input type="button" class="pageCutSmallBtnDel" id="input4" value="删除" /></td>
              </tr>
              </c:forEach>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="checkbox" id="allCheckSelected" />
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
