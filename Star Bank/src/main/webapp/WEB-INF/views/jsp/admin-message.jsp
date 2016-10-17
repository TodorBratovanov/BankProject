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
	<div class="banner">
		<div class="container">
			<div class="wthree_banner_info">
				<section class="slider">
					<div class="flexslider fl-slider">
						<ul class="slides">
							<li>
								<div class="wthree_banner_info_grid">
									<p>You are good</p>
									<h3>Today</h3>
								</div>
							</li>
							<li>
								<div class="wthree_banner_info_grid">
									<p>You are better</p>
									<h3>Tomorrow</h3>
								</div>
							</li>
							<li>
								<div class="wthree_banner_info_grid">
									<p>We are best</p>
									<h3>Together</h3>
								</div>
							</li>
						</ul>
					</div>
				</section>
				<!-- flexSlider -->
				<link rel="stylesheet" href="css/flexslider.css" type="text/css"
					media="screen" property="" />
				<script defer src="js/jquery.flexslider.js"></script>
				<script type="text/javascript">
					$(window).load(function() {
						$('.flexslider').flexslider({
							animation : "slide",
							start : function(slider) {
								$('body').removeClass('loading');
							}
						});
					});
				</script>
				<!-- //flexSlider -->
			</div>
		</div>
	</div>

	<!-- banner-bottom -->
	<div class="banner-bottom">
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
				<div style="cursor: pointer;" onclick="window.location='admin-open';"
					class="col-md-3g agile_banner_bottom_grid">
					<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
						<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
					</div>
					<h3>Open account</h3>
				</div>
				<div style="cursor: pointer;" onclick="window.location='admin-close';"
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