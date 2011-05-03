package com.ivan.scbcd6.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivan.scbcd6.SingletonSessionBeanA;
import com.ivan.scbcd6.SingletonSessionBeanB;

/**
 * Servlet acting as a client of the two singleton session beans.
 */
@WebServlet(name = "SingletonClientServlet", urlPatterns = "/test.do")
public class SingletonClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	private final static String STORE_ACTION = "store";
	private final static String CLEAR_ACTION = "clear";
	/* Instance variable(s): */
	@EJB
	private SingletonSessionBeanA mSingletonBeanA;
	@EJB
	private SingletonSessionBeanB mSingletonBeanB;

	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		System.out.println("**** Entering SingletonClientServlet");
		String theRequestNameParam = inRequest.getParameter("name");
		String theRequestActionParam = inRequest.getParameter("action");
		/* Set default name if none provided. */
		if (theRequestNameParam == null || theRequestNameParam.equals("")) {
			theRequestNameParam = "Anonymous Coward";
		}
		/* Generate output from servlet using session beans. */
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theMessage;
		theMessage = mSingletonBeanA.retrieveMessage();
		theResponseWriter.println(theMessage);
		theMessage = mSingletonBeanB.retrieveMessage();
		theResponseWriter.println(theMessage);
		/*
		 * Store or clear data in one of the singleton session beans if the
		 * supplied action so specifies.
		 */
		if (theRequestActionParam != null) {
			if (STORE_ACTION.equals(theRequestActionParam)) {
				mSingletonBeanA.storeMessage(theRequestNameParam);
			}
			if (CLEAR_ACTION.equals(theRequestActionParam)) {
				mSingletonBeanA.storeMessage("[CLEARED]");
			}
		}
		System.out.println("**** Exiting SingletonClientServlet");
		theResponseWriter.println("Finished invoking singleton session beans!");
	}
}
