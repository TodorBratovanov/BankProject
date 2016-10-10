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
<!-- //banner -->
<!-- banner-bottom -->
<div class="banner-bottom">
	<div class="container">
		<div class="agile_banner_bottom_grids">
			<div style="cursor: pointer;" onclick="window.location='information';" class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
				</div>
				<h3>Information</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='payments';" class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-export" aria-hidden="true"></span>
				</div>
				<h3>Payments</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='utility';" class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
				</div>
				<h3>Utility bills</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='statements';" class="col-md-3g agile_banner_bottom_grid">
				<div class="agile_banner_bottom_grid1 hvr-rectangle-out">
					<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
				</div>
				<h3>Statements</h3>
			</div>
			<div style="cursor: pointer;" onclick="window.location='accounts';" class="col-md-3g agile_banner_bottom_grid">
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