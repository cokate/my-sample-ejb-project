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
	/* Instance variable(s): */
	@EJB
	private SingletonSessionBeanA mSingletonBeanA;
	@EJB
	private SingletonSessionBeanB mSingletonBeanB;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		System.out.println("*** Entering SingletonClientServlet");
		/* Get parameter specifying which test to run. */
		String theSelectorString = inRequest.getParameter("selector");
		if (theSelectorString == null || theSelectorString.equals("")) {
			theSelectorString = "1";
		}
		int theSelector = Integer.parseInt(theSelectorString);
		switch (theSelector) {
		case 1:
			testContainerManagedConcurrency();
			break;
		case 2:
			testBeanManagedConcurrency();
			break;
		default:
			break;
		}
		System.out.println("*** Exiting SingletonClientServlet");
		/* Display a message on the web page. */
		PrintWriter theResponseWriter = inResponse.getWriter();
		theResponseWriter.println("Finished invoking singleton session bean " + "concurrency test " + theSelector);
	}

	private void testContainerManagedConcurrency() {
		System.out.println("*** Entering testContainerManagedConcurrency");
		/*
		 * Call first the slow method and then the fast method from separate
		 * threads.
		 */
		System.out.println(" Calling slowMethod...");
		new Thread() {
			@Override
			public void run() {
				mSingletonBeanA.slowMethod();
			}
		}.start();
		System.out.println(" Calling fastMethod...");
		new Thread() {
			@Override
			public void run() {
				mSingletonBeanA.fastMethod();
			}
		}.start();
		System.out.println("*** Exiting testContainerManagedConcurrency");
	}

	private void testBeanManagedConcurrency() {
		System.out.println("*** Entering testBeanManagedConcurrency");
		/*
		 * Call first the slow method and then the fast method from separate
		 * threads.
		 */
		System.out.println(" Calling slowMethod...");
		new Thread() {
			@Override
			public void run() {
				mSingletonBeanB.slowMethod();
			}
		}.start();
		System.out.println(" Calling fastMethod...");
		new Thread() {
			@Override
			public void run() {
				mSingletonBeanB.fastMethod();
			}
		}.start();
		System.out.println("*** Exiting testBeanManagedConcurrency");
	}
}
