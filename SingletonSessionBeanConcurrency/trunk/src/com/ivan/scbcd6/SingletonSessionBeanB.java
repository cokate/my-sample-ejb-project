package com.ivan.scbcd6;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * This class implements a singleton session bean with bean-managed concurrency.
 */

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SingletonSessionBeanB {
	public synchronized void slowMethod() {
		System.out.println("SingletonSessionBeanB - Entering slowMethod");
		waitSomeTime(10);
		System.out.println("SingletonSessionBeanB - Exiting slowMethod");
	}

	public synchronized void fastMethod() {
		System.out.println("SingletonSessionBeanB - Entering fastMethod");
		waitSomeTime(1);
		System.out.println("SingletonSessionBeanB - Exiting fastMethod");
	}

	private void waitSomeTime(final long inSecondsDelay) {
		try {
			Thread.sleep(1000L * inSecondsDelay);
		} catch (InterruptedException e) {
			// Ignore exceptions.
		}
	}
}
