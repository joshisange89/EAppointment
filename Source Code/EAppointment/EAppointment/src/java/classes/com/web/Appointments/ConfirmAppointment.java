package classes.com.web.Appointments;

import Constants.Constants;
import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.EAppointment_Users.Staff;
import classes.com.model.EAppointment_Users.StaffFactory;
import classes.com.model.EmailNotifier.JavaEmailSender;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ekta Khiani
 */
public class ConfirmAppointment extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
		//processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		//Set the appointment whose status is to be updated.
		String sStudentEmail = request.getParameter("email_student");
		String sStaffEmail = request.getParameter("email_staff");
		Appointment appointment = new Appointment();
		appointment.setsEmailOfAppointee(sStudentEmail);
		appointment.setsEmailOfAppointmentWith(sStaffEmail);
		appointment.setsDate(request.getParameter("dateOfAppointee"));
		
		String sTimeOfAppt = request.getParameter("timeOfAppt");
        if(sTimeOfAppt!=null)
        {
            String[] result = sTimeOfAppt.split("-");
            if(result != null && result.length > 0)
            {
                String sStartTime = result[0];
                appointment.setsStartTime(sStartTime);
            }
        }
		String actionToTake = request.getParameter("action");
		appointment.setsApptStatus(actionToTake);
		
		String sUserType = request.getParameter("type");
		StaffFactory factory = new StaffFactory();
		Staff staff = factory.getUserObject(sUserType);
		staff.setsEmail(sStaffEmail);

		//Update the Status of the Appointee
		Status status = staff.updateStatusOf(appointment);

		//After Successful update Send an notification to the student.
		if (status == Constants.Status.SUCCESS) {
			String sFullName = request.getParameter("name");
			
			String sSubjectOfStudent, sContentOfStudent;
			if(actionToTake!=null && actionToTake.equals("Rejected"))
			{
				String sReasonOfRejection = request.getParameter("rejectReason");
				
				sSubjectOfStudent="Your Appointment has been Cancelled";
				sContentOfStudent = "Hello " +sFullName+ ",<br><br>"
				+ "Your Appointment scheduled with <b>" + staff.getsFName() +" "+staff.getsLName() +"</b> at <b>"
				+ appointment.getsDate() + " " + appointment.getsStartTime() + "</b> has been cancelled.<br><br>"
				+"<b>Rejection Reason: </b>"+sReasonOfRejection+""
				+"<br><br><br> Regards,<br>ITU Appointment Team";
			}
			else
			{
				 sSubjectOfStudent= "Appointment Confirmed";
				sContentOfStudent = "Hello " +sFullName+ ",<br><br>"
				+ "Your Appointment has been Confirmed with <b>" + staff.getsFName() +" "+staff.getsLName() +"</b> at <b>"
				+ appointment.getsDate() + " " + appointment.getsStartTime() + "</b>.<br><br><br> Regards,<br>ITU Appointment Team";
				
			}
			JavaEmailSender email = new JavaEmailSender();
			//Send an Email to the Student.
			email.createAndSendEmail(sStudentEmail, sSubjectOfStudent, sContentOfStudent);
			
		}
		//Send it to the client in the json format.
		String json = new Gson().toJson(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
