<%-- 
    Document   : ViewAppointments
    Created on : Mar 23, 2016, 3:03:46 PM
    Author     : Ekta Khiani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
	<head>
		<!-- CSS -->
		<link href="../jquery/Datatables/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>

		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/Datatables/js/jquery.dataTables.min.js" type="text/javascript"></script>

		<script type="text/javascript" charset="utf-8">
			$(document).ready(function () {
			<%String emailUser = request.getParameter("email");%>
			<%String usertype = request.getParameter("type");%>
				var emailOfUser = "<%= emailUser%>";
				var utype = "<%= usertype%>";

				$.ajax({
					type: 'GET',
					url: '../Appointments/ViewMyAppointments?callback=?',
					data: {email: emailOfUser, type: utype},
					success: function (data) {
						$('#tableId').dataTable({
							data: data,
							'sort': false,
							'bInfo': false,
							'searching': false,
							"bPaginate": false,
							bJQueryUI: true,
							bFilter: true,
							//"sPaginationType":"full_numbers",
							'scrollY': '50vh',
							columns: [
								{'data': "sFullName"},
								{'data': "sEmailOfAppointmentWith"},
								{
									'data': "sUserType",
									'sortable': false
								},
								{
									'data': "sDate",
									'sortable': false
								},
								{
									'data': "sTime",
									'searchable': false,
									'sortable': false

								},
								{
									'data': "sApptStatus",
									'sortable': false

								},
								
								{
									'data': "sApptNotes",
									'searchable': false,
									'sortable': false

								},
								
							]

						});//table
					} //sucess

				}); //ajax

			});//ready

		</script>


	</head>
	<body>
		<div id="appointments" align="center">
			<h3 style="color:#00622F"><b> My Appointments</b></h3>
			<table cellpadding="0" cellspacing="0" border="1"  id="tableId">
				<thead>
					<tr>
						<th width="15%" >Appointment With</th>
						<th width="10%" >Email</th>
						<th width="10%" >Type</th>
						<th width="10%" >Date</th>
						<th width="15%" >Time</th>
						<th width="10%" >Status</th>
						<th width="20%" >Notes</th>
					</tr>
				</thead>

			</table>

		</div>
	</body>
</html>
