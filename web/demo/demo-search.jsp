<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/include/include.jsp" %>
</head>
<body>
	
	<%@include file="/include/header.jsp" %>
	
	<div id="container">
	
		<%@include file="/include/navg.jsp" %>
	
		<div id="main">
			
			<div id="message">${errorMessage }</div>
			
			<div id="content">
				<table cellpadding="0px" cellspacing="0px">
					<thead>
						<tr>
							<th>id</th>
							<th>名称</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="demo" items="${demoList }">
						<tr>
							<td>${demo.id }&nbsp;</td>
							<td>${demo.name }&nbsp;</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		
		</div>
	
	</div>
	
	<%@include file="/include/footer.jsp" %>

</body>
</html>