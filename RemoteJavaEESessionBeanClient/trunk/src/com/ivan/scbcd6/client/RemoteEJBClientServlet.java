package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.StatefulSession1Remote;

/**
 * Servlet implementation class RemoteEJBClientServlet
 */
@WebServlet("/test.do")
public class RemoteEJBClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB(lookup = "java:global/RemoteSessionBean/StatefulSession1Bean")
	private StatefulSession1Remote mRemoteSessionBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theRequestNameParam = inRequest.getParameter("name");
		if (theRequestNameParam == null) {
			theRequestNameParam = "Anonymous";
		}
		String theResponse = mRemoteSessionBean.greeting(theRequestNameParam);
		theResponseWriter.println("Response from the EJB: " + theResponse);
		/* Process a list to examine parameter passing semantics. */
		List<String> theList = new ArrayList<String>();
		theList.add("string 1");
		theList.add("string 2");
		theList.add("last string");
		mRemoteSessionBean.processList(theList);
		/* Output list after EJB invocation. */
		String theListStrings = "";
		for (String theString : theList) {
			theListStrings += theString + ", ";
		}
		System.out.println("\nList after having invoked EJB processList: [" + theListStrings + "]");
	}

}
