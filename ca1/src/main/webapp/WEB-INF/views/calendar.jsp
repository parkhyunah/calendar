<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ca.common.CommonUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	CommonUtil util = new CommonUtil();
	ArrayList<String> calList = (ArrayList<String>)request.getAttribute("calList");
	ArrayList<Integer> calCountList = (ArrayList<Integer>)request.getAttribute("calCountList");
	
	String year = util.nvl((String)request.getAttribute("year"));
	String month = util.nvl((String)request.getAttribute("month"));
	String date = util.nvl((String)request.getAttribute("date"));
	
	/* for(int i=1 ; i<=calList.size() ; i++)
	{
		System.out.print(" --"+calList.get(i-1)+"--  ");
		if(i%7==0)
			System.out.println();
	} */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=month %>월</title>
<link rel="stylesheet" type="text/css" href="/ca/css/main.css"> 
<script type="text/javascript">
	function goDetailPop(date){
		alert(date);
	}
	
	function dayDetail(date)
	{
		var url = "/ca/calendarPop.do?date="+date;
		window.open(url, "productPop", "width=400px, height=600px, resizable=no")
	}
	
	function goPrevMonth()
	{
		document.getElementById("direction").value = "prev";
		frm.submit();
	}
	
	function goNextMonth()
	{
		document.getElementById("direction").value = "next";
		frm.submit();
	}
	
	function goToday()
	{
		document.getElementById("year").value = "";
		document.getElementById("month").value = "";
		document.getElementById("date").value = "";
		frm.submit();
	}
	function goProcess()
	{
		var nlpText = document.getElementById("nlpText").value
		alert("process success! : "+nlpText);
		frm.submit();
	}
</script>
</head>
<body>
<form name="frm" method="POST" action="/ca/month.do">
<input type="hidden" id="year" name="year" value="<%=year %>"/>
<input type="hidden" id="month" name="month" value="<%=month %>"/>
<input type="hidden" id="date" name="date" value="<%=date %>"/>
<input type="hidden" id="direction" name="direction" />


	<div id="calendar">
		<!-- 툴바 영역 -->
		<div class="toolBar">
			<div class="toolBar-left">
				<input type="button" value="<-" onclick="javascript:goPrevMonth()"/>
				<input type="button" value="->" onclick="javascript:goNextMonth()"/>
				<input type="button" value="이번달" onclick="javascript:goToday()"/>
			</div>
			<div class="toolBar-right">
				<a href="/ca/month.do"><input type="button" value="년" disabled/></a>
				<input type="button" value="월" disabled/>
				<input type="button" value="일" disabled/>
			</div>
			<div class="toolBar-center">
				<span ><%=year %>년 <%=month %>월</span>
			</div>
			<div class="toolBar-clear">
			</div>
		</div>
		
		<div class="chat">
			<input type="text" id="nlpText" name="nlpText" size="50"/>
			<input type="button" value="확인" onclick="javascript:goProcess();"/>
		</div>
		
		
		<!-- 달력표시 영역 -->
		<div class="calendarView">
			<table class="calendarTable">
				<colgroup>
					<col width="300px" />	<!-- 이 부분에 화면 사이즈 * 7등분 해서 픽셀 값 넣어 줘야 함 -->
					<col width="300px" />
					<col width="300px" />
					<col width="300px" />
					<col width="300px" />
					<col width="300px" />
					<col width="300px" />
				</colgroup>
				<tr>
					<th class="labelSunday">일</th>
					<th class="labelWeekday">월</th>		
					<th class="labelWeekday">화</th>
					<th class="labelWeekday">수</th>
					<th class="labelWeekday">목</th>
					<th class="labelWeekday">금</th>
					<th class="labelSaturday">토</th>
				</tr>
				
				
				
				
				<%
				System.out.println("calList size: "+calList.size());
				int weekSize = (calList.size()>35)?6:5;
				for(int i=0 ; i< weekSize; i++ )
				{
				%>
				<tr>
					<%
						for(int j=0 ; j<7 ; j++ )
						{
							if(((7*i)+j)<calList.size())
							{
								String printDate = (calList.get((7*i)+j)) ;
								if(!"".equals(printDate))
								{
									printDate = printDate.substring(6);
									
									%>
										<td style="cursor:pointer;" onclick="javascript:dayDetail('<%=calList.get((7*i)+j) %>')">
											<ul>
												<li>
													<%=printDate %>&nbsp;<label style="font-weight: bold; color: red">+<%=calCountList.get((7*i)+j) %></label>
												</li>
											</ul>
										</td>
									<%
								}
								else
								{
									%>
										<td>
											<ul>
											</ul>
										</td>
									<%
								}
							}
							else
							{
					%>
						<td>
							<ul>
							</ul>
						</td>
					<%
								
							}
						}
					%>
				</tr>
				<%
				}
				%>
			</table>
		</div>
		
	</div>
</form>
</body>
</html>