<%-- 
    Document   : Thankyou
    Created on : Mar 30, 2016, 10:57:02 AM
    Author     : Ekta Khiani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!-- CSS -->
        <link href="../css/ProspectThankYou.css" rel="stylesheet" type="text/css"/>
        <title>Appointment Confirmation</title>
    </head>
    <body class="background">
		<div id="content" align="center">
            <img class="logo" src="http://ituedu-141b.kxcdn.com/lp/1001/CREST2015_TransparentBG.png" style="width:250px;max-height:121px; padding:20px;" alt="ITU Logo" align="left">
            <br/><br/><br/><br/><br/><br/>
            <form class="transbox">
                <h3>Your Appointment is Confirmed with <%=request.getParameter("advisorName")%> on <%=request.getParameter("date")%> at <%=request.getParameter("timeslot")%>.<br>An Email Notification is sent to you at <%=request.getParameter("email")%> </h3>
            </form>
        </div><!-- #content -->
    </body>
</html>
