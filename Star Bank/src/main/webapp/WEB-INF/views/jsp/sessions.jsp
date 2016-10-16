<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<div class="mail">
	<div class="container">
		<h3>
			<span>Sessions</span>
		</h3>
		<div id="messages">
			<!-- Put messages from server -->
			<table class="table table-hover msg-table" id="msg-table">
				<thead>
					<tr>
						<th>Date</th>
						<th>Description</th>
						<th>IP Address</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sessions}" var="session">

						<tr>
							<td><c:out value="${session.dateTime}" /></td>
							<td><c:out value="${session.description}" /></td>
							<td><c:out value="${session.ipAddress}" /></td>
						</tr>
					</c:forEach>

					<c:forEach begin="1" end="${numberofsessions}" varStatus="index">

						<button class="page-button" >
							<a href="./sessions?id=${index.index}">${index.index}</a>
						</button>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />