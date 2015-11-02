<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/abin-tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@include file="/include/include.jsp" %>

</head>
<body>

	<c:if test="${not empty errorStack['message']}">
		${errorStack['message']}
	</c:if>

	<a href="${ctx }/dog/dog!add.action">add</a>
	
	<form action="${ctx }/dog/dog" method="post">
		名字:<input type="text" name="like.dog.name" value="${searchParameters.likeDogName }">
		年龄:<input type="text" name="eq.dog.age" value="${searchParameters.eqDogAge }">
		<button type="submit">查询</button>
	</form>

	<table>
		<thead>
			<tr>
				<th>名字</th>
				<th>年龄</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="dog" items="${result }">
			<tr>
				<td>${dog.name }</td>
				<td>${dog.age }</td>
			</tr>
			</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="2"> </td>
			</tr>
		</tfoot>
		
	</table>
	
</body>
</html>