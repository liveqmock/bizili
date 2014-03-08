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
	<h3 class="bordFont bigFont">资产管理</h3>
	<div class="tab">
		<form action="accountbalance-initial.htm" id="queryForm" name="queryForm" method="post">
		<table class="bugSteel first" style="border-top: 0;">
						<tr>
							<td class="twof">会计期间</td>
							<td class="twef" style="width:100px;"><input type="text" onclick="javascript:SelectDateYL(this,'yyyy-MM',0,-22)" style="width:90px;" id="accountPeriod" name="accountPeriod"/>-<input type="text" onclick="javascript:SelectDateYL(this,'yyyy-MM',0,-22)" style="width:90px;" name="createDate"/></td>
								
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
                <td class="fivf" rowspan="2">会计科目</td>
                <td class="sixf" rowspan="2">科目名称</td>
                <td class="fouf" rowspan="2">余额方向</td>
                <td class="fouf" colspan="2">期初余额</td>
                <td class="fouf" colspan="2">本期发生额</td>
                <td class="sixf" colspan="2">本年发生额合计</td>
                <td class="fivf" colspan="2" style="border-right:1px #09f solid">期末余额</td>
              </tr>
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="fouf">借方</td>
                <td class="sixf">贷方</td>
                <td class="sixf">借方</td>
                <td class="sixf">贷方</td>
                <td class="sixf">借方</td>
                <td class="sixf">贷方</td>
                <td class="sixf">借方</td>
                <td class="sixf" style="border-right:1px #09f solid">贷方</td>
              </tr>
              <c:forEach items="${listResult}" var="balance" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="fivf">&nbsp;${balance.subjectCode}</td>
                <td class="sixf">${balance.subjectName}</td>
                <td class="fouf">${balance.balanceDirection}</td>
                <td class="sixf"><c:if test="${balance.startBalanceDebit ==0}"></c:if><c:if test="${balance.startBalanceDebit !=0}"><fmt:formatNumber value="${balance.startBalanceDebit}" pattern="#,#00.00#"/></c:if></td>
                <td class="fouf"><c:if test="${balance.startBalanceCredit ==0}"></c:if><c:if test="${balance.startBalanceCredit !=0}"><fmt:formatNumber value="${balance.startBalanceCredit}" pattern="#,#00.00#"/></c:if></td>
                <td class="fouf"><c:if test="${balance.currenceBalanceDebit ==0}"></c:if><c:if test="${balance.currenceBalanceDebit !=0}"><fmt:formatNumber value="${balance.currenceBalanceDebit}" pattern="#,#00.00#"/></c:if></td>
                <td class="fouf"><c:if test="${balance.currenceBalanceCredit ==0}"></c:if><c:if test="${balance.currenceBalanceCredit !=0}"><fmt:formatNumber value="${balance.currenceBalanceCredit}" pattern="#,#00.00#"/></c:if></td>
                <td class="sixf"><c:if test="${balance.yearSumBalanceDebit ==0}"></c:if><c:if test="${balance.yearSumBalanceDebit !=0}"><fmt:formatNumber value="${balance.yearSumBalanceDebit}" pattern="#,#00.00#"/></c:if></td>
                <td class="sixf"><c:if test="${balance.yearSumBalanceCredit ==0}"></c:if><c:if test="${balance.yearSumBalanceCredit !=0}"><fmt:formatNumber value="${balance.yearSumBalanceCredit}" pattern="#,#00.00#"/></c:if></td>
                <td class="sixf"><c:if test="${balance.endBalanceDebit ==0}"></c:if><c:if test="${balance.endBalanceDebit !=0}"><fmt:formatNumber value="${balance.endBalanceDebit}" pattern="#,#00.00#"/></c:if></td>
                <td class="fivf" style="border-right:1px #09f solid"><c:if test="${balance.endBalanceCredit ==0}"></c:if><c:if test="${balance.endBalanceCredit !=0}"><fmt:formatNumber value="${balance.endBalanceCredit}" pattern="#,#00.00#"/></c:if></td>
              </tr>
              </c:forEach>
              <tr class="jiacu">
                <td class="fivf">&nbsp;合计：</td>
                <td class="fivf"></td>
                <td class="fivf"></td>
                <td class="sixf"><fmt:formatNumber value="${startDebit}" pattern="#,#0.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${startCredit}" pattern="#,#0.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${currentDebit}" pattern="#,#00.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${currentCredit}" pattern="#,#00.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${yearDebit}" pattern="#,#00.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${yearCredit}" pattern="#,#00.00#"/></td>
                <td class="sixf"><fmt:formatNumber value="${endDebit}" pattern="#,#00.00#"/></td>
                <td class="sixf" style="border-right:1px #09f solid"><fmt:formatNumber value="${endCredit}" pattern="#,#00.00#"/></td>
              </tr>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="button" class="pageCutSmallBtnCheckout" id="input2" value="导出" />
		  </p>
		  </form>
	</div>
	</div>
	</div>