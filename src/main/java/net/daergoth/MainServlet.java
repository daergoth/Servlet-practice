package net.daergoth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_NAME_CHANGE = 5;

    public MainServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("name") != null) {
			response.getWriter().append("Hello ").append(session.getAttribute("name").toString());
		} else {
			response.getWriter().append("Set a name!");
		}
	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("nameChangeNum") == null) {
			Integer maxNameChange;
			
			try {
				maxNameChange = Integer.parseInt(getServletContext().getInitParameter("nameChangeNum"));
			} catch (NumberFormatException ex) {
				System.err.println("nameChangeNum parameter is malformed! Defaulted to " + DEFAULT_NAME_CHANGE);
				maxNameChange = DEFAULT_NAME_CHANGE;
			}
			
			session.setAttribute("nameChangeNum", maxNameChange);
		}
		
		session.setAttribute("nameChangeNum", (Integer)session.getAttribute("nameChangeNum") - 1);
		
		if ((Integer)session.getAttribute("nameChangeNum") >= 0) {
			session.setAttribute("name", request.getParameter("name"));
			response.getWriter().append("Name set!");
		} else {
			response.getWriter().append("Too many name change requests!");
			response.setStatus(418);
		}
		
		response.getWriter().append("<br/>Hello ").append((String)session.getAttribute("name"));

	}

}
