
<html>
	<head>
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/validate/jquery.validate.js" type="text/javascript"></script>

		<title>Change Password</title>
		<script>

			/*$("#cpwd").click(function () {
			 $('#pwdChanged').hide();
			 $('#notpwdChanged').hide();
			 
			 changePassword();
			 return false;
			 });*/





		</script>
	</head>
	<body>
		<br/>
		<div align="center">
			<form class="transbox" autocomplete="off" id="formPassword">
				<h3><b>Change Password</b></h3>
				<table>
					<tr>
						<td colspan="2">
							<div id="pwdChanged" style ="display: none">
								<font color="green"><b>Your Password has been changed successfully.</b></font>
							</div>
							<div id="notpwdChanged" style ="display: none">
								<font color="red"><b>Your Current Password is Invalid.</b></font>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<input type="password" name="curpwd" id="curpwd" placeholder="Enter Current Password" />
						</td>
					</tr>
					<tr>
						<td>
							<input type="password" name="newpwd" id="newpwd" placeholder="Enter New Password" />
						</td>
					</tr>
					<tr>
						<td>
							<input  type="password" name="repwd" id="repwd" placeholder="Retype New Password"  title="Password must not be blank and contain only letters one number and a special character." required pattern="^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{8,}"/>
							<div id="donotMatch" style ="display: none">
								<font color="red"><b>Passwords do not match.</b></font>
							</div>

						</td>

					</tr>
					<tr>
						<td>
							<button type="submit" style="cursor:pointer" class="btn" name="cpwd" id="cpwd">Change Password</button>
						</td>
					</tr>
				</table>
			</form>

			<style>
				.my-error-class {
					color:#FF0000;  /* red */
				}
				.my-valid-class {
					color:#00CC00; /* green */
				}
			</style>
			<script>
				$(function () {

					$('#curpwd').val("");

					$("#formPassword").validate({
						wrapper: 'div',
						errorLabelContainer: "#messageBox",
						errorClass: "my-error-class",
						validClass: "my-valid-class",
						rules: {
							newpwd: {
								required: true,
								pwcheck: true,
								minlength: 8
							},
							repwd: {
								required: true,
								equalTo: "#newpwd"
							}
						},
						messages: {
							newpwd: {
								required: "Enter password",
								pwcheck: "Password must contain at least 8 characters, including UPPER, special character and numbers",
								minlength: "The password does not meet the criteria!"
							},
							repwd: {
								required: "Repeat Password",
								equalTo: "The passwords do not match"},
						},
						onkeyup: false, //turn off auto validate whilst typing
						submitHandler: function (form) {
							$('#pwdChanged').hide();
							$('#notpwdChanged').hide();

							changePassword();
							return false;
						}
					});

					$.validator.addMethod("pwcheck",
						function (value) {
							return /^.*(?=.{8,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&]).*$/.test(value);
						});

							function changePassword()
							{
					<%String emailUser = request.getParameter("email");%>
								var emailOfUser = "<%= emailUser%>";
								var oldp = $("#curpwd").val();
								var newp = $("#newpwd").val();
								var retypep = $('#repwd').val();
								if (retypep === newp)
								{
									$('#donotMatch').hide();
									$.ajax({
										type: 'POST',
										url: '../Profile/ChangePwd',
										data: {email: emailOfUser, curpwd: oldp, newpwd: newp},
										success: function (opts) {
											if (opts === "true")
											{
												$('#pwdChanged').show();
												$('#notpwdChanged').hide();
												$('#curpwd').val("");
												$('#newpwd').val("");
												$('#repwd').val("");
												// $('#curpwd').css("border", "1px solid red");
												//$('#curpwd').next('span').show();

											} else {
												$('#pwdChanged').hide();
												$('#notpwdChanged').show();
												//$('#curpwd').css("border", "1px solid #000");
												//$('#curpwd').next('span').hide();

											}
										}
									}); //AJAX
								} else
								{
									$('#donotMatch').show();
								}
							}

						});
			</script>
			<br/>
		</div>
	</body>
</html>
