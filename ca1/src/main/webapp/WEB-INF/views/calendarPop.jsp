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
		//window.close();
	}
	
	function goClose()
	{
		if(confirm("창을 닫습니다."))
		{
			location.href="/ca/calendarPop.do?date=<%=date%>";
			opener.document.frm.submit();
			window.close();
		}
		else
		{
			return false;
		}
		
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
		if(confirm(delId+"를 정말 삭제하시겠습니까?"))
		{
			document.getElementById("delId").value = delId;
			document.frm.action = "/ca/calendarPop/delete.do";
			document.frm.submit();
		}
		else
		{
			return false;
		}
		
	}
	
	
	
	function addModField(delId, count)
	{
		
		if(confirm("delId: "+delId+", modTitle: "+modTitle+", modText: "+modText+", modStartDay: "+modStartDay+", modEndDay: "+modEndDay
				+"이 내용을 수정하시겠습니까?"))
		{
			var modTitle = document.getElementById("Title"+count).value
			var modText = document.getElementById("Text"+count).value
			var modStartDay = document.getElementById("StartDay"+count).value
			var modEndDay = document.getElementById("EndDay"+count).value

			
			document.getElementById("delId").value = delId;
			document.getElementById("modTitle").value = modTitle;
			document.getElementById("modText").value = modText;
			document.getElementById("modStartDay").value = modStartDay;
			document.getElementById("modEndDay").value = modEndDay;
			
			
			document.frm.action = "/ca/calendarPop/modify.do";
			document.frm.submit();
		}
		else
		{
			return false;
		}
		
	} 
	
	function goModSchedule(modId)
	{
		alert(modId);
		
	}
</script>
</head>

<body>
	<form name="frm" method="POST">
		<input type="hidden" id="date" name="date" value="<%=date %>" />
		<input type="hidden" id="delId" name="delId" />
		<input type="hidden" id="modTitle" name="modTitle" />
		<input type="hidden" id="modText" name="modText" />
		<input type="hidden" id="modStartDay" name="modStartDay" />
		<input type="hidden" id="modEndDay" name="modEndDay" />
		
		
		<h2 ><%=year %>년 <%=month %>월 <%=day %>일 일정목록</h2>
		<h6 onclick="javascript:goPage();">새로고침</h6>
		<h5 style="color: red;" onclick="javascript:goClose();">창 닫기</h5>
		
		<table >
			<colgroup>
				<col width="300px" />	
				<col width="50px" />
			</colgroup>
			<tr>
				<td style="border: solid 2px red;">
					일정제목 : <input type="text" name="insertTitle" id="insertTitle" size="21"><br>
					일정내용 : <textarea rows="5" cols="23" name="insertText" id="insertText" ></textarea><br>
					일정시작 : <input type="text" name="insertStartDay" id="insertStartDay" size="21" maxlength="14" value="<%=date %>091010"><br>
					일정종료 : <input type="text" name="insertEndDay" id="insertEndDay" size="21"  maxlength="14" value="<%=date %>091010">yyyymmddhh24miss 형식임<br>
				</td>
					
				<td style="border: solid 2px red;">
					<input type="button" name="insertButton" id="insertButton" value="등록" onclick="javascript:goInsertSchedule();"/>
				</td>
			</tr>
			<c:forEach items="${dateList}" var="dateList" varStatus="status" >
			<tr>
				<td style="border: solid 2px skyblue;">
					일정제목 : <input type='text' name='Title${status.count}' id='Title${status.count}' size='21' value='${dateList.TITLE }'><br>
					일정내용 : <textarea rows='5' cols='23' name='Text${status.count}' id='Text${status.count}' >${dateList.TEXT }</textarea><br>
					일정시작 : <input type='text' name='StartDay${status.count}' id='StartDay${status.count}' size='21' maxlength='14' value='${dateList.START_DT}'><br>
					일정종료 : <input type='text' name='EndDay${status.count}' id='EndDay${status.count}' size='21' maxlength='14' value='${dateList.END_DT}'><br>
				</td>
				
				<td style="border: solid 2px skyblue;">
					<input type="button" value="삭제" onclick="javascript:goDelSchedule('${dateList.ID }');"/>
					<input type="button" value="수정" onclick="javascript:addModField('${dateList.ID}', '${status.count}');"/>
				</td>
			</tr>
		</c:forEach>
			
		</table>
		
	</form>
</body>
</html>