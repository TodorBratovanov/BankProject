<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<div id="payments">
	<div class="transfer-money">
		<form action="payments" method="post">
			<div class="mail">
				<div class="container">
					<div class="payments-head">
						<h3>Payments</h3>
					</div>
				</div>
			</div>
			<div class="select-send">
				<h4>Select from</h4>
				<select id="sender" name="sender">
					<option value="0"><c:out value="- select -" /></option>
					<c:forEach var="account" items="${accounts}" varStatus="index">
						<option onclick="selectIban('${index.index}')"
							id="${index.index + 10}"><c:out value="${account.iban}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="account-radio">
				<p>
				<h3>Select transfer type:</h3>
				<br> <input type="radio" name="transferType" checked="checked"
					onclick="selectAccounts('my-account')" value="my" /> Transfer to my
				account <input type="radio" name="transferType"
					onclick="selectAccounts('other-account')" value="other" /> Transfer
				to other account
				</p>
			</div>
			<div class="my-account" id="my-account">
				<h4>Transfer to my account</h4>
				<select id="receiver" name="receiverMy">
					<option value="0"><c:out value="- select -" /></option>
					<c:forEach var="account" items="${accounts}" varStatus="index">
						<option onclick="selectIban('${index.index + 10}')"
							id="${index.index}"><c:out value="${account.iban}" /></option>
					</c:forEach>
				</select>
			</div>
			<div class="other-account" id="other-account">
				<h4>Transfer to other account</h4>
				<div class="input-group">
					<input type="text" class="form-control" aria-label="..."
						pattern="[A-Z0-9]{15,31}" placeholder="IBAN ..."
						name="receiverOther">
				</div>
			</div>
			<br>
			<div class="other-account amount" id="other-account">
				<h4>Amount</h4>
				<div class="input-group">
					<input type="text" class="form-control" aria-label="..." pattern="^[1-9]+[0-9]{0,4}"
						required="required" placeholder="Amount ..." name="amount">
				</div>
			</div>
			<div class="more">
				<button type="submit" class="hvr-bounce-to-top">Submit</button>
			</div>
		</form>
	</div>
</div>

<c:import url="/includes/menubar-tight.jsp" />

<c:import url="/includes/footer.jsp" />