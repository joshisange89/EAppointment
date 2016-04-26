
package classes.com.web.Appointments;

import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.EAppointment_Users.Prospect_Student;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.EmailNotifier.JavaEmailSender;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *This class creates an appointment of Prospect student to Student Advisor.
 * @author Ekta Khiani
 * Dated: 3/18/2016
 * 
 */
public class Book_Appointment_For_Prospect_Student extends HttpServlet
{

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		
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
			throws ServletException, IOException
	{
		processRequest(request, response);
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
		try 
		{
			//Create a prospect Student since we are storing the students who showed interest in the course.
			
			Prospect_Student prospect = new Prospect_Student();
		
			//Set its attributes.
			String sProspectFName = request.getParameter("fname");
			String sPropectLName = request.getParameter("lname");
			prospect.setsFName(sProspectFName);
			prospect.setsLName(sPropectLName);
			prospect.setsEmail(request.getParameter("email"));
			prospect.setsMobile(request.getParameter("mobile"));
		
			
			//Create Appointment 
			Appointment appointment = new Appointment();

			//Set its attributes
			String sEmail = request.getParameter("email");
			appointment.setsEmailOfAppointee(sEmail);
			
			String sStaffEmail = request.getParameter("Advisor"); 
			appointment.setsEmailOfAppointmentWith(sStaffEmail);
			
			String sDate = request.getParameter("date");
			appointment.setsDate(sDate);
			
			appointment.setsApptNotes(request.getParameter("notes"));
			
			String sTime = request.getParameter("timeslot");
			appointment.setsStartTime(sTime);
			appointment.setsApptStatus("Confirmed");
			

			//Populate the database with new Prospect students 
			InsertToDatabase.InsertProspectStudents(prospect);
			
			//& book its appointment with the student advisor
			if(Status.SUCCESS == prospect.bookAnAppointment(appointment))
			{
				//Send an Email Notification to the prospect student .
				String sAdvisorName = request.getParameter("advisorName");
				String sSubject ="Appointment Confirmation";
				String sContent = "Hello "+ sProspectFName+" "+sPropectLName +",<br><br>" +
					" Thank you for your interest in ITU.<br>"
					+ "Your Appointment is Confirmed with <b>"+sAdvisorName+ "</b> at <b>"+ 
					sDate +" " +sTime+"</b>.<br><br><br> Regards,<br>ITU Appointment Team";
				
				JavaEmailSender email = new JavaEmailSender();
				 email.createAndSendEmail(sEmail, sSubject, sContent);
				
				
				 //Send An Email Notification to the Student Advisor.
				sSubject ="New Appointment Scheduled";
				sContent = "Hello "+ sAdvisorName +",<br><br>" 
						+ "A new appointment has been scheduled with <b>"+sProspectFName+" "+ sPropectLName+ "</b> at <b>"+ 
					sDate +" " +sTime+"</b>."
					+"<br><br> Please login to E-Appointment System to see all your Scheduled Appointments."
					+ "<br><br><br> Regards,<br>ITU Appointment Team";
				
				 email.createAndSendEmail(sStaffEmail, sSubject, sContent);
			
				request.getRequestDispatcher("Thankyou.jsp").forward(request, response);
			
			}
			
			
		
	}
		catch (SQLException ex) {
			Logger.getLogger(Book_Appointment_For_Prospect_Student.class.getName()).log(Level.SEVERE, null, ex);

		} catch (ParseException ex) {
                Logger.getLogger(Book_Appointment_For_Prospect_Student.class.getName()).log(Level.SEVERE, null, ex);
            }

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

