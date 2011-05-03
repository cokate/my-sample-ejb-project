package com.ivan.scbcd6;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class StatelessSession2Bean implements StatelessSession2Local {
	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatelessSession2Bean " + mInstanceNumber + " created.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ivan.scbcd6.StatelessSession2Local#greeting(java.lang.String)
	 */
	@Override
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am stateless session bean " + mInstanceNumber + ". The time is now: "
				+ theCurrentTime;
		return theMessage;
	}

}
