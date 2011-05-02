package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;

/**
 * Simplest possible stateful session bean without using annotations.
 */
public class StatefulSession4Bean {
	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatefulSession4Bean " + mInstanceNumber + " created.");
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
		String theMessage = "Hello " + inName + ", I am stateful session bean " + mInstanceNumber + ". The time is now: "
				+ theCurrentTime;
		return theMessage;
	}
}
