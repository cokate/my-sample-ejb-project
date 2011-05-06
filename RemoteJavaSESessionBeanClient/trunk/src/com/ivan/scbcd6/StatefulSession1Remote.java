package com.ivan.scbcd6;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface StatefulSession1Remote {
	/**
	 * Creates a greeting to the person with the supplied name.
	 * 
	 * @param inName
	 *            Name of person to greet.
	 * @return Greeting.
	 */
	public String greeting(final String inName);

	/**
	 * Processes the supplied list. The purpose of this method is to illustrate
	 * the difference in parameter passing semantics between local and remote
	 * EJBs.
	 * 
	 * @param inList
	 *            List to process.
	 */
	public void processList(List<String> inList);

}
