<%-- 
    Document   : Landing
    Created on : Mar 25, 2016, 12:14:52 PM
    Author     : Ekta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- CSS -->
		<link href="../jquery/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
		<link href="../css/headerfooter.css" rel="stylesheet" type="text/css"/>
		<link href="../css/login.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/Datatables/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>	
	</head>
	
	<body>
		<div id="wrapper">		
			<div id="header">
				<div class="header-inner"> 
					<div class="logo">
						<a title="Home" href="http://itu.edu/">
						<img src="../Images/ITULogo.png" alt="Logo">
						</a>
						
					</div>
					
				</div>
			</div><!-- #header -->
			<div id="content" class="background">
				
				<div id="tabs" class="transbox">
					<ul>
						<li><a  href='../Appointments/ViewAppointmentsOfAdvisor.jsp?email=<%=(String)request.getAttribute("email")%>&type=<%=(String)request.getAttribute("type")%>'><span>My Appointments</span></a></li>
						<li><a  href='../Appointments/ManageAppointments.jsp?email=<%=(String)request.getAttribute("email")%>'> <span>Manage Appointments</span></a></li>
						<li><a href="../Profile/ChangePassword.jsp?email=<%=(String)request.getAttribute("email")%>"><span>Change Password</span></a></li>
						<li style="float:right;"> <div>(<a onClick="location.href='../Login/EAppointmentLogin.jsp'" style="color: #428bca;cursor:pointer;">Logout</a>)</div></li>
						<li style= "float:right;color:#00622F;"><div>Welcome <%=request.getAttribute("user")%></div></li>
					</ul>

				</div>
			</div><!-- #content -->
			
			<div id="footer">
				<div class="footer-inner">
					<a title="Home" href="http://itu.edu/">
						<img src="../Images/footerLogo.png" alt="Logo">
					</a>
				</div>
			</div><!-- #footer -->	
		</div><!-- #wrapper -->
		
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		<script src="../jquery/js/ui.js" type="text/javascript"></script>
		<script src="../jquery/Datatables/js/jquery.dataTables.js" type="text/javascript"></script>	
	</body>
</html>
