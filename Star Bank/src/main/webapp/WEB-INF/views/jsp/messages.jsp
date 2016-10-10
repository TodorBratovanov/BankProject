<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<!-- //header -->
<!-- about -->
	<div class="about">
		<div class="container">
			<h3>Mail Us</h3>
			<ul>
				<li><a href="index.html">Home</a><i>|</i></li>
				<li>Mail Us</li>
			</ul>
		</div>
	</div>
<!-- //about -->
<!-- mail -->
	<div class="w3ls_map">
		<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d7859812.209374686!2d101.30962748076922!3d15.86716175385292!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31157a4d736a1e5f%3A0xb03bb0c9e2fe62be!2sVietnam!5e0!3m2!1sen!2sin!4v1461066179025" style="border:0"></iframe>
		<div class="w3ls_map_color">
			<div class="contact-bottom-grids">
				<div class="container">
					<div class="col-md-4 contact-bottom-grid">
						<div class="dot">
							<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
						</div>
						<h4>JI.Paulnadwam 2nd D.No:23-50-903.<span>United States</span></h4>
					</div>
					<div class="col-md-4 contact-bottom-grid">
						<div class="dot">
							<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						</div>
						<a href="mailto:info@example.com">info@example1.com</a>
						<a href="mailto:info@example.com">info@example2.com</a>
					</div>
					<div class="col-md-4 contact-bottom-grid">
						<div class="dot">
							<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
						</div>
						<h4>+020 456 9696<span>+020 456 9695</span></h4>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>
	</div>
<div class="mail">
	<div class="container">
		<h3>
			<span>My Mails</span>
			<div id="messages">
				<!-- Put messages from server -->
				<table>
					<ul>
						<c:forEach var="message" items="${messages}">
							<li>${message}</li>
							</br>
						</c:forEach>
					</ul>
				</table>
			</div>
	</div>
</div>
<!-- //mail -->
<c:import url="/includes/footer.jsp" />