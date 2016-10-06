<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<div class="banner">
	<div class="container">
		<div class="wthree_banner_info">
			<section class="slider">
				<div class="flexslider">
					<ul class="slides">
						<li>
							<div class="wthree_banner_info_grid">
								<p>Duis aute irure dolor reprehenderit</p>
								<h3>Excepteur sint occaecat cupidatat non proident, sunt
									officia.</h3>
							</div>
						</li>
						<li>
							<div class="wthree_banner_info_grid">
								<p>ut aliquip ex ea commodo consequat</p>
								<h3>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit.</h3>
							</div>
						</li>
						<li>
							<div class="wthree_banner_info_grid">
								<p>Quis autem vel eum iure ea</p>
								<h3>Ut enim ad minima veniam, quis nostrum exercit.</h3>
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
<!-- //banner -->
<!-- banner-bottom -->
<div class="banner-bottom">
	<div class="container">
		<div class="agile_banner_bottom_grids">
			<div class="col-md-3 agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
				</div>
				<h3>Quis autem vel reprehenderit qui.</h3>
			</div>
			<div class="col-md-3 agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-signal" aria-hidden="true"></span>
				</div>
				<h3>voluptate velit non proident quam.</h3>
			</div>
			<div class="col-md-3 agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
				</div>
				<h3>autem autem vel repre qui voluptate.</h3>
			</div>
			<div class="col-md-3 agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-export" aria-hidden="true"></span>
				</div>
				<h3>qui in ea voluptate velit esse quam.</h3>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- //banner-bottom -->
<!-- welcome -->
<div class="welcome">
	<div class="container">
		<div class="col-md-6 agileits_welcome_grid_left">
			<h2>Welcome To Barter !</h2>
			<p>
				Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut
				fugit, sed quia consequuntur magni dolores eos qui ratione
				voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem
				ipsum quia dolor sit amet, consectetur, adipisci velit. Ut enim ad
				minima veniam, quis nostrum exercitationem ullam corporis suscipit
				laboriosam, nisi ut aliquid ex ea commodi consequatur? <i>Quis
					autem vel eum iure reprehenderit qui in ea voluptate velit esse
					quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat
					quo voluptas nulla pariatur?</i>
			</p>
		</div>
		<div class="col-md-6 agileits_welcome_grid_right">
			<img src="images/1.jpg" alt=" " class="img-responsive" />
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<!-- //welcome -->
<!-- welcome-bottom -->
<div class="welcome-bottom">
	<div class="container">
		<h3>eum fugiat quo voluptas</h3>
		<p>Quis autem vel eum iure reprehenderit qui in ea voluptate velit
			esse quam nihil molestiae consequatur, vel illum qui dolorem eum
			fugiat quo voluptas nulla pariatur?</p>
		<div class="more">
			<a href="single.html" class="hvr-bounce-to-top">Learn More</a>
		</div>
	</div>
</div>
<c:import url="/includes/footer.jsp" />