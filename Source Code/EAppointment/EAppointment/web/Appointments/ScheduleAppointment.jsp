<%-- 
    Document   : ScheduleAppointment
    Created on : Apr 6, 2016, 3:40:14 PM
    Author     : Ekta Khiani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- CSS -->
		<link href="../jquery/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/LoadingScreen/waitMe.min.css" rel="stylesheet" type="text/css"/>
		
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		<script src="../jquery/LoadingScreen/waitMe.min.js" type="text/javascript"></script>
		
		<script>
			var weekday = new Array(7);
			var dayOfWeek;
			weekday[0] = "Sunday";
			weekday[1] = "Monday";
			weekday[2] = "Tuesday";
			weekday[3] = "Wednesday";
			weekday[4] = "Thursday";
			weekday[5] = "Friday";
			weekday[6] = "Saturday";
			
			$("document").ready(function () 
			{
				$('#BookAppointment').hide();
				//$('#thankYouDiv').hide();
				$('#staffEmail').prop('disabled', 'disabled');
				$('#timeslot').prop('disabled', 'disabled');
				$("#date").datepicker({minDate: 1}); // Datepicker - Disable past days
				$("#date").change(function () 
				{
					$('#staffEmail').prop('disabled', false);
					$('#timeslot').prop('disabled', false);
					$("#staffEmail").val("None");
					$('>option', $("#timeslot")).remove();
					
					$("#timeslot").append($('<option/>').text("Please select a Staff Member"));
					if ($("#date").val() === "") {
						$('#staffEmail').prop('disabled', 'disabled');
						$('#timeslot').prop('disabled', 'disabled');
					}
					else {
						$("label[for='date']").html($("#date").val());
					}
				});
				$('#type').change(function()
				{
					//$('#topDiv').hide();
					var usertype = $('#type').val();
					if (usertype === "None") {
						$('#BookAppointment').hide();
					}
					else {
						$("label[for='myalue']").html(usertype);
						$("label[for='staffMember']").html(usertype);
						
						$('#BookAppointment').show();
						$("#date").datepicker({minDate: 1}); // Datepicker - Disable past days

						var actionVal="GetStaffMembers";
                                        	var student_Email = "<%=request.getParameter("email")%>";
						//Fill Up the ComboBox Of Staff
						$.ajax({
							type: 'GET',
							url: '../Appointments/ConfirmAppointment?callback=?',
						        data: {studentMail: student_Email, type: usertype, action:actionVal},
							success: function (opts) {
								//alert("Success:"+opts);
								$('>option',  $("#staffEmail")).remove(); // Clean old options first.

								if (jQuery.isEmptyObject(opts))
								{
									$("#staffEmail").append($('<option/>').text("No Members"));   //To Check
								} 
								else
								{
									$("#staffEmail").append($('<option/>').val("None").text("Select "+usertype+"..."));
									$.each(opts, function (key, value) 
									{
										 $("#staffEmail").append($('<option/>').val(key).text(value));
									});
								}
							}
						});//AJAX.
					}	
				});

				$("#staffEmail").change(function () {
					var nameOfStaffMember = $("#staffEmail option:selected").text();
					$("label[for='staffName']").html(nameOfStaffMember);
					$("#staffName").val(nameOfStaffMember);
					//alert("THe Name of Staff Member:---"+nameOfStaffMember);
					var usertype = $('#type').val();
					if ($("#staffEmail").val() !== "None") {
						fillCombo("timeslot", usertype, this);
					} else {
						$('>option', $("#timeslot")).remove();
						$("#timeslot").append($('<option/>').text("Please select a Staff Member"));   //To Check
					}
				});
			
				var form = $('#form1');
				form.submit(function () 
				{
					var staffMember = $("#staffEmail option:selected").text();
					$("label[for='time']").html($("#timeslot option:selected").text());			
					var staffType = $("#type option:selected").text();			
					
					if(staffType === " Academic Advisor ")
					{
						$("label[for='status']").html("Confirmed");
						$("label[for='msg']").html("");
					}
					else 
					{
						$("label[for='status']").html("Pending");
						var messageText = "An Email Notification will be send to you at "+$("#studentEmail").val()+", when your appointment is confirmed.";
						$("label[for='msg']").html(messageText);
					}
					run_waitMe('timer');
					$.ajax({
						type: 'post',
						url: '../Appointments/ConfirmAppointment',
						data: form.serialize(),
						success: function (data) {
							$('#appmntSection').waitMe('hide');
							$('#appmntSection').hide();
							$('#thankYouDiv').show();
						},
						error: function (jqXHR, exception, data) {
							console.log(jqXHR);

							if (jqXHR.status === 0) {
								alert("Not connect.\n Verify Network");
							} else if (jqXHR.status == 404) {
								alert("Requested page not found. [404]");
							} else if (jqXHR.status == 500) {
								alert("Internal Server Error [500]");
							} else if (exception === 'parsererror') {
								alert("Requested JSON parse failed");
							} else if (exception === 'timeout') {
								alert("Time out error");
							} else if (exception === 'abort') {
								alert("Ajax request aborted");
							} else {
								alert("Uncaught Error.\n") + jqXHR.responseText;
							}
						}, //ELSE

					});//ajax

					return false; //Very Important
				});//FormFunction

			});//ready
			
			function run_waitMe(effect){
				$('#appmntSection').waitMe({
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
			
			function fillCombo(timeSlotsId, usertype, callingElement)
			{
				var d = $(date).datepicker({dateFormat: 'mm/dd/yyyy'}).val();
				var dd = $(date).datepicker("getDate");
				dayOfWeek = weekday[dd.getUTCDay()];
				var timeSlot = $('#' + timeSlotsId);
				var actionVal="GetTimeSlots";
			
				$.ajax({
					type: 'GET',
					url: '../Appointments/ConfirmAppointment?callback',
					data: {day: dayOfWeek, email: $(callingElement).val(), type:usertype, date: d, action: actionVal},
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
					
					}
				});//AJAX
			}
		</script>
	</head>

	<body>
		<br/>
		<div id="appmntSection">
			<div id="topDiv" align="center">
				<form class="transbox">

					<select id="type">
						<option value="None">Appointment with... </option>
						<option value="Faculty"> Faculty </option>
						<option value="ISO"> ISO </option>
						<option value="Academic Advisor"> Academic Advisor </option>
					</select>
				</form>
				<br/>
			</div>

			<div id="BookAppointment" style="display:none"  align="center">
			<form action="../Appointments/ConfirmAppointment" method="post" id="form1"  class="transbox" style="width:700px;">
			<input type="hidden" value="<%=request.getParameter("email")%>" name="studentEmail" id ="studentEmail"/>
			<h3><b> Schedule an Appointment with <label for="myalue"></label></b></h3>
				<table id="appointmentTable">		
					<tr>
						<td>
							<input  type="text" name= "date"   id="date" required placeholder="Select Date"> 
						</td>
						<td rowspan="3">
							<textarea name="notes" rows = "4" cols="20" required placeholder="Appointment Notes..." ></textarea>
						</td>
					</tr>
					<tr>
						<td>
							<select name="staffEmail" id="staffEmail" required>
								<option  value="None">Select Staff</option>
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
						</td>
					</tr>
					<tr>					
						<td colspan="2" align="center">
							<button type="submit" class="btn" id="schApp">Schedule Appointment</button>
						</td>
					</tr>
				</table>
				<input type="hidden" name ="staffName" id="staffName"/>
			</form>
			</div>
		</div>
		<div id="thankYouDiv" border="1" style="display:none">
			<form class="transbox" style="width:1000px">
                <h3>Your Appointment scheduled on <label for="date"></label> at <label for="time"></label> is <label for="status"></label> with ITU <label for="staffMember"></label> - <label for="staffName"></label>. <label for="msg"></label></h3>		 
			</form>    
		</div>
		<br/>
	</body>
</html>
