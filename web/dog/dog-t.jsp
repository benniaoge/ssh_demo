<%@page import="com.abin.demo.dict.DictType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" uri="/abin-tag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@include file="/include/include.jsp" %>

</head>
<body>

	<form action="${ctx }/dog/dog!t.action" method="post">
		<select name="test">
			<t:options type="<%=DictType.PLAN_STATE.value() %>" selectValue="${test }"></t:options>
		</select>
		<button type="submit">submit</button>
	</form>
	
</body>
</html>