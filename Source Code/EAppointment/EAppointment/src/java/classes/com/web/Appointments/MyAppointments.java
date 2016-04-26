/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com.web.Appointments;

import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.QueryDatabase;
import classes.com.model.EAppointment_Users.Non_Admin_Factory;
import classes.com.model.EAppointment_Users.Non_Admin_Uer;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
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
public class MyAppointments extends HttpServlet {

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
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet MyAppointments</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet MyAppointments at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
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
		
		String userEmail = request.getParameter("email");
		String sUserType = request.getParameter("type");
		Non_Admin_Factory factory = new Non_Admin_Factory();
		Non_Admin_Uer user = factory.getUserObject(sUserType);

		if(user != null)
		{
			
			try {
				ArrayList<Appointment> myAppointmentsList = user.MyAppointments(userEmail);
				
				//Send it to the client in the json format.
				String json = new Gson().toJson(myAppointmentsList);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				System.out.println("ArrayList"+json);
			}
			//else
			//request.getRequestDispatcher("ViewAppointments.jsp").forward(request, response);
			catch (ParseException ex) {
				Logger.getLogger(MyAppointments.class.getName()).log(Level.SEVERE, null, ex);
			}
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
