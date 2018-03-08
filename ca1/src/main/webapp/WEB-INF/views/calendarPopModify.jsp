<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String date = (String)request.getParameter("date");
	System.out.println(date);
	String year = date.substring(0,4);
	String month = date.substring(4,6);
	String day = date.substring(6);
	System.out.println(year+"  "+month);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=year %>년 <%=month %>월 <%=day %>일 일정목록</title>
<script type="text/javascript">
	function goPage()
	{
		location.href="/ca/calendarPop.do?date=<%=date%>";
		opener.document.frm.submit();
		window.close();
	}
	
	function goInsertSchedule()
	{
		var insertTitle = document.getElementById("insertTitle").value;
		var insertText = document.getElementById("insertText").value;
		var insertStartDay = document.getElementById("insertStartDay").value;
		var insertEndDay = document.getElementById("insertEndDay").value;
		
		if(insertTitle=="" || insertText=="" || insertStartDay=="" || insertEndDay=="")
		{
			alert("항목을 모두 적어주세요.");
			return false;
		}
		else 
		{
			//alert("insertTitle: "+insertTitle+", insertText: "+insertText+", insertStartDay: "+insertStartDay+", insertEndDay: "+insertEndDay);
			document.frm.action = "/ca/calendarPop/regist.do";
			document.frm.submit();
		}
		
	}
	
	function goDelSchedule(delId)
	{
		document.getElementById("delId").value = delId;
		document.frm.action = "/ca/calendarPop/delete.do";
		document.frm.submit();
	}
	function goModSchedule(delId)
	{
		var url = "/ca/calendarPop.do?date="+date;
		window.open(url, "productPop", "width=500px, height=500px, top=100px, left=300px resizable=no")
	}
</script>
</head>

<body>
	<form name="frm" method="POST">
		<input type="hidden" id="date" name="date" value="<%=date %>" />
		<input type="hidden" id="delId" name="delId" />
		일정제목 : <input type="text" name="insertTitle" id="insertTitle" size="21"><br>
		일정내용 : <textarea rows="5" cols="23" name="insertText" id="insertText" ></textarea><br>
		일정시작 : <input type="text" name="insertStartDay" id="insertStartDay" size="14" maxlength="14" value="<%=date %>091010"><br>
		일정종료 : <input type="text" name="insertEndDay" id="insertEndDay" size="14"  maxlength="14" value="<%=date %>091010">yyyymmddhh24miss 형식임<br>
		<input type="button" name="insertButton" id="insertButton" value="등록" onclick="javascript:goInsertSchedule();"/>
		<table >
			<colgroup>
				<col width="300px" />	
				<col width="300px" />
			</colgroup>
			
			<c:forEach items="${dateList}" var="dateList">
			<tr>
				<td style="border: solid 2px #000000; cursor: pointer;" onclick="javascript:goPage();">
					시작일자 : ${dateList.START_DT.substring(0,4)}년 ${dateList.START_DT.substring(4,6)}월 ${dateList.START_DT.substring(6,8)}일 ${dateList.START_DT.substring(8,10)}시 ${dateList.START_DT.substring(10,12)}분
					<br>종료일자 : ${dateList.END_DT.substring(0,4)}년 ${dateList.END_DT.substring(4,6)}월 ${dateList.END_DT.substring(6,8)}일 ${dateList.END_DT.substring(8,10)}시 ${dateList.END_DT.substring(10,12)}분
					<br>제    목 : ${dateList.TITLE }
					<br>내    용 : ${dateList.TEXT }
				</td>
				
				<td style="border: solid 2px #000000;">
					<input type="button" value="삭제" onclick="javascript:goDelSchedule('${dateList.ID }');"/>
					<input type="button" value="수정" onclick="javascript:goModSchedule('${dateList.ID }');"/>
				</td>
			</tr>
		</c:forEach>
			
		</table>
		
	</form>
</body>
</html>