<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<div id="payments">
	<div class="transfer-money">
		<form action="">
			<div class="mail">
				<div class="container">
					<div class="payments-head">
						<h3>Payments</h3>
					</div>
				</div>
			</div>
			<div class="select-send">
				<h4>Select from</h4>
				<select id="sender">
					<option value="0"><c:out value="- select -" /></option>
					<c:forEach var="account" items="${accounts}" varStatus="index">
						<option onclick="selectIban('${index.index}')"
							id="${index.index + 10}"><c:out value="${account.iban}" /></option>
					</c:forEach>
				</select>
			</div>
			<br>
			<form action="http://www.example.com/profile.php" class="account-radio">
				<p>
					Select transfer type: <br> <br> <input type="radio"
						name="transferTo" value="my" checked="checked"
						onclick="selectAccounts('my-account')" /> Transfer to my account
					<input type="radio" name="transferTo" value="other"
						onclick="selectAccounts('other-account')" /> Transfer to other
					account
				</p>
			</form>
			<br>
			<div class="my-account" id="my-account">
				<h4>Transfer to my account</h4>
				<select id="receiver">
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
						placeholder="IBAN ...">
				</div>
				<br> <br>
			</div>
			<br>
			<button>Submit</button>
		</form>
	</div>
</div>

<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />