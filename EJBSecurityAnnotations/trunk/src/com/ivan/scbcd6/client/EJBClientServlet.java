package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.ejbs.StatelessSession1Bean;

/**
 * Servlet implementation class EJBClientServlet
 */
@WebServlet(name = "EJBClientServlet", urlPatterns = "/test.do")
@ServletSecurity(httpMethodConstraints = { @HttpMethodConstraint(value = "GET", rolesAllowed = { "plainusers", "superusers" }) })
public class EJBClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB()
	private StatelessSession1Bean mSessionBean1;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		System.out.println("*** EJBClientServlet.doGet");
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theRequestNameParam = inRequest.getParameter("name");
		if (theRequestNameParam == null) {
			theRequestNameParam = "Anonymous";
		}
		String theResponse = mSessionBean1.greeting(theRequestNameParam);
		theResponseWriter.println("Response from the EJB:\n" + theResponse);
	}

}
