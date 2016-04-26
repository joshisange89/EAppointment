<%-- 
    Document   : AddUsers
    Created on : Mar 16, 2016, 4:34:26 PM
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
		<link href="../jquery/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css"/>
		<link href="../jquery/multiselect/style.css" rel="stylesheet" type="text/css"/>
		<!-- Jquery -->
		<script src="../jquery/jquery-1.12.0.min.js" type="text/javascript"></script>
		<script src="../jquery/Datatables/js/jquery.dataTables.min.js" type="text/javascript"></script>
		<script src="../jquery/LoadingScreen/waitMe.min.js" type="text/javascript"></script>
		<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
		<script src="../jquery/multiselect/jquery.multiselect.js" type="text/javascript"></script>
		<script src="../jquery/validate/jquery.validate.js" type="text/javascript"></script>
		<script>
			$("document").ready(function () {

				$("#course").multiselect({
					noneSelectedText: "Select Course(s)..",
					selectedText: "# Course(s) selected"
				});

	//Fill up the Courses on load.
				$.ajax({
					type: 'GET',
					url: '../Admin/InsertToDb?callback=?',
					contentType: "application/json; charset=utf-8",
					success: function (opts) {
						$('>option', $('#course')).remove(); // Clean old options first.
						if (jQuery.isEmptyObject(opts))
						{
							$('#course').append($('<option/>').text("Course List Empty"));   //To Check
						} else
						{
							$.each(opts, function (key, value) {
								$('#course').append($('<option/>').val(key).text(value));
							});
						}
						$("#course").multiselect('refresh');
					}
				});//AJAX
				
				
				$('#type').change(function () {
					if ($('#type').val() === 'Student' || $('#type').val() === 'Academic Advisor' || $('#type').val() === 'Faculty') {
						$('#courseDiv').show();
					} else {
						$('#courseDiv').hide();
					}

				});


			});

			function addUser()
			{
				$('#tableDiv').hide();
				document.getElementById("form1").reset();
				$('#courseDiv').hide();
				$('#formDiv').show();

			}
		</script>			
	</head>

	<title>Create User</title>

	<body>
		<div id="formDiv" align="center">
			<br/>
			<form action="../Admin/InsertToDb" method="post" id="form1" name="form1" class="transbox">
				<h3><b>Create New User</b></h3>
				<div>
					<input type="text" id="ituId" name="ituId" required placeholder="ITU Id" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
				</div>
				<div>
					<input type="text" name="fname"  minlength="2" required placeholder="First Name"/>
				</div>
				<div>
					<input type="text" name="lname" minlength="2"  placeholder="Last Name" required/>
				</div>
				<div>
					<input type="email" name="email" placeholder="Email" required />
				</div>
				<div>
					<input type="text" name="mobile" maxlength="10" id="mobile" onkeypress='return event.charCode >= 48 && event.charCode <= 57' placeholder="Mobile"  pattern="\d{10}" title="Please enter exactly 10 digits" />
				</div>

				<div>
					<select name="type" id="type">
						<option value="None">Select Type...</option>
						<option value="Student">Student</option>
						<option value="Faculty">Faculty</option>
						<option value="ISO">ISO</option>
						<option value="Academic Advisor">Academic Advisor</option>
						<option value="Student Advisor">Student Advisor</option>
						<option value="Admin">Admin</option>
					</select>
				</div> 
				<div id="courseDiv" style="display:none;">
					<select multiple name="course" id="course">
						<option value="None">Select Course...</option>					
					</select> 
				</div>
				<br/>
				<div>
					<button type="submit" class="btn">Create User</button>
				</div>
			</form> 

			<style>
				.my-error-class {
					color:#FF0000;  /* red */
				}

			</style>

			<script>
				$("#form1").validate({
					wrapper: 'div',
					errorLabelContainer: "#messageBox",
					errorClass: "my-error-class",
					submitHandler: function (form) {
						submitform();
					}
				});

				function submitform() {
					var form = $('#form1');
					run_waitMe('roundBounce');
					$.ajax({
						type: 'post',
						url: '../Admin/InsertToDb?callback',
						data: form.serialize(),
						datatype: 'json',
						success: function (data) {
							$('#formDiv').waitMe('hide');
							$('#formDiv').hide();
							$('#tableDiv').show();
							$('#tableAdmin').dataTable({
								destroy: true,
								bJQueryUI: true,
								bProcessing: true,
								bPaginate: false,
								bInfo: false,
								bFilter: false,
								'searching': false,
								data: data,
								columns: [
									{'data': 'sFName'},
									{'data': 'sLName'},
									{'data': 'sEmail'},
									{'data': 'sUserType'},
								]

							});
						}
					});//ajax
					return false; //Very Important
				}

				function run_waitMe(effect) {
					$('#formDiv').waitMe({
						//none, rotateplane, stretch, orbit, roundBounce, win8, 
						//win8_linear, ios, facebook, rotation, timer, pulse, 
						//progressBar, bouncePulse or img
						effect: 'roundBounce',
						//place text under the effect (string).
						text: 'Creating New User...',
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

			</script>
		</div>

		<div id="tableDiv" style="display:none"> 
			<div class="messageDiv" align="center">
				<h4>New User Got Created Successfully.</h4>
			</div>
			<table id="tableAdmin" name="tableAdmin"  border="1" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email Id</th>
						<th>User Type</th>

					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>	
			<br/>
			<div align="center">
				<button id="myButton" class="btn" style="cursor:pointer;align:center"  onclick="addUser();" >Add New User</button>
			</div>	
		</div>
		<br/><br/>
	</body>
</html>
