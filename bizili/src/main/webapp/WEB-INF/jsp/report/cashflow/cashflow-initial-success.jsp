<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
<%@ taglib prefix="vte" uri="/WEB-INF/tld/" %>
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#queryButton').click(function(){
    		var queryForm = $('#queryForm');
    		queryForm.submit();
    	});
    });
    </script>
<script language="javascript" type="text/javascript" src="../js/dateSelect.js"></script>
<div id="epMcContent" style="margin-bottom:10px;">
	<div class="epMcCtContent">
	<h3 class="bordFont bigFont">现金流量表</h3>
	<div class="tab">
		<form action="cashflow-initial.htm" id="queryForm" name="queryForm" method="post">
		<table class="bugSteel first" style="border-top: 0;">
						<tr>
							<td class="twof">会计期间</td>
							<td class="twef" style="width:100px;"><input type="text" onclick="javascript:SelectDateYL(this,'yyyy-MM',0,-22)" style="width:90px;" id="accountPeriod" name="accountPeriod"/></td>
								
						<td style="width: 80px"><span style="margin-left: 20px;">
						<input type="checkbox" style="margin-top:6px;" id="allCheckSelected" />
						<label for="allCheckSelected" style="position: relative; bottom: 5px;">无发生额不显示</label>
						</span></td>
						<td style="width:100px;">
						<input type="checkbox" style="margin-top:6px;" id="allCheckSelected" />
						<label for="allCheckSelected" style="position: relative; bottom: 5px;">无发生额且余额为0不显示</label>
						</td>
								
								<td class="tenf">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
									<input type="reset" value="清除" class="cancellation" />
								</td>
						</tr>
			</table>
	
			<table class="tableSteelSkmbw" style="margin-top:0px;">
              <tr class="title" style="border-right:1px #bfd2ed solid;" >
                <td class="sixf">项目</td>
                <td class="sixf">行次</td>
                <td class="fivf">本年累计金额</td>
                <td class="fivf" style="border-right:1px #09f solid">本月金额</td>
              </tr>
              <c:forEach items="${listResult}" var="balance" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="sixf">&nbsp;${balance.itemName}</td>
                <td class="sixf">${balance.rowNumber}</td>
                <td class="fivf"><c:if test="${balance.thisYearSum ==0}"></c:if><c:if test="${balance.thisYearSum !=0}"><fmt:formatNumber value="${balance.thisYearSum}" pattern="#,#00.00#"/></c:if></td>
                <td class="fivf" style="border-right:1px #09f solid"><c:if test="${balance.thisPeriodCount ==0}"></c:if><c:if test="${balance.thisPeriodCount !=0}"><fmt:formatNumber value="${balance.thisPeriodCount}" pattern="#,#00.00#"/></c:if></td>
              </tr>
              </c:forEach>
            </table>
        
	      <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="button" class="pageCutSmallBtnCheckout" id="input2" value="导出" />
		  </p>
		  </form>
	</div>
	</div>
	</div>