package com.ivan.scbcd6;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Simplest possible stateful session bean exposing a local, no-interface view.
 */
@Stateful
@LocalBean
@StatefulTimeout(value = 10, unit = TimeUnit.SECONDS)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class StatefulSession1Bean {
	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatefulSession1Bean " + mInstanceNumber
				+ " created.");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("*** StatefulSession1Bean " + mInstanceNumber
				+ " destroyed.");
	}

	@Remove
	public void remove() {
		System.out.println("*** StatefulSession1Bean " + mInstanceNumber
				+ " remove.");
	}

	/**
	 * Creates a greeting to the person with the supplied name.
	 * 
	 * @param inName
	 *            Name of person to greet.
	 * @return Greeting.
	 */
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am stateful session bean "
				+ mInstanceNumber + ". The time is now: " + theCurrentTime;
		return theMessage;
	}
}
