<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../inc/taglib.inc"%>
<%@ include file="../../../inc/constants.inc" %>
<%@ include file="../../../inc/script.inc" %>
<%@ include file="../../../inc/style.inc" %>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
<html>
	<head>      
    <title>资源查询</title>
    <style type="text/css">
    table.tableSteel{
    width:795px;
    margin:0 auto;
    }
    #container{
    width:800px;
    }
    #container #epMcContent{
    width:800px;
    }
    #container .epMcCtContent{
    margin-top:2px;
    }
    table.bugSteel{
    width:795px;
    }
    tr td {
    font:12px/1.4 宋体,arial,helvertica,sans-serif;
    }
    input{
	border:1px #ccc solid;
	}
    </style>
    
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#allCheckSelected').click(function(){
    		var listSize = parseInt($('#listSize').val());
    		for (var i=1; i <= listSize ; i++) {
    			$('#check'+i).attr("checked",true);
    		}
    	});
    	$('#btnReturn').click(function(){
    		var listSize = parseInt($('#listSize').val());
    		var ret = "";
    		for (var i=1; i<=listSize; i++) {
    			if ($('#check'+i).attr('checked')=='checked'){
    				ret += $('#check'+i).val()+";";
    			}
    		}
    		window.returnValue = ret;
    		window.close();
    	});
    	$('#queryButton').click(function(){
    		var queryForm = $('#queryForm');
    		queryForm.submit();
    	});
    });
    </script>
    </head>
  <body>
  <div id="container">
<div id="epMcContent" style="margin-bottom:100px;">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">资源查询</h3>
	<div class="tab">
		<form action="resources-list.htm" id="queryForm" name="queryForm" method="post">
		<table class="bugSteel first" style="border-top: 0;">
							<tr>
								<td style="width:50px;">&nbsp;资源名</td>
								<td class="twef">
								<input type="text" name="resourceName" />
								</td>
								<td class="twof">URL</td>
								<td class="twef">
								<input type="text" name="resourceUrl" />
								</td>
								<td class="twof">类型</td><td class="fotf"><input type="text" name="resourceType" /></td>
								<td class="twef">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
								</td>
							</tr>
						</table>
	
			<table class="tableSteel">
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="twof">序号</td>
                <td class="fouf">资源名</td>
                <td class="fivf">类型</td>
                <td class="fivf">URL</td>
              </tr>
              
              <c:forEach items="${listResult}" var="resource" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="twof"><input type="checkbox" id="check${st.count}" value="${resource.id}#${resource.resourceUrl}"/></td>
                <td class="fouf">${resource.resourceName}</td>
                <td class="fivf">${resource.resourceType}</td>
                <td class="fivf">${resource.resourceUrl}</td>
              </tr>
              </c:forEach>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="checkbox" id="allCheckSelected" />
          <label for="pageCutSmallCheckbox1" style="position:relative ;bottom:5px;">全选</label>
          <input type="button" class="pageCutSmallBtnCheckout" id="btnReturn" value="确定" />
          <input type="hidden" id="listSize" value="${listResult.size()}">
		  </p>
		  <div id="page">
			<p><vte:pages hasForm="true" beanName="page" formName="queryForm"/></p>
		  </div>
		  </form>
	</div>
	</div>
	</div>
</div>
</body>
</html>
