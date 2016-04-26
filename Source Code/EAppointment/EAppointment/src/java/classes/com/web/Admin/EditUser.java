/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com.web.Admin;

import Constants.Constants.Status;
import classes.com.model.EAppointment_Users.Admin;
import classes.com.model.EAppointment_Users.Login_Access_User;
import classes.com.model.EAppointment_Users.User_Factory;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ekta Khiani
 */
public class EditUser extends HttpServlet {
	
	Admin admin;
	@Override
	public void init() throws ServletException {
		admin = new Admin();
		
	}

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
		
		
		String sEmail = request.getParameter("email");
		Login_Access_User user = admin.getUserDetails(sEmail);
		
		//Send it to the client in the json format.
		String json = new Gson().toJson(user);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		System.out.println("Json is"+json);
		
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
		
			Status status = Status.FAILURE;
			String sAction = request.getParameter("action");
			//Delete
			if(sAction!=null && sAction.equals("Delete"))
			{
				String sUserEmail = request.getParameter("email");
				String userType = request.getParameter("type");
				status = admin.DeleteUser(sUserEmail, userType);
				System.out.println("The email is "+sUserEmail+"-----"+userType);
			}
			else //Update user
			{	
				String sUserEmail = request.getParameter("emailHid");
				String userType = request.getParameter("usertypeHid");
				User_Factory userFactory = new User_Factory();
				Login_Access_User user = userFactory.getUserObject(userType);

				String sFName = request.getParameter("fname");
				String sLName = request.getParameter("lname");
				String sItuId = request.getParameter("itu");
				String[]courseArr = request.getParameterValues("courseid");
				String sMobile = request.getParameter("mobileid");
				System.out.println("The Fname is "+sFName+"-----"+sLName);
				
				user.setsMobile(sMobile);
				user.setsEmail(sUserEmail);
				user.setsFName(sFName);
				user.setsLName(sLName);
				user.setsUserType(userType);
				user.setITUid(sItuId);
				
				if(courseArr!=null && courseArr.length>0)
				{
					ArrayList<String> courseList = new ArrayList<>(Arrays.asList(courseArr));
					user.setCourseList(courseList);
				}
				status = admin.UpdateUser(user);
				
			}
				String json = new Gson().toJson(status	);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
				System.out.println("Json is"+json);
					
		
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
