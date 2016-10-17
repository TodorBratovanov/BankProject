<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Star Bank - Today, Tomorrow, Together</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript">
	
	
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } 


</script>
<!-- //for-mobile-apps -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<!-- //js -->
<link href='//fonts.googleapis.com/css?family=Maven+Pro:400,500,700,900'
	rel='stylesheet' type='text/css'>
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic'
	rel='stylesheet' type='text/css'>
<!-- start-smoth-scrolling -->

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event) {
			event.preventDefault();
			$('html,body').animate({
				scrollTop : $(this.hash).offset().top
			}, 1000);
		});
	});
</script>
<!-- start-smoth-scrolling -->
</head>
<body>
	<!-- header -->
	<div class="header" id="ban">
		<div class="container">
			<div class="w3ls_logo">
				<h1>
					<a href="admin-index"><strong>Star Bank</strong></a>
				</h1>
			</div>
			<div class="w3ls_logo">
				<h2>Admin</h2>
			</div>
			<div class="header_right">
				<nav class="navbar navbar-default">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
					</div>

					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse nav-wil"
						id="bs-example-navbar-collapse-1">
						<nav class="link-effect-7" id="link-effect-7">
							<ul class="nav navbar-nav">
								<li class="active act"><a href="admin-index" data-hover="Home">Home</a></li>
								<li><a href="logout" data-hover="Logout">Logout</a></li>
							</ul>
						</nav>
					</div>
					<script type="text/javascript">
						$('ul.navbar-nav li').click(function() {
							window.sessionStorage.activeMenuItem = this.id;
						});
					</script>
					<script type="text/javascript">
						if (window.sessionStorage.activeMenuItem) {
							$("#" + sessionStorage.activeMenuItem).addClass(
									'active');
						}
					</script>
					<!-- /.navbar-collapse -->
				</nav>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>

	<div id="payments">
		<div class="transfer-money">
			<form action="admin-confirm" method="post">
				<div class="mail">
					<div class="container">
						<div class="payments-head confirm-reg">
							<h3>Confirm registration</h3>
						</div>
					</div>
				</div>

				<div class="select-send select-user">
					<h4>Select user</h4>
					<select id="sender" name="email">
						<c:forEach var="user" items="${users}" varStatus="index">
							<option value="${user.email}" id="${index.index}"><c:out
									value="${user.firstName} " /><c:out
									value="${user.lastName} | " />
								<c:out value="${user.email}" /></option>
						</c:forEach>
					</select>
				</div>
				<div class="account-radio">
					<p>
					<h3>Select account type:</h3>
					<br> <input type="radio" name="accountType" checked="checked"
						value="Current" /> Current account <input type="radio"
						name="accountType" value="Deposit" /> Deposit <input type="radio"
						name="accountType" value="Credit" /> Credit
					</p>
				</div>
				<div class="my-account validity" id="my-account">
					<h4>Validity period</h4>
					<select id="receiver" name="years">
						<c:forEach begin="1" end="10" varStatus="year">
							<option value="${year.index}"><c:out
									value="${year.index}" />&nbsp years
							</option>
						</c:forEach>
					</select>
				</div>
				<div class="open-account-amount amount" id="other-account">
					<h4>IBAN</h4>
					<div class="input-group">
						<input type="text" class="form-control" aria-label="..."
							pattern="[A-Z0-9]{15,31}" placeholder="IBAN ..."
							name="iban">
					</div>
				</div>
				<div class="open-account-amount amount" id="other-account">
					<h4>Card number</h4>
					<div class="input-group">
						<input type="text" class="form-control" aria-label="..."
							pattern="[0-9]{16,16}" placeholder="Card number ..."
							name="number">
					</div>
				</div>
				<div class="open-account-amount amount" id="other-account">
					<h4>Amount</h4>
					<div class="input-group">
						<input type="text" class="form-control" aria-label="..."
							pattern="^[1-9]+[0-9]{0,4}" placeholder="Amount ..."
							name="amount">
					</div>
				</div>
				<br>
				<div class="more">
					<button type="submit" class="hvr-bounce-to-top">Confirm</button>
				</div>
			</form>
		</div>
	</div>

	<!-- banner-bottom -->
	<div class="banner-bottom-accounts">
		<div class="container">
			<div class="agile_banner_bottom_grids">
				<div style="cursor: pointer;"
					onclick="window.location='admin-confirm';"
					class="col-md-3g agile_banner_bottom_grid">
					<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					</div>
					<h3>Confirm registration</h3>
				</div>
				<div style="cursor: pointer;"
					onclick="window.location='admin-open';"
					class="col-md-3g agile_banner_bottom_grid">
					<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
						<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
					</div>
					<h3>Open account</h3>
				</div>
				<div style="cursor: pointer;"
					onclick="window.location='admin-close';"
					class="col-md-3g agile_banner_bottom_grid">
					<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</div>
					<h3>Close account</h3>
				</div>
				<div style="cursor: pointer;"
					onclick="window.location='admin-message';"
					class="col-md-3g agile_banner_bottom_grid">
					<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
						<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
					</div>
					<h3>Send message</h3>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

	<script src="js/bootstrap.js"></script>
</body>
</html>