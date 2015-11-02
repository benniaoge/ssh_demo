<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/abin-tag" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<c:set var="ctx" value="${pageContext.request.contextPath }"/>

<script type="text/javascript" src="${ctx }/js/jquery-1.2.4.js"></script>
<script type="text/javascript" src="${ctx }/js/pagination.js"></script>
</head>
<body>

	<a href="${ctx }/dog/dog.action">list</a>

	<form action="${ctx }/dog/dog!save.action">
		
		昵称:<input type="text" name="dog.name"/>
		年龄:<input type="text" name="dog.age"/>
		<button type="submit">提交</button>
		
	</form>
	
</body>
</html>