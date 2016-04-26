
package classes.com.web.Servlets.Login;

import classes.com.model.Profile.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ekta Khiani
 */
public class LoginAuthenticationServlet extends HttpServlet {

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
			out.println("<title>Servlet LoginAuthenticationServlet</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet LoginAuthenticationServlet at " + request.getContextPath() + "</h1>");
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
		//doPost(request, response);
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
		String sPassword = request.getParameter("password");
		String sUserType = request.getParameter("usertype");
		
			
		String success = Validation.validateCredentials(sEmail, sPassword, sUserType);
		
		if(!success.equals("UnKnown"))
		{
			request.setAttribute("email" , (String)sEmail);
			request.setAttribute("type", (String)sUserType);
		
			String sPageType = null;
			switch(sUserType)
			{
				case "Admin":
					sPageType ="Admin";
					break;
				case "Student Advisor":
            	case "Academic Advisor":    
					sPageType="SAdvisor";
					break;
				case "Student":
					sPageType="Student";
					break;
				case "Faculty":
				case	"ISO":
					sPageType = "Staff";
					break;
			}
			
			
			String sPage = "../LandingPagesAfterLogin/";
			sPage= sPage+"Login"+sPageType+".jsp";
			
			
			//request.getSession().setAttribute("user", success);
			request.setAttribute("user", success);
			request.getRequestDispatcher(sPage).forward(request, response);
		}
		else
		{
			String action ="failed";
			request.setAttribute("status", action);
			request.getRequestDispatcher("EAppointmentLogin.jsp").forward(request, response);
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
