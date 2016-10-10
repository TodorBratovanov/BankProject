<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Star Bank - Today, Tomorrow, Together</title>

<!-- CSS -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link href='//fonts.googleapis.com/css?family=Maven+Pro:400,500,700,900'
	rel='stylesheet' type='text/css'>
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/form-elements.css">
<link rel="stylesheet" href="assets/css/style.css">
<!-- Favicon and touch icons -->
<link rel="shortcut icon" href="assets/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="assets/ico/apple-touch-icon-57-precomposed.png">

</head>

<body>
	<!-- Top content -->
	<div class="top-content">

		<div class="inner-bg">
			<div class="container">
				<div class="row">
					<div class="col-sm-5">
						<div class="form-box">
							<div class="form-top">
								<div class="form-top-left">
									<div class="row">
										<div class="col-sm-8 col-sm-offset-2 text">
											<h1>
												Register to<br> <strong>Star Bank</strong>
											</h1>
										</div>
									</div>
									<p>Fill in the form below:</p>
								</div>
								<div class="form-top-right">
									<i class="fa fa-lock"></i>
								</div>
							</div>

							<form action="./login" method="post" id="reg-form">
								<div class="form-bottom">
									<div class="form-group">
										<label class="sr-only" for="form-first-name">First
											name</label> <input type="text" name="firstName"
											placeholder="First name..."
											class="form-first-name form-control" id="form-first-name">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-middle-name">Middle
											name</label> <input type="text" name="middleName"
											placeholder="Middle name..."
											class="form-middle-name form-control" id="form-middle-name">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-last-name">Last name</label>
										<input type="text" name="lastName"
											placeholder="Last name..."
											class="form-last-name form-control" id="form-last-name">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-email">Email</label> <input
											type="text" name="email" placeholder="Email..."
											class="form-email form-control" id="form-email">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Password</label> <input
											type="password" name="password"
											placeholder="Password..." class="form-password form-control"
											id="form-password">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-egn">EGN</label> <input
											type="text" name="egn" placeholder="EGN..."
											class="form-egn form-control" id="form-egn">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-address">Address</label> <input
											type="text" name="address" placeholder="Address..."
											class="form-address form-control" id="form-address">
									</div>
									<button class="btn regbtn">Sign
										me up!</button>
									<a href="login">Already a member? Login</a>
								</div>
							</form>

						</div>
					</div>

				</div>
			</div>
		</div>
		<!-- Javascript -->
	<script src='<c:url value="assets/js/jquery-1.11.1.min.js"/>'></script>
	<script src='<c:url value="assets/bootstrap/js/bootstrap.min.js"/>'></script>
	<script src='<c:url value="assets/js/jquery.backstretch.min.js"/>'></script>
	<script src='<c:url value="assets/js/scripts.js"/>'></script>

		<!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->
</body>

</html>