package com.ivan.scbcd6;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class StatefulSession1Bean
 */
@Stateful
public class StatefulSession1Bean implements StatefulSession1Remote {

	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatefulSession1Bean " + mInstanceNumber + " created.");
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

	/**
	 * Processes the supplied list. The purpose of this method is to illustrate
	 * the difference in parameter passing semantics between local and remote
	 * EJBs.
	 * 
	 * @param inList
	 *            List to process.
	 */
	public void processList(List<String> inList) {
		String theListStrings = "";
		for (String theString : inList) {
			theListStrings += theString + ", ";
		}
		System.out.println("\nStatefulSession1Bean.processList: " + "The list contains: [" + theListStrings + "]");
		/*
		 * Add string to list. If parameter passing is by reference, the client
		 * will also be able to see this string, but if parameter passing is by
		 * value, then this modification to the list is local only.
		 */
		inList.add("String added in EJB");
	}

}
