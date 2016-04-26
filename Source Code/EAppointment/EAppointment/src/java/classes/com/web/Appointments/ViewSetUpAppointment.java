/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com.web.Appointments;

import classes.com.model.DBTables.Schedule;
import classes.com.model.DBTables.SetUpWrapper;
import classes.com.model.EAppointment_Users.Staff;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ekta Khiani
 */
public class ViewSetUpAppointment extends HttpServlet {

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
		
		String sEmail = request.getParameter("email");
		ArrayList<SetUpWrapper> setUpList = Staff.getSetUpAppointments(sEmail);
			
	//Tokenize & send it to client to fill ComboBox Table of SetUp.
		ArrayList<Schedule> scheduleList = tokenSetUp(setUpList);
		
			
		//Send it to the client in the json format.
		String json1 = new Gson().toJson(setUpList);
		String json2 = new Gson().toJson(scheduleList);
		String sBothJson = "["+json1+","+json2+"]"; //Put both objects in an array of 2 elements
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(sBothJson);
	}
	
	public ArrayList<Schedule> tokenSetUp(ArrayList<SetUpWrapper> setup)
	{
		ArrayList<Schedule> scheduleList= new ArrayList<>();
		String sTimeSlots;
		for(SetUpWrapper eachWrap: setup)
		{
			//Tokenize the TimeSlot String.
			sTimeSlots = eachWrap.getsTimeSlots();
			String[]result = sTimeSlots.split(":|\\s");
			
			Schedule schedule = new Schedule();
			schedule.setsStartHour(result[0]);
			schedule.setsStartMinutes(result[1]);
			schedule.setsStartAmPm(result[2]);
			
			schedule.setsEndHour(result[4]);
			schedule.setsEndMinutes(result[5]);
			schedule.setsEndAmPm(result[6]);
			
			schedule.setsDay(eachWrap.getsDay());
			schedule.setsDuration(eachWrap.getsDuration());
			
			scheduleList.add(schedule);
		}
		return scheduleList;
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
		throws ServletException, IOException {
		processRequest(request, response);
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
