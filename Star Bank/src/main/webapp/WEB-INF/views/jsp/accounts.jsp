<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<!-- accounts -->
<div class="pricing-plans">
	<div class="container">
		<h3>Accounts</h3>
		<div class="pricing-grids">
			<div class="col-md-4 accounts pricing-grid">
				<div class="price-value">
					<h4>Current accounts</h4>
				</div>
				<div class="price-bg">

					<c:forEach var="account" items="${currentAccounts}">
						<ul class="count">
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span> <c:out value="${account.iban}" /></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
							<c:out value="account.netAvlbBalance" /></li>
							<li><span class="glyphicon glyphicon-star-empty"
								aria-hidden="true"></span>
							<c:out value="account.currency" /></li>
						</ul>
						<hr>
					</c:forEach>


					<p class="price-label-1">
						$<span>7</span>monthly
					</p>
					<ul class="count">
						<li><span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							24/7 Tech Support</li>
						<li><span class="glyphicon glyphicon-duplicate"
							aria-hidden="true"></span>Advanced Options</li>
						<li><span class="glyphicon glyphicon-heart"
							aria-hidden="true"></span>200GB Storage</li>
						<li><span class="glyphicon glyphicon-star" aria-hidden="true"></span>3GB
							BandWidth</li>
					</ul>
					<p class="bottom">your business requires more than just a
						server or VPS you need a partner</p>
				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid">
				<div class="price-value two">
					<h4>Deposits</h4>
				</div>
				<div class="price-bg">
					<p class="price-label-2">
						$<span>10</span>monthly
					</p>
					<ul class="count">
						<li><span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							24/7 Tech Support</li>
						<li><span class="glyphicon glyphicon-duplicate"
							aria-hidden="true"></span>Advanced Options</li>
						<li><span class="glyphicon glyphicon-heart"
							aria-hidden="true"></span>250GB Storage</li>
						<li><span class="glyphicon glyphicon-star" aria-hidden="true"></span>3GB
							BandWidth</li>
					</ul>
					<p class="bottom">your business requires more than just a
						server or VPS you need a partner</p>
				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid">
				<div class="price-value three">
					<h4>Credits</h4>
				</div>
				<div class="price-bg">
					<p class="price-label-3">
						$<span>15</span>monthly
					</p>
					<ul class="count">
						<li><span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							24/7 Tech Support</li>
						<li><span class="glyphicon glyphicon-duplicate"
							aria-hidden="true"></span>Advanced Options</li>
						<li><span class="glyphicon glyphicon-heart"
							aria-hidden="true"></span>300GB Storage</li>
						<li><span class="glyphicon glyphicon-star" aria-hidden="true"></span>4GB
							BandWidth</li>
					</ul>
					<p class="bottom">your business requires more than just a
						server or VPS you need a partner</p>
				</div>
			</div>
			<div class="col-md-4 accounts pricing-grid">
				<div class="price-value four">
					<h4>Cards</h4>
				</div>
				<div class="price-bg">
					<p class="price-label-3">
						$<span>15</span>monthly
					</p>
					<ul class="count">
						<li><span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							24/7 Tech Support</li>
						<li><span class="glyphicon glyphicon-duplicate"
							aria-hidden="true"></span>Advanced Options</li>
						<li><span class="glyphicon glyphicon-heart"
							aria-hidden="true"></span>300GB Storage</li>
						<li><span class="glyphicon glyphicon-star" aria-hidden="true"></span>4GB
							BandWidth</li>
					</ul>
					<p class="bottom">your business requires more than just a
						server or VPS you need a partner</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- //accounts -->

<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />