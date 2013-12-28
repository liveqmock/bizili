<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/inc/taglib.inc"%>
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
	<h3 class="bordFont bigFont">明细账</h3>
	<div class="tab">
		<form action="detailaccount-initial.htm" id="queryForm" name="queryForm" method="post">
		<table class="bugSteel first" style="border-top: 0;">
							<tr><td class="twof" style="width:50px;">会计期间</td>
								<td class="twef"><input type="text" onclick="javascript:SelectDateYL(this,'yyyy-MM',0,-22)" style="width:90px;" id="startPeriod" name="startPeriod"/>-<input type="text" onclick="javascript:SelectDateYL(this,'yyyy-MM',0,-22)" style="width:90px;" id="endPeriod" name="endPeriod"/></td>
								
								<td style="width:50px">会计科目</td>
								<td style="width:140px;"><input type="text" id="startLevel" name="startLevel" style="width:70px;" />&nbsp;<input type="text" id="startLevelShow" style="width:110px;" readonly="readonly"/></td>
								<td class="twof">至</td>
								<td style="width:130px;">
								<input type="text" style="width:70px;" id="endLevel" name="endLevel"/>&nbsp;<input type="text" style="width:110px;" id="endLevelShow" disabled="disabled" />
								</td>
								<td style="width:40px">科目级次</td><td style="width:80px;"><select style="width:80px;" name="level"><option value="1">一级</option><option value="2">二级</option><option value="3">三级</option></select></td>
								<td class="twof">币别</td>
								<td class="fivf">
								<select id="currency" name="currency" style="width:100px;">
										<option value="CNY">人民币</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width: 80px" colspan="2"><span style="margin-left: 20px;">
								<input type="checkbox" style="margin-top:6px;" id="allCheckSelected" />
								<label for="allCheckSelected" style="position: relative; bottom: 5px;">无发生额不显示</label>
								</span></td>
								<td style="width:100px;" colspan="2">
								<input type="checkbox" style="margin-top:6px;" id="allCheckSelected" />
								<label for="allCheckSelected" style="position: relative; bottom: 5px;">无发生额且余额为0不显示</label>
								</td>
								<td class="twof"></td>
								<td style="width:160px;"></td>
								<td class="twof"></td>
								<td class="tenf">
								<input type="button" value="查询" class="determination" id="queryButton" style="margin-right:20px;" />
									<input type="reset" value="清除" class="cancellation" />
								</td>
							</tr>
						</table>
	
			<table class="tableSteelSkmbw">
              <tr class="title" style="border-right:1px #bfd2ed solid;">
                <td class="fouf">日期</td>
                <td class="fouf">凭证号</td>
                <td class="fouf">摘要</td>
                <td class="fouf">科目代码</td>
                <td class="fivf">科目名称</td>
                <td class="fouf">借方金额</td>
                <td class="fouf">贷方金额</td>
                <td class="fouf">余额方向</td>
                <td class="fouf" style="border-right:1px #09f solid">余额</td>
              </tr>
              
              <c:forEach items="${listResult}" var="detail" varStatus="st">
              <tr style="${st.count%2==0?'background:#f3f3f3':''}">
                <td class="fouf">${detail.createDate}</td>
                <td class="fouf">${detail.codeNo}</td>
                <td class="fouf">${detail.summary}</td>
                <td class="fouf">${detail.subjectCode}</td>
                <td class="sixf">${detail.subjectName}</td>
                <td class="fouf"><c:if test="${detail.debitBalance ==0}"></c:if><c:if test="${detail.debitBalance !=0}"><fmt:formatNumber value="${detail.debitBalance}" pattern="#,#00.00#"/></c:if></td>
                <td class="fouf"><c:if test="${detail.creditBalance ==0}"></c:if><c:if test="${detail.creditBalance !=0}"><fmt:formatNumber value="${detail.creditBalance}" pattern="#,#00.00#"/></c:if></td>
                <td class="fouf">${detail.balanceDirection}</td>
                <td class="fouf" style="border-right:1px #09f solid"><c:if test="${detail.balance ==0}"></c:if><c:if test="${detail.balance !=0}"><fmt:formatNumber value="${detail.balance}" pattern="#,#00.00#"/></c:if></td>
              </tr>
              </c:forEach>
            </table>
        
	        <p style="padding-left:16px;line-height:30px; height:30px;">
          <input type="button" class="pageCutSmallBtnCheckout" id="exportExcel" value="导出" />
		  </p>
		  </form>
	</div>
	</div>
	</div>