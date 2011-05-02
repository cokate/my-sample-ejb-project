package com.ivan.scbcd6;

import javax.ejb.Remote;

@Remote
public interface StatefulSession3Remote {
	/**
	 * Creates a greeting to the person with the supplied name.
	 * 
	 * @param inName
	 *            Name of person to greet.
	 * @return Greeting.
	 */
	public String greeting(final String inName);

}
