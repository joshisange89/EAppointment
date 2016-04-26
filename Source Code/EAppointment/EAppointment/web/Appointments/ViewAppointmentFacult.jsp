<%-- 
    Document   : ViewAppointmentFacult
    Created on : Mar 25, 2016, 3:16:03 PM
    Author     : Ekta Khiani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<!-- CSS -->
		<link href="../jquery/Datatables/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/LoadingScreen/waitMe.min.css" rel="stylesheet" type="text/css"/>	
		<link href="../jquery/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
		
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/Datatables/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="../jquery/LoadingScreen/waitMe.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function () {
			<%String emailUser = request.getParameter("email");%>
			<%String usertype = request.getParameter("type");%>
				var emailOfUser = "<%= emailUser%>";
				var utype = "<%= usertype%>";
				var reasonOfRejection;   //This is Set in The Rejection Button.
				
				$("#appointments").show();
				$.ajax({
					type: 'GET',
					url: '../Appointments/ViewMyAppointments?callback=?',
					data: {email: emailOfUser, type: utype},
					success: function (data) {
					
						$('#tableId').dataTable({
							bJQueryUI: true,
							data: data,
							'sort': false,
							'searching': false	,
							'paging': false,
							'bInfo': false,
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
								{
									'data': "sApptStatus",
									'searchable': false,
									'sortable': false

								},
								{
									'data': "sApptNotes",
									'searchable': false,
									'sortable': false

								},
								{
									'data': "sApptStatus",
									"mRender": function (data, type, full) {
										if(data !== "Confirmed" && data !== "Rejected"){
												return '<a id="approvebtn" style="color:#428bca;font-weight:bold;text-decoration:underline;" href=#>' + 'Approve' + '</a>'+' / '+'<a  id = "rejectbtn" style="color:#428bca;font-weight:bold;text-decoration:underline;" href=#>' + 'Reject' + '</a>';
										}else{											
											return '';
										}
									}

								}
							]

						});//table
					} //sucess

				}); //ajax
				var dialogReject = 	$("#dialogPopup").dialog({
							modal: true,
							height: 250,
							width: 300,
							autoOpen:false,
							title: "Reject Appointment",
							buttons:{
								Reject: function () {
									reasonOfRejection = $("#rejectReason").val();
									run_waitMe('pulse');
									updateStatusOfAppointment();
								
									$("#rejectReason").val('');
									$(this).dialog("close");
								}
							},
							close: function(){
									 $("#rejectReason").val('');
									 $(this).dialog("close");
							}
							
					
					});
					
				var emailOfAppointee, dateOfAppt, timeOfAppt, fullnameOfStudent, actionOnAppointment, updateRow, actionCell;	
				$('#tableId tbody').on('click', '#rejectbtn', function () {
					
					var dataTable = $('#tableId').dataTable();
					var $row = $(this).closest('tr').first();
					
					fullnameOfStudent = $row.find("td:nth-child(1)").text();
					emailOfAppointee = $row.find("td:nth-child(2)").text(); 
					dateOfAppt =  $row.find("td:nth-child(3)").text();
					timeOfAppt = $row.find("td:nth-child(4)").text();
					updateRow = $row.find("td:nth-child(5)");
					actionCell =  $row.find("td:nth-child(7)");
					actionOnAppointment="Rejected";
					dialogReject.dialog('open');
					return false;
				});
				
					
				$('#tableId tbody').on('click', '#approvebtn', function () {
					var dataTable = $('#tableId').dataTable();
					var $row = $(this).closest('tr').first();
					fullnameOfStudent = $row.find("td:nth-child(1)").text();
					emailOfAppointee = $row.find("td:nth-child(2)").text(); 
					dateOfAppt =  $row.find("td:nth-child(3)").text();
					timeOfAppt = $row.find("td:nth-child(4)").text();
					updateRow = $row.find("td:nth-child(5)");
					actionCell =  $row.find("td:nth-child(7)");
					actionOnAppointment="Confirmed";
					updateStatusOfAppointment();
					
					run_waitMe('pulse');
					return false;
				});
				
				
			
				
				function updateStatusOfAppointment(){
			
					$.ajax({
						type: 'POST',
						url: '../Appointments/UpdateStatus?callback=?',
						data: {email_staff: emailOfUser, type: utype, name: fullnameOfStudent, email_student: emailOfAppointee, dateOfAppointee: dateOfAppt, timeOfAppt: timeOfAppt, action: actionOnAppointment, rejectReason: reasonOfRejection},
						success: function (data) {
							$('#appointments').waitMe('hide');
							if(data === "SUCCESS")
							{
								updateRow.text(actionOnAppointment);
								actionCell.text('');

							}
						}
					});

				}
			
				function run_waitMe(effect){
					$('#appointments').waitMe({
						//none, rotateplane, stretch, orbit, roundBounce, win8, 
						//win8_linear, ios, facebook, rotation, timer, pulse, 
						//progressBar, bouncePulse or img
						effect: 'pulse',

						//place text under the effect (string).
						text: 'Updating Appointment Status...',

						//background for container (string).
						bg: 'rgba(255,255,255,0.7)',

						//color for background animation and text (string).
						color: '#000',

						//change width for elem animation (string).
						sizeW: '',

						//change height for elem animation (string).
						sizeH: '',

						// url to image
						source: '',

						// callback
						onClose: function() {}

					});
				}
		
		
				
		});//ready

		</script>


	</head>
	<body>
		<div id="appointments" align="center" style="display:none" >
			<h3 style="color:#00622F"><b> My Appointments</b></h3>
			<table cellpadding="0" cellspacing="0" border="1"  id="tableId" width="600px">
				<thead>
					<tr>
						<th width="15%" id="studentName">Student Name</th>
						<th id="email">Email</th>
						<th id="date">Date </th>
						<th width="15%" id="startTime">Time</th>
						<th id="status">Status</th>
						<th width="20%" id="Notes">Notes</th>
						<th id="action">Action</th>

					</tr>
				</thead>
				<tbody>

				</tbody>

			</table>
		</div>
		<div id="dialogPopup" style="display:none">
			<span>
				<textarea id="rejectReason" name="rejectionReason" rows = "4" cols="20" placeholder="Rejection Notes..." ></textarea>
			</span>
		</div>
		<br/>
	</body>
</html>
