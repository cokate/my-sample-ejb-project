package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.StatelessSession4Bean;

/**
 * Servlet implementing a local EJB client.
 * 
 * @author Ivan A Krizsan
 */
@WebServlet(name = "StatelessSession1Servlet", urlPatterns = "/test.do")
public class StatelessSession1Servlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB
	private StatelessSession4Bean mStatelessSessionBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theRequestNameParam = inRequest.getParameter("name");
		if (theRequestNameParam == null) {
			theRequestNameParam = "Anonymous Coward";
		}
		String theResponse = mStatelessSessionBean.greeting(theRequestNameParam);
		theResponseWriter.println("Response from the EJB: " + theResponse);
	}
}
