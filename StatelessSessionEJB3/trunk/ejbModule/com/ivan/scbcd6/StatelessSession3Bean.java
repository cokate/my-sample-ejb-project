package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class StatelessSession3Bean implements StatelessSession3Remote {

	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatelessSession3Bean " + mInstanceNumber + " created.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ivan.scbcd6.StatelessSession3Remote#greeting(java.lang.String)
	 */
	@Override
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am stateless session bean " + mInstanceNumber + ". The time is now: "
				+ theCurrentTime;
		return theMessage;
	}

}
