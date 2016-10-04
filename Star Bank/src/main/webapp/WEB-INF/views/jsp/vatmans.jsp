<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Vatmans Page</title>
</head>
<body>
	<spring:form method="post" commandName="vatman">
		<label>Vatman name</label>
		<spring:input type="text" path="name"/>
		
		<br/>
		
		<label>Vatman broi cigari</label>
		<spring:input type="number" path="broiCigarki"/>
		
		<br/>
		
		<input type="submit"/>
	</spring:form>
</body>
</html>