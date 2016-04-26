
package classes.com.web.Appointments;

import classes.com.model.Database.QueryDatabase;
import classes.com.model.EAppointment_Users.Staff;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class has the list of Student advisors & its available slots for the appointment of Prospect students.
 * @since 03/17/2016
 * @author Ekta Khiani
 * 
 */
public class GetAvailTimeSlotsOfAdvisor extends HttpServlet {

	HashMap<String, Staff> emailToAdvisorsObjectMap;
	
	
	//Initialize the list of advisors & its time slots.
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		/*super.init(config);
		try {
			emailToAdvisorsObjectMap = QueryDatabase.QueryDbToFetchStudentAdvisors();
			
		} catch (SQLException ex) {
			Logger.getLogger(GetAvailTimeSlotsOfAdvisor.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		
	}
		
	
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
		
		
		try {
			emailToAdvisorsObjectMap = QueryDatabase.QueryDbToFetchStaffAppointmentsSetUp("Student Advisor");
			
			request.setAttribute("AdvisorsList",  emailToAdvisorsObjectMap);
			
			
			//Fetch all the parameters from user to fix the appointment to the Student Advisor
			String email = request.getParameter("advisorEmail");
			String dayOfWeek = request.getParameter("day"); 
			String dateOfAppointment = request.getParameter("date");
		
			//If the user has selected an Advisor, then we have to send the list of his available timeslots.
			if(email!= null)
			{
				try {
					//Fetch the appointment setup (Parameters) of an advisor.
					List <String> timeSlotsList = (ArrayList) emailToAdvisorsObjectMap.get(email).getMapOfDayToTimeSlots().get(dayOfWeek);
					
					//Get the already set appointments of the advisors. 
					
					if(dateOfAppointment != null)
					{
						HashSet<String> appointmentSlotsSet = QueryDatabase.QueryDbToFetchAppointmentsOfStaffMember(email, dateOfAppointment);
						if(!appointmentSlotsSet.isEmpty())
						{
							Iterator<String> ite = timeSlotsList.iterator();
							while(ite.hasNext())
							{
								String eachTimeSlot = ite.next();
								if (appointmentSlotsSet.contains(eachTimeSlot))
									ite.remove();
							}
						}
					}
					//Send it to the client in the json format.
					String json = new Gson().toJson(timeSlotsList);
					
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				} //if
				catch (SQLException ex) {
					Logger.getLogger(GetAvailTimeSlotsOfAdvisor.class.getName()).log(Level.SEVERE, null, ex);
				}
				
			}
		} catch (SQLException ex) {
			Logger.getLogger(GetAvailTimeSlotsOfAdvisor.class.getName()).log(Level.SEVERE, null, ex);
		}
				
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
		
		doGet(request, response);
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
