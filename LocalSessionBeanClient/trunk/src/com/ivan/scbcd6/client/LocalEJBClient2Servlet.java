package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.StatefulSession1Bean;

/**
 * Servlet implementing a local EJB client.
 */
@WebServlet(name = "LocalEJBClient2Servlet", urlPatterns = "/test2.do")
public class LocalEJBClient2Servlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	private StatefulSession1Bean mLocalSessionBean;

	/**
	 * Initializes the servlet after creation.
	 */
	@PostConstruct
	public void intialize() {
		/*
		 * Use the JNDI API to look up a reference to the session bean the
		 * servlet is a client of. Any of the following JNDI names can be used:
		 * java:global/LocalSessionBeanClient/StatefulSession1Bean
		 * java:global/LocalSessionBeanClient/StatefulSession1Bean!
		 * com.ivan.scbcd6.StatefulSession1Bean
		 * java:app/LocalSessionBeanClient/StatefulSession1Bean
		 * java:app/LocalSessionBeanClient/StatefulSession1Bean!
		 * com.ivan.scbcd6.StatefulSession1Bean java:module/StatefulSession1Bean
		 * java:module/StatefulSession1Bean!com.ivan.scbcd6.StatefulSession1Bean
		 */
		try {
			InitialContext theInitialContext = new InitialContext();
			mLocalSessionBean = (StatefulSession1Bean) theInitialContext.lookup("java:module/StatefulSession1Bean");
		} catch (NamingException theException) {
			theException.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		System.out.println("EJB reference: " + mLocalSessionBean);
		System.out.println("EJB reference type: " + mLocalSessionBean.getClass());
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
