<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="classes.com.model.EAppointment_Users.Staff"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html >
<html>          
			 
		<jsp:include page = "BookAppointment.jsp"/>
		<%
			HashMap<String, Staff> emailToAdvisorDetailMap;
			emailToAdvisorDetailMap = (HashMap<String, Staff>) request.getAttribute("AdvisorsList");
		%>
		
		<!-- CSS -->
		<link href="../jquery/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/LoadingScreen/waitMe.min.css" rel="stylesheet" type="text/css"/>
		<link href="../css/ProspectAppointment.css" rel="stylesheet" type="text/css"/>
		
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		<script src="../jquery/LoadingScreen/waitMe.min.js" type="text/javascript"></script>

		<script language="javascript">
			var weekday = new Array(7);
			var dayOfWeek;
			weekday[0] = "Sunday";
			weekday[1] = "Monday";
			weekday[2] = "Tuesday";
			weekday[3] = "Wednesday";
			weekday[4] = "Thursday";
			weekday[5] = "Friday";
			weekday[6] = "Saturday";

			$("document").ready(function () { 
				$("#date").datepicker({minDate: 1}); // Datepicker - Disable past days
				// Onload disable advisor & slots
				$('#selectAdvisor').prop('disabled', 'disabled');
				$('#timeslot').prop('disabled', 'disabled');
				// On date change enable advisor & slots
				$("#date").change(function () {
					$('#selectAdvisor').prop('disabled', false);
					$('#timeslot').prop('disabled', false);
					$("#selectAdvisor").val("None");
					$('>option', $("#timeslot")).remove();
					$("#timeslot").append($('<option/>').text("Please select an advisor"));
					if ($("#date").val() === "") {
						$('#selectAdvisor').prop('disabled', 'disabled');
						$('#timeslot').prop('disabled', 'disabled');
					}
				});
				$("#selectAdvisor").change(function () {
					var nameOfSelectedAdvisor = $("#selectAdvisor option:selected").text();
					$("#advisorName").val(nameOfSelectedAdvisor);
					if ($("#selectAdvisor").val() !== "None") {
						fillCombo("timeslot", this);
					} else {
						$('>option', $("#timeslot")).remove();
						$("#timeslot").append($('<option/>').text("Please select an advisor"));   //To Check
					}
				});
				
				var form = $('#formid');
				form.submit(function () {
					run_waitMe('timer');
				});
				function run_waitMe(effect){
					$('#content').waitMe({
						//none, rotateplane, stretch, orbit, roundBounce, win8, 
						//win8_linear, ios, facebook, rotation, timer, pulse, 
						//progressBar, bouncePulse or img
						effect: 'timer',

						//place text under the effect (string).
						text: 'Scheduling Appointment',

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
			});

			function fillCombo(timeSlotsId, callingElement)
			{
				var d = $(date).datepicker({dateFormat: 'mm/dd/yyyy'}).val();
				var dd = $(date).datepicker("getDate");
				dayOfWeek = weekday[dd.getUTCDay()];
				var timeSlot = $('#' + timeSlotsId);

				$.ajax({
					type: 'GET',
					url: 'BookAppointment.jsp?callback=?',
					data: {day: dayOfWeek, advisorEmail: $(callingElement).val(), date: d},
					contentType: "application/json; charset=utf-8",
					success: function (opts) {
						$('>option', timeSlot).remove(); // Clean old options first.
						if ($.trim(opts) !== "") {
							
							$.each(opts, function (key, value) {
								timeSlot.append($('<option/>').val(value).text(value));
							});
						} else
						{
							
							timeSlot.append($('<option/>').text("No Slots Available"));   //To Check
						}
						

					},
					error: function (jqXHR, exception, data) {
						console.log(jqXHR);

						var msg = '';
						if (jqXHR.status === 0) {
							alert("Not connect.\n Verify Network");
						} else if (jqXHR.status == 404) {
							alert("Requested page not found. [404]");
						} else if (jqXHR.status == 500) {
							alert("Internal Server Error [500]");
						} else if (exception === 'parsererror') {
							alert("Requested JSON parse failed");
							alert(data);
						} else if (exception === 'timeout') {
							alert("Time out error");
						} else if (exception === 'abort') {
							alert("Ajax request aborted");
						} else {
							alert("Uncaught Error.\n") + jqXHR.responseText;
						}
					}, //ELSE

				});//AJAX
			}




        </script>
    

    <body class="background">
	<div id="wrapper">			
		<div id="content" align="center">
			<br/> 
			<img class="logo" src="http://ituedu-141b.kxcdn.com/lp/1001/CREST2015_TransparentBG.png" style="width:200px;max-height:121px; padding:20px;" alt="ITU Logo" align="left">
			<br/><br/>
			<form action="BookAppointmentForProspectStudent" method="post" id="formid"  class="transbox">
				<h3><b> Schedule Appointment</b></h3>

				<table>		
					<tr>
						<td>
							<input type="text"  name="fname" property="t1" required placeholder="First Name"/>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="lname" property="t1" placeholder="Last Name"/>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="email"  required pattern="^\w+([.-]?\w+)*@\w+([.-]?\w+)*(.\w{2,3})+$" placeholder="Email"/>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="mobile"  pattern="\d{10}" title="Please enter exactly 10 digits" placeholder="Mobile"/>
						</td>
					</tr>
					<tr>
						<td>
							<input  type="text" name= "date"   id="date" required placeholder="Select Date"> 
						</td>
					</tr>
					<tr>
						<td>
							<select name="Advisor"  id="selectAdvisor" required>                   
								<option  value="None">Select Advisor...</option>   
								<%-- This gets the array of Advisors from the database which need to be populated in the ComboBox --%> 
								<%								
									for (String email : emailToAdvisorDetailMap.keySet()) {
										Staff advisor = emailToAdvisorDetailMap.get(email);
										String Fullname = advisor.getsFName() + " " + advisor.getsLName();
								%>

								<option  value="<%= email%>"><%= Fullname%></option>
								<%
									}
								%>

							</select>
						</td>
					</tr>
					<tr>
						<td>
							<select name="timeslot" id="timeslot" required>
								<option  value="None">No Slots Available</option>
							</select>
						</td>
					</tr> 
					<tr>
						<td>
							<textarea name="notes" rows = "4" cols="20" required placeholder="Appointment Notes..." ></textarea>
						</td> 
					</tr>
				
					<tr>
						<td>
						</td>
					</tr>
					<tr>					
						<td>
							<button type="submit" class="btn">Schedule Appointment</button>
						</td>
					</tr>
				</table>
				<input type="hidden" name ="advisorName" id="advisorName"/>
			</form>
		</div><!-- #content -->
		<br/><br/>
	</div><!-- #wrapper -->	
    </body>
</html>