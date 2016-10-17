<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="/includes/header.jsp" />


<div class="mail">
	<div class="container">
		<h3>
			<span>Statements</span>
		</h3>
		<div id="messages">
			<!-- Put messages from server -->
			<table class="table table-hover msg-table" id="msg-table">
				<thead>
					<tr>
						<th>Date</th>
						<th>IBAN Sender</th>
						<th>IBAN Recipient</th>
						<th>Amount</th>
						<th>Currency</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${transactions}" var="transaction">

						<tr>
							<td><c:out value="${transaction.date}" /></td>
							<td><c:out value="${transaction.senderIban}" /></td>
							<td><c:out value="${transaction.recipientIban}" /></td>
							<td><c:out value="${transaction.amount}" /></td>
							<td><c:out value="${transaction.currency}" /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			<form action="./statements" method="get">
				Start date<br> <input type="date"
					name="strdate" min="2016-01-01" max="2016-12-31"><br>
				<br> End date:<br> <input type="date"
					name="enddate" min="2016-01-01" max="2016-12-31"><br>
				<br> 
				<div class="more"><button type="submit" class = "hvr-bounce-to-top" >Get Statements</button></div>
			</form>
			<div class = "error"><c:if test="${not empty error_time}"><c:out value="${error_time}"/></c:if>
			</div>
			
		</div>
	</div>
</div>
<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />