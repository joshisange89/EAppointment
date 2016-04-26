/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com.web.Admin;

import Constants.Constants;
import classes.com.model.EAppointment_Users.Admin;
import classes.com.model.EAppointment_Users.Login_Access_User;
import classes.com.model.EAppointment_Users.User_Factory;
import classes.com.model.EmailNotifier.JavaEmailSender;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class AddUser extends HttpServlet {
	
	Admin admin;
	@Override
	public void init() throws ServletException {
		admin = new Admin();
		
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
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet InsertIntoUserDetailsServlet</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet InsertIntoUserDetailsServlet at " + request.getContextPath() + "</h1>");
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
			throws ServletException, IOException
	{
		//Load Courses onLoad of admin page.
		HashMap<String, String> idToCourseNameMap = admin.LoadCourses();
		
		//Send it to the client in the json format.
		String json = new Gson().toJson(idToCourseNameMap);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
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
			
			String userType = request.getParameter("type");
			
			User_Factory userFactory = new User_Factory();
			Login_Access_User user = userFactory.getUserObject(userType);
			String sFName = request.getParameter("fname");
			String sLName = request.getParameter("lname");
			user.setsFName(sFName);
			user.setsLName(sLName);
			String sUserEmail = request.getParameter("email");
			user.setsEmail(sUserEmail);
			user.setsMobile(request.getParameter("mobile"));
			user.setsUserType(userType);
	
			String sItuId = request.getParameter("ituId");
			user.setITUid(sItuId);
			String[]courseArr = request.getParameterValues("course");
			
			if(courseArr!=null && courseArr.length > 0)
			{
				ArrayList<String> courseList = new ArrayList<>(Arrays.asList(courseArr));
				user.setCourseList(courseList);
			}
			//Add the new user to the database & Send Email Notification to the newly created user.
			if(Constants.Status.SUCCESS == admin.addUsers(user))
			{
				
				String sSubject ="Your ITU E-Appointment Account has been created";
				String sContent = "Hello "+ sFName+" "+sLName +",<br><br>" +
					" Your E-Appointment Login has been created. Following are your credentials.<br>"
					+ "<b>UserName: </b>"+sUserEmail+"<br>"
					+ "<b>Temporary Password: </b>"+ user.getsPassword() +"<br>"
					+"<br>"
					+"<b>Note:</b> Please change your password once you login to E-Appointment System."
					+"<br><br><br> Regards,<br>ITU Appointment Team";
				
				JavaEmailSender email = new JavaEmailSender();
				//Sending an Email Notification.
				email.createAndSendEmail(sUserEmail, sSubject, sContent);
				
				ArrayList<Login_Access_User> arr = new ArrayList<>();
				arr.add(user);
				//Send it to the client in the json format.
				String json = new Gson().toJson(arr);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
	
			}
		}
		catch (SQLException ex) 
		{
			Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
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
