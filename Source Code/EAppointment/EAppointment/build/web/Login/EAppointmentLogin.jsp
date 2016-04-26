<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ITU E-Appointment Login</title>

        <!-- CSS -->
		<link rel="shortcut icon" href="http://example.com/favicon.ico">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="../bootstrap/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="../bootstrap/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../bootstrap/assets/css/form-elements.css">
        <link rel="stylesheet" href="../bootstrap/assets/css/style.css">
		
		<script src="../bootstrap/assets/js/jquery-1.11.1.min.js"></script>
		<script>
			$("document").ready(function(){
				var status = "<%=(String)request.getAttribute("status")%>";
				if(status!==null && status == "failed")
				{
					$('#wrongPassword').show();

				}
				else
				{
					$('#wrongPassword').hide();

				}
		});
		</script>
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
	                        			<h3><b>ITU Member Login</b></h3>
	                            		<p>Enter type, username and password to log on:</p>
	                        		</div>
	                        		<div class="form-top-right">
	                        			<i class="fa fa-lock"></i>
	                        		</div>
	                            </div>
	                            <div class="form-bottom">
					<form id="memberLogin" role="form" action="AuthenticateLogin" method="post" class="login-form">
						 <div class="form-group">
							 <div id="wrongPassword" style="display:none"> 
							<font color="red">The email or password is invalid</font>
							 </div>
							 <label class="sr-only" for="form-type">Type</label>
							 <select name="usertype"  class="form-username form-control" id="type">
								 <option value="None">Login As..</option>
								 <option value="Student">Student</option>
								 <option value="Faculty">Faculty</option>
								 <option value="ISO">ISO</option>
								 <option value="Academic Advisor">Academic Advisor</option>
								 <option value="Student Advisor">Student Advisor</option>
								 <option value="Admin">Admin</option>
							 </select>
							 <div id="errSelectType" style="display:none"> 
								 <font color="red"> Please select Login As.</font>
							 </div>
							 </div>
						 <div class="form-group">
							 <label class="sr-only" for="form-username">Username</label>
							 <input type="text" name="email" placeholder="Username..." class="form-username form-control" id="form-username" required pattern="^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$" >
							 <div id="errUsername" style="display:none"> 
								 <font color="red">Please enter Username.</font>
							 </div>
						 </div>
						 <div class="form-group">
							 <label class="sr-only" for="form-password">Password</label>
							 <input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
							 <div id="errPassword" style="display:none"> 
												 <font color="red">Please enter Password.</font>
							 </div>
						</div>
						<button id="loginBtn" type="submit" class="btn" onclick="validateMethod();">Sign in!</button>
					</form>
				</div>
		         </div>
                   </div>
                        
                        <div class="col-sm-1 middle-border"></div>
                        <div class="col-sm-1"></div>
                        	
                        <div class="col-sm-5">
                        	
                        	<div class="form-box">
                        		<div class="form-top">
	                        		<div class="form-top-left">
	                        			<h3><b>Guest Student</b></h3>
	                            		<p>Schedule appointment as Guest:</p>
	                        		</div>
	                        		<div class="form-top-right">
	                        			<i class="fa fa-pencil"></i>
	                        		</div>
	                            </div>
	                            <div class="form-bottom">
				                    <form role="form" action="" method="post" class="registration-form">
				                        <button type="submit" class="btn" formaction ="../Appointments/ProspectBookAppointment.jsp">Schedule Appointment as Guest</button>
				                    </form>
			                    </div>
                        	</div>
                        	
                        </div>
                    </div>
                    
                </div>
            </div>
            
        </div>

        <!-- Javascript -->

        
        <script src="../bootstrap/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../bootstrap/assets/js/jquery.backstretch.min.js"></script>
        <script src="../bootstrap/assets/js/scripts.js"></script>
     	<script language="javascript">
		/*$(document).ready(function () {
			$("#loginBtn").click(function () {
				$('#memberLogin').bootstrapValidator('validate');
			});
			validateMethod();
		});*/
		
		function validateMethod() {
			if(document.getElementById("type").value == "None") {
				document.getElementById("errSelectType").style.display = "block";
			}
			else {
				document.getElementById("errSelectType").style.display = "none";
			}
			if(document.getElementById("form-username").value == "") {
				document.getElementById("errUsername").style.display = "block";
			}
			else {
				document.getElementById("errUsername").style.display = "none";
			}
			if(document.getElementById("form-password").value == "") {
				document.getElementById("errPassword").style.display = "block";
			}
			else {
				document.getElementById("errPassword").style.display = "none";
			}
		}
		</script>   
        

    </body>

</html>