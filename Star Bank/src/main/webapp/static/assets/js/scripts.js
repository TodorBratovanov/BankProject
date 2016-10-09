jQuery(document)
		.ready(
				function() {

					/*
					 * Fullscreen background
					 */
					$.backstretch("assets/img/backgrounds/1.jpg");

					/*
					 * Login form validation
					 */
					$(
							'.login-form input[type="text"], .login-form input[type="password"], .login-form textarea')
							.on('focus', function() {
								$(this).removeClass('input-error');
							});

					$('.login-form')
							.on(
									'submit',
									function(e) {

										$(this)
												.find(
														'input[type="text"], input[type="password"], textarea')
												.each(
														function() {
															if ($(this).val() == "") {
																e
																		.preventDefault();
																$(this)
																		.addClass(
																				'input-error');
															} else {
																$(this)
																		.removeClass(
																				'input-error');
															}
														});

									});

					/*
					 * Registration form validation
					 */
					$(
							'.registration-form input[type="text"], .registration-form textarea')
							.on('focus', function() {
								$(this).removeClass('input-error');
							});

					$('.registration-form').on(
							'submit',
							function(e) {

								$(this).find('input[type="text"], textarea')
										.each(
												function() {
													if ($(this).val() == "") {
														e.preventDefault();
														$(this).addClass(
																'input-error');
													} else {
														$(this).removeClass(
																'input-error');
													}
												});

							});

				});

function registerUserAJAX() {
	var myData = {};
	myData['firstName'] = document.getElementById("form-first-name").value;
	myData['middleName'] = document.getElementById("form-middle-name").value;
	myData['lastName'] = document.getElementById("form-last-name").value;
	myData['email'] = document.getElementById("form-email").value;
	myData['password'] = document.getElementById("form-password").value;
	myData['address'] = document.getElementById("form-address").value;
	myData['egn'] = document.getElementById("form-egn").value;

	$.ajax({
		url : "register2",
		dataType : 'html',
		data : JSON.stringify(myData),
		type : 'POST',
		contentType : 'application/json',
		success : function() {
			console.log("successThrown");
//			 relog();
			window.location.href = "login";
		},
		error : function() {
			console.log("errorThrown");
		}
	});
}

function relog() {

	$.ajax({
		url : "index",
		type : 'GET'
	});

}

function loginUser() {
	var myData = {};
	myData['email'] = document.getElementById("form-email-login").value;
	myData['password'] = document.getElementById("form-password-login").value;

	$.ajax({
		url : "confirmLogin",
		dataType : 'json',
		data : JSON.stringify(myData),
		type : 'POST',
		contentType : 'application/json',
		success : function(data) {
			console.log("successThrown");
			window.location.href = "index";

		},
		error : function() {
			console.log("errorThrown");
		}
		
	});
}
