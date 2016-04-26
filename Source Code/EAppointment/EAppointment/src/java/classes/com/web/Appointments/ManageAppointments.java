package classes.com.web.Appointments;

import classes.com.model.DBTables.Schedule;
import classes.com.model.DBTables.SetUpWrapper;
import classes.com.model.DBTables.SetupAppointment;
import classes.com.model.EAppointment_Users.Staff;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This Servlet saves the setup of the appointments of a staff member.
 *@author  Ekta Khiani
 * @dated 03/27/2016
 * 
 */
public class ManageAppointments extends HttpServlet {

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
		throws ServletException, IOException {
		doPost(request,response);
	
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
		throws ServletException, IOException {
			String sEmail = request.getParameter("email");
		//Fetch The Setup Data from client.
		String sDay[] = request.getParameterValues("day");
	
		//Start Time 
		String sStartHour[] = request.getParameterValues("startHour");
		String sStartMinutes[] = request.getParameterValues("startMinutes");
		String sStartAmPm[] = request.getParameterValues("startAmPm");
		
		//End Time
		String sEndHour[] = request.getParameterValues("endHour");
		String sEndMinutes[] = request.getParameterValues("endMinutes");
		String sEndAmPm[] = request.getParameterValues("endAmPm");
		
		//Duration
		String sDuration[] = request.getParameterValues("apptDuration");
		
		//Initialize values of the setup of the appointments.
		SetupAppointment appointment = new SetupAppointment();
		appointment.setsEmail(sEmail);
		
		for (int nEntry = 0; nEntry < sDay.length; nEntry++)
		{
			Schedule schedule = new Schedule();
			schedule.setsDay(sDay[nEntry]);
			schedule.setsDuration(sDuration[nEntry]);
			
			schedule.setsStartHour(sStartHour[nEntry]);
			schedule.setsStartMinutes(sStartMinutes[nEntry]);
			schedule.setsStartAmPm(sStartAmPm[nEntry]);
			
			schedule.setsEndHour(sEndHour[nEntry]);
			schedule.setsEndMinutes(sEndMinutes[nEntry]);
			schedule.setsEndAmPm(sEndAmPm[nEntry]);
			appointment.setASchedule(schedule);
		}
		
		
		//Create Setup of the appointments for a staff member.
		Staff.setUpAppointments(appointment);	
		
		//Retrieves SetUp
		ArrayList<SetUpWrapper> setUpList = Staff.getSetUpAppointments(sEmail);
			
		//Send it to the client in the json format.
		String json = new Gson().toJson(setUpList);
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
