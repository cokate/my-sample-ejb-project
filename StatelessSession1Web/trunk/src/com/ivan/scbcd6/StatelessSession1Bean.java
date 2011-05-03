package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Simplest possible stateless session bean exposing a local, no-interface view.
 */
@Stateless
@LocalBean
public class StatelessSession1Bean {
	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatelessSession1Bean " + mInstanceNumber + " created.");
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
		String theMessage = "Hello " + inName + ", I am stateless session bean " + mInstanceNumber + ". The time is now: "
				+ theCurrentTime;
		return theMessage;
	}
}
