<%-- 
    Document   : ViewAppointmentFacult
    Created on : Mar 25, 2016, 3:16:03 PM
    Author     : Ekta
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
				var emailOfUser = "<%= emailUser%>";
			<%String usertype = request.getParameter("type");%>
				var utype = "<%= usertype%>";

				$("#appointments").show();
				$.ajax({
					type: 'GET',
					url: '../Appointments/ViewMyAppointments?callback=?',
					data: {email: emailOfUser, type: utype},
					contentType: "application/json; charset=utf-8",
					success: function (data) {
						$('#tableId').dataTable({
							bJQueryUI: true,
							data: data,
							'sort': false,
							'bInfo': false,
							'searching': false,
							'paging': false,
							'scrollY': '50vh',
							columns: [
								{'data': "sFullName"},
								{'data': "sEmailOfAppointee"},
								{
									'data': "sDate",
									'searchable': false,
									'sortable': false
								},
								{
									'data': "sTime",
									'searchable': false,
									'sortable': false

								},
								{'data': "sApptStatus"},
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
		<div align="center">
			<h3 style="color:#00622F"><b> My Appointments</b></h3>
			<table cellpadding="0" cellspacing="0" border="1"  id="tableId" width="600px">
				<thead>
					<tr>
						<th>Student Name</th>
						<th>Email</th>
						<th>Date</th>
						<th width="15%">Time</th>
						<th>Status</th>
						<th width="20%">Notes</th>

					</tr>
				</thead>
				<tbody>

				</tbody>

			</table>
		</div>
	</body>
</html>
