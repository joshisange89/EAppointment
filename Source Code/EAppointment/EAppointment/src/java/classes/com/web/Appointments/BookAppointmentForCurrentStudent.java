package classes.com.web.Appointments;

import Constants.Constants;
import classes.com.model.DBTables.Appointment;
import classes.com.model.EAppointment_Users.Current_Student;
import classes.com.model.EAppointment_Users.Staff;
import classes.com.model.EAppointment_Users.StaffFactory;
import classes.com.model.EmailNotifier.JavaEmailSender;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ekta Khiani
 */
public class BookAppointmentForCurrentStudent extends HttpServlet {
    
    private String sUserType;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
		String action= request.getParameter("action");
		sUserType = request.getParameter("type");
              
                StaffFactory factory = new StaffFactory();
		Staff member = factory.getUserObject(sUserType);
                
		
		String json = null;
		if(action.equals("GetTimeSlots"))
		{
			try {
				String sEmail = request.getParameter("email");
				String sDay = request.getParameter("day");
				String sDate = request.getParameter("date");
				ArrayList<String>timeSlotsList = member.getTimeSlotsOfAStaffMember(sEmail, sDay);
				if(sDate!=null)
				{
					HashSet<String> apptSet = member.getAppointmentsOfAStaff(sEmail, sDate);
					System.out.println("HashSet:"+apptSet);
					if(!apptSet.isEmpty())
					{
						Iterator<String> ite = timeSlotsList.iterator();
						while(ite.hasNext())
						{
							String eachTimeSlot = ite.next();
							if (apptSet.contains(eachTimeSlot))
								ite.remove();
						}
					}
				}
			json = new Gson().toJson(timeSlotsList);
			} catch (SQLException ex) {
				Logger.getLogger(BookAppointmentForCurrentStudent.class.getName()).log(Level.SEVERE, null, ex);
			}
			
	
		}
		else
		{
			String sStudentEmail = request.getParameter("studentMail");
			HashMap<String, String> emailToNameMap = member.getAllStaffNames(sStudentEmail);
			json = new Gson().toJson(emailToNameMap);
		}
		//Send it to the client in the json format.
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
		System.out.println("JSON "+json);
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
		
		try {
			String sStudentEmail = request.getParameter("studentEmail");
			Current_Student student = new Current_Student();
			student.setsEmail(sStudentEmail);
		
			//Create Appointment
			Appointment appointment = new Appointment();

			//Set its attributes
			
			appointment.setsEmailOfAppointee(sStudentEmail);
			
			String sStaffEmail = request.getParameter("staffEmail"); 
			System.out.println("Staff Email"+sStaffEmail);
			appointment.setsEmailOfAppointmentWith(sStaffEmail);
			
			String sDate = request.getParameter("date");
			appointment.setsDate(sDate);
			
			appointment.setsApptNotes(request.getParameter("notes"));
			
			String sTime = request.getParameter("timeslot");
			appointment.setsStartTime(sTime);
                        
            if(sUserType!=null && sUserType.equals("Academic Advisor"))
            {
                appointment.setsApptStatus("Confirmed");
            }

			//& book its appointment with the Staff Member
			if(Constants.Status.SUCCESS == student.bookAnAppointment(appointment))
			{
				//Send an Email Notification to the prospect student .
				String sMemberName = request.getParameter("staffName");
				String sSubject ="New Appointment Scheduled";
				String sContent = "Hello "+ student.getsFName()+" "+student.getsLName() +",<br><br>" +
					" Thank you for your interest in ITU.<br>"
					+ "Your Appointment is "+appointment.getsApptStatus()+" with <b>"+sMemberName+ "</b> at <b>"+ 
					sDate +" " +sTime+"</b>.<br><br><br> Regards,<br>ITU Appointment Team";
				
				JavaEmailSender email = new JavaEmailSender();
				email.createAndSendEmail(sStudentEmail, sSubject, sContent);
				
				
				//Send An Email Notification to the Staff.
				sSubject ="New Appointment Scheduled";
				sContent = "Hello "+ sMemberName +",<br><br>"
					+ "A new appointment has been scheduled with <b>"+student.getsFName()+" "+ student.getsLName()+ "</b> at <b>"+ 
					sDate +" " +sTime+"</b>."
					+"<br><br> Please login to E-Appointment System to see all your Scheduled Appointments."
					+ "<br><br><br> Regards,<br>ITU Appointment Team";
				
				email.createAndSendEmail(sStaffEmail, sSubject, sContent);
				
				request.getRequestDispatcher("Thankyou.jsp").forward(request, response);
			
			}
		} catch (SQLException ex) {
			Logger.getLogger(BookAppointmentForCurrentStudent.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ParseException ex) {
            Logger.getLogger(BookAppointmentForCurrentStudent.class.getName()).log(Level.SEVERE, null, ex);
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
