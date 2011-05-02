package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.StatefulSession4Bean;

/**
 * Servlet implementing a local EJB client.
 * 
 * @author Ivan A Krizsan
 */
@WebServlet(name = "StatefulSession1Servlet", urlPatterns = "/test.do")
public class StatefulSession1Servlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB
	private StatefulSession4Bean mStatefulSessionBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest,
			HttpServletResponse inResponse) throws ServletException,
			IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theRequestNameParam = inRequest.getParameter("name");
		if (theRequestNameParam == null) {
			theRequestNameParam = "Anonymous Coward";
		}
		String theResponse = mStatefulSessionBean.greeting(theRequestNameParam);
		theResponseWriter.println("Response from the EJB: " + theResponse);
	}
}