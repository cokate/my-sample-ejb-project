package com.ivan.scbcd6;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * This class implements a singleton session bean with container-managed
 * concurrency.
 */
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SingletonSessionBeanA {
	public void slowMethod() {
		System.out.println("SingletonSessionBeanA - Entering slowMethod");
		waitSomeTime(10);
		System.out.println("SingletonSessionBeanA - Exiting slowMethod");
	}

	public void fastMethod() {
		System.out.println("SingletonSessionBeanA - Entering fastMethod");
		waitSomeTime(1);
		System.out.println("SingletonSessionBeanA - Exiting fastMethod");
	}

	private void waitSomeTime(final long inSecondsDelay) {
		try {
			Thread.sleep(1000L * inSecondsDelay);
		} catch (InterruptedException e) {
			// Ignore exceptions.
		}
	}
}
