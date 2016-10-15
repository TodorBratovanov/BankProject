<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
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

<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />