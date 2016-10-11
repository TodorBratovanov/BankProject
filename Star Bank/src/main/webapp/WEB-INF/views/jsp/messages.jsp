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
	<iframe
		src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBCjqOr7fON-XrxJjkGEXqjIXriyngjSLc&q=Infinity+Tower,1404+Sofia"
		style="border: 0"></iframe>
	<div class="w3ls_map_color">
		<div class="contact-bottom-grids">
			<div class="container">
				<div class="col-md-4 col-md-4m contact-bottom-grid" style="cursor: pointer;"
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
					<div class="dot" style="cursor: pointer;" onclick="window.location.href='mailto:tsb87@abv.bg kochev.svetoslav@gmail.com';">
						<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
					</div>
					<a href="mailto:tsb87@abv.bg">tsb87@abv.bg</a> <a
						href="mailto:kochev.svetoslav@gmail.com">kochev.svetoslav@gmail.com</a>
				</div>
				<div class="col-md-4 col-md-4m contact-bottom-grid">
					<div class="dot" style="cursor: pointer;" onclick="window.location.href='tel:+359877 000 000';">
						<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
					</div>
					<h4>
						+359877 000 000
					</h4>
				</div>
				<div class="clearfix"></div>
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

				<table class="table" >
					
					<thead>
						<tr>
							
							
							<th>Subject</th>
							<th>Received Date</th>
							<th>Preview</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${messages}" var="message">
							<tr >
								<td><c:out value="${message.title}" /></td>
								<td><c:out value="${message.date}" /></td>
								<td><c:out value="${message.text}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
</div>
<!-- //mail -->
<script>
function popupCenter(url, title, w, h) {
var left = (screen.width/2)-(w/2);
var top = (screen.height/2)-(h/2);
return window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
}
</script>

<c:import url="/includes/menubar.jsp" />

<c:import url="/includes/footer.jsp" />