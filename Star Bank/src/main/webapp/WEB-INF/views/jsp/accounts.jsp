<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<!-- accounts -->
<div class="pricing-plans" id="accounts-plates">
	<div class="container">
		<h3>Accounts</h3>
		<div class="pricing-grids">
			<div class="col-md-4 accounts pricing-grid" style="cursor: pointer;"
				onclick="showAccounts('currents-table')">
				<div class="price-value">
					<h4>Current accounts</h4>
				</div>
				<div class="price-bg">

					<c:forEach var="account" items="${currentAccounts}">
						<ul class="count">
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>IBAN:&nbsp</p> <strong><c:out
										value="${account.iban}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Net available balance:&nbsp</p> <strong><c:out
										value="${account.netAvlbBalance}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Currency:&nbsp</p> <strong><c:out
										value="${account.currency}" /></strong></li>
						</ul>
					</c:forEach>

				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid" style="cursor: pointer;"
				onclick="showAccounts('deposits-table')">
				<div class="price-value two">
					<h4>Deposits</h4>
				</div>
				<div class="price-bg">

					<c:forEach var="deposit" items="${deposits}">
						<ul class="count">
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>IBAN:&nbsp</p> <strong><c:out
										value="${deposit.iban}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Net available balance:&nbsp</p> <strong><c:out
										value="${deposit.netAvlbBalance}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Currency:&nbsp</p> <strong><c:out
										value="${deposit.currency}" /></strong></li>
						</ul>
					</c:forEach>

				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid" style="cursor: pointer;"
				onclick="showAccounts('credits-table')">
				<div class="price-value three">
					<h4>Credits</h4>
				</div>
				<div class="price-bg">

					<c:forEach var="credit" items="${credits}">
						<ul class="count">
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>IBAN:&nbsp</p> <strong><c:out value="${credit.iban}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Net available balance:&nbsp</p> <strong><c:out
										value="${credit.netAvlbBalance}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Currency:&nbsp</p> <strong><c:out
										value="${credit.currency}" /></strong></li>
						</ul>
					</c:forEach>

				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid" style="cursor: pointer;"
				onclick="showAccounts('cards-table')">
				<div class="price-value four">
					<h4>Cards</h4>
				</div>
				<div class="price-bg">

					<c:forEach var="card" items="${cards}">
						<ul class="count">
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>IBAN:&nbsp</p> <strong><c:out value="${card.iban}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Number:&nbsp</p> <strong><c:out
										value="${card.number}" /></strong></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
								<p>Holder:&nbsp</p> <strong><c:out value="${card.name}" /></strong></li>
						</ul>
					</c:forEach>

				</div>
			</div>
		</div>
	</div>
</div>
<!-- //accounts -->
<div class="acc-table">
	<div class="mail" id="currents-table">
		<div class="container">
			<h3>
				<span>Current accounts</span>
			</h3>
			<div id="messages">
				<table class="table table-hover" id="acc">
					<thead>
						<tr>
							<th>IBAN</th>
							<th>Net available balance</th>
							<th>Current balance</th>
							<th>Blocked amount</th>
							<th>Currency</th>
							<th>Credit limit</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${currentAccounts}" var="current">
							<tr>
								<th><c:out value="${current.iban}" /></th>
								<th><c:out value="${current.netAvlbBalance}" /></th>
								<th><c:out value="${current.currentBalance}" /></th>
								<th><c:out value="${current.blockedAmount}" /></th>
								<th><c:out value="${current.currency}" /></th>
								<th><c:out value="${current.creditLimit}" /></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="mail" id="deposits-table">
		<div class="container">
			<h3>
				<span>Deposits</span>
			</h3>
			<div id="messages">
				<table class="table table-hover" id="acc">
					<thead>
						<tr>
							<th>IBAN</th>
							<th>Net available balance</th>
							<th>Current balance</th>
							<th>Blocked amount</th>
							<th>Currency</th>
							<th>Date open</th>
							<th>Maturity</th>
							<th>Interest</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deposits}" var="deposit">
							<tr>
								<th><c:out value="${deposit.iban}" /></th>
								<th><c:out value="${deposit.netAvlbBalance}" /></th>
								<th><c:out value="${deposit.currentBalance}" /></th>
								<th><c:out value="${deposit.blockedAmount}" /></th>
								<th><c:out value="${deposit.currency}" /></th>
								<th><c:out value="${deposit.dateOpen}" /></th>
								<th><c:out value="${deposit.maturity}" /></th>
								<th><c:out value="${deposit.interest}" /></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="mail" id="credits-table">
		<div class="container">
			<h3>
				<span>Credits</span>
			</h3>
			<div id="messages">
				<table class="table table-hover" id="acc">
					<thead>
						<tr>
							<th>IBAN</th>
							<th>Net available balance</th>
							<th>Current balance</th>
							<th>Blocked amount</th>
							<th>Currency</th>
							<th>Interest</th>
							<th>Payment</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${credits}" var="credit">
							<tr>
								<th><c:out value="${credit.iban}" /></th>
								<th><c:out value="${credit.netAvlbBalance}" /></th>
								<th><c:out value="${credit.currentBalance}" /></th>
								<th><c:out value="${credit.blockedAmount}" /></th>
								<th><c:out value="${credit.currency}" /></th>
								<th><c:out value="${credit.interest}" /></th>
								<th><c:out value="${credit.payment}" /></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div class="mail" id="cards-table">
		<div class="container">
			<h3>
				<span>Cards</span>
			</h3>
			<div id="messages">
				<table class="table table-hover" id="acc">
					<thead>
						<tr>
							<th>IBAN</th>
							<th>Net available balance</th>
							<th>Current balance</th>
							<th>Blocked amount</th>
							<th>Currency</th>
							<th>Holder</th>
							<th>Type</th>
							<th>Card number</th>
							<th>Issued on</th>
							<th>Valid through</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cards}" var="card">
							<tr>
								<th><c:out value="${card.iban}" /></th>
								<th><c:out value="${card.netAvlbBalance}" /></th>
								<th><c:out value="${card.currentBalance}" /></th>
								<th><c:out value="${card.blockedAmount}" /></th>
								<th><c:out value="${card.currency}" /></th>
								<th><c:out value="${card.name}" /></th>
								<th><c:out value="${card.type}" /></th>
								<th><c:out value="${card.number}" /></th>
								<th><c:out value="${card.issuedOn}" /></th>
								<th><c:out value="${card.validThrough}" /></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- //banner -->
<!-- banner-bottom -->
<div class="banner-bottom-accounts">
	<div class="container">
		<div class="agile_banner_bottom_grids">
			<div style="cursor: pointer;"
				onclick="window.location='information';"
				class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
				</div>
				<h3>Information</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='payments';"
				class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-export" aria-hidden="true"></span>
				</div>
				<h3>Payments</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='utility';"
				class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
				</div>
				<h3>Utility bills</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='statements';"
				class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
				</div>
				<h3>Statements</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='accounts';"
				class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-credit-card " aria-hidden="true"></span>
				</div>
				<h3>Accounts</h3>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>

<c:import url="/includes/footer.jsp" />