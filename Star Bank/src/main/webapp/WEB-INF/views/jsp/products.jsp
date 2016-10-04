<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products</title>
<style type="text/css">
	.error{
		color: #ff0000;
	}
	.errorblock{
		color: #000;
		background-color: #ffEEEE;
		border: 3px solid #ff0000;
		padding: 8px;
		margin: 16px;
	}

</style>
</head>
<body>	
	<a href="?language=en">
		English
	</a>
	<a href="?language=es">
		Spanish
	</a> 
	<form:form commandName="product">
		<!-- Handle errors -->
		<form:errors path="*" cssClass="errorblock" element="div"/>
		
		<!-- Input for name of the product with css class for errors. -->
		<label for="textinput1"><spring:message code="product.name" /></label>
		<form:input path="name" cssErrorClass="error"/>
		<form:errors path="name" cssClass="error" />
		<br>
		
		<label for="textinput2"><spring:message code="product.quantity" /></label>
		<form:input path="quantity" cssErrorClass="error"/>
		<form:errors path="quantity" cssClass="error" />
		<br>
		
		<input type="submit" value=<spring:message code="product.quantity" /> />
	</form:form>
</body>
</html>