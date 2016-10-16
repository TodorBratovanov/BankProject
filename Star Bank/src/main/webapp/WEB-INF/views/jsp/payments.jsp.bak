<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<div class="mail">
	<div class="container">
		<h3>
			<span>Payments</span>
		</h3>
	</div>
</div>

<h4>Select from</h4>

<form>
	<select>
		<c:forEach var="account" items="${accounts}" varStatus="index">
			<option id="${index.index}"><c:out value="${account.iban}" /></option>
		</c:forEach>
	</select>
</form>

<h4>Transfer to my account</h4>

<form>
	<select>
		<c:forEach var="account" items="${accounts}">
			<option><c:out value="${account.iban}" /></option>
		</c:forEach>
	</select>
</form>

<h4>Transfer to other account</h4>

<div class="input-group">
	<span class="input-group-addon"> <input type="radio"
		aria-label="...">
	</span> <input type="text" class="form-control" aria-label="...">
</div>

<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />