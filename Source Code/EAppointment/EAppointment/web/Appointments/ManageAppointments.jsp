<HTML>
	<head>
		<title>Manage Appointments </title>
		<!-- CSS -->
		<link href="../jquery/Datatables/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/LoadingScreen/waitMe.min.css" rel="stylesheet" type="text/css"/>
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/Datatables/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		<script src="../jquery/LoadingScreen/waitMe.min.js" type="text/javascript"></script>
		<script language="javascript">
			$(document).ready(function () {

			<%String emailUser = request.getParameter("email");%>
				var emailOfUser = "<%= emailUser%>";
				$.ajax({
					type: 'GET',
					url: '../Appointments/MySetUp?callback=?',
					data: {email: emailOfUser},
					success: function (data) {
						var data1 = data[0];
						var data2 = data[1];
						$("#setUp").show();
						$('#setupTable').dataTable({//Fill the SetUp Table
							bJQueryUI: true,
							bProcessing: true,
							bPaginate: false,
							bInfo: false,
							bFilter: false,
							'searching': false,
							data: data1,
							columns: [
								{'data': "sDay"},
								{'data': "sDuration"},
								{'data': "sTimeSlots"},
							]
						});

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

				});//ajax

				var form = $('#form1');
				form.submit(function () {
					run_waitMe('roundBounce');
					$.ajax({
						type: 'post',
						url: '../Appointments/SetUpAppointments',
						data: form.serialize(),
						success: function (data) {
							$('#combo').waitMe('hide');
							$('#combo').hide();  //Hide the Combo Table
							$("#setUp").show();
							$('#setupTable').dataTable({
								destroy: true,
								bJQueryUI: true,
								bProcessing: true,
								bPaginate: false,
								bInfo: false,
								bFilter: false,
								'sort': false,
								'searching': false,
								data: data,
								columns: [
									{'data': "sDay"},
									{'data': "sDuration"},
									{'data': "sTimeSlots"},
								]
							});//table
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

					});//ajax

					return false; //Very Important
				});//FormFunction
				
				function run_waitMe(effect) {
					$('#combo').waitMe({
						//none, rotateplane, stretch, orbit, roundBounce, win8, 
						//win8_linear, ios, facebook, rotation, timer, pulse, 
						//progressBar, bouncePulse or img
						effect: 'roundBounce',
						//place text under the effect (string).
						text: 'Creating Appointment SetUp',
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
						onClose: function () {}

					});
				}

			});//READY

			$("#addSetUp").click(function ()
			{
				$('#setUp').hide();
				$('#combo').show();

			}
			);
		
						function addRow(tableID) {

				var table = document.getElementById(tableID);

				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);

				var colCount = table.rows[1].cells.length;

				for (var i = 0; i < colCount; i++) {

					var newcell = row.insertCell(i);

					newcell.innerHTML = table.rows[1].cells[i].innerHTML;
					//alert(newcell.childNodes);
					switch (newcell.childNodes[1].type) {
						case "text":
							newcell.childNodes[0].value = "";
							break;
						case "checkbox":
							newcell.childNodes[0].checked = false;
							break;
						case "select-one":
							newcell.childNodes[0].selectedIndex = 0;
							break;
					}
				}
			}

			function deleteRow(tableID) {
				try {
					var table = document.getElementById(tableID);
					var rowCount = table.rows.length;

					for (var i = 0; i < rowCount; i++) {
						var row = table.rows[i];
						var chkbox = row.cells[0].childNodes[0];
						if (null !== chkbox && true === chkbox.checked) {
							if (rowCount <= 1) {
								break;
							}
							table.deleteRow(i);
							rowCount--;
							i--;
						}
					}
				} catch (e) {
					alert(e);
				}
			}
		</script>
	</head>
	<BODY>
		<div id="setUp" style="display:none" align="center">
			<h3 style="color:#00622F"><b> Appointment Settings </b></h3>
			<table  border="1"  id="setupTable">
				<thead>
					<tr>
						<th>Day</th>
						<th>Duration</th>
						<th>TimeSlots</th>

					</tr>
				</thead>
				<tbody>

				</tbody>

			</table>
			<br/>
			<button type="button" style="cursor:pointer;align:center;" class="btn" name="addSetUp" id="addSetUp">Create New Appointment Set Up</button>
		</div>
		<div id="combo" style="display:none" align="center">
			<br/>
			<form action="../Appointments/SetupAppointments" method="post" id="form1" style="width:1200px" class="transbox">
				<!-- <INPUT type="button" value="Delete Row" onclick="deleteRow('dataTable')" /> -->
				<input type="hidden" value="<%=request.getParameter("email")%>" name ="email"> 
				<h3><b> Set Up Appointments </b></h3>
				<TABLE id="comboTable" border="1" align="center">
					<THEAD>
						<TR>
							<TH>
								Day
							</TH>
							<TH>
								Start Time
							</TH>
							<TH>
								End Time
							</TH>
							<TH>
								Appt Duration
							</TH>
						</TR>
					</THEAD>
					<TR>
						<TD>
							<SELECT name="day" style="width:150px">
								<OPTION value="None">Select a Day </OPTION>
								<OPTION value="Monday">Monday</OPTION>
								<OPTION value="Tuesday">Tuesday</OPTION>
								<OPTION value="Wednesday">Wednesday</OPTION>
								<OPTION value="Thursday">Thursday</OPTION>
								<OPTION value="Friday">Friday</OPTION>
								<OPTION value="Saturday">Saturday</OPTION>
								<OPTION value="Sunday">Sunday</OPTION>
							</SELECT>
						</TD>
						<TD>
							<SELECT name="startHour" style="width:100px">
								<OPTION value="01">01</OPTION>
								<OPTION value="02">02</OPTION>
								<OPTION value="03">03</OPTION>
								<OPTION value="04">04</OPTION>
								<OPTION value="05">05</OPTION>
								<OPTION value="06">06</OPTION>
								<OPTION value="07">07</OPTION>
								<OPTION value="08">08</OPTION>
								<OPTION value="09">09</OPTION>
								<OPTION value="10">10</OPTION>
								<OPTION value="11">11</OPTION>
								<OPTION value="12">12</OPTION>	

							</SELECT> : 

							<SELECT name="startMinutes" style="width:100px">
								<OPTION value="00">00</OPTION>
								<OPTION value="15">15</OPTION>
								<OPTION value="30">30</OPTION>
								<OPTION value="45">45</OPTION>

							</SELECT> : 

							<SELECT name="startAmPm" style="width:120px">
								<OPTION value="AM">AM</OPTION>
								<OPTION value="PM">PM</OPTION>

							</SELECT>
						</TD>
						<TD>
							<SELECT name="endHour" style="width:100px">
								<OPTION value="01">01</OPTION>
								<OPTION value="02">02</OPTION>
								<OPTION value="03">03</OPTION>
								<OPTION value="04">04</OPTION>
								<OPTION value="05">05</OPTION>
								<OPTION value="06">06</OPTION>
								<OPTION value="07">07</OPTION>
								<OPTION value="08">08</OPTION>
								<OPTION value="09">09</OPTION>
								<OPTION value="10">10</OPTION>
								<OPTION value="11">11</OPTION>
								<OPTION value="12">12</OPTION>

							</SELECT> : 

							<SELECT name="endMinutes" style="width:100px">
								<OPTION value="00">00</OPTION>
								<OPTION value="15">15</OPTION>
								<OPTION value="30">30</OPTION>
								<OPTION value="45">45</OPTION>

							</SELECT> : 

							<SELECT name="endAmPm" style="width:120px">
								<OPTION value="AM">AM</OPTION>
								<OPTION value="PM">PM</OPTION>

							</SELECT>
						</TD>
						<TD>
							<SELECT name="apptDuration" style="width:100px">
								<OPTION value="15">15</OPTION>
								<OPTION value="30">30</OPTION>
								<OPTION value="60">60</OPTION>
							</SELECT>
						</TD>
					</TR>
				</TABLE>
				<br/>

				<button type="button" style="cursor:pointer;" onclick="addRow('comboTable')" class="btnEdit">Add Setup</button>&nbsp;&nbsp;
				<button type="submit" style="cursor:pointer;" class="btnEdit">Save Setup</button> 
			</form>
		</div>
		<br/>
	</BODY>
</HTML>