<%-- 
    Document   : EditUsers
    Created on : Mar 16, 2016, 4:34:26 PM
    Author     : Ekta Khiani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html
	<html>
<head>

	<link href="../jquery/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<link href="../jquery/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css"/>

	<script src="../jquery/js/jquery-ui.js" type="text/javascript"></script>
	<script src="../jquery/multiselect/jquery.multiselect.js" type="text/javascript"></script>

	<script>
		$("document").ready(function () {
			$('#form2')[0].reset();
			
			//Fill up Courses in the menu on load
				
				$("#courseid").multiselect({
					noneSelectedText: "Select Course(s)..",
					selectedText: "# Course(s) selected"
				});
			


				$.ajax({
					type: 'GET',
					url: '../Admin/InsertToDb?callback=?',
					contentType: "application/json; charset=utf-8",
					success: function (opts) {
						$('>option', $('#courseid')).remove(); // Clean old options first.
						if (jQuery.isEmptyObject(opts))
						{
							$('#courseid').append($('<option/>').text("Course List Empty")); //To Check
						} else
						{
							$.each(opts, function (key, value) {
								$('#courseid').append($('<option/>').val(key).text(value));
							});
						}
						$("#courseid").multiselect('refresh');
					},
				}); //AJAX

			$('#search').click(function () {
				$('#userSection').show();
				$('#searchDiv').hide();
				var emailOfUser = $('#searchByEmail').val();
				$.ajax({
					type: 'GET',
					url: '../Admin/Edit?callback=?',
					data: {email: emailOfUser},
					contentType: "application/json; charset=utf-8",
					success: function (data) {
						$('#mobileid').val(data.sMobile);
						$('#itu').val(data.ITUid);
						$('#fname').val(data.sFName);
						$('#lname').val(data.sLName);
						$('#emailid').val(emailOfUser);
						$('#emailHid').val(emailOfUser);//Hidden Value
						$('#usertype').val(data.sUserType);
						$('#usertypeHid').val(data.sUserType);
						$('#usertype').prop('disabled', 'disabled');
						$('#emailid').prop('disabled', 'disabled');
						$('#courseid').val(data.courseList);
						$("#courseid").multiselect('refresh');
					}
				});
			});
			$('#del').click(function () {
				var emailOfUser = $('#emailHid').val();
				var typeVal = $('#usertypeHid').val();
				var actionVal = "Delete";

				$.ajax({
					type: 'POST',
					url: '../Admin/Edit?callback=?',
					data: {email: emailOfUser, type: typeVal, action: actionVal},
					success: function (data) {
						if (data === "SUCCESS")
						{
							$('#spanDeleted').html("<b>User Deleted Successfully.</b>")
							$('#form2')[0].reset();
							$('#userSection').hide();
							$('#searchDiv').show();

						} else
						{
							$('#spanDeleted').html("<b>User cannot be deleted.</b>")
						}

						$(function () {
							$("#dialog-deleted").dialog({
								modal: true,
								buttons: {
									Ok: function () {
										$(this).dialog("close");
									}
								}
							});
						});

					}


				});//ajax

			});//del
			var form = $('#form2');
			form.submit(function () {
				$.ajax({
					type: 'Post',
					url: '../Admin/Edit',
					data: form.serialize(),
					success: function (data) {

						if (data === "SUCCESS")
						{
							$('#spanUpdate').html("<b>Update Successful</b>")
						} else
						{
							$('#spanUpdate').html("<b>Update Failed</b>")
						}
						$(function () {
							$("#dialog-updated").dialog({
								modal: true,
								closeOnEscape: false,
								buttons: {
									Ok: function () {
										$(this).dialog("close");
									}
								}
							});
						});
					},
				}); //ajax

				return false; //Very Important

			});


		});//ready
	</script>

</head>

<title>Edit/Delete User</title>


<body>
	<div id="dialog-updated" title="Update" hidden="hidden">
		<p>
			<span id="spanUpdate"> </span>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>

		</p>
	</div>
	<div id="dialog-deleted" title="Delete" hidden="hidden">
		<p>
			<span id="spanDeleted"></span>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>

		</p>
	</div>
	<br>
	<form action="../Admin/Edit" method="post" id="form2"  name="form2" class="transbox">
		<div id="searchDiv" align="center">
			<h3><b>Search By Email</b></h3>
			<div>
				<input type="text" name="searchByEmail" id="searchByEmail" placeholder="Enter Email-id to search...."  required/>
			</div>
			<div>
				<button type="button" class="btnEdit" value="search" id="search">Search</button>
			</div>
		</div>
		<div id="userSection" style="display:none" align="center">
			<h3><b>User Detail</b></h3>
			<div>
				<input type="text" name="itu" placeholder="ITU Id" id="itu" />
			</div>
		
			<div>
				<input type="text"  id="fname" name="fname" placeholder="First Name"/>
			</div>
			<div>
				<input type="text" name="lname" id="lname" placeholder="Last Name" required/>
			</div>
			<div>
				<input type="email" name="emailid" placeholder="Email" required id="emailid" />
				<input type ="hidden" id="emailHid" name ="emailHid"/>
			</div>
			<div>
				<input type="text" name ="mobileid" placeholder="Mobile" id="mobileid" />
			</div>
			<div>
				<select name="usertype" id="usertype">
					<option value="None">Select Type...</option>
					<option value="Student">Student</option>
					<option value="Faculty">Faculty</option>
					<option value="ISO">ISO</option>
					<option value="Academic Advisor">Academic Advisor</option>
					<option value="Student Advisor">Student Advisor</option>
					<option value="Admin">Admin</option>
				</select>
				<input type ="hidden" id="usertypeHid" name ="usertypeHid"/>
			</div>
			<div id="courseDiv" >
				<select multiple name="courseid" id="courseid" >
					<option value="None">Select Course...</option>					
				</select>
			</div>
			<br/>
			<div>
				<table><tr>
						<td>
							<button type="submit" class="btnEdit" style="cursor:pointer;">Update User</button> 
						</td>
						<td>
							<button type="button" class="btnEdit" id="del" value="del" style="cursor:pointer;">Delete User</button>
						</td>
					</tr></table>
			</div>
		</div>
	</form>
	<br/><br/>
</body>
</html>
