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
					<a href="index"><strong>Star Bank</strong></a>
				</h1>
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
								<li class="act"><a href="index" data-hover="Home">Home</a></li>
								<li><a class="active" href="messages" data-hover="Messages">Messages</a></li>
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
	<!-- //header -->
	<div class="mail">
		<div class="container">
			<h3>
				<span>My Messages</span>
			</h3>
			<div id="messages">
				<!-- Put messages from server -->
				<table class="table table-hover msg-table" id="msg-table">
					<thead>
						<tr>
							<th>Subject</th>
							<th>Received Date</th>
							<th>Preview</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${messages}" var="message">

							<tr>
								<td><c:out value="${message.title}" /></td>
								<td><c:out value="${message.date}" /></td>
								<td><c:out value="${message.text}" /></td>
							</tr>
						</c:forEach>
						<c:forEach begin="1" end="${number_pages}" varStatus="index">
							<a href="./messages?id=${index.index}"><button
									class="page-button">${index.index}</button></a>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- //mail -->
	<script>
		function popupCenter(url, title, w, h) {
			var left = (screen.width / 2) - (w / 2);
			var top = (screen.height / 2) - (h / 2);
			return window
					.open(
							url,
							title,
							'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='
									+ w
									+ ', height='
									+ h
									+ ', top='
									+ top
									+ ', left=' + left);
		}
	</script>

	<c:import url="/includes/menubar.jsp" />

	<!-- mail -->
	<div class="w3ls_map">
		<iframe
			src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBCjqOr7fON-XrxJjkGEXqjIXriyngjSLc&q=Infinity+Tower,1404+Sofia"
			style="border: 0"></iframe>
		<div class="w3ls_map_color">
			<div class="contact-bottom-grids">
				<div class="container">
					<div class="col-md-4 col-md-4m contact-bottom-grid"
						style="cursor: pointer;"
						onclick="popupCenter('https://www.google.bg/maps/place/%D0%98%D0%BD%D1%84%D0%B8%D0%B8%D0%BD%D0%B8%D1%82%D0%B8+%D0%A2%D0%B0%D1%83%D1%8A%D1%80,+1404+%D0%A1%D0%BE%D1%84%D0%B8%D1%8F,+%D0%91%D1%8A%D0%BB%D0%B3%D0%B0%D1%80%D0%B8%D1%8F/@42.6641545,23.2858174,17z/data=!3m1!4b1!4m5!3m4!1s0x40aa84ea538dfdef:0xd78af264a1dfcc28!8m2!3d42.6641409!4d23.2879827?hl=bg', 'Star Bank location',800,600);"
						href="javascript:void(0);">
						<div class="dot">
							<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
						</div>
						<h4>
							Infinity Tower, 1404, Sofia<span>Bulgaria</span>
						</h4>
					</div>
					<div class="col-md-4 col-md-4m contact-bottom-grid">
						<div class="dot" style="cursor: pointer;"
							onclick="window.location.href='mailto:tsb87@abv.bg, kochev.svetoslav@gmail.com';">
							<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						</div>
						<a href="mailto:tsb87@abv.bg">tsb87@abv.bg</a> <a
							href="mailto:kochev.svetoslav@gmail.com">kochev.svetoslav@gmail.com</a>
					</div>
					<div class="col-md-4 col-md-4m contact-bottom-grid">
						<div class="dot" style="cursor: pointer;"
							onclick="window.location.href='tel:+359877 000 000';">
							<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
						</div>
						<h4>+359877 000 000</h4>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>

	<c:import url="/includes/footer.jsp" />