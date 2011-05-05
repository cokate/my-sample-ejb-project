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

import com.ivan.scbcd6.StatefulSession1Bean;

/**
 * Servlet implementing a local EJB client.
 */
@WebServlet(name = "LocalEJBClientServlet", urlPatterns = "/test.do")
public class LocalEJBClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB
	private StatefulSession1Bean mLocalSessionBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theResponse = mLocalSessionBean.greeting("Anonymous");
		theResponseWriter.println("Response from the EJB: " + theResponse);
		/* Process a list to examine parameter passing semantics. */
		List<String> theList = new ArrayList<String>();
		theList.add("string 1");
		theList.add("string 2");
		theList.add("last string");
		mLocalSessionBean.processList(theList);
		/* Output list after EJB invocation. */
		String theListStrings = "";
		for (String theString : theList) {
			theListStrings += theString + ", ";
		}
		System.out.println("\nList after having invoked EJB processList: [" + theListStrings + "]");
	}

}
