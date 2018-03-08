<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>달력~</title>
<script type="text/javascript">
	function goSchedule(str)
	{
		alert(str);
	}
	
	function goPrevMonth()
	{
		alert("이전달로~");
	}
	
	function goCurMonth()
	{
		alert("현재달로");
	}
	
	function goNextMonth()
	{
		alert("다음달로");
	}
	
	
</script>
<style type="text/css">
	#allWrapper {
		
	}
	
	#controlWrapper {
		background-color: rgba(43, 90, 167, 0.5);
		height: 100px;
		
		
	}
	#controlButtons {
		display: inline;
		
	}
	
	#controlButtons img{
		cursor: pointer;
		width: 90px;
		height: 90px;
	}
	#leftButton {
		float: left;
	}
	
	#rightButton {
		float: right;
	}
	
	#tableWrapper {
		vertical-align: middle;
		
	}
	
	.labelBasic{
		font-weight: bold;
		font-size: 15px;
		
	}
	
	.labelSunday {
		color: #ffbfbf;
	}
	
	.labelSaturday {
		color: #83ddff;
	}
	
	.labelOtherday {
		color: #ffffff;
	}
	
	.calendarTable {
		border: solid 2px #ffffff;
		background-image: url("http://cfs15.tistory.com/image/32/tistory/2009/07/26/14/41/4a6bec82d24ad");
		
	}
	
	.calendarTable td {
		background-color: rgba(244, 237, 185, 0.4);
		vertical-align: top;
		height: 140px;
		margin : 0;
		padding : 0;
	}
	.calendarTable td ul {
		margin : 0;
		padding : 0;
		padding-left: 20px;
	}
	.calendarTable td ul li {
		font-size: 9px;
		font-weight: bold;
	}
	.calendarTable th{
		
	}
	
	.calendarTable th, .calendarTable td{
		 border: 2px;
	}
</style>
</head>
<body>
	<div id="allWrapper">
		<div id="controlWrapper">
			<div id="controlButtons">
				<img id="leftButton" onclick="javascript:goPrevMonth()"  src="http://tiagoribeiro.me/images/prev_button.png">
				<img id="rightButton" onclick="javascript:goNextMonth()"  src="http://tiagoribeiro.me/images/next_button.png"> 
			</div>
		</div>
		<div id="tableWrapper">
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
					<th class="labelSunday" style="background-color: rgba(255, 0, 0, 0.5)" >일</th>
					<th class="labelOtherday" style="background-color: rgba(168, 107, 197, 0.5)">월</th>		
					<th class="labelOtherday" style="background-color: rgba(168, 107, 197, 0.5)">화</th>
					<th class="labelOtherday" style="background-color: rgba(168, 107, 197, 0.5)">수</th>
					<th class="labelOtherday" style="background-color: rgba(168, 107, 197, 0.5)">목</th>
					<th class="labelOtherday" style="background-color: rgba(168, 107, 197, 0.5)">금</th>
					<th class="labelSaturday" style="background-color: rgba(0, 0, 255, 0.5)">토</th>
				</tr>
				<%
				
				for(int i=0 ; i<5 ; i++ )
				{
				%>
				<tr>
					<%
						for(int j=1 ; j<=7 ; j++ )
						{
							if(((7*i)+j)<=31)
							{
					%>
						<td><ul><li <%if(j==1){ %>class="labelSunday"<%} else if(j==7){%>class="labelSaturday"<%} %>onclick="javascript:goSchedule('test<%=Integer.toString((7*i)+j) %>');" style="cursor:pointer">test<%=Integer.toString((7*i)+j) %></li></ul></td>
					<%
							}
							else
							{
					%>
						<td><ul></ul></td>
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
</body>
</html>